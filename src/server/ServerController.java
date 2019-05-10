package server;

import java.util.Arrays;
import java.util.Comparator;

public class ServerController {
	private ServerViewer viewer;
	private ServerUserUI userUI;
	private Queue userQueue;
	private User[] highScoreList;
	private User currentUser;
	
	public ServerController() {
		userUI = new ServerUserUI();
		userQueue = new Queue();
		highScoreList=new User[10];
	}
	public void showUI(ServerViewer viewer) {
		this.viewer = viewer;
	}
	
	public void writeToLog(String msg) {
		viewer.addText(msg);
	}
	
	
	/**
	 * @param user, String of the "user" to add to the queue
	 */
	public void addToQueue(String user) {
		writeToLog("New user in line to play: " + user);
		userQueue.add(user);
	}
	public void compareScore() {
		if(currentUser.getPoints()>=highScoreList[10].getPoints()) {
			highScoreList[10]=currentUser;
			Arrays.sort(highScoreList);
			
			}
		}
	
	
	/**
	 * @return string of next user in the queue, sets it as the current user and
	 *  also removes it from the queue. 
	 */
	public String getNextUser() {
		currentUser=new User(userQueue.remove());
		return currentUser.getName();
	}
	
	public static void main(String[] args) {
		ServerController controller = new ServerController();
		controller.showUI(new ServerViewer());
		new Server(controller, 5000);
	}
}
