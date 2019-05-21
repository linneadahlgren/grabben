package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Comparator;



/**
 * 
 * 
 * @author Tove Rumar and John Lindahl
 *
 */
public class ServerController {
	private ServerViewer viewer;
	private ServerUserUI userUI;
	private Queue userQueue;
	private User[] highScoreList;
	private User currentUser=new User();
	private User nextUser=new User();
	private String highscoreFile="files/highscoreFile";
	
	public ServerController() {
		userUI = new ServerUserUI(this);
		userQueue = new Queue();
		highScoreList=new User[]{new User(),new User(),new User(),new User(),new User(),new User(),new User(),new User(),new User(),new User()};
		userUI.setHighscorePanel(highScoreList);
		readOldHighScore();
		
		
	}
	public void showUI(ServerViewer viewer) {
		this.viewer = viewer;
	}
	
	/**
	 * writes to log 
	 * @param msg
	 */
	public void writeToLog(String msg) {
		viewer.addText(msg);
	}
	
	
	/**
	 * @param user, String of the "user" to add to the queue
	 */
	public void addToQueue(String user) {
		writeToLog("New user in line to play: " + user);
		userQueue.add(user);
		if(nextUser.getName().equals("NO ONE")) {
			setNextUser();
		}
	}
	/**
	 * 
	 * starts the highscorereader-thread
	 */
	public void readOldHighScore() {
		new HighScoreReader().start();	
	}
	/**
	 * 
	 * Controlls the currentUsers score against the highscorelist at the end of every freeplaygame.
	 * 
	 */
	public void compareScore() {
		System.out.println("In comparescore method");
		//synchronized(currentUser) {
		if (currentUser!=null) {
			if(currentUser.getPoints()>=highScoreList[highScoreList.length-1].getPoints()) {
				
				highScoreList[9]=currentUser;
					
				}
				   Arrays.sort(highScoreList);
				   System.out.println("higscorelist sorted");
				   userUI.updateHighscore(highScoreList);	
			}
	}	
	//}
			
		
	/**
	 * @return string of next user in the queue, sets it as the current user and
	 *  also removes it from the queue. 
	 */
	public String getNextUser() {
		currentUser=new User(userQueue.remove());
		if (currentUser.getName().equals("EMPTYQUEUE")) {
			currentUser.setName("NO ONE");
		}
		else {
		userUI.updateCurrentUser(currentUser);
		setNextUser();
		}
		return currentUser.getName();
	}
	/**
	 * 
	 * Sets the variable nextUser to the next user in queue and sends it to the UI to be shown.
	 * @returns the next user in queue
	 */
	public String setNextUser() {
		
		nextUser=new User(userQueue.element());
		
		userUI.updateNextUser(nextUser);
		
		return nextUser.getName();
	}
	/**
	 * starts the highscoreSaver-thread.
	 */
	public void saveHighScore() {
		
		new HighscoreSaver().start();
	}	
	/**
	 * Takes a user as a parameter and sets this as the currentUser and sends that to the ui.
	 * 
	 * @param user
	 */
		public void setCurrentUser(User user) {
			currentUser=user;
			userUI.updateCurrentUser(currentUser);
		}
		/**
		 * Takes a string of points as a parameters. Multiplies the points by ten and then cuts of the decimals.
		 * Compares these points to the points that the currentuser has now and sets it as the new points-value if it is the bigger number.
		 * Then it sends these points to ui.
		 * @param points
		 */
		public void setScore(String points) {
			int currentPoints=(int)((Float.parseFloat(points))*10);
			
			if(currentPoints>currentUser.getPoints()) {
			currentUser.setPoints(currentPoints);
			userUI.updatePoints(currentPoints);
			}
			
			
		}
	
	/**
	 * 
	 * 
	 * Messages below are stubs for testing
	 */
	public void add(User user) {
		userQueue.add(user.getName());
	}
	
	
	
	public void emptyHighScore() {
		for(int i=0;i<highScoreList.length;i++) {
			highScoreList[i]=new User();
		}
		userUI.updateHighscore(highScoreList);	

	}
	

	private class HighscoreSaver extends Thread{

		public void run() {
			System.out.println("highscoresaver started");
				try{
					System.out.println("trying to setup streams");
					ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(highscoreFile)));
					System.out.println("stream is setup");
					for(int i=0;i<highScoreList.length;i++) {
						output.writeObject(highScoreList[i]);
						output.flush();
					}
					output.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
	
	private class HighScoreReader extends Thread{

		public void run() {
			
			try(ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(highscoreFile)))){
				
				for(int i = 0; i < highScoreList.length; i++) {
					User user =  (User) input.readObject();
					if (user!=null) {
						highScoreList[i]=user;
					}
					else if(user==null) {
						highScoreList[i]=new User();
					}
					System.out.println(highScoreList[i].getName());
					   
				}
				input.close();
				userUI.updateHighscore(highScoreList);	
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
		
	public static void main(String[] args) {
		ServerController controller = new ServerController();
		controller.showUI(new ServerViewer());
		new Server(controller, 5000);
		//controller.emptyHighScore();
	
		
	
	}
		
}


