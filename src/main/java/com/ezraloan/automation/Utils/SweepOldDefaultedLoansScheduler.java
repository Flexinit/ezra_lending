package com.ezraloan.automation.Utils;

import com.ezraloan.automation.entity.LendingRequest;
import com.ezraloan.automation.service.LoanConfigService;
import com.ezraloan.automation.service.LendingRequestService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public record SweepOldDefaultedLoansScheduler(LendingRequestService lendingRequestService, LoanConfigService configService) {


    @Scheduled(cron = "0 0 0 1 * ?") // Runs at midnight on the 1st day of every month
    public void clearOldDefaultedLoans() {
        // Filter Old Defaulted Loans
        Long maxLoanAge = configService.getMaxLoanAge().get().getMaxLoanAge();
        List<LendingRequest> lendingRequests = lendingRequestService.filterOldLoans(maxLoanAge);

        lendingRequests.stream().forEach(
                req->{
                    req.setArchived(true);
                    req.setArchiveDate(new Date());
                    req.setArchivedBy(APIUtils.getLoggedInUser.get());
                }
        );
    }
}
