package com.api.service;

import com.api.dto.ContractDTO;
import com.api.dto.ShopRevDTO;
import com.api.model.Contract;
import com.api.model.Shop;
import com.api.repository.ContractRepository;
import com.api.repository.ShopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final ShopRepository shopRepository;

    public ContractDTO createContract(Contract contract  , Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found with id: " + shopId));
        contract.setShop(shop);
        Contract savedContract = contractRepository.save(contract);
        ContractDTO dto = new ContractDTO();
        dto.setId(savedContract.getId());
        dto.setContractNumber(savedContract.getContractNumber());
        dto.setAmount(savedContract.getAmount());
        dto.setContractType(savedContract.getContractType());
        dto.setStartDate(savedContract.getStartDate());
        dto.setEndDate(savedContract.getEndDate());
        dto.setStatus(savedContract.getStatus());
        if(savedContract.getShop() != null){
            ShopRevDTO shopRevDTO = new ShopRevDTO();
            shopRevDTO.setId(savedContract.getShop().getId());
            shopRevDTO.setShopName(savedContract.getShop().getShopName());
            dto.setShop(shopRevDTO);
        }
        return dto;
    }

  public List<ContractDTO> getAllContracts() {
      List<Contract> contracts = contractRepository.findAll();

      return contracts.stream().map(contract -> {
          ContractDTO dto = new ContractDTO();
          dto.setId(contract.getId());
          dto.setContractNumber(contract.getContractNumber());
          dto.setStatus(contract.getStatus());
          dto.setStartDate(contract.getStartDate());
          dto.setEndDate(contract.getEndDate());
          dto.setAmount(contract.getAmount());
          dto.setContractType(contract.getContractType());

          if(contract.getShop() != null){
              ShopRevDTO shopMinDTO = new ShopRevDTO();
              shopMinDTO.setId(contract.getShop().getId());
              shopMinDTO.setShopName(contract.getShop().getShopName());
              dto.setShop(shopMinDTO);
          }
          return dto;
      }).collect(Collectors.toList());
  }

}
