package com.ezraloan.automation.controller;

import com.ezraloan.automation.entity.LoanProduct;
import com.ezraloan.automation.service.LoanProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/loanProducts")
public record LoanProductController(LoanProductService loanProductService) {

    @PostMapping
    public Optional<LoanProduct> createNewLoanProduct(@RequestBody LoanProduct loanProduct){
        return loanProductService.createNewLoanProduct(loanProduct);
    }

    @GetMapping
    public Optional<List<LoanProduct>>getLoanProducts(@RequestParam(required = false) Long loanProductId){
        return loanProductService.getLoanProducts(loanProductId);
    }

    @PutMapping
    public Optional<LoanProduct>updateLoanProduct(
            @RequestParam(required = false) Long loanProductId,
            @RequestParam(required = false) String maxLoanAge
            ){
        return loanProductService.updateLoanProduct(loanProductId, maxLoanAge);
    }
}
