package com.xgg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xgg.domain.entity.Comment;
import org.apache.ibatis.annotations.Mapper;


/**
 * 评论表(SgComment)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-09 15:29:55
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}


