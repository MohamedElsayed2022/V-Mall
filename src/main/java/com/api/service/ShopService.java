package com.api.service;

import com.api.dto.CategoryDTO;
import com.api.dto.OwnerDTO;
import com.api.dto.ShopResponseDTO;
import com.api.model.Shop;
import com.api.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
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
    private ShopResponseDTO convertToDto(Shop shop) {

        ShopResponseDTO dto = new ShopResponseDTO();

        dto.setId(shop.getId());
        dto.setShopName(shop.getShopName());
        dto.setDescription(shop.getDescription());
        dto.setImages(shop.getImages());

        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setId(shop.getOwner().getId());
        ownerDTO.setFirstName(shop.getOwner().getFirstName());
        ownerDTO.setLastName(shop.getOwner().getLastName());
        ownerDTO.setEmail(shop.getOwner().getEmail());

        dto.setOwner(ownerDTO);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(shop.getCategory().getId());
        categoryDTO.setName(shop.getCategory().getName());
        categoryDTO.setLogo(shop.getCategory().getLogo());

        dto.setCategory(categoryDTO);

        return dto;
    }

    public List<ShopResponseDTO> getShops(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return shopRepository.findAll(pageable).getContent().stream()
                .map(this::convertToDto)
                .toList();
    }

    public ShopResponseDTO createShop(Shop shop , List<MultipartFile> imageFiles) {
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

        Shop savedShop = shopRepository.save(shop);

        ShopResponseDTO dto = new ShopResponseDTO();
        dto.setId(savedShop.getId());
        dto.setShopName(savedShop.getShopName());
        dto.setDescription(savedShop.getDescription());
        dto.setImages(savedShop.getImages());

        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setId(savedShop.getOwner().getId());
        ownerDTO.setFirstName(savedShop.getOwner().getFirstName());
        ownerDTO.setLastName(savedShop.getOwner().getLastName());
        ownerDTO.setEmail(savedShop.getOwner().getEmail());
        dto.setOwner(ownerDTO);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(savedShop.getCategory().getId());
        categoryDTO.setName(savedShop.getCategory().getName());
        categoryDTO.setLogo(savedShop.getCategory().getLogo());
        dto.setCategory(categoryDTO);

        return dto;

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
