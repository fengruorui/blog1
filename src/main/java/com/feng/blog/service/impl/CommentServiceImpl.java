package com.feng.blog.service.impl;

import com.feng.blog.dao.CommentRepository;
import com.feng.blog.po.Comment;
import com.feng.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        Sort sort=new Sort(Sort.Direction.ASC,"createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
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

    /**
     * 获取每个根评论节点
     * @param comments
     * @return
     */
    public List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentView=new ArrayList<>();
        for (Comment comment : comments) {
            Comment c=new Comment();
            BeanUtils.copyProperties(comment, c);
            commentView.add(c);
        }
        combineChildren(commentView);
        return commentView;
    }

    /**
     *
     * @param commentView root根节点，blog不为空的对象集合
     */
    public void combineChildren(List<Comment> commentView){
        for (Comment comment : commentView) {
            List<Comment> replys1=comment.getReplyComment();
            for (Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys集合中
                recursively(reply1);
            }
            comment.setReplyComment(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }
    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     * @param comment   被迭代的对象
     */
    public void recursively(Comment comment){
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if(comment.getReplyComment().size()>0){
            List<Comment> replys=comment.getReplyComment();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComment().size()>0){
                    recursively(reply);
                }
            }
        }
    }


}
