package com.ezraloan.automation.Utils.DBUtils.DBDumper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Slf4j
@Service
public class DatabaseDumper {

    public Consumer<String> dumpDatabase =backupFileName->  {
        try {
            String[] command = {
                    "pg_dump",
                    "--host=localhost",
                    "--port=5433",
                    "-U",
                    "postgres",
                    "-d",
                    "ezra_loans",
                    "-f",
                    backupFileName
            };

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // Wait for the process to finish
            try {
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    log.info("Database backup completed successfully.");
                } else {
                    log.info("Database backup failed with exit code: " + exitCode);
                }
            } catch (Exception e) {
                log.error("Interrupted while waiting for the process to complete.");
                Thread.currentThread().interrupt();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    };
}
