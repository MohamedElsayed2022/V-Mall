package com.api.service;

import com.api.model.Category;
import com.api.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
    public Category createCategory(Category category , MultipartFile image) {
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path path = Paths.get("uploads/categories/" + fileName);

        try {
            Files.copy(image.getInputStream(), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        category.setLogo(fileName);
        return categoryRepository.save(category);
    }
}
