package com.xgg.mapper;


import com.xgg.domain.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * 文章表(Article)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-03 17:10:03
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article>{
}


