package com.api.service;

import com.api.dto.ContractDTO;
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
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

  //  public List<ContractDTO> getAllContracts() {
    //    List<Contract> contracts = contractRepository.findAll();
    //    return contracts.stream().map(contract -> {
    //        ContractDTO dto = new ContractDTO();
    //        dto.setId(contract.getId());
     //       return dto;
     //   }).collect(Collectors.toList());
    //}
}
