package UI;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPpull {
	
	
	public IPpull() throws UnknownHostException{
		InetAddress inetAddress = InetAddress.getLocalHost();
		System.out.println("IP Address:- " + inetAddress.getHostAddress());
    	System.out.println("Host Name:- " + inetAddress.getHostName());
	}
}
