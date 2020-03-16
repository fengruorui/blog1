package com.feng.blog.dao;

import com.feng.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Feng on 2020/3/8.
 */
public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);
    @Query("select t from Type t")
    List<Type> findTob(Pageable pageable);
}
