package com.feng.blog.service.impl;

import com.feng.blog.dao.UserRepository;
import com.feng.blog.po.User;
import com.feng.blog.service.UserService;
import com.feng.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Feng on 2020/3/7.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional
    public User checkUser(String name, String password) {
        User user=userRepository.findByNameAndPassword(name,MD5Utils.code(password));
        return user;
    }
}
