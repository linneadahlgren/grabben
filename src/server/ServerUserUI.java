package server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.synth.*;
import javafx.*;

public class ServerUserUI extends JFrame{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private ServerController controller;
	private JPanel  groundPanel= new JPanel();
	private JPanel titlePanel=new JPanel();
	private JPanel highscorePanel=new JPanel();
	private JPanel centerPanel=new JPanel();
	private JPanel westPanel=new JPanel();
	private JLabel titleLabel=new JLabel();
	private JLabel centertextLbl=new JLabel();
	private JLabel nexPlayerLbl=new JLabel("NO ONE IS PLAYING");
	
	private Font titleFont;
	private Font textFont;
	private Font smallTextFont;
	private JLabel[] scoreLabels= new JLabel[10];
	private JPanel loginPanel=new JPanel();
	private JButton loginBtn=new JButton("GET IN LINE");
	private JLabel loginLbl=new JLabel("ENTER YOUR NAME");
	private JTextField loginTxt=new JTextField();
	private User currentUser;
	private User nextUser=new User();
	private Color lightBlue=new Color(51,204,255);
	private Color purple=new Color(102,0,153);
	private Color pink=new Color(255,20,147);
	
	
	

	
	public ServerUserUI(ServerController controller){
		
		titleFont= createFont("/Users/toverumar/Documents/Fonts/dragonmark/dragonmark.ttf",160f);
		textFont=createFont("/Users/toverumar/Documents/Fonts/linestrider-mini/linestrider-mini.ttf",100f);
		smallTextFont=createFont("/Users/toverumar/Documents/Fonts/linestrider-mini/linestrider-mini.ttf",30f);
		this.controller = controller;
		
		setPreferredSize(new Dimension(1500,800));
		
		groundPanel.setPreferredSize(new Dimension(1500,800));
		groundPanel.setBackground(Color.BLACK);
		groundPanel.setLayout(new BorderLayout());
		
		highscorePanel.setPreferredSize(new Dimension(300,800));
		highscorePanel.setBackground(Color.BLACK);
		highscorePanel.setForeground(pink);
		highscorePanel.setBorder(BorderFactory.createLineBorder(pink));
		highscorePanel.setLayout(new BoxLayout(highscorePanel,BoxLayout.PAGE_AXIS));
		
		centerPanel.setPreferredSize(new Dimension(200,200));
		centerPanel.setBackground(Color.BLACK);
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		centerPanel.setLayout(new BorderLayout());
		centertextLbl.setBackground(Color.BLACK);
		centertextLbl.setForeground(lightBlue);
		centertextLbl.setPreferredSize(new Dimension(400,400));
		centertextLbl.setText("<html>CURRENT PLAYER<br>NO ONE</html>");
		centertextLbl.setFont(textFont);
		centertextLbl.setVerticalAlignment(JLabel.CENTER);
		centertextLbl.setHorizontalAlignment(JLabel.CENTER);
		
		centertextLbl.setBorder(BorderFactory.createLineBorder(lightBlue));
		nexPlayerLbl.setText("Next Player:");
		nexPlayerLbl.setFont(textFont);
		nexPlayerLbl.setBackground(Color.BLACK);
		nexPlayerLbl.setForeground(purple);
		nexPlayerLbl.setPreferredSize(new Dimension(400,200));
		nexPlayerLbl.setVerticalAlignment(JLabel.CENTER);
		nexPlayerLbl.setHorizontalAlignment(JLabel.CENTER);
		centerPanel.add(centertextLbl);
		centerPanel.add(nexPlayerLbl,BorderLayout.SOUTH);
		
		loginPanel.setPreferredSize(new Dimension(300,400));
		loginPanel.setBorder(BorderFactory.createLineBorder(pink));
		loginPanel.setBackground(Color.BLACK);
		loginPanel.setLayout(new BorderLayout());
		loginLbl.setPreferredSize(new Dimension(300,100));
		loginLbl.setBackground(Color.BLACK);
		loginLbl.setFont(smallTextFont);
		loginLbl.setForeground(purple);
		loginLbl.setHorizontalAlignment(JLabel.CENTER);
		loginTxt.setPreferredSize(new Dimension(250,100));
		loginTxt.setBackground(Color.BLACK);
		loginTxt.setForeground(pink);
		loginTxt.setFont(smallTextFont);
		loginTxt.setCaretColor(pink);
		loginTxt.setHorizontalAlignment(JLabel.CENTER);
		
		loginTxt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		loginBtn.setPreferredSize(new Dimension(300,100));
		
		loginBtn.setFont(smallTextFont);
		loginPanel.add(loginLbl,BorderLayout.NORTH);
		loginPanel.add(loginTxt, BorderLayout.CENTER);
		
		loginPanel.add(loginBtn, BorderLayout.SOUTH);
		loginBtn.addActionListener(new BtnListener());
		
		titlePanel.setPreferredSize(new Dimension(800,200));
		titlePanel.setBackground(Color.BLACK);
		titlePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		titleLabel.setText("CLAW");
		
		titleLabel.setFont(titleFont);
		titleLabel.setForeground(lightBlue);
		titlePanel.add(titleLabel);
		

		westPanel.setPreferredSize(new Dimension(300,800));
		westPanel.setLayout(new BorderLayout());
		westPanel.setBackground(Color.BLACK);
		westPanel.setBorder(BorderFactory.createLineBorder(pink));
		
		westPanel.add(loginPanel,BorderLayout.SOUTH);
		centerPanel.add(titlePanel,BorderLayout.NORTH);
		groundPanel.add(highscorePanel,BorderLayout.EAST);
		groundPanel.add(centerPanel,BorderLayout.CENTER);
		groundPanel.add(westPanel, BorderLayout.WEST);
		
		add(groundPanel);
		
		new BlinkThread().start();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
	
		
		
	}
	
	public void updateCurrentUSer(User currentUser) {
		this.currentUser=currentUser;
		
		centertextLbl.setText("<html>CURRENT PLAYER<br>"+currentUser.getName()+"</html>");
	}
	
	public void updateNextUser(User nextUser) {
		
		this.nextUser=nextUser;
		
		
		
	}
	
	public void updateHighscore(User[] highScoreList) {
		for(int i=0;i<scoreLabels.length;i++) {
			scoreLabels[i].setText(highScoreList[i].getName()+": " + highScoreList[i].getPoints());
		}
		
	}
	public void setHighscorePanel(User[] highScoreList) {
		for(int i=0;i<scoreLabels.length;i++) {
			scoreLabels[i]=new JLabel(highScoreList[i].getName()+": " + highScoreList[i].getPoints());
			scoreLabels[i].setVerticalAlignment(JLabel.CENTER);
			scoreLabels[i].setHorizontalAlignment(JLabel.CENTER);
			scoreLabels[i].setMinimumSize(new Dimension(300,100));
			scoreLabels[i].setMaximumSize(new Dimension(300,100));
			scoreLabels[i].setFont(smallTextFont);
			scoreLabels[i].setForeground(pink);
			highscorePanel.add(scoreLabels[i]);
			
		}
	}
	public Font createFont(String filename,float size) {
		Font customFont=null;
		try {
		   
		    customFont = Font.createFont(Font.TRUETYPE_FONT, new File(filename)).deriveFont(size);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    
		    ge.registerFont(customFont);
		    
		    
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}

		return customFont;
	}
	
	private class BtnListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(loginBtn == e.getSource()) {
				String newUser=loginTxt.getText().toUpperCase();
				controller.addToQueue(newUser);	
				
		
			}
		}
	}
	private class BlinkThread extends Thread{
		public void run() {
			while(true) {
				nexPlayerLbl.setText(nextUser.getName());
			repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nexPlayerLbl.setText("NEXT PLAYER: ");
			repaint();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
	}
	}

	
