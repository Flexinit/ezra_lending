package com.ezraloan.automation.repository;

import com.ezraloan.automation.entity.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface LoanProductRepository extends JpaRepository<LoanProduct, Long> {
    Optional<List<LoanProduct>> findByid(Long fromString);
}