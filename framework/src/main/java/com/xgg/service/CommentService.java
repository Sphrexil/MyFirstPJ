package com.xgg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xgg.domain.ResponseResult;
import com.xgg.domain.entity.Comment;


/**
 * 评论表(SgComment)表服务接口
 *
 * @author makejava
 * @since 2022-04-09 15:29:55
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}


