package com.ddgotxdy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddgotxdy.dto.*;
import com.ddgotxdy.entity.Article;
import com.ddgotxdy.entity.ArticleTag;
import com.ddgotxdy.entity.Tag;
import com.ddgotxdy.exception.ServerException;
import com.ddgotxdy.mapper.ArticleMapper;
import com.ddgotxdy.mapper.ArticleTagMapper;
import com.ddgotxdy.mapper.CategoryMapper;
import com.ddgotxdy.mapper.TagMapper;
import com.ddgotxdy.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddgotxdy.service.ITagService;
import com.ddgotxdy.util.BeanCopyUtil;
import com.ddgotxdy.util.CommonUtil;
import com.ddgotxdy.util.PageUtil;
import com.ddgotxdy.util.RedisUtil;
import com.ddgotxdy.vo.ConditionVO;
import com.ddgotxdy.vo.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.ddgotxdy.constant.CommonConst.ARTICLE_SET;
import static com.ddgotxdy.constant.RedisPrefixConst.ARTICLE_LIKE_COUNT;
import static com.ddgotxdy.constant.RedisPrefixConst.ARTICLE_VIEWS_COUNT;
import static com.ddgotxdy.enums.TalkStatusEnum.PUBLIC;

/**
 * @author ddgo
 * @description: 文章服务实现类
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private HttpSession session;
    @Autowired
    private ITagService tagService;


    @Override
    public List<ArticleHomeDTO> listArticles() {
        // 获取分页的文章
        Page<Article> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        Page<Article> articlePage = articleMapper.selectPage(page, null);
        List<Article> articleList = articlePage.getRecords();
        // 转换为DTO对象
        List<ArticleHomeDTO> articleHomeDTOList = new ArrayList<>(16);
        articleList.forEach(article -> {
            ArticleHomeDTO articleHomeDTO = new ArticleHomeDTO();
            BeanUtils.copyProperties(article, articleHomeDTO);
            // 根据分类id查询分类名称
            articleHomeDTO.setCategoryName(categoryMapper.selectById(article.getCategoryId()).getCategoryName());
            // 设置所有的标签列表
            articleHomeDTO.setTagDTOList(tagService.getTagDTOListByArticleId(article.getId()));
            articleHomeDTOList.add(articleHomeDTO);
        });
        return articleHomeDTOList;
    }

    @Override
    public PageResult<ArchiveDTO> listArchives() {
        Page<Article> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        // 获取分页数据
        Page<Article> articlePage = articleMapper.selectPage(page, new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getCreateTime)
                // 按照发布时间降序
                .orderByDesc(Article::getCreateTime)
                // 必须是公开的数据
                .eq(Article::getStatus, PUBLIC.getStatus()));
        List<ArchiveDTO> archiveDTOList = BeanCopyUtil.copyList(articlePage.getRecords(), ArchiveDTO.class);
        return new PageResult<>(archiveDTOList, articlePage.getTotal());
    }

    @Override
    public ArticlePreviewListDTO listArticlesByCondition(ConditionVO condition) {
        return null;
    }

    @Override
    public ArticleDTO getArticleById(Integer articleId) {
        // 查询推荐文章 TODO
        // 查询最新文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(Article::getId, Article::getArticleTitle, Article::getArticleCover, Article::getCreateTime)
                .eq(Article::getStatus, PUBLIC.getStatus())
                .orderByDesc(Article::getId)
                .last("limit 5");
        List<Article> newestArticleList = articleMapper.selectList(queryWrapper);
        List<ArticleRecommendDTO> articleRecommendDTOList = BeanCopyUtil.copyList(newestArticleList, ArticleRecommendDTO.class);

        // 查询id对应文章
        Article article = articleMapper.selectById(articleId);

        if (Objects.isNull(article)) {
            throw new ServerException("文章不存在");
        }

        // 转换为DTO
        ArticleDTO articleDTO = new ArticleDTO();
        BeanUtils.copyProperties(article, articleDTO);

        // 根据分类id查询分类名称
        articleDTO.setCategoryName(
                categoryMapper.
                        selectById(article.getCategoryId())
                        .getCategoryName());
        // 设置所有的标签列表
        // 获取当前文章的所有标签id
        articleDTO.setTagDTOList(tagService.getTagDTOListByArticleId(article.getId()));

        // 更新文章浏览量
        updateArticleViewsCount(articleId);

        // 查询上一篇下一篇文章
        Article lastArticle = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getArticleCover)
                .eq(Article::getStatus, PUBLIC.getStatus())
                .lt(Article::getId, articleId)
                .orderByDesc(Article::getId)
                .last("limit 1"));

        Article nextArticle = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getArticleCover)
                .eq(Article::getStatus, PUBLIC.getStatus())
                .gt(Article::getId, articleId)
                .orderByAsc(Article::getId)
                .last("limit 1"));

        articleDTO.setLastArticle(BeanCopyUtil.copyObject(lastArticle, ArticlePaginationDTO.class));
        articleDTO.setNextArticle(BeanCopyUtil.copyObject(nextArticle, ArticlePaginationDTO.class));

        // 封装点赞量和浏览量
        Double score = redisUtil.zScore(ARTICLE_VIEWS_COUNT, articleId);
        articleDTO.setViewsCount(score.intValue());
        articleDTO.setLikeCount((Integer) redisUtil.hGet(ARTICLE_LIKE_COUNT, articleId.toString()));

        // 封装文章信息
        articleDTO.setRecommendArticleList(null);
        articleDTO.setNewestArticleList(articleRecommendDTOList);

        return articleDTO;
    }


    /**
     * 更新文章浏览量
     *
     * @param articleId 文章id
     */
    private void updateArticleViewsCount(Integer articleId) {
        // 判断是否第一次访问，增加浏览量
        Set<Integer> articleSet = CommonUtil.castSet(Optional.ofNullable(session.getAttribute(ARTICLE_SET)).orElseGet(HashSet::new), Integer.class);
        if (!articleSet.contains(articleId)) {
            articleSet.add(articleId);
            session.setAttribute(ARTICLE_SET, articleSet);
            // 浏览量+1
            redisUtil.zIncr(ARTICLE_VIEWS_COUNT, articleId, 1D);
        }
    }

}
