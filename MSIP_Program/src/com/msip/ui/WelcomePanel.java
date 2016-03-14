package com.msip.ui;

import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.msip.manager.MISPCore;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import java.awt.Component;
import javax.swing.Box;

public class WelcomePanel extends JPanel implements ActionListener
{
	private MISPCore manager;
	private JButton logOutbtn;
	private WelcomePanel welcomePanel;
	private JPanel panel;
	private JLabel messageToast;
	private JLabel animTimer;
	private JPanel welcomeCards;
	private JPanel blankPanel;
	private StudentSurveyPanel StudentSurveyPanel;
	private NotificationsPanel NotificationsPanel;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	private Timer timer;
	private NotificationCard NotificationCard;
	
	
	
	public WelcomePanel(final MISPCore manager) 
	{
		
		setBackground(Color.WHITE);
		this.manager = manager;
		setBounds(new Rectangle(0, 0, 800, 480));
		setLayout(new BorderLayout(0, 0));
		
		
		panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.NORTH);
		
		animTimer = new JLabel("Timer\r\n");
		panel.add(animTimer);
		
		horizontalStrut_1 = Box.createHorizontalStrut(50);
		panel.add(horizontalStrut_1);
		
		messageToast = new JLabel("Message");
		messageToast.setFont(new Font("Tahoma", Font.PLAIN, 40));
		messageToast.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(messageToast);
		
		horizontalStrut = Box.createHorizontalStrut(50);
		panel.add(horizontalStrut);
		
		logOutbtn = new JButton("Log Out");
		logOutbtn.addActionListener(this);
		panel.add(logOutbtn);
		
		
		welcomeCards = new JPanel();
		welcomeCards.setLayout(new CardLayout(0, 0));
		
	
		
		//Construct Cards
		 StudentSurveyPanel = new StudentSurveyPanel(manager, timer);
		 NotificationsPanel = new NotificationsPanel(manager);
		 NotificationCard = new NotificationCard();
		 blankPanel = new JPanel();
		 blankPanel.setBackground(Color.WHITE);
		
		//Add the Cards to the JPanel
		welcomeCards.add(StudentSurveyPanel, GlobalUI.StudentSurveyPanel);
		welcomeCards.add(NotificationsPanel, GlobalUI.NotificationsPanel);
		welcomeCards.add(NotificationCard, GlobalUI.NotificationCard);
		welcomeCards.add(blankPanel, GlobalUI.BlankPanel);
		add(welcomeCards, BorderLayout.CENTER);
		//Set up A Auto Time-Out for 6 Seconds
		addComponentListener(new ComponentAdapter() {
			Timer timer = new Timer();
			private WelcomePanel WelcomePanel;
			public void componentHidden(ComponentEvent e1)
			{
				timer.cancel();
			}
			
			public void componentShown(ComponentEvent e) 
			{
				//if Error Conditions only in LoginPanel
				timer = new Timer();
				welcomePanel = this.WelcomePanel;
				String message = messageToast.getText();
				if ((message == GlobalUI.adminPassError) || (message == GlobalUI.help)
				|| (message == GlobalUI.newStudentMessage) || (message == GlobalUI.InsertAdminPassMessage))
					showNoPanel();
				else
				{
					//Set up Random Generator
					Random randGen = new Random();
					double randChance = randGen.nextDouble();
					generateRandomPanel(randChance);
				}
					
					timer.schedule(new TimerTask() {

						public void run() 
						{
							CardLayout cl = (CardLayout) WelcomePanel.this.manager.getCards().getLayout();
							cl.show(WelcomePanel.this.manager.getCards(),GlobalUI.LoginPanel);
						}
					}, 15000L);
			}
	});
	}
	
	
	public void showNoPanel() {
		//Do not show blankPanel
		CardLayout cl = (CardLayout) welcomeCards.getLayout();
		cl.show(this.getCards(), GlobalUI.BlankPanel);
	}
	public void setMessage(String message)
	{
		messageToast.setText(message);
	}
	
	//TODO Random # by Chance
	//70% see notifications, 30% will see survey
	//below .7 / .3
	public void generateRandomPanel(double rand)
	{
		if (rand < .3)
		{
			CardLayout cl = (CardLayout) welcomeCards.getLayout();
			cl.show(this.getCards(),GlobalUI.StudentSurveyPanel);
		}
		else
		{
			CardLayout cl = (CardLayout) welcomeCards.getLayout();
			cl.show(this.getCards(),GlobalUI.NotificationCard);
		}
		
		
	}
//	public void showCenterMessage(WelcomePanel panel, JLabel label){
//		panel = this.welcomePanel;
//		panel.remove(label);
//		panel.remove(welcomeCards);
//		panel.add(label, BorderLayout.CENTER);
//	}
	public JPanel getCards()
	{
		return welcomeCards;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
			if (logOutbtn == e.getSource()){
				CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
				cl.show(this.manager.getCards(),GlobalUI.LoginPanel);
			}
			
		
	}
}
