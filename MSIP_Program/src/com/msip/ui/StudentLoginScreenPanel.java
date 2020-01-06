package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import com.msip.db.SurveyTable;
import com.msip.manager.MISPCore;

public class StudentLoginScreenPanel extends JPanel implements ActionListener {
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
	private StudentLoginScreenPanel studentLoginScreenPanel;
	private SurveyTable surveyTable;
	private NotificationCard notificationCard;

	private JPanel layoutPanel;

	public StudentLoginScreenPanel(final MISPCore manager) {
		setBackground(Color.WHITE);
		this.manager = manager;
		this.studentLoginScreenPanel = this;
		surveyTable = this.manager.getSurveyTable();
		notificationCard = new NotificationCard(manager);

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

		exitButton = new JButton();

		ImageIcon icon = CreateIcon("Exit2.png", 60, 60);

		exitButton.setBorder(GlobalUI.blackBorder);
		exitButton.setIcon(icon);
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
				notificationCard.updateNotification();
				timer.cancel();
			}

			public void componentShown(ComponentEvent e) {
				timer = new Timer();
				// Set up Random Generator
				Random randGen = new Random();
				double randChance = randGen.nextDouble();
				//Check for notifications
				generateRandomPanel(randChance);
				NotificationCard.updateNotification();
				// If there is no Question, Show the Notification Card
				if (surveyTable.getID() == -2) {
					//checkForNotifications();
					notificationCard.updateNotification();
					CardLayout cl = (CardLayout) welcomeCards.getLayout();
					cl.show(studentLoginScreenPanel.getCards(), GlobalUI.NotificationCard);
				} else {
					// If The Question Length does not meet the questionLength,
					// show NotificationCard.
					if (surveyTable.getQuestion().length() < GlobalUI.minQuestionLength) {
						//checkForNotifications();
						notificationCard.updateNotification();

						CardLayout cl = (CardLayout) welcomeCards.getLayout();
						cl.show(studentLoginScreenPanel.getCards(),
								GlobalUI.NotificationCard);
					} else {
						// Set the Text of the StudentSurveyPanel to the
						// Question
						String question = surveyTable.getQuestion();
						StudentSurveyPanel.setQuestion(question);
					}

				}

				timer.schedule(new TimerTask() {

					public void run() {
						// Auto Exits after 60 Seconds.
						CardLayout cl = (CardLayout) StudentLoginScreenPanel.this.manager
								.getCards().getLayout();
						cl.show(StudentLoginScreenPanel.this.manager.getCards(),
								GlobalUI.LoginPanel);
					}
				}, 5000);
			}
		});
	}

	public ImageIcon CreateIcon(String filename, int width, int height) {
		InputStream url = getClass().getResourceAsStream("/images/" + filename);
		BufferedImage img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon image = new ImageIcon(img.getScaledInstance(width, height, 0));
		return image;
	}

	public StudentSurveyPanel getStudentSurveyPanel() {
		return StudentSurveyPanel;
	}

	public void setMessage(String message) {
		messageToast.setText(message);
	}

	/**
	 * Generates a Random Chance to see either the StudentSurveyPanel or
	 * NotificationCard.
	 * 
	 * @param rand
	 */
	public void generateRandomPanel(double rand) {
		if (rand < .3) {
			CardLayout cl = (CardLayout) welcomeCards.getLayout();
			cl.show(this.getCards(), GlobalUI.StudentSurveyPanel);
		} else {
			checkForNotifications();
			notificationCard.updateNotification();
			CardLayout cl = (CardLayout) welcomeCards.getLayout();
			cl.show(this.getCards(), GlobalUI.NotificationCard);
		}

	}

	public JPanel getCards() {
		return welcomeCards;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Exit from the StudentLoginScreenPanel
		if (exitButton == e.getSource()) {
			StudentSurveyPanel.clearRButtons();
			CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
			cl.show(this.manager.getCards(), GlobalUI.LoginPanel);
		}

	}

	/**
	 * Checks If there is any Notifications in the DB.
	 */
	public void checkForNotifications() {
		// if the arrayList is empty
		if (this.manager.getAllNotifications().size() == 0) {
			NotificationCard.setNoNotifications();
		}
	}

	public StudentSurveyPanel getSurveyPanel() {
		return StudentSurveyPanel;
	}
}
