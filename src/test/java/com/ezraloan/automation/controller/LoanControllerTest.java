package com.ezraloan.automation.controller;

import com.ezraloan.automation.entity.LendingRequest;
import com.ezraloan.automation.entity.RepaymentRequest;
import com.ezraloan.automation.repository.LendingRequestRepository;
import com.ezraloan.automation.service.LendingRequestService;
import com.ezraloan.automation.service.RepaymentRequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Date;

@SpringBootTest
public class LoanControllerTest{


    @MockBean
    private LendingRequestService lendingRequestService;

    @MockBean
    private RepaymentRequestService repaymentRequestService;

    @MockBean
    private LendingRequestRepository lendingRequestRepository;

    @Test
    public void applyForLoan(){

        LendingRequest lendingRequest = new LendingRequest();
        lendingRequest.setAmountDue(10000);
        lendingRequest.setLoanProductId("1");
        lendingRequest.setSubscriberId(2L);
        lendingRequest.setDateOfRepayment(new Date());

        lendingRequestService.applyForLoan(lendingRequest);

        System.out.println("lendingRequest1 = " + lendingRequest);

    }

    @Test
    public void repayLoan(){
        RepaymentRequest repaymentRequest = new RepaymentRequest();
        repaymentRequest.setLendingRequestId(1L);
        repaymentRequest.setAmountPaid(200000);
        repaymentRequest.setSubscriberId(20L);

        repaymentRequestService.repayLoan(repaymentRequest);
        System.out.println("repaymentRequest = " + repaymentRequest);

    }

}
