package com.feng.blog.service.impl;

import com.feng.blog.NotFoundException;
import com.feng.blog.dao.BlogRepository;
import com.feng.blog.po.Blog;
import com.feng.blog.po.Type;
import com.feng.blog.service.BlogService;
import com.feng.blog.util.MarkdownUtils;
import com.feng.blog.util.MyBeanUtils;
import com.feng.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Feng on 2020/3/11.
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepository blogRepository;
    @Override
    @Transactional
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    @Transactional
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle() !=null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {

        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable= PageRequest.of(0, size,sort);
        return blogRepository.findTob(pageable);
    }

    @Override
    @Transactional
    public Blog saveBlog(Blog blog) {
        if (blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Override
    @Transactional
    public Blog updateBlog(Long id, Blog blog) {
        Blog one = blogRepository.getOne(id);
        if(one==null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, one, MyBeanUtils.getNullPropertyNames(blog));
        one.setUpdateTime(new Date());
        return blogRepository.save(one);
    }

    @Override
    @Transactional
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getOne(id);
        if(blog == null){
            throw new NotFoundException("该博客不存在");
        }
        Blog blog1=new Blog();
        BeanUtils.copyProperties(blog, blog1);
        String content = blog1.getContent();
        blog1.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return blog1;
    }
}
