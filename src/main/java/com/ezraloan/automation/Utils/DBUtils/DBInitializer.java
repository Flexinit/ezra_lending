package com.ezraloan.automation.Utils.DBUtils;

import javax.annotation.PostConstruct;

import com.ezraloan.automation.entity.MaxLoanAgeConfig;
import com.ezraloan.automation.service.LoanConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DBInitializer {

    @Autowired
    private LoanConfigService loanConfigService;

    @PostConstruct
    public void init() {
        //Create default

        Optional<MaxLoanAgeConfig> loanAgeConfig = loanConfigService.getMaxLoanAge();

        if(!loanAgeConfig.isPresent()){
            MaxLoanAgeConfig config = new MaxLoanAgeConfig();
            config.setMaxLoanAge(6L);
            loanConfigService.setMaxLoanAge(config);
        }

        System.out.println("DB Set Up Done Successfully");
    }
}
