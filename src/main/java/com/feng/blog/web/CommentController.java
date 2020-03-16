package com.feng.blog.web;

import com.feng.blog.po.Comment;
import com.feng.blog.service.BlogService;
import com.feng.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Feng on 2020/3/16.
 */
@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    BlogService blogService;
    @Value("${comment.avatar}")
    private String avatar;
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }
    @PostMapping("/comments")
    public String post(Comment  comment){
        Long blogId=comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        comment.setAvatar(avatar);
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
