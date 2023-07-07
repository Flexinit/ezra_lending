package com.ezraloan.automation.Utils.DBUtils.DBDumper;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public record DatabaseDumpScheduler(DatabaseDumpService databaseDumpService,
                                    DatabaseDumper databaseDumper) {

  //  @Scheduled(fixedDelay = 24 * 60 * 60 * 1000) // Executes every 24 hours
  @Scheduled(fixedRate = 5000)// Runs every 5 seconds
    public void scheduleDatabaseDump() {

        //databaseDumpService.generateAndTransferDatabaseDump();
        String fileName = "Ezra_Loans_Dump" + LocalDateTime.now() + ".sql";
        databaseDumper.dumpDatabase.accept(fileName);
    }
}
