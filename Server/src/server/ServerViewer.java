package server;

import java.awt.*;
import javax.swing.*;

/**
 * @author John Lindahl
 * A class to set up a window that is shown in the server
 */
public class ServerViewer extends JFrame{
	private LogPanel panel = new LogPanel();
	
	public ServerViewer() {
		setPreferredSize(new Dimension(600,500));
		setLayout(new BorderLayout());
		
		add(panel,BorderLayout.CENTER);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public void addText(String msg) {
		panel.addText(msg);
	}

}

