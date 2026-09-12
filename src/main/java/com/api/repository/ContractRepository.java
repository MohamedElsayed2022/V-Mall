package com.api.repository;

import com.api.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;


public interface ContractRepository extends JpaRepository<Contract, Long> {
    boolean existsByShopIdAndEndDateAfter(Long shopId, LocalDate today);
}
