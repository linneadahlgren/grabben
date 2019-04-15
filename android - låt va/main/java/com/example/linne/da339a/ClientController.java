package com.example.linne.da339a;

import android.util.Log;
import java.io.IOException;
import java.net.*;



public class ClientController {
	private String ip;
	private int port;
	private Socket socket;
	private Client client;
	private ClientThread thread;
	private MainActivity viewer;
	
	
	

	public ClientController() {
		Log.i("myinfo", "-----------------NEW TEST---------------");


        /*try {
            Socket socket = new Socket("127.0.0.1", 5000);


            Log.i("myinfo","Connecting to server...kfldflisf");*/
            if (thread == null) {
                thread = new ClientThread();
                thread.start();
                //	Log.i("myinfo","connected to server");
            }


	}

	public void connect(){
		this.ip= "127.0.0.1";
		this.port= 5000;

		try{
			Log.i("myinfo", "trying to connect...");
			socket = new Socket(ip,port);
		}catch(UnknownHostException e){
			Log.i("myinfo", "cannot find server");
		}catch(IOException e){
			Log.i("myinfo", "ioexception when trying to connect to server");
		}
	}
	public void disconnect(){
		if(socket!=null) {
			try {
				socket.close();
			}catch (IOException e) {
				e.printStackTrace();
				Log.i("myinfo","IO Exception when trying to close the connection");
			}
		}
	}
	public void send(char instruction) {
		if(socket!= null) {
			if (client == null) {
				try {
					wait();

				} catch (InterruptedException e) {
					e.printStackTrace();
					Log.i("myinfo", "Inetrruption occured");
				}
			}
			try {
				client.getOutputStream().writeChar(instruction);
				client.getOutputStream().flush();

			} catch (IOException e) {
				e.printStackTrace();
				Log.i("myinfo", "Exception occured when trying to send instruction");
			}
		}
	}


	
	private class ClientThread extends Thread {

		public ClientThread() {
			Client client;
		}

		public void run() {
			Log.i("myinfo", "tthread");
			connect();
			client = new Client(socket);


			//String stringToSend=JOptionPane.showInputDialog("Input string to send");

			send('C');

			if (socket != null) {
				while (!socket.isClosed()) {
					try {
						String incomingString = "";
						char incoming = client.getInputStream().readChar();
						incomingString += incoming;
						Log.i("myinfo", incomingString);
					} catch (IOException e) {
						e.printStackTrace();

					}
				}
			}
		}
	}




}



