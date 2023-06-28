package com.ezraloan.automation.Utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Supplier;

@Slf4j
public class APIUtils {
    public static Supplier<String> getLoggedInUser = () -> {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); //return name of the logged in user
    };

    public static ResponseEntity<String> sendSMS(String msisdn,String name, float amountDue, float remainingAmount){
        log.info("SMS sent to {} for loan repayment", msisdn);
        String body =  "Dear "+name+ " \n"+
                "You have successfully repaid your loan of "+ amountDue+
                ". Outstanding balance is "+remainingAmount+ ".";
        return new ResponseEntity<>("SMS sent Successfully", HttpStatus.OK);
    }

    public static ResponseEntity<String> sendSMS(String msisdn,String name, float amountDue){
        log.info("SMS sent to {} for loan repayment", msisdn);
        String body =  "Dear "+name+ " \n"+
                "You have successfully repaid your loan of "+ amountDue+
                ". Outstanding balance is "+amountDue+ ".";
        return new ResponseEntity<>("SMS sent Successfully", HttpStatus.OK);
    }

    public static Long getMonthsBetweenDates(LocalDate startDate, LocalDate endDate) {
        Period period = Period.between(startDate, endDate);
        return period.toTotalMonths();
    }
}
