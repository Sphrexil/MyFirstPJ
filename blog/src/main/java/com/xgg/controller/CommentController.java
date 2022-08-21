package com.xgg.controller;

import com.xgg.constants.SystemConstants;
import com.xgg.domain.ResponseResult;
import com.xgg.domain.entity.Comment;
import com.xgg.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){


        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment, HttpServletRequest request){


        return commentService.addComment(comment, request);
    }
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return  commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }


}
