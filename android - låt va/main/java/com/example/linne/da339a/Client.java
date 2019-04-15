package com.example.linne.da339a;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	private Socket socket;
	private DataOutputStream output;
	private DataInputStream input;
	
	
	public Client(Socket socket) {
		this.socket=socket;

	}
	
	public DataOutputStream getOutputStream() {
		
		if(output==null && socket!=null) {
			try {
				output= new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			}catch(IOException e) {
				e.printStackTrace();	
			}
		}
		
		return output;
	}
	public DataInputStream getInputStream() {
		
		if(input==null && socket != null) {
			try {
				input=new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			}catch(IOException e) {
				e.printStackTrace();
				
			}
		}
		return input;
		
	}
	

}
