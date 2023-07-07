package com.ezraloan.automation.Utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Supplier;

import static com.ezraloan.automation.Utils.SMS.SendMessage.sendSMSToSubscriber;

@Slf4j
public class APIUtils {
    public static Supplier<String> getLoggedInUser = () -> {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); //return name of the logged in user
    };

    @Async
    public static ResponseEntity<String> sendSMS(String msisdn,String name, float amountDue, float remainingAmount){
        log.info("SMS sent to {} for loan repayment", msisdn);
        String body =  "Dear "+name+ " \n"+
                "You have successfully repaid your loan of KES:"+ amountDue+
                ". Outstanding balance is KES:"+remainingAmount+ ".";

        sendSMSToSubscriber.accept(msisdn,body);
        return new ResponseEntity<>("SMS sent Successfully", HttpStatus.OK);
    }

    @Async
    public static ResponseEntity<String> sendSMS(String msisdn,String name, float amountDue){
        log.info("SMS sent to {} for loan repayment", msisdn);
        String body =  "Dear "+name+ " \n"+
                "You have successfully applied for a loan of KES:"+ amountDue+
                ". Outstanding balance is KES:"+amountDue+ ".";

        sendSMSToSubscriber.accept(msisdn,body);
        return new ResponseEntity<>("SMS sent Successfully", HttpStatus.OK);
    }

    public static Long getMonthsBetweenDates(LocalDate startDate, LocalDate endDate) {
        Period period = Period.between(startDate, endDate);
        return period.toTotalMonths();
    }


}
