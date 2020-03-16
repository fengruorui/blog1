package com.feng.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Feng on 2020/3/6.
 */
@Entity
@Table(name = "t_comment")
public class Comment {
    /*字段*/
    @Id
    @GeneratedValue
    private Long id;//评论id
    private String nickname;//评论昵称
    private String email;//评论邮箱
    private String content;//评论内容
    private String avatar;//评论头像
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//评论创建时间
    /*表关系*/
    @ManyToOne
    private Blog blog;//多对一，一个blog可以有多个comment
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComment=new ArrayList<>();//回复的评论，父评论下面的子评论，是一对多
    @ManyToOne
    private Comment parentComment;//评论中的父评论，是多对一

    public Comment() {
    }

    public List<Comment> getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(List<Comment> replyComment) {
        this.replyComment = replyComment;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", blog=" + blog +
                ", replyComment=" + replyComment +
                ", parentComment=" + parentComment +
                '}';
    }
}
