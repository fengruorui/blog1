package com.feng.blog.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2020/3/6.
 */
@Entity
@Table(name = "t_type")
public class Type {
    /*字段*/
    @Id
    @GeneratedValue
    private Long id;//分类id
    @NotBlank(message = "不能为空")
    private String name;//分类名称
    /*表关系*/
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs=new ArrayList<Blog>();
    public Type() {
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
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }
}
