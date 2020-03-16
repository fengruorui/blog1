package com.feng.blog.dao;

import com.feng.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Feng on 2020/3/7.
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByNameAndPassword(String name,String password);
}
