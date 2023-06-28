package com.ezraloan.automation.service;

import com.ezraloan.automation.Utils.APIUtils;
import com.ezraloan.automation.entity.Subscriber;
import com.ezraloan.automation.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Service
public class SubscriberService {
    @Autowired
    private SubscriberRepository subscriberRepository;
    public Optional<Subscriber> registerSubscriber(Subscriber subscriber) {
        subscriber.setCreatedBy(APIUtils.getLoggedInUser.get());
        return Optional.ofNullable(subscriberRepository.save(subscriber));
    }

    public CompletableFuture<Optional<List<Subscriber>>> getSubscribers(String msisdn) {
        CompletableFuture<Optional<List<Subscriber>>> future = new CompletableFuture<>();

        if (msisdn != null) {
            subscriberRepository.findByMsisdn(msisdn)
                    .thenApply(Optional::ofNullable)
                    .thenAccept(future::complete);
        } else {
            subscriberRepository.findAllSubscribers()
                    .thenApply(Optional::ofNullable)
                    .thenAccept(future::complete);
        }

        return future;
    }

    public Optional<Subscriber> deleteSubscriber(Long subscriberId) {
        Optional<Subscriber> subscriberToDelete = Optional.of(subscriberRepository
                .findById(subscriberId)
                .stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Subscriber with id: " + subscriberId +
                        " Does not exist")));

        subscriberToDelete.get().setIsArchived(true);
        subscriberToDelete.get().setArchivedBy(APIUtils.getLoggedInUser.get());
        subscriberToDelete.get().setActive(false);
        subscriberToDelete.get().setDateArchived(new Date());

        return subscriberToDelete;
    }

    @Transactional
    public ResponseEntity<Subscriber> updateSubscriber(Long subscriberId, String msisdn,
                                                                 String firstName,
                                                                 String lastName, String email) {
        Optional<Subscriber> subscriberToUpdate = Optional.ofNullable(subscriberRepository
                .findById(subscriberId)
                .stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Subscriber with id: " + subscriberId +
                        "Does not exist")));

        if (msisdn !=null) {
            subscriberToUpdate.get().setMsisdn(msisdn);
        } else if (firstName!=null) {
            subscriberToUpdate.get().setFirstName(msisdn);
        } else if (lastName!=null) {
            subscriberToUpdate.get().setLastName(lastName);

        } else if (email!=null) {

            subscriberToUpdate.get().setEmail(email);
        }

        subscriberToUpdate.get().setUpdatedBy(APIUtils.getLoggedInUser.get());
        subscriberToUpdate.get().setUpdatedAt(new Date());
        subscriberRepository.save(subscriberToUpdate.get());

        return new ResponseEntity<>(subscriberToUpdate.get(), HttpStatus.OK);
    }

}




