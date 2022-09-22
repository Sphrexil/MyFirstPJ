package com.xgg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xgg.constants.SystemConstants;
import com.xgg.domain.ResponseResult;
import com.xgg.domain.entity.Comment;
import com.xgg.domain.vo.CommentVo;
import com.xgg.domain.vo.PageVo;
import com.xgg.enums.AppHttpCodeEnum;
import com.xgg.handler.exception.SystemException;
import com.xgg.mapper.CommentMapper;
import com.xgg.service.CommentService;
import com.xgg.service.UserService;
import com.xgg.utils.BeanCopyUtils;
import com.xgg.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 评论表(SgComment)表服务实现类
 *
 * @author makejava
 * @since 2022-04-09 15:29:55
 */
@Service("CommentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService user;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {


        //获取根评论


        //通过文章Id获取评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId);
        //根评论的rootId为-1
        //评论类型
        queryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_ROOT);
        queryWrapper.eq(Comment::getType, commentType);
        //分页查询
        Page<Comment> page = page(new Page<Comment>(pageNum, pageSize), queryWrapper);
        //用Vo进行封装

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //查询所有的子评论集合，并且赋值给对应的属性
        commentVoList
                .forEach(commentVo -> {

                    commentVo.setChildren(getChildrenList(commentVo.getId()));
                });


        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment, HttpServletRequest request) {
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.NULL_CONTENT);
        } else if(Objects.isNull(request.getHeader("token"))) {
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        }
        String token = request.getHeader("token");
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
            String userId = claims.getSubject();
            comment.setCreateBy(Long.valueOf(userId));
        } catch (Exception e) {
            e.printStackTrace();
        }

        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> getChildrenList(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        return toCommentVoList(list(queryWrapper));
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        commentVos.forEach(commentVo -> {
            commentVo.setUsername(user.getById(commentVo.getCreateBy()).getNickName());
            commentVo.setAvatar(user.getById(commentVo.getCreateBy()).getAvatar());
            if (commentVo.getToCommentId() != -1) {
                commentVo.setToCommentUserName(user.getById(commentVo.getToCommentUserId()).getNickName());
            }
        });
        return commentVos;
    }

}
