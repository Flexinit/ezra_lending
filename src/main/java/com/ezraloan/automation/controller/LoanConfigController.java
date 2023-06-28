package com.ezraloan.automation.controller;

import com.ezraloan.automation.entity.MaxLoanAgeConfig;
import com.ezraloan.automation.service.LoanConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/config")
public record LoanConfigController(LoanConfigService loanConfigService) {

    @PostMapping
    public Optional<MaxLoanAgeConfig> setMaxLoanAge(@RequestBody MaxLoanAgeConfig maxLoanAgeConfig){
        return loanConfigService.setMaxLoanAge(maxLoanAgeConfig);
    }

    @GetMapping
    public Optional<MaxLoanAgeConfig> getMaxLoanAge(){
        return loanConfigService.getMaxLoanAge();
    }

    @PutMapping
    public Optional<MaxLoanAgeConfig> updateMaxLoanAge(Long maxLoanAge){
        return loanConfigService.updateMaxLoanAge(maxLoanAge);
    }

}
