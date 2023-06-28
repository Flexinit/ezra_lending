package com.ezraloan.automation.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.ezraloan.automation.entity.Subscriber;
import com.ezraloan.automation.service.SubscriberService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {SubscriberController.class})
@ExtendWith(SpringExtension.class)
class SubscriberControllerTest {
    @Autowired
    private SubscriberController subscriberController;

    @MockBean
    private SubscriberService subscriberService;

    /**
     * Method under test: {@link SubscriberController#deleteSubscriber(Long)}
     */
    @Test
    void testDeleteSubscriber() throws Exception {
        Subscriber subscriber = new Subscriber();
        subscriber.setActive(true);
        subscriber.setArchivedBy("Archived By");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        subscriber.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        subscriber.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        subscriber.setDateArchived(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        subscriber.setDateOfBirth("2020-03-01");
        subscriber.setEmail("jane.doe@example.org");
        subscriber.setFirstName("Jane");
        subscriber.setId(123L);
        subscriber.setIsArchived(true);
        subscriber.setLastName("Doe");
        subscriber.setMsisdn("Msisdn");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        subscriber.setUpdatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        subscriber.setUpdatedBy("2020-03-01");
        Optional<Subscriber> ofResult = Optional.of(subscriber);
        when(subscriberService.deleteSubscriber((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/subscriber/delete");
        MockHttpServletRequestBuilder requestBuilder = deleteResult.param("subscriberId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(subscriberController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"msisdn\":\"Msisdn\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"active"
                                        + "\":true,\"dateOfBirth\":\"2020-03-01\",\"createdAt\":0,\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\",\"updatedBy"
                                        + "\":\"2020-03-01\",\"updatedAt\":0,\"isArchived\":true,\"dateArchived\":0,\"archivedBy\":\"Archived By\"}"));
    }

    /**
     * Method under test: {@link SubscriberController#getSubscribers(String)}
     */
    @Test
    void testGetSubscribers() throws Exception {
        when(subscriberService.getSubscribers((String) any())).thenReturn(new CompletableFuture<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/subscriber/list");
        MockMvcBuilders.standaloneSetup(subscriberController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link SubscriberController#getSubscribers(String)}
     */
    @Test
    void testGetSubscribers2() throws Exception {
        when(subscriberService.getSubscribers((String) any())).thenReturn(new CompletableFuture<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/subscriber/list");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(subscriberController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link SubscriberController#registerSubscriber(Subscriber)}
     */
    @Test
    void testRegisterSubscriber() throws Exception {
        Subscriber subscriber = new Subscriber();
        subscriber.setActive(true);
        subscriber.setArchivedBy("Archived By");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        subscriber.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        subscriber.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        subscriber.setDateArchived(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        subscriber.setDateOfBirth("2020-03-01");
        subscriber.setEmail("jane.doe@example.org");
        subscriber.setFirstName("Jane");
        subscriber.setId(123L);
        subscriber.setIsArchived(true);
        subscriber.setLastName("Doe");
        subscriber.setMsisdn("Msisdn");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        subscriber.setUpdatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        subscriber.setUpdatedBy("2020-03-01");
        Optional<Subscriber> ofResult = Optional.of(subscriber);
        when(subscriberService.registerSubscriber((Subscriber) any())).thenReturn(ofResult);

        Subscriber subscriber1 = new Subscriber();
        subscriber1.setActive(true);
        subscriber1.setArchivedBy("Archived By");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        subscriber1.setCreatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        subscriber1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        subscriber1.setDateArchived(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        subscriber1.setDateOfBirth("2020-03-01");
        subscriber1.setEmail("jane.doe@example.org");
        subscriber1.setFirstName("Jane");
        subscriber1.setId(123L);
        subscriber1.setIsArchived(true);
        subscriber1.setLastName("Doe");
        subscriber1.setMsisdn("Msisdn");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        subscriber1.setUpdatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        subscriber1.setUpdatedBy("2020-03-01");
        String content = (new ObjectMapper()).writeValueAsString(subscriber1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/subscriber/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(subscriberController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"msisdn\":\"Msisdn\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"active"
                                        + "\":true,\"dateOfBirth\":\"2020-03-01\",\"createdAt\":0,\"createdBy\":\"Jan 1, 2020 8:00am GMT+0100\",\"updatedBy"
                                        + "\":\"2020-03-01\",\"updatedAt\":0,\"isArchived\":true,\"dateArchived\":0,\"archivedBy\":\"Archived By\"}"));
    }

    /**
     * Method under test: {@link SubscriberController#updateSubscriber(Long, String, String, String, String)}
     */
    @Test
    void testUpdateSubscriber() throws Exception {
        when(subscriberService.updateSubscriber((Long) any(), (String) any(), (String) any(), (String) any(),
                (String) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/api/v1/subscriber/update");
        MockHttpServletRequestBuilder requestBuilder = putResult.param("subscriberId", String.valueOf(1L));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(subscriberController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }
}

