package com.xgg.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.primitives.Longs;
import com.xgg.domain.entity.Article;
import com.xgg.mapper.ArticleMapper;
import com.xgg.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class updateToDB {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleMapper articleMapper;
    //设置好分隔两分钟更新到数据库一次
    @Scheduled(cron = "0 0/1 * * * ?" )
    public void update(){
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        for (Article article : articleList) {
            article.setViewCount(Longs.tryParse(redisCache.getCacheObject("articleId:"+article.getId())));
            articleMapper.updateById(article);
        }
    }
}
