package com.feng.blog.service;

import com.feng.blog.po.Comment;

import java.util.List;

/**
 * Created by Feng on 2020/3/16.
 */
public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);
    Comment saveComment(Comment comment);
}
