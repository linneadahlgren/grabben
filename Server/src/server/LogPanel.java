package server;

import java.awt.*;
import java.net.*;

import javax.swing.*;
/**
 * 
 * @author Pontus Folke, John Lindahl och Oscar Jonsson
 * This class represents a JComponent which is used in ServerUI
 * and to show logs at specific times.
 *
 */
public class LogPanel extends JPanel{
	private JTextArea textArea = new JTextArea("Server UI\n");
	private JScrollPane scroller = new JScrollPane(textArea);
	
	public LogPanel() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(480, 450));
		setOpaque(true);
		
		try {
			addText(InetAddress.getLocalHost().getHostAddress() + "\n");
		} catch (UnknownHostException e) {
			System.out.println(e);
		}		
		
		
		textArea.setEditable(false);
		scroller.setOpaque(true);
		add(scroller, BorderLayout.CENTER);
	}
	
	public void addText(String text) {
		textArea.append(text+"\n");
	}
	
	public String getText() {
		return textArea.getText();
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new LogPanel());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}