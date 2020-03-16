package com.feng.blog.service;

import com.feng.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Feng on 2020/3/10.
 */
public interface TagService {
    Tag saveTag(Tag tag);
    Tag getTag(Long id);
    Tag getTagByName(String name);
    Page<Tag> listTag(Pageable pageable);
    List<Tag> listTag();
    List<Tag> listTag(String ids);
    Tag updateTag(Long id,Tag tag);
    void deleteTag(Long id);

    List<Tag> listTagTop(Integer size);
}
