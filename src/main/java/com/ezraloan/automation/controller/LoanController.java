package com.ezraloan.automation.controller;

import com.ezraloan.automation.entity.LendingRequest;
import com.ezraloan.automation.entity.RepaymentRequest;
import com.ezraloan.automation.service.LendingRequestService;
import com.ezraloan.automation.service.RepaymentRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("api/v1/lending")
public record LoanController(LendingRequestService lendingRequestService, RepaymentRequestService repaymentRequestService) {

    @PostMapping
    public CompletableFuture<Optional<LendingRequest>> applyForLoan(@RequestBody LendingRequest lendingRequest) {
        log.info("making lending request {}", lendingRequest);
        return lendingRequestService.applyForLoan(lendingRequest);
    }

    @GetMapping
    public Optional<List<LendingRequest>> getLendingRequests(@RequestParam(required = false) Long lendingRequestId) {
        log.info("Fetching lending requests ");
        return lendingRequestService.getLendingRequests(lendingRequestId);
    }

    @PutMapping
    public Optional<LendingRequest> updateLendingRequest(
            @RequestParam(required = false) Long lendingRequestId,
            @RequestParam float amount
    ) {
        log.info("updating lending request id {}, amount: {} ", lendingRequestId, amount);
        return lendingRequestService.updateLendingRequest(lendingRequestId, amount);
    }

    //Loan Repayment
    @PostMapping("/repay_loan")
    public CompletableFuture<Optional<RepaymentRequest>> repayLoan(@RequestBody RepaymentRequest repaymentRequest){
        log.info("making repayment request {}", repaymentRequest);
        return repaymentRequestService.repayLoan(repaymentRequest);
    }
}

