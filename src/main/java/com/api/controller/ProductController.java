package com.api.controller;

import com.api.dto.ProductDTO;
import com.api.model.Product;
import com.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class ProductController {
    private final ProductService productService;

    @RequestMapping("{shopId}/products")
    public List<ProductDTO> getProductsByShopId(@PathVariable Long shopId){
        return productService.getProductsByShopId(shopId);
    }
    @PostMapping(value = "{shopId}/product" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> createProduct( @PathVariable Long shopId , @RequestParam("categoryId") Long categoryId, @ModelAttribute Product product ,
                                  @RequestParam(value = "imageFiles") List<MultipartFile> imageFiles)
    {
        ProductDTO createdProduct = productService.createProduct(shopId , categoryId , product, imageFiles);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
}
