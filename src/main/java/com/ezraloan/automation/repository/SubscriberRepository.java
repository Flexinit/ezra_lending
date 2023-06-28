package com.ezraloan.automation.repository;

import com.ezraloan.automation.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    CompletableFuture<List<Subscriber>> findByMsisdn(String msisdn);

    @Query(value = "SELECT  *  FROM subscriber", nativeQuery = true)
    CompletableFuture<List<Subscriber>> findAllSubscribers();
}