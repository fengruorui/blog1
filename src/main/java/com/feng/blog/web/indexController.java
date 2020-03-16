package com.feng.blog.web;

import com.feng.blog.service.BlogService;
import com.feng.blog.service.TagService;
import com.feng.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Feng on 2020/3/4.
 */
@Controller
public class indexController {
    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
    @GetMapping("/")
    public String index(Model model, @PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC)
                        Pageable pageable){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        return "index";
    }
    @PostMapping("/search")
    public String search(Model model, @PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC)
                         Pageable pageable,@RequestParam String query){
        model.addAttribute("page",blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query",query);
        return "search";
    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }
}
