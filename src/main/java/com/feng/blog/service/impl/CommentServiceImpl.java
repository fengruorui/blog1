package com.feng.blog.service.impl;

import com.feng.blog.dao.CommentRepository;
import com.feng.blog.po.Comment;
import com.feng.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Feng on 2020/3/16.
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort=new Sort(Sort.Direction.DESC,"createTime");
        return commentRepository.findByBlogId(blogId, sort);
    }

    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

}
