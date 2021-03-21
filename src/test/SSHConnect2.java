package test;
import java.io.InputStream;

import com.jcraft.jsch.*;

public class SSHConnect2 {
	 public static void main(String args[]) {
	        String user = "cts";
	        String password = "63722469";
	        String host = "192.168.100.54";
	        int port = 22;
	        String command1 = "echo 63722469 | sudo -S useradd -m -G input -s /bin/bash zakaria";
	        //String command1 = "ls -l";
	        String remoteFile = "/home/cts/test.txt";

	        try {
	            JSch jsch = new JSch();
	            Session session = jsch.getSession(user, host, port);
	            session.setPassword(password);
	            session.setConfig("StrictHostKeyChecking", "no");
	            System.out.println("Establishing Connection...");
	            session.connect();
	            System.out.println("Connection established.");
    	
    	Channel channel=session.openChannel("exec");
        ((ChannelExec)channel).setCommand(command1);
        channel.setInputStream(null);
        ((ChannelExec)channel).setErrStream(System.err);
        
        InputStream in=channel.getInputStream();
        channel.connect();
        byte[] tmp=new byte[1024];
        while(true){
          while(in.available()>0){
            int i=in.read(tmp, 0, 1024);
            if(i<0)break;
            System.out.print(new String(tmp, 0, i));
          }
          if(channel.isClosed()){
            System.out.println("exit-status: "+channel.getExitStatus());
            break;
          }
          try{Thread.sleep(1000);}catch(Exception ee){}
        }
        channel.disconnect();
        session.disconnect();
        System.out.println("DONE");
    }catch(Exception e){
    	e.printStackTrace();
    }
}
}
