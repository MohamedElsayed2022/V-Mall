package com.api.service;

import com.api.model.Shop;
import com.api.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
//import java.awt.print.Pageable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    public List<Shop> getShops(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);

        return shopRepository.findAll(pageable).getContent();
    }

    public Shop createShop(Shop shop , List<MultipartFile> imageFiles) {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile img : imageFiles) {
            String fileName = System.currentTimeMillis() + "_" + img.getOriginalFilename();
            Path path = Paths.get("uploads/shops/" + fileName);
            try {
                Files.copy(img.getInputStream(), path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileNames.add(fileName);

        }
        shop.setImages(fileNames);
        return shopRepository.save(shop);
    }

    public Shop updateShop( Long id ,  Shop newShop , List<MultipartFile> imageFiles) {
        Shop oldShop = shopRepository.findById(id).orElseThrow(()-> new RuntimeException(("Shop Not Found")));
        oldShop.setDescription(newShop.getDescription());
        oldShop.setImages(newShop.getImages());
        oldShop.setShopName(newShop.getShopName());
        if(imageFiles != null && imageFiles.isEmpty())  {
            List<String> fileNames = new ArrayList<>();
            for (MultipartFile img : imageFiles) {
                String fileName = System.currentTimeMillis() + "_" + img.getOriginalFilename();
                Path path = Paths.get("uploads/shops/" + fileName);
                try {
                    Files.copy(img.getInputStream(), path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileNames.add(fileName);

            }
            oldShop.setImages(fileNames);
        }
        return shopRepository.save(oldShop);

    }

    }
