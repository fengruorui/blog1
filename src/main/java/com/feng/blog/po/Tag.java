package com.feng.blog.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2020/3/6.
 */
@Entity
@Table(name = "t_tag")
public class Tag {
    /*字段*/
    @Id
    @GeneratedValue
    private Long id;//标签id
    @NotBlank(message = "不能为空")
    private String name;//标签名字
    /*表关系*/
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs=new ArrayList<>();//tag与blog是多对多关系
    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }
}
