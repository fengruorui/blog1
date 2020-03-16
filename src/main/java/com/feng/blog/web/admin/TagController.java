package com.feng.blog.web.admin;

import com.feng.blog.po.Tag;
import com.feng.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Created by Feng on 2020/3/10.
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute(new Tag());
        return "admin/tags-input";
    }
    @PostMapping("/tags")
    public String post(Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tagByName = tagService.getTagByName(tag.getName());
        if(tagByName !=null){
            result.rejectValue("name", "nameError","该标签已经存在");
        }
        if (result.hasErrors()) {
            return "admin/tags";
        }
        Tag tag1 = tagService.saveTag(tag);
        if(tag1 == null){
            attributes.addFlashAttribute("message", "添加失败");
        }else {
            attributes.addFlashAttribute("message", "添加成功");
        }
        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }
    @PostMapping("/tags/{id}")
    public String post(Tag tag,@PathVariable Long id, BindingResult result, RedirectAttributes attributes){
        Tag tagByName = tagService.getTagByName(tag.getName());
        if(tagByName !=null){
            result.rejectValue("name", "nameError","该标签已经存在");
        }
        if (result.hasErrors()) {
            return "admin/tags";
        }
        Tag tag1 = tagService.updateTag(id, tag);
        if(tag1 == null){
            attributes.addFlashAttribute("message", "更新失败");
        }else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
