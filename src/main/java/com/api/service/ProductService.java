package com.api.service;

import com.api.dto.CategoryDTO;
import com.api.dto.ProductDTO;
import com.api.model.Category;
import com.api.model.Product;
import com.api.model.Shop;
import com.api.repository.CategoryRepository;
import com.api.repository.ProductRepository;
import com.api.repository.ShopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;
    public List<ProductDTO> getProductsByShopId(Long shopId) {
        List<Product> products = productRepository.findByShopId(shopId);

        return products.stream().map(product -> {
            ProductDTO dto = new ProductDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setQuantity(product.getQuantity());
            dto.setSku(product.getSku());
            dto.setActive(product.getActive());
            dto.setImages(product.getImages());
  /// /////////////////////////////////////////////////////////////
            if (product.getCategory() != null) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(product.getCategory().getId());
                categoryDTO.setName(product.getCategory().getName());
                categoryDTO.setLogo(product.getCategory().getLogo());

                dto.setCategory(categoryDTO);
            }
            return dto;
        }).toList();
    }
//
    public ProductDTO createProduct(Long shopId , Long categoryId , Product product  , List<MultipartFile> imageFiles){
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile img : imageFiles) {
            String fileName = System.currentTimeMillis() + "_" + img.getOriginalFilename();
            Path path = Paths.get("uploads/products/" + fileName);
            try {
                Files.copy(img.getInputStream(), path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileNames.add(fileName);

        }
        product.setImages(fileNames);
        /// //////////////////////////////////////////
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found with id: " + shopId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        product.setShop(shop);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);

        /// /////////////////////////////////////////////////
        ProductDTO dto = new ProductDTO();
        dto.setId(savedProduct.getId());
        dto.setName(savedProduct.getName());
        dto.setDescription(savedProduct.getDescription());
        dto.setPrice(savedProduct.getPrice());
        dto.setQuantity(savedProduct.getQuantity());
        dto.setSku(savedProduct.getSku());
        dto.setActive(savedProduct.getActive());
        dto.setImages(savedProduct.getImages());

        if (product.getCategory() != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(product.getCategory().getId());
            categoryDTO.setName(product.getCategory().getName());
            categoryDTO.setLogo(product.getCategory().getLogo());

            dto.setCategory(categoryDTO);
        }

        return dto;

    }

}
