package com.ddgotxdy.service.impl;

import com.ddgotxdy.entity.Article;
import com.ddgotxdy.mapper.ArticleMapper;
import com.ddgotxdy.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ddgo
 * @since 2022-06-19
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
