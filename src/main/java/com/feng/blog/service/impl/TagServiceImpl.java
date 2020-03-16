package com.feng.blog.service.impl;

import com.feng.blog.NotFoundException;
import com.feng.blog.dao.TagRepository;
import com.feng.blog.po.Tag;
import com.feng.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2020/3/10.
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    @Transactional
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids){
        List<Long> list=new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] idarray=ids.split(",");
            for (int i = 0; i < idarray.length; i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t1 = tagRepository.findById(id).orElse(null);
        if(t1 == null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag, t1);
        return tagRepository.save(t1);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable= PageRequest.of(0, size,sort);
        return tagRepository.findTop(pageable);
    }
}
