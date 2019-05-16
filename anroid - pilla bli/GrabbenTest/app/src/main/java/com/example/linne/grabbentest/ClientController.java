package com.example.linne.grabbentest;

import android.content.res.Resources;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientController {
	private String ip;
	private int port = 5000;
	private Socket socket;
	private Client client;
	private ClientThread clientThread;
	private UpdateUser game;
	private String activeUser = "-1";

	public ClientController(String ip, UpdateUser game) {
		Log.e("myinfo", "-----------------NEW TEST---------------");
		this.game = game;
		this.ip = ip;

		if (clientThread == null) {
			clientThread = new ClientThread();
			clientThread.start();
		}

	}


	public ClientController(String ip, UpdateUser game, String username){
		Log.e("myinfo", "-----------------NEW TEST---------------");
		this.game = game;
		this.ip = ip;

		if (clientThread == null) {
			clientThread = new ClientThread();
			clientThread.start();
		}

		activeUser = username;

	}

	public void connect() {

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
				client.getOutputStream().write(instruction);
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
			send("COMPUTER\n");

			if(activeUser.equals("-1")){

				send("GETNEXTUSER\n");

			}else {
				game.newUser(activeUser);
			}



			if (socket != null) {

				while (!socket.isClosed()) {


					try {
						Log.e("myinfo", "läser data");


						String incoming = client.getInputStream().readLine();

						Log.e("myinfo", "kommer vi hit?");

						if(incoming.equals("USER:EMPTYQUEUE")){
							Log.e("myinfo", "EMPTYQUEUE");
							game.noUser();

						}else if(incoming.contains("USER:")){
							String userName = incoming.substring(5);
							game.newUser(userName);
                            Log.e("myinfo", "inkommande användarnamn " + userName);

						}else{
							Log.e("myinfo", incoming);

						}

					} catch (IOException e) {
						e.printStackTrace();

					}
				}
			}

		}
	}

}





