package com.api.controller;

import com.api.model.Contract;
import com.api.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class ContractController {
    private final ContractService contractService;

    @PostMapping("contract")
    public Contract createContract(@RequestBody Contract contract) {
        return contractService.createContract(contract);
    }
}
