package server;


public class ServerController {
	private ServerViewer viewer;

	public ServerController() {
		
	}
	public void showUI(ServerViewer viewer) {
		this.viewer = viewer;
	}
	
	public void writeToLog(String msg) {
		viewer.addText(msg);
	}
	
	public static void main(String[] args) {
		ServerController controller = new ServerController();
		controller.showUI(new ServerViewer());
		new Server(controller, 5000);
	}
}
