package com.feng.blog.dao;

import com.feng.blog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Feng on 2020/3/10.
 */
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String name);
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
