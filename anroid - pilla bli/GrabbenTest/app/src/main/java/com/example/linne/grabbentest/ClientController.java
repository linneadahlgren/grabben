package com.example.linne.grabbentest;

import android.content.res.Resources;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * Author : Linnea Dahlgren & Tove Rumar
 * */
public class ClientController {
	private String ip;
	private int port = 5000;
	private Socket socket;
	private Client client;
	private ClientThread clientThread;
	private UpdateUser game;
	private String activeUser = "-1";
	private String mode;

	public ClientController(String ip, UpdateUser game) {
		Log.e("myinfo", "-----------------NEW TEST---------------");
		this.game = game;
		this.ip = ip;

		if (clientThread == null) {
			clientThread = new ClientThread();
			clientThread.start();
		}

	}

	/*
	* Konstruktorn tar en IP-adress, ett updateGame interface, använarnamn i form av sträng och en sträng med GameMode
	*Den startar en ny tråd som hanterar kommunikationen
	* */
	public ClientController(String ip, UpdateUser game, String username, String gameMode){
		Log.e("myinfo", "-----------------NEW TEST---------------");
		this.game = game;
		this.ip = ip;
		this.mode = gameMode;
		if (clientThread == null) {
			clientThread = new ClientThread();
			clientThread.start();
		}

		activeUser = username;

	}

	/*
	* connect anropas från clientHandler coh skapar en socket med ip och port angivet från konsturktorn
	* */
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
/*
* send metoden tar emot en sträng som sedan skickas vidare till servern.
* om socket är null så väntar den
* */
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
	/*
	* checkActiveUser hanterar om appen vill ha en ny användare från kön eller om det är samma person som ska spela igen.
	* Om det det finns en aktiveUser så skickar den denna till servern annars skickar den en request om en ny användare till servern
	* */
	public void checkActiveUser(){

		if(activeUser.equals("-1")){

			send("GETNEXTUSER\n");

		}else {
			game.newUser(activeUser);
			send("NEWUSER:"+activeUser+"\n");
		}

	}
	/*
	* ClientThread hanterar all kommunikation.
	* Det första den gör är att skapa en anslutning med att anropa connect(). Sedan skickar den all nödvändig information om sigsjälv som servern
	* behöver för att starta ett game
	*
	* */
	private class ClientThread extends Thread {

		@Override
		public void run() {
			Log.e("myinfo", "thread");
			connect();
			client = new Client(socket);

			send("COMPUTER\n");
			send(mode + "\n");

			if(mode.equals("FREEMODE")){
				checkActiveUser();
			}else{

			}


			if (socket != null) {

				while (!socket.isClosed()) {


					try {
						Log.e("myinfo", "läser data");

						String incoming = "";
						incoming = client.getInputStream().readLine();

						Log.e("myinfo", incoming + "1");


						if(incoming.equals("USER:NO ONE")){
							Log.e("myinfo", "EMPTYQUEUE");
							game.noUser();

						}else if(!incoming.contains("NO ONE")){
							String userName = incoming.substring(5);
							game.newUser(userName);
                            Log.e("myinfo", "inkommande användarnamn " + userName);

						}else{
							Log.e("myinfo", incoming + "2");

						}

					} catch (IOException e) {
						e.printStackTrace();

					}
				}
			}

		}
	}

}





