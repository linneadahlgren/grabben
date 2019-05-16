package server;


public class ServerController {
	private ServerViewer viewer;
	private ServerUserUI userUI;
	private Queue userQueue;
	
	public ServerController() {
		userUI = new ServerUserUI(this);
		userQueue = new Queue();
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
		writeToLog(userQueue.entireQueue());

	}
	
	/**
	 * @return string of next user in the queue, also removes it from the queue
	 */
	public String getNextUser() {
		return userQueue.remove();
	}
	
	public static void main(String[] args) {
		ServerController controller = new ServerController();
		controller.showUI(new ServerViewer());
		new Server(controller, 5000);
	}
}
