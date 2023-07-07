package com.ezraloan.automation.service;

import com.ezraloan.automation.Utils.APIUtils;
import com.ezraloan.automation.entity.LendingRequest;
import com.ezraloan.automation.entity.Subscriber;
import com.ezraloan.automation.repository.LendingRequestRepository;
import com.ezraloan.automation.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LendingRequestService{
        @Autowired
        private LendingRequestRepository lendingRequestRepository;

        @Autowired
        private SubscriberRepository subscriberRepository;

    public Optional<LendingRequest> applyForLoan(LendingRequest lendingRequest) {
        Optional<Subscriber> subscriber = Optional.of(subscriberRepository.findById(lendingRequest.getSubscriberId())
                .stream().findFirst()
                .orElseThrow(()-> new IllegalStateException("Please register as a Subscriber before you can take a loan")));
        String name = subscriber.get().getFirstName() + " " +subscriber.get().getLastName();
        String msisdn = subscriber.get().getMsisdn();

        lendingRequest.setCreatedBy(APIUtils.getLoggedInUser.get());

        //Send SMS
        //APIUtils.sendSMS(msisdn,name,lendingRequest.getAmountDue());

        return Optional.ofNullable(lendingRequestRepository.save(lendingRequest));
    }

    public Optional<List<LendingRequest>> getLendingRequests(Long lendingRequestId) {
        if (lendingRequestId != null) {
            return lendingRequestRepository.findByid(lendingRequestId);
        }
        return Optional.of(lendingRequestRepository.findAll());
    }

    @Transactional
    public Optional<LendingRequest> updateLendingRequest(Long lendingRequestId, float amount) {
        Optional<LendingRequest> lendingRequestToUpdate = Optional.of(lendingRequestRepository
                .findById(lendingRequestId)
                .stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Lending Request with id: " + lendingRequestId +
                        "Does not exist")));

        lendingRequestToUpdate.get().setAmountDue(amount);
        lendingRequestToUpdate.get().setUpdatedBy(APIUtils.getLoggedInUser.get());
        lendingRequestToUpdate.get().setUpdatedAt(new Date());

        lendingRequestRepository.save(lendingRequestToUpdate.get());
        return lendingRequestToUpdate;

    }

    public List<LendingRequest> filterOldLoans(Long maxLoanAge) {
        List<LendingRequest> oldLoans = lendingRequestRepository.findAll();

        if(oldLoans.size() != 0) {
            return oldLoans.stream()
                    .filter(loan -> (APIUtils.getMonthsBetweenDates(loan.getCreatedAt(), LocalDate.now()) > maxLoanAge))// Replace with your actual filtering condition
                    .collect(Collectors.toList());
        }
        return null;
    }
}