package com.ezraloan.automation.Utils.DBUtils.DBDumper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Slf4j
@Service
public class DatabaseDumper {

    @Value("${file.path}")
    public  String dumpDirectory;
    public Consumer<String> dumpDatabase = backupFileName ->  {
        try {

            String pgDumpPath = "/Library/PostgreSQL/15/bin/pg_dump";
            String host = "localhost";
            String port = "5433";
            String username = "postgres";
            String password = "root";
            String database = "ezra_loans";

            // Build the command
            String[] command = {
                    pgDumpPath,
                    "--host=" + host,
                    "--port=" + port,
                    "-U",
                    username,
                    "-w", // Use -w option to provide password programmatically
                    "-d",
                    database,
                    "-f",
                    backupFileName
            };

            String[] env = {"PGPASSWORD=" + password};

            // Start the process
            Process process = Runtime.getRuntime().exec(command, env);

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    log.info("DATABASE BACKUP SUCCESSFUL ******************.");
                } else {
                    System.err.println("pg_dump failed with exit code: " + exitCode);
                }
            } catch (Exception e) {
                System.err.println("Error while waiting for pg_dump process to complete: " + e.getMessage());
            }
    };
}
