package test;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.jcraft.jsch.*;

public class SSHConnect {


	    public static void main(String args[]) {
	        String user = "cts";
	        String password = "63722469";
	        String host = "192.168.100.54";
	        int port = 22;
	        String remoteFile = "/home/cts/test.txt";

	        try {
	            JSch jsch = new JSch();
	            Session session = jsch.getSession(user, host, port);
	            session.setPassword(password);
	            session.setConfig("StrictHostKeyChecking", "no");
	            System.out.println("Establishing Connection...");
	            session.connect();
	            System.out.println("Connection established.");
	            
	            System.out.println("Crating SFTP Channel.");
	            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
	            sftpChannel.connect();
	            System.out.println("SFTP Channel created.");

	            InputStream inputStream = sftpChannel.get(remoteFile);

	            try (Scanner scanner = new Scanner(new InputStreamReader(inputStream))) {
	                while (scanner.hasNextLine()) {
	                    String line = scanner.nextLine();
	                    System.out.println(line);
	                }
	            }
	        } catch (JSchException | SftpException e) {
	            e.printStackTrace();
	        }
	    }
	}

