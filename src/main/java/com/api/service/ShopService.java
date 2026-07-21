package com.api.service;

import com.api.dto.*;
import com.api.model.Contract;
import com.api.model.Review;
import com.api.model.Shop;
import com.api.repository.ReviewRepository;
import com.api.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
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
    private final ReviewRepository reviewRepository;

    private ShopResponseDTO convertToDto(Shop shop) {

        ShopResponseDTO dto = new ShopResponseDTO();

        dto.setId(shop.getId());
        dto.setShopName(shop.getShopName());
        dto.setDescription(shop.getDescription());
        dto.setImages(shop.getImages());
        dto.setCreatedDate(shop.getCreatedDate());
        dto.setLastModifiedDate(shop.getLastModifiedDate());


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

        if (shop.getReviews() != null && !shop.getReviews().isEmpty()) {

            List<ReviewDTO> reviewsList = shop.getReviews().stream()
                    .map(review -> {
                        ReviewDTO reviewDTO = new ReviewDTO();
                        reviewDTO.setId(review.getId());
                        reviewDTO.setRating(review.getRating());
                        reviewDTO.setComment(review.getComment());
                        return reviewDTO;
                    })
                    .toList();

            dto.setReviews(reviewsList);
        }




        if (shop.getContracts() != null && !shop.getContracts().isEmpty()) {

            Contract latestContract = shop.getContracts().get(shop.getContracts().size() - 1);

            ContractDTO contractDTO = new ContractDTO();
            contractDTO.setId(latestContract.getId());
            contractDTO.setContractNumber(latestContract.getContractNumber());
            contractDTO.setStatus(latestContract.getStatus());
            contractDTO.setStartDate(latestContract.getStartDate());
            contractDTO.setEndDate(latestContract.getEndDate());
            contractDTO.setAmount(latestContract.getAmount());
            contractDTO.setContractType(latestContract.getContractType());

            dto.setContract(contractDTO);
        }

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
//        int reviewsCount = (int) reviewRepository.countByShopId(savedShop.getId());
        ShopResponseDTO dto = new ShopResponseDTO();
        dto.setId(savedShop.getId());
        dto.setShopName(savedShop.getShopName());
        dto.setDescription(savedShop.getDescription());
        dto.setImages(savedShop.getImages());
       // dto.setTotalReviews(reviewsCount);
        dto.setArea(shop.getArea());
        dto.setRentPrice(shop.getRentPrice());

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

    public String deleteShop(Long id){
        shopRepository.deleteById(id);
        return "Shop Deleted Successfully with id: " + id;
    }

    }
