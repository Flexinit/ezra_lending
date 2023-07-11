package com.ezraloan.automation.service;

import com.ezraloan.automation.Utils.APIUtils;
import com.ezraloan.automation.entity.LendingRequest;
import com.ezraloan.automation.entity.Subscriber;
import com.ezraloan.automation.repository.LendingRequestRepository;
import com.ezraloan.automation.repository.SubscriberRepository;
import kotlin.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LendingRequestService{
        @Autowired
        private LendingRequestRepository lendingRequestRepository;

        @Autowired
        private SubscriberRepository subscriberRepository;

    public CompletableFuture<Optional<LendingRequest>> applyForLoan(LendingRequest lendingRequest) {

        //Check if Subscriber Exists
        CompletableFuture<Optional<Subscriber>> subscriberFuture = CompletableFuture.supplyAsync(() ->
                Optional.of(subscriberRepository.findById(lendingRequest.getSubscriberId())
                        .stream().findFirst()
                        .orElseThrow(() -> new IllegalStateException("Please register as a Subscriber before you can take a loan")))
        );

        //Declarative Function within a function
        Function<Optional<Subscriber>, Pair<String, String>> processSubscriber = subscriberOptional -> {
            Subscriber subscriber = subscriberOptional.orElseThrow(() -> new IllegalStateException("Subscriber not found"));
            String name = subscriber.getFirstName() + " " + subscriber.getLastName();
            String msisdn = subscriber.getMsisdn();
            return new Pair<>(name, msisdn);
        };

        //Execute get First Name and last Name
        CompletableFuture<Pair<String, String>> nameAndMsisdnFuture = subscriberFuture.thenApplyAsync(processSubscriber);

        BiConsumer<String, String> sendSMS = (name, msisdn) ->
                APIUtils.sendSMS(msisdn, name, lendingRequest.getAmountDue());

        CompletableFuture<Void> smsFuture = nameAndMsisdnFuture.thenAcceptAsync(pair -> sendSMS.accept(pair.getFirst(), pair.getSecond()));

        return smsFuture.thenApplyAsync(voidResult ->
                Optional.ofNullable(lendingRequestRepository.save(lendingRequest))
        );
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