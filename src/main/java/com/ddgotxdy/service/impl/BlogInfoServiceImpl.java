package com.ddgotxdy.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ddgotxdy.dto.BlogHomeInfoDTO;
import com.ddgotxdy.entity.Article;
import com.ddgotxdy.mapper.ArticleMapper;
import com.ddgotxdy.mapper.CategoryMapper;
import com.ddgotxdy.mapper.TagMapper;
import com.ddgotxdy.mapper.WebsiteConfigMapper;
import com.ddgotxdy.service.BlogInfoService;
import com.ddgotxdy.service.IPageService;
import com.ddgotxdy.util.IpUtil;
import com.ddgotxdy.util.RedisUtil;
import com.ddgotxdy.vo.PageVO;
import com.ddgotxdy.vo.WebsiteConfigVO;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.ddgotxdy.constant.CommonConst.*;
import static com.ddgotxdy.constant.RedisPrefixConst.*;
import static com.ddgotxdy.enums.TalkStatusEnum.PUBLIC;

/**
 * @author: ddgo
 * @description: 博客信息服务实现类
 */
@Service
public class BlogInfoServiceImpl implements BlogInfoService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private WebsiteConfigMapper websiteConfigMapper;
    @Resource
    private HttpServletRequest request;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IPageService pageService;

    @Override
    public BlogHomeInfoDTO getBlogHomeInfo() {
        // 查询文章数量
        Long articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getStatus, PUBLIC.getStatus())
                .eq(Article::getIsDelete, FALSE));
        // 查询分类数量
        Long categoryCount = categoryMapper.selectCount(null);
        // 查询标签数量
        Long tagCount = tagMapper.selectCount(null);
        // 查询访问量
        Object count = redisUtil.get(BLOG_VIEWS_COUNT);
        String viewsCount = Optional.ofNullable(count).orElse(0).toString();
        // 查询网站配置
        WebsiteConfigVO websiteConfig = getWebsiteConfig();
        // 查询页面图片
        List<PageVO> pageVOList = pageService.listPages();
        // 封装数据
        return BlogHomeInfoDTO.builder()
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .viewsCount(viewsCount)
                .websiteConfig(websiteConfig)
                .pageList(pageVOList)
                .build();
    }

    @Override
    public String getAbout() {
        Object value = redisUtil.get(ABOUT);
        return Objects.nonNull(value) ? value.toString() : "";
    }

    @Override
    public void report() {
        // 获取ip
        String ipAddress = IpUtil.getIpAddress(request);
        // 获取访问设备
        UserAgent userAgent = IpUtil.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        // 判断是否访问
        if (!redisUtil.sIsMember(UNIQUE_VISITOR, md5)) {
            // 统计游客地域分布
            String ipSource = IpUtil.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                ipSource = ipSource.substring(0, 2)
                        .replaceAll(PROVINCE, "")
                        .replaceAll(CITY, "");
                redisUtil.hIncr(VISITOR_AREA, ipSource, 1L);
            } else {
                redisUtil.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }
            // 访问量+1
            redisUtil.incr(BLOG_VIEWS_COUNT, 1);
            // 保存唯一标识
            redisUtil.sAdd(UNIQUE_VISITOR, md5);
        }
    }

    @Override
    public WebsiteConfigVO getWebsiteConfig() {
        WebsiteConfigVO websiteConfigVO;
        // 获取缓存数据
        Object websiteConfig = redisUtil.get(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigVO = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVO.class);
        } else {
            // 从数据库中加载
            String config = websiteConfigMapper.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigVO = JSON.parseObject(config, WebsiteConfigVO.class);
            redisUtil.set(WEBSITE_CONFIG, config);
        }
        return websiteConfigVO;
    }


}
