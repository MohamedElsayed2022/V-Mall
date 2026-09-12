package com.api.controller;

import com.api.dto.ContractDTO;
import com.api.model.Contract;
import com.api.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class ContractController {
    private final ContractService contractService;

    @PostMapping("contract")
    public ContractDTO createContract(@RequestBody Contract contract, @RequestParam Long shopId) {
        return contractService.createContract(contract, shopId);
    }
    @GetMapping("contracts")
    public List<ContractDTO> getALlContracts() {
        return contractService.getAllContracts();
    }
}
