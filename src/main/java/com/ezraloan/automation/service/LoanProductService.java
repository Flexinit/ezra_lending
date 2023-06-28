package com.ezraloan.automation.service;

import com.ezraloan.automation.Utils.APIUtils;
import com.ezraloan.automation.entity.LoanProduct;
import com.ezraloan.automation.repository.LoanProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanProductService{
        @Autowired
       private  LoanProductRepository loanProductRepository;
    public Optional<LoanProduct> createNewLoanProduct(LoanProduct loanProduct) {
        loanProduct.setCreatedBy(APIUtils.getLoggedInUser.get());
        return Optional.of(loanProductRepository.save(loanProduct));
    }

    public Optional<List<LoanProduct>> getLoanProducts(Long loanProductId) {
        if(loanProductId!=null){
            return loanProductRepository.findByid(loanProductId);
        }
        return Optional.of(loanProductRepository.findAll());
    }

    @Transactional
    public Optional<LoanProduct> updateLoanProduct(Long loanProductId, String maxLoanAge) {
        Optional<LoanProduct> loanProductToUpdate = Optional.of(loanProductRepository
                .findById(loanProductId)
                .stream().findFirst()
                .orElseThrow(()-> new IllegalStateException("Loan Product with id: "+ loanProductId
                + " Does not Exist")));

        loanProductToUpdate.get().setMaximumLoanAgeInmonths(maxLoanAge);
        loanProductToUpdate.get().setUpdatedBy(APIUtils.getLoggedInUser.get());
        loanProductToUpdate.get().setUpdatedAt(new Date());

        return Optional.of(loanProductRepository.save(loanProductToUpdate.get()));
    }
}
