package com.example.linne.da339a;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import java.io.IOException;
import java.net.*;



public class ClientController {
	private String ip;
	private int port;
	private Socket socket;
	private Client client;
	private ClientThread clientThread;
	private MainActivity viewer;


	public ClientController() {
		Log.e("myinfo", "-----------------NEW TEST---------------");

		if (clientThread == null) {
			clientThread = new ClientThread();
			clientThread.start();
		}

	}
	public void connect() {
		this.ip = "10.2.7.110";
		this.port = 5000;

		try {
			Log.e("myinfo", "trying to connect...");
			socket = new Socket(ip, port);
		} catch (UnknownHostException e) {
			Log.e("myinfo", "cannot find server");
		} catch (IOException e) {
			Log.e("myinfo", "ioexception when trying to connect to server");
		}
	}

	public void disconnect() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				Log.e("myinfo", "IO Exception when trying to close the connection");
			}
		}
	}

	public void send(String instruction) {
		if (socket != null) {
			if (client == null) {
				try {
					wait();

				} catch (InterruptedException e) {
					e.printStackTrace();
					Log.e("myinfo", "Inetrruption occured");
				}
			}
			try {
				client.getOutputStream().writeUTF(instruction);
				Log.e("myinfo", "sent " + instruction);
				client.getOutputStream().flush();

			} catch (IOException e) {
				e.printStackTrace();
				Log.e("myinfo", "Exception occured when trying to send instruction");
			} catch (NetworkOnMainThreadException e) {
				Log.e("myinfo", "network on main thread ");
			}
		}
	}

	private class ClientThread extends Thread {

		@Override
		public void run() {
			Log.e("myinfo", "thread");
			connect();
			client = new Client(socket);



			send("C");

			if (socket != null) {
				while (!socket.isClosed()) {
					try {
						String incomingString = "";
						char incoming = client.getInputStream().readChar();
						incomingString += incoming;
						Log.e("myinfo", incomingString);
					} catch (IOException e) {
						e.printStackTrace();

					}
				}
			}

		}
	}

}





