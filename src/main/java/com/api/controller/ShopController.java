package com.api.controller;

import com.api.dto.ShopResponseDTO;
import com.api.model.Shop;
import com.api.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.awt.print.Pageable;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")

public class ShopController {
    private final ShopService shopService;

    @GetMapping(value = "shops")
    public List<ShopResponseDTO> getShops(@RequestParam int page , @RequestParam int limit) {
        return shopService.getShops(page , limit);
    }
    @PostMapping(value = "shop", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ShopResponseDTO createShop(@ModelAttribute Shop shop , @RequestParam("imageFiles") List<MultipartFile> imageFiles) {
        return shopService.createShop(shop, imageFiles);
    }

    @PutMapping(value = "shop/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Shop updateShop( @PathVariable long id ,@ModelAttribute Shop shop, @RequestParam("imageFiles") List<MultipartFile> imageFiles) {
        return shopService.updateShop(id, shop, imageFiles);
    }
    @DeleteMapping(value = "shop/{id}")
    public String deleteShop(@PathVariable long id ){
        return shopService.deleteShop(id);
    }

}
