package JavaClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



/**
 * @author linnea
 *
 */
public class UIClient extends JFrame implements ClientViewer{
	private ClientController client;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel buttons = new JPanel();
	private JButton uppBtn = new JButton("^");
	private JButton downBtn = new JButton("v");
	private JButton leftBtn = new JButton("<");
	private JButton rightBtn = new JButton(">");
	private JButton grabBtn = new JButton("o");
	
	/*
	 * konstruktor som visar alla komponenter
	 * l�gger �ven till actionlisteners p� alla knappar
	 * */
	public UIClient(ClientController client) {
		this.client=client;
		setLayout(new BorderLayout(200,200));
		buttons.setPreferredSize(new Dimension(200,150));
		uppBtn.setPreferredSize(new Dimension(70,30));
		downBtn.setPreferredSize(new Dimension(70,30));
		leftBtn.setPreferredSize(new Dimension(70,30));
		rightBtn.setPreferredSize(new Dimension(70,30));
		grabBtn.setPreferredSize(new Dimension(70,30));
		
		buttons.add(uppBtn, BorderLayout.NORTH);
		buttons.add(downBtn, BorderLayout.SOUTH);
		buttons.add(leftBtn, BorderLayout.WEST);
		buttons.add(rightBtn, BorderLayout.EAST);
		buttons.add(grabBtn, BorderLayout.CENTER);
		
		add(buttons);
		uppBtn.addActionListener(new BtnListener());
		rightBtn.addActionListener(new BtnListener());
		grabBtn.addActionListener(new BtnListener());

		downBtn.addActionListener(new BtnListener());
		leftBtn.addActionListener(new BtnListener());
		pack();
		setVisible(true);
	}
	
	
	/*
	 * btnlistener 
	 * hanterar alla knapptryck
	 * */
	private class BtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(uppBtn == e.getSource()) {
				client.send("90");
				System.out.println("90");
			}
			else if(downBtn == e.getSource()) {
				client.send("270");
				System.out.println("270");
			}
			
			else if(leftBtn == e.getSource()) {
				client.send("0");
				System.out.println("0");
			}
			
			else if(rightBtn == e.getSource()) {
				client.send("180");
				System.out.println("180");
			}
			
			else if(grabBtn == e.getSource()) {
				client.send("grab");
				System.out.println("grab");
			}
			
		}
		
		
		
	} 
	
}
