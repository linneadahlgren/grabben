package com.example.linne.grabbentest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
	private Socket socket;
	private BufferedWriter output;
	private BufferedReader input;
	
	
	public Client(Socket socket) {
		this.socket=socket;

	}
	
	public BufferedWriter getOutputStream() {
		
		if(output==null && socket!=null) {
			try {
				output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			}catch(IOException e) {
				e.printStackTrace();	
			}
		}
		
		return output;
	}
	public BufferedReader getInputStream() {
		
		if(input==null && socket != null) {
			try {
				input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}catch(IOException e) {
				e.printStackTrace();
				
			}
		}
		return input;
		
	}
	

}
