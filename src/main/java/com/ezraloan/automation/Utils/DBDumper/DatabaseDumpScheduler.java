package com.ezraloan.automation.Utils.DBDumper;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public record DatabaseDumpScheduler(DatabaseDumpService databaseDumpService) {

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000) // Executes every 24 hours
    public void scheduleDatabaseDump() {
        databaseDumpService.generateAndTransferDatabaseDump();
    }
}
