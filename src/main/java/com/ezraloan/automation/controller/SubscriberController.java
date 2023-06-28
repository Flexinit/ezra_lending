package com.ezraloan.automation.controller;

import com.ezraloan.automation.entity.Subscriber;
import com.ezraloan.automation.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("api/v1/subscriber")
public record SubscriberController(SubscriberService subscriberService) {

    @PostMapping("/create")
    public Optional<Subscriber> registerSubscriber(@RequestBody Subscriber subscriber){
        log.info("new Subscriber registrationRequest {}", subscriber);
       return  subscriberService.registerSubscriber(subscriber);
    }

    @GetMapping("/list")
    public CompletableFuture<Optional<List<Subscriber>>> getSubscribers(@RequestParam(required = false) String msisdn){
        log.info("Fetching list of subscribers");
        return  subscriberService.getSubscribers(msisdn);
    }

    @DeleteMapping("/delete")
    public Optional<Subscriber> deleteSubscriber(@RequestParam(required = true)Long subscriberId){
        log.warn("Delete Subscriber with Id {}", subscriberId);
        return subscriberService.deleteSubscriber(subscriberId);
    }

    @PutMapping("/update")
    public ResponseEntity<Subscriber> updateSubscriber(
            @RequestParam(required = true)Long subscriberId,
            @RequestParam(required = false)String msisdn,
            @RequestParam(required = false)String firstName,
            @RequestParam(required = false)String lastName,
            @RequestParam(required = false)String email
    ){
        log.info("Updating Subscriber: msisdn: {}, firstName: {}, lastName: {}, email: {}", msisdn, firstName, lastName, email);

        return subscriberService.updateSubscriber(subscriberId, msisdn, firstName, lastName, email);

    }
}
