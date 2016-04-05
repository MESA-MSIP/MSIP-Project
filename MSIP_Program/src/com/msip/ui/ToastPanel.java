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

import com.msip.db.SurveyTable;
import com.msip.manager.MISPCore;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;

import java.awt.Component;
import javax.swing.Box;

public class ToastPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7045096990489164680L;

	private MISPCore manager;
	private JButton exitButton;
	private JPanel panel;
	private JLabel messageToast;
	private JPanel welcomeCards;
	private StudentSurveyPanel StudentSurveyPanel;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	private Timer timer;
	private NotificationCard NotificationCard;
	private ToastPanel toastPanel;
	private SurveyTable surveyTable;

	private JPanel layoutPanel;

	public ToastPanel(final MISPCore manager) {
		setBackground(Color.WHITE);
		this.manager = manager;
		this.toastPanel = this;
		surveyTable = this.manager.getSurveyTable();
		
		setBounds(new Rectangle(0, 0, 800, 480));
		// Construct ConditionCards
		setLayout(new CardLayout(0, 0));

		layoutPanel = new JPanel();
		add(layoutPanel, "n");
		layoutPanel.setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		layoutPanel.add(panel, BorderLayout.NORTH);
		panel.setBorder(new MatteBorder(0, 0, 1, 0, GlobalUI.blackColor));
		panel.setBackground(Color.WHITE);

		horizontalStrut_1 = Box.createHorizontalStrut(50);
		panel.add(horizontalStrut_1);

		messageToast = new JLabel("Message");
		messageToast.setFont(new Font("Tahoma", Font.PLAIN, 40));
		messageToast.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(messageToast);

		horizontalStrut = Box.createHorizontalStrut(50);
		panel.add(horizontalStrut);

		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		panel.add(exitButton);

		welcomeCards = new JPanel();
		layoutPanel.add(welcomeCards, BorderLayout.CENTER);
		welcomeCards.setLayout(new CardLayout(0, 0));
		// Construct welcomeCards
		StudentSurveyPanel = new StudentSurveyPanel(this.manager, timer);
		NotificationCard = new NotificationCard(this.manager);
		NotificationCard.setVisible(false);
		

		// Add the Cards to the JPanel
		welcomeCards.add(NotificationCard, GlobalUI.NotificationCard);
		welcomeCards.add(StudentSurveyPanel, GlobalUI.StudentSurveyPanel);
		
		
		//

		
		// Set up A Auto Time-Out for 6 Seconds
		addComponentListener(new ComponentAdapter() {
			Timer timer = new Timer();
			

			public void componentHidden(ComponentEvent e1) {

				timer.cancel();
			}

			public void componentShown(ComponentEvent e) {
				
			

				// if Error Conditions only in LoginPanel
				timer = new Timer();

				if (messageToast.getText() == GlobalUI.errorMessage) {
					//
					CardLayout cl = (CardLayout) toastPanel.getLayout();
					cl.show(toastPanel, GlobalUI.newStudentMessagePanel);
				} else {
					// Set up Random Generator
					Random randGen = new Random();
					double randChance = randGen.nextDouble();
					checkForNotifications();
					generateRandomPanel(randChance);
				}
				if (surveyTable.getID() == -2) {
					//If there is no Question, Show the Notification Card
					checkForNotifications();
					CardLayout cl = (CardLayout) welcomeCards.getLayout();
					cl.show(toastPanel.getCards(), GlobalUI.NotificationCard);
				}
				else{
					if(surveyTable.getQuestion().length() < GlobalUI.minQuestionLength){
						checkForNotifications();
						CardLayout cl = (CardLayout) welcomeCards.getLayout();
						cl.show(toastPanel.getCards(), GlobalUI.NotificationCard);						
					}
					//Set the Text of the StudentSurveyPanel to the Question
					String question  = surveyTable.getQuestion();
					StudentSurveyPanel.setQuestion(question);
				}

				timer.schedule(new TimerTask() {

					public void run() {
						CardLayout cl = (CardLayout) ToastPanel.this.manager.getCards().getLayout();
						cl.show(ToastPanel.this.manager.getCards(), GlobalUI.LoginPanel);
					}
				}, 60000L);
			}
		});
	}
	public StudentSurveyPanel getStudentSurveyPanel(){
		return StudentSurveyPanel;
	}
	public void setMessage(String message) {
		messageToast.setText(message);
	}
	// 70% see notifications, 30% will see survey
	// below .7 / .3
	public void generateRandomPanel(double rand) {
		if (rand < .3) {
			CardLayout cl = (CardLayout) welcomeCards.getLayout();
			cl.show(this.getCards(), GlobalUI.StudentSurveyPanel);
		} else {
			checkForNotifications();
			CardLayout cl = (CardLayout) welcomeCards.getLayout();
			cl.show(this.getCards(), GlobalUI.NotificationCard);
		}

	}
	public JPanel getCards() {
		return welcomeCards;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (exitButton == e.getSource()) {
			CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
			cl.show(this.manager.getCards(), GlobalUI.LoginPanel);
		}

	}
	public void checkForNotifications(){
		//if the arrayList is empty
		if (this.manager.getAllNotifications().size() == 0){
			NotificationCard.setNoNotifications();
		}
			
	}
	public StudentSurveyPanel getSurveyPanel(){
		return StudentSurveyPanel;
	}
}
