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

@Service
public class RepaymentRequestService{

    @Autowired
    private RepaymentRequestRepository repaymentRequestRepository;
     @Autowired
    private LendingRequestRepository lendingRequestRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;


    @Transactional
    public Optional<RepaymentRequest> repayLoan(RepaymentRequest repaymentRequest) {

        Optional<LendingRequest> request = Optional.of(lendingRequestRepository.
                findById(repaymentRequest.getLendingRequestId())
                .stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("The Lending Request with Id: " + repaymentRequest.getLendingRequestId() +
                        " Does Not Exist")));

        float amntDue = request.get().getAmountDue();
        float remainingAmount = amntDue - repaymentRequest.getAmountPaid();
        request.get().setAmountDue(remainingAmount);
        repaymentRequest.setAmountDue(remainingAmount);

        String loanApplicantFirstName = subscriberRepository.findById(request.get().getSubscriberId())
                .stream().findFirst()
                .get()
                .getFirstName();

        String loanApplicantLastName = subscriberRepository.findById(request.get().getSubscriberId())
                .stream().findFirst()
                .get()
                .getLastName();

        String msisdn = subscriberRepository.findById(request.get().getSubscriberId())
                .stream().findFirst()
                .get()
                .getMsisdn();

        String name = loanApplicantFirstName+ " "+loanApplicantLastName+ " \n";

        //SEND SMS ASYNCHRONOUSLY
       // APIUtils.sendSMS(msisdn, name,  amntDue, remainingAmount);
        lendingRequestRepository.save(request.get());

        return Optional.of(repaymentRequestRepository.save(repaymentRequest));
    }
}

