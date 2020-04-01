package com.feng.blog.web;

import com.feng.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Feng on 2020/3/26.
 */
@Controller
public class ArchivesShowController {
    @Autowired
    private BlogService blogService;
    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archivesMap",blogService.archives());
        model.addAttribute("blogCount",blogService.findCount());
        return "archives";
    }
}
