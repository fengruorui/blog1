package com.feng.blog.service;

import com.feng.blog.po.User;

/**
 * Created by Feng on 2020/3/7.
 */
public interface UserService {
    User checkUser(String name, String password);
}
