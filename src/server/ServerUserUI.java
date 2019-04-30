package server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.plaf.synth.*;

public class ServerUserUI extends JFrame {
	public static void main(String[] args) {
		ServerController con = new ServerController();
		ServerUserUI ui = new ServerUserUI(con);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton addUserBtn = new JButton("put user");
	private JTextField 	userInput = new JTextField();
	private JLabel 	userLabel = new JLabel("stand i virtual queue to play");
	private ServerController controller;
	private JPanel panel = new JPanel();

	public ServerUserUI(ServerController cont){
		this.controller = cont;
		setLayout(new BorderLayout(400,400));
		panel.setPreferredSize(new Dimension(400,200));
		userLabel.setPreferredSize(new Dimension(200,40));
		addUserBtn.setPreferredSize(new Dimension(200,40));
		userInput.setPreferredSize(new Dimension(200,40));
		panel.add(userLabel, BorderLayout.NORTH);

		panel.add(userInput, BorderLayout.CENTER);
		panel.add(addUserBtn, BorderLayout.SOUTH);
		add(panel);
		
		addUserBtn.addActionListener(new BtnListener());
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
	}
	private class BtnListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(addUserBtn == e.getSource()) {
				System.out.println("lägg till användare");
			}
		}}
			
	}	
	
