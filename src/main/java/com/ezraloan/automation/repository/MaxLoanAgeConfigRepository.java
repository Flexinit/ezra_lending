package com.ezraloan.automation.repository;

import com.ezraloan.automation.entity.MaxLoanAgeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface MaxLoanAgeConfigRepository extends JpaRepository<MaxLoanAgeConfig, Long> {
}