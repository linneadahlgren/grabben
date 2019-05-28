package client;

import java.io.*;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 
 * This class is used as a base for the androidclient. In the making of the system this client was used before the android-client was done
 * @author toverumar
 *
 */
public class Client {
	private Socket socket;
	private BufferedWriter output;
	private BufferedReader input;
	
	
	public Client(Socket socket) {
		this.socket=socket;
	}
	
	public BufferedWriter getOutputStream() {
		
		if(output==null) {
			try {
				output=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			}catch(IOException e) {
				e.printStackTrace();	
			}
		}
		
		return output;
	}
	public BufferedReader getInputStream() {
		
		if(input==null) {
			try {
				input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}catch(IOException e) {
				e.printStackTrace();
				
			}
		}
		return input;
		
	}


}
