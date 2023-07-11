package com.ezraloan.automation.service;

import com.ezraloan.automation.Utils.APIUtils;
import com.ezraloan.automation.entity.LendingRequest;
import com.ezraloan.automation.entity.RepaymentRequest;
import com.ezraloan.automation.repository.LendingRequestRepository;
import com.ezraloan.automation.repository.RepaymentRequestRepository;
import com.ezraloan.automation.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Service
public class RepaymentRequestService{

    @Autowired
    private RepaymentRequestRepository repaymentRequestRepository;
     @Autowired
    private LendingRequestRepository lendingRequestRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;


    @Transactional
    public CompletableFuture<Optional<RepaymentRequest>> repayLoan(RepaymentRequest repaymentRequest) {

        //Get the Lending request from db if exists
        CompletableFuture<Optional<LendingRequest>> lendingRequestFuture = CompletableFuture.supplyAsync(() ->
                Optional.of(lendingRequestRepository.findById(repaymentRequest.getLendingRequestId())
                        .stream().findFirst()
                        .orElseThrow(() -> new IllegalStateException("The Lending Request with Id: " + repaymentRequest.getLendingRequestId() +
                                " Does Not Exist")))
        );

        CompletableFuture<Optional<RepaymentRequest>> resultFuture = lendingRequestFuture.thenApplyAsync(lendingRequestOptional -> {
            LendingRequest lendingRequest = lendingRequestOptional.orElseThrow(() -> new IllegalStateException("Lending Request not found"));

            float amntDue = lendingRequest.getAmountDue();
            float remainingAmount = (amntDue - repaymentRequest.getAmountPaid());
            lendingRequest.setAmountDue(remainingAmount);
            repaymentRequest.setAmountDue(remainingAmount);



            //Get First name
           Supplier<String> firstName  = () -> subscriberRepository.findById(lendingRequest.getSubscriberId())
                    .stream().findFirst()
                    .orElseThrow(() -> new IllegalStateException("Subscriber not found"))
                    .getFirstName();


           //Get Last Name
            Supplier<String>   loanApplicantLastName = ()-> subscriberRepository.findById(lendingRequest.getSubscriberId())
                    .stream().findFirst()
                    .orElseThrow(() -> new IllegalStateException("Subscriber not found"))
                    .getLastName();

            //Get MSISDN
            Supplier<String> msisdn = () -> subscriberRepository.findById(lendingRequest.getSubscriberId())
                    .stream().findFirst()
                    .orElseThrow(() -> new IllegalStateException("Subscriber not found"))
                    .getMsisdn()
                    ;

            String name = firstName.get() + " " + loanApplicantLastName.get() + " \n";

            //SEND SMS Using Completable Future
            CompletableFuture.runAsync(() -> APIUtils.sendSMS(msisdn.get(), name, amntDue, remainingAmount))
                    .thenRunAsync(() ->lendingRequestRepository.save(lendingRequest));

            return Optional.of(repaymentRequestRepository.save(repaymentRequest));
        });

        return resultFuture;
    }

}

