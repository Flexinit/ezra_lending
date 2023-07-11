package com.ezraloan.automation.Utils.DBUtils.DBDumper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Slf4j
@Component
public record DatabaseDumpScheduler(DatabaseDumpService databaseDumpService,
                                    DatabaseDumper databaseDumper) {

  //  @Scheduled(fixedDelay = 24 * 60 * 60 * 1000) // Executes every 24 hours
  @Scheduled(fixedRate = 60000)// Runs every 1 min
    public void scheduleDatabaseDump() {

      log.info("DB DUMP STARTED **************");

        String fileName = "Ezra_Loans_Dump" + LocalDateTime.now() + ".sql";
        databaseDumper.dumpDatabase.accept(fileName);
    }
}
