package com.ezraloan.automation.Utils.DBUtils.DBDumper;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Slf4j
@Service
public class DatabaseDumpService {

    @Value("${sftp.host}")
    private String sftpHost;
    
    @Value("${sftp.port}")
    private int sftpPort;
    
    @Value("${sftp.username}")
    private String sftpUsername;
    
    @Value("${sftp.password}")
    private String sftpPassword;
    
    @Value("${spring.datasource.url}")
    private String databaseUrl;
    
    @Value("${database.username}")
    private String databaseUsername;
    
    @Value("${database.password}")
    private String databasePassword;

    @Value("${file.path}")
    public  String dumpDirectory;

    @Value("${database.name}")
    public  String dbName;

    public void generateAndTransferDatabaseDump() {
        // Generate the database dump
        generateDatabaseDump();
        String dumpFilePath = generateDatabaseDump();
        // Transfer the dump file to the SFTP server
       // transferDumpFileToSftp(dumpFilePath);
        
        // Optionally, you can delete the local dump file after transferring it
        //deleteLocalDumpFile(dumpFilePath);
    }

    private String generateDatabaseDump() {
        // Construct the command to generate the database dump using the 'pg_dump' command
        String dumpFilePath = dumpDirectory + dbName;
        String command = String.format("pg_dump --host=localhost --port=5433 -U %s -d %s -f %s", databaseUsername, dbName, dumpFilePath);
        
        try {
            // Execute the command
            log.info("DB Dump Service Running");
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                // Dump generation successful
                return dumpFilePath;
            } else {
                try {
                    // Dump generation failed
                    log.error("Failed to generate database dump");
                    String errorOutput = readStream(process.getErrorStream());
                    System.out.println("Error output: " + errorOutput);
                    //throw new RuntimeException("Failed to generate the database dump.");
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating the database dump.", e);
        }
        return null;
    }



    // Helper method to read the stream
    private static String readStream(InputStream inputStream) throws Exception {
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append(System.lineSeparator());
        }
        return output.toString();
    }

    private void transferDumpFileToSftp(String dumpFilePath) {
        // Configure the SFTP connection
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp sftpChannel = null;
        
        try {
            session = jsch.getSession(sftpUsername, sftpHost, sftpPort);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(sftpPassword);
            session.connect();
            
            // Create an SFTP channel
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;
            
            // Transfer the dump file to the SFTP server
            sftpChannel.put(dumpFilePath, "/path/on/sftp/server/database_dump.sql");
        } catch (JSchException | SftpException e) {
            throw new RuntimeException("Error transferring the dump file to the SFTP server.", e);
        } finally {
            // Disconnect the SFTP channel and session
            if (sftpChannel != null) {
                sftpChannel.exit();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    private void deleteLocalDumpFile(String dumpFilePath) {
        // Delete the local dump file
        File dumpFile = new File(dumpFilePath);
        if (dumpFile.exists()) {
            if (!dumpFile.delete()) {
                throw new RuntimeException("Failed to delete the local dump file.");
            }
        }
    }
}
