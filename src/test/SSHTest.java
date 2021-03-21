package test;


import com.chilkatsoft.*;

public class SSHTest {
	  static {
		    try {
		        System.loadLibrary("chilkat");
		    } catch (UnsatisfiedLinkError e) {
		      System.err.println("Native code library failed to load.\n" + e);
		      System.exit(1);
		    }
		  }
	  
	public static void main(String[] args) {
	//  This example assumes Chilkat SSH/SFTP to have been previously unlocked.
	    //  See Unlock SSH for sample code.
	    CkSsh ssh = new CkSsh();
	    

	    int port = 22;
	    boolean success = ssh.Connect("192.168.100.54",port);
	    if (success != true) {
	        System.out.println(ssh.lastErrorText());
	        return;
	        }

	    //  Authenticate using login/password:
	    success = ssh.AuthenticatePw("cts","63722469");
	    if (success != true) {
	        System.out.println(ssh.lastErrorText());
	        return;
	        }

	    //  Send some commands and get the output.
	    String strOutput = ssh.quickCommand("df","ansi");
	    if (ssh.get_LastMethodSuccess() != true) {
	        System.out.println(ssh.lastErrorText());
	        return;
	        }

	    System.out.println("---- df ----");
	    System.out.println(strOutput);

	    strOutput = ssh.quickCommand("echo hello world","ansi");
	    if (ssh.get_LastMethodSuccess() != true) {
	        System.out.println(ssh.lastErrorText());
	        return;
	        }

	    System.out.println("---- echo hello world ----");
	    System.out.println(strOutput);

	    strOutput = ssh.quickCommand("date","ansi");
	    if (ssh.get_LastMethodSuccess() != true) {
	        System.out.println(ssh.lastErrorText());
	        return;
	        }

	    System.out.println("---- date ----");
	    System.out.println(strOutput);
	}
}
