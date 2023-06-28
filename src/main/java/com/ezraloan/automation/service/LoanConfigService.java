package com.ezraloan.automation.service;

import com.ezraloan.automation.entity.MaxLoanAgeConfig;
import com.ezraloan.automation.repository.MaxLoanAgeConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class LoanConfigService{

       @Autowired
       private MaxLoanAgeConfigRepository maxLoanAgeConfigRepository;
    public Optional<MaxLoanAgeConfig> setMaxLoanAge(MaxLoanAgeConfig maxLoanAgeConfig) {
        return Optional.of(maxLoanAgeConfigRepository.save(maxLoanAgeConfig));
    }

    public Optional<MaxLoanAgeConfig> getMaxLoanAge() {
        return maxLoanAgeConfigRepository.findAll().stream().findFirst();
    }

    @Transactional
    public Optional<MaxLoanAgeConfig> updateMaxLoanAge(Long maxLoanAge) {
        Optional<MaxLoanAgeConfig> maxLoanAgeConfig = Optional.of(maxLoanAgeConfigRepository
                .findAll().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("No Loan Config exists. Please create one")));

        maxLoanAgeConfig.get().setMaxLoanAge(maxLoanAge);

        return Optional.of(maxLoanAgeConfigRepository.save(maxLoanAgeConfig.get()));
    }
}
