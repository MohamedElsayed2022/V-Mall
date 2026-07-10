package com.api.dto;

import com.api.model.Shop;
import com.fasterxml.jackson.annotation.JsonInclude;
import enums.ContractStatus;
import enums.ContractType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ContractDTO {
    private Long id;
    private String contractNumber;
    private ContractStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private ContractType contractType;
    private BigDecimal amount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ShopMinDTO shop;

//    private ShopResponseDTO shop;


}
