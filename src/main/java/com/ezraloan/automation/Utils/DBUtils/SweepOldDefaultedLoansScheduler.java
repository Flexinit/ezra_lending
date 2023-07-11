package com.ezraloan.automation.Utils.DBUtils;

import com.ezraloan.automation.entity.LendingRequest;
import com.ezraloan.automation.repository.LendingRequestRepository;
import com.ezraloan.automation.service.LoanConfigService;
import com.ezraloan.automation.service.LendingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public record SweepOldDefaultedLoansScheduler(LendingRequestService lendingRequestService,
                                              LoanConfigService configService,
                                              LendingRequestRepository lendingRequestRepository) {


    //@Scheduled(cron = "0 0 0 1 * ?") // Runs at midnight on the 1st day of every month
    @Scheduled(fixedRate = 5000)// Runs every 5 seconds
    public void clearOldDefaultedLoans() {

        // Filter Old Defaulted Loans

        Long maxLoanAge = configService.getMaxLoanAge().get().getMaxLoanAge();
        List<LendingRequest> lendingRequests = lendingRequestService.filterOldLoans(maxLoanAge);

        if (lendingRequests.size() != 0) {

            lendingRequests.stream().forEach(
                    req -> {
                        if (!req.getArchived()) {
                            req.setArchived(true);
                            req.setArchiveDate(new Date());
                            //req.setArchivedBy(authentication.getName());
                            lendingRequestRepository.save(req);
                        }
                    }
            );
        }
    }
}
