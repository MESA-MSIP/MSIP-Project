
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

import com.msip.db.NotificationTable;
import com.msip.db.SurveyTable;
import com.msip.manager.MISPCore;
//package com.msip.ui;



public class StudentLoginPanel extends JPanel implements ActionListener {
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
	private StudentNotificationCardPanel StudentNotificationCardPanel;
	private StudentLoginPanel studentLoginPanel;
	private SurveyTable surveyTable;
	private StudentNotificationCardPanel studentNotificationCardPanel;
	private NotificationTable notificationTable;
	private JPanel layoutPanel;

	public StudentLoginPanel(final MISPCore manager) {
		setBackground(GlobalUI.GLOBAL_BACKGROUND_COLOR);
		this.manager = manager;
		this.studentLoginPanel = this;
		surveyTable = this.manager.getSurveyTable();
		studentNotificationCardPanel = new StudentNotificationCardPanel(manager);
		this.notificationTable = manager.getNotificationTable();
		setBounds(new Rectangle(0, 0, 800, 480));
		// Construct ConditionCards
		setLayout(new CardLayout(0, 0));

		layoutPanel = new JPanel();
		add(layoutPanel, "n");
		layoutPanel.setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		layoutPanel.add(panel, BorderLayout.NORTH);
		panel.setBorder(new MatteBorder(0, 0, 1, 0, GlobalUI.blackColor));
		panel.setBackground(GlobalUI.GLOBAL_BACKGROUND_COLOR);

		horizontalStrut_1 = Box.createHorizontalStrut(180);
		panel.add(horizontalStrut_1);

		messageToast = new JLabel("Message");
		messageToast.setFont(new Font("Tahoma", Font.PLAIN, 40));
		messageToast.setForeground(Color.black);
		messageToast.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(messageToast);

		horizontalStrut = Box.createHorizontalStrut(110);
		panel.add(horizontalStrut);

		exitButton = new JButton();

		ImageIcon iconoff = CreateIcon("exitoff.png", 75, 25);
		ImageIcon iconon = CreateIcon("exiton.png", 75, 25);
		exitButton.setIcon(iconoff);
		exitButton.setBackground(GlobalUI.GLOBAL_BACKGROUND_COLOR);
		exitButton.setBorder(null);
		exitButton.setRolloverEnabled(true);
		exitButton.setRolloverIcon(iconon);
		exitButton.addActionListener(this);
		panel.add(exitButton);

		welcomeCards = new JPanel();
		layoutPanel.add(welcomeCards, BorderLayout.CENTER);
		welcomeCards.setLayout(new CardLayout(0, 0));
		// Construct welcomeCards
		StudentSurveyPanel = new StudentSurveyPanel(this.manager, timer);
		StudentNotificationCardPanel = new StudentNotificationCardPanel(this.manager);
		StudentNotificationCardPanel.setVisible(false);

		// Add the Cards to the JPanel
		welcomeCards.add(StudentNotificationCardPanel, GlobalUI.NotificationCard);
		welcomeCards.add(StudentSurveyPanel, GlobalUI.StudentSurveyPanel);


		// Set up A Auto Time-Out for 6 Seconds
		addComponentListener(new ComponentAdapter() {
			Timer timer = new Timer();

			public void componentHidden(ComponentEvent e1) {
				studentNotificationCardPanel.updateNotification();
				timer.cancel();
			}

			public void componentShown(ComponentEvent e) {
				timer = new Timer();
				// Set up Random Generator
				Random randGen = new Random();
				double randChance = randGen.nextDouble();
				//Check for notifications
				generateRandomPanel(randChance);
				StudentNotificationCardPanel.updateNotification();
				// If there is no Question, Show the Notification Card
				if (surveyTable.getID() == -2) {
					//checkForNotifications();
					studentNotificationCardPanel.updateNotification();
					CardLayout cl = (CardLayout) welcomeCards.getLayout();
					cl.show(studentLoginPanel.getCards(), GlobalUI.NotificationCard);
				} else {
					// If The Question Length does not meet the questionLength,
					// show NotificationCard.
					if (surveyTable.getQuestion().length() < GlobalUI.minQuestionLength) {
						//checkForNotifications();
						studentNotificationCardPanel.updateNotification();

						CardLayout cl = (CardLayout) welcomeCards.getLayout();
						cl.show(studentLoginPanel.getCards(),
								GlobalUI.NotificationCard);
					} else {
						// Set the Text of the StudentSurveyPanel to the
						// Question
						String question = surveyTable.getQuestion();
						StudentSurveyPanel.setQuestion(question);
					}

				}
				int numOfNotifications = notificationTable.notificationSize();
				int timeOutDelay = 15000 * numOfNotifications;

				timer.schedule(new TimerTask() {

					public void run() {
						// Auto Exits after 60 Seconds.
						CardLayout cl = (CardLayout) StudentLoginPanel.this.manager
								.getCards().getLayout();
						cl.show(StudentLoginPanel.this.manager.getCards(),
								GlobalUI.LoginPanel);
					}
				}, timeOutDelay);
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
			studentNotificationCardPanel.updateNotification();
			CardLayout cl = (CardLayout) welcomeCards.getLayout();
			cl.show(this.getCards(), GlobalUI.NotificationCard);
		}

	}

	public JPanel getCards() {
		return welcomeCards;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Exit from the ToastPanel
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
			StudentNotificationCardPanel.setNoNotifications();
		}
	}

	public StudentSurveyPanel getSurveyPanel() {
		return StudentSurveyPanel;
	}
}

