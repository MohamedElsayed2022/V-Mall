package com.api.controller;


import com.api.model.Category;
import com.api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class CategoryController {
    private final CategoryService categoryService;

    @RequestMapping(value = "categories")
    public List<Category> getCategories(@RequestParam int page ,  @RequestParam int limit) {
        return categoryService.getCategories(page , limit);
    }

    @PostMapping(value = "category" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public Category createCategory(Category category , @RequestParam MultipartFile image) {
        return categoryService.createCategory(category , image);
    }
}
