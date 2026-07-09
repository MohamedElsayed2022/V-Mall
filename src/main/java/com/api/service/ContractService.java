package com.api.service;

import com.api.dto.ContractDTO;
import com.api.dto.ShopMinDTO;
import com.api.model.Contract;
import com.api.repository.ContractRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;

    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
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

          if(contract.getShop() != null){
              ShopMinDTO shopMinDTO = new ShopMinDTO();
              shopMinDTO.setId(contract.getShop().getId());
              shopMinDTO.setShopName(contract.getShop().getShopName());
              shopMinDTO.setDescription(contract.getShop().getDescription());
              shopMinDTO.setImages(contract.getShop().getImages());
              shopMinDTO.setOwnerEmail(contract.getShop().getOwner().getEmail());
              dto.setShop(shopMinDTO);
          }
          return dto;
      }).collect(Collectors.toList());
  }

}
