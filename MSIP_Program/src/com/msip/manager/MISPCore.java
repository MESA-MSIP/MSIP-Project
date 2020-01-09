package com.msip.manager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.msip.db.AdminTable;
import com.msip.db.DBConnector;
import com.msip.db.Global;
import com.msip.db.LoginTable;
import com.msip.db.NotificationTable;
import com.msip.db.StudentTable;
import com.msip.db.SurveyTable;
import com.msip.db.SurveyTableLables;
import com.msip.external.SerialPort;
import com.msip.external.Utility;
import com.msip.model.Admin;
import com.msip.model.Notification;
import com.msip.model.Student;
import com.msip.model.Student.ParcipitationState;
import com.msip.ui.AdminToolsPanel;
import com.msip.ui.DateTimeDialog;
import com.msip.ui.GlobalUI;
import com.msip.ui.WelcomePanel;
import com.msip.ui.StudentLoginPanel;

/**
 * @author Juan Zepeda, Christian Martinez, Fernando Estevez, Celina Lazaro
 *
 */
public class MISPCore {

	private JPanel cards; // a panel that uses CardLayout
	private SerialPort serialport;
	private DBConnector dbConnector;
	private AdminTable adminTable;
	private StudentTable studentTable;
	private LoginTable loginTable;
	private static final int LOW_BOUNDARY = 3;
	private static final int MEDIAN_BOUNDARY = 6;
	private NotificationTable notificationTable;
	private WelcomePanel loginPanel;
	private StudentLoginPanel welcomePanel;
	private SurveyTable surveyTable;
	private static AdminToolsPanel adminToolsPanel;
	private SurveyTableLables surveyTableLables;

	public MISPCore() {

		if (Global.ISPI) {
			serialport = new SerialPort(this);
		}
		//TODO Should DBConnector instantiate all the other tables and MISP should only instantiate DBConnector?
		System.out.println("Accessing MySQL DBConnector...");
		dbConnector = new DBConnector();
		adminTable = new AdminTable();
		studentTable = new StudentTable();
		loginTable = new LoginTable();
		notificationTable = new NotificationTable();
		surveyTable = new SurveyTable();
		surveyTableLables = new SurveyTableLables();
	}

	/**
	 * @param contentPane
	 */
	private void addComponentToPane(Container contentPane) {

		// Create the cards
		welcomePanel = new StudentLoginPanel(this);
		loginPanel = new WelcomePanel(this, welcomePanel);
		adminToolsPanel = new AdminToolsPanel(this);

		// Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		cards.add(loginPanel, GlobalUI.LoginPanel);
		cards.add(adminToolsPanel, GlobalUI.AdminToolsPanel);
		cards.add(welcomePanel, GlobalUI.WelcomePanel);

		contentPane.add(cards, BorderLayout.CENTER);
	}

	public StudentLoginPanel getToastPanel() {
		return welcomePanel;
	}

	/**
	 * @return cards
	 */
	public JPanel getCards() {
		return cards;
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {


		
		// Create and set up the window.
		JFrame frame = new JFrame("MSIP");
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(800, 480));
		frame.setMinimumSize(new Dimension(800, 480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		MISPCore window = new MISPCore();
		window.addComponentToPane(frame.getContentPane());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
		
		//Setup the StartUpItem DateTimeDialog
		DateTimeDialog dateTimeDialog = new DateTimeDialog("Set Date and Time", adminToolsPanel, Dialog.DEFAULT_MODALITY_TYPE);
		dateTimeDialog.isAlwaysOnTop();
		dateTimeDialog.setVisible(true);
		

	}

	// **********************************************************//
	// **********************************************************//
	// *** Login Entries Functions ****//
	// **********************************************************//
	// **********************************************************//

	/**
	 * Called by UI to make an entry in the login entry DB.
	 * 
	 * @param kNumber
	 * @return if successful entry was made. 1 success and 0 failed.
	 */
	public int logStudent(int kNumber) {
		try {
			loginTable.add(kNumber);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("MISPCore.logStudent() SQLException Error ");
			return GlobalUI.FAIL;
		}
		return GlobalUI.SUCCESS;
	}

	/**
	 * Return the dates for student that has logged in within startDate and End
	 * Date if knumber is null then it returns all the students
	 * 
	 * @param Knumber
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ArrayList<Date> getStudentDataRange(int Knumber, Date startDate,
			Date endDate) {
		return loginTable.getEntryRange(Knumber, startDate, endDate);
	}

	/**
	 * Called by External Interface to set the number that was scanned UI then
	 * gets notified that a number was scanned
	 * 
	 * @param kNumber
	 */
	public void setScannedNumber(int kNumber) {
		loginPanel.setScannedNumber(kNumber);
	}

	// **********************************************************//
	// **********************************************************//
	// *** Student Functions ****//
	// **********************************************************//
	// **********************************************************//

	/**
	 * Called by UI to see if student exists in the Student DB.
	 * 
	 * @param kNumber
	 * @return If student exists return 1, if not 0.
	 */
	public int isStudent(int kNumber) {
		try {
			if (studentTable.getInfo(kNumber) == null) {
				return GlobalUI.FAIL;
			}
		} catch (SQLException e) {
			return GlobalUI.FAIL;
		}
		return GlobalUI.SUCCESS;
	}

	/**
	 * Get all students in the DB
	 * 
	 * @return
	 */
	public ArrayList<Student> getStudents() {
		return studentTable.getAll();
	}

	/**
	 * Deletes the student from the student table
	 * 
	 * @param kNumber
	 */
	public void deleteStudent(int kNumber) throws SQLException {
		studentTable.remove(kNumber);
	}

	/**
	 * Add Student to Database
	 * 
	 * @param student
	 */
	public void addStudent(Student student) throws SQLException {
		int STUDENT_EXISTS = 1;

		if (isStudent(student.getkNumber()) == STUDENT_EXISTS) {
			// TODO update there name?
			studentTable.modify(student.getkNumber(), student.getFirstName(),
					student.getLastName(), student.getMajor());
		} else {
			studentTable.add(student.getkNumber(), student.getFirstName(),
					student.getLastName(), student.getMajor());
		}
	}

	/**
	 * Modify Student in Database
	 * 
	 * @param student
	 * @throws SQLException
	 */
	public void modifyStudent(Student student) throws SQLException {
		studentTable.modify(student.getkNumber(), student.getFirstName(),
				student.getLastName(), student.getMajor());
		// TODO can modify Name of student too
	}

	public ParcipitationState isStudentActive(int Knumber) {
		int participation = loginTable.getParticipation(Knumber).size();

		if ((0 <= participation) && (participation <= LOW_BOUNDARY)) {
			return ParcipitationState.LOW_ACTIVE_STUDENT;
		} else if ((LOW_BOUNDARY <= participation)
				&& (participation <= MEDIAN_BOUNDARY)) {
			return ParcipitationState.MEDIAN_ACTIVE_STUDENT;
		} else {
			return ParcipitationState.HIGH_ACTIVE_STUDENT;
		}
	}

	// **********************************************************//
	// **********************************************************//
	// *** Admin Functions ****//
	// **********************************************************//
	// **********************************************************//

	/**
	 * Called by UI to see if admin exists in the admin table
	 * 
	 * @param kNumber
	 * @return If Admin return 1, if not 0.
	 */
	public int isAdmin(int kNumber) {
		try {
			if (adminTable.getInfo(kNumber) == null) {
				return GlobalUI.FAIL;
			}
		} catch (SQLException e) {
			return GlobalUI.FAIL;
		}
		return GlobalUI.SUCCESS;
	}

	/**
	 * Called by UI to see if admin exists in the admin table and correct
	 * password was entered.
	 * 
	 * @param kNumber
	 * @param pHash
	 * @return If Admin and password are correct return 1, if not 0.
	 */
	public int verifyAdmin(int kNumber, String pHash) {

		String hashedPasswordFromUser;
		try {
			hashedPasswordFromUser = Utility.getHashedPassword(pHash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return GlobalUI.FAIL;
		}

		// If returns null.. knumber not in DB
		if (adminTable.getPhash(kNumber) == null) {
			return GlobalUI.FAIL;
		}

		// hash's don't match so fail
		if (hashedPasswordFromUser.compareTo(adminTable.getPhash(kNumber)) != 0) {
			return GlobalUI.FAIL;
		}

		return GlobalUI.SUCCESS;
	}

	/**
	 * Get all Admins in the DB
	 * 
	 * @return ArrayList<Admin>
	 */
	public ArrayList<Admin> getAdmins() {
		return adminTable.getAll();
	}

	public void addAdmin(Admin admin) throws SQLException {
		adminTable.add(admin.getkNumber(), admin.getFirstName(),
				admin.getLastName(), admin.getpHash());

	}

	/**
	 * Modify Admin in Database
	 * 
	 * @param student
	 * @throws SQLException
	 */
	public void modifyAdmin(Admin admin) throws SQLException {
		adminTable.modify(admin.getkNumber(), admin.getFirstName(),
				admin.getLastName(), admin.getpHash());
		// TODO can modify Names too..
	}

	/**
	 * Deletes the admin from the student table
	 * 
	 * @param kNumber
	 * @throws SQLException
	 */
	public void deleteAdmin(int kNumber) throws SQLException {
		adminTable.remove(kNumber);
	}

	// **********************************************************//
	// **********************************************************//
	// *** Notifications Functions ****//
	// **********************************************************//
	// **********************************************************//
	public void addNotification(String notification, Date startDate,
			Date expirationDate) {
		notificationTable.addToNotificationTable(notification, startDate,
				expirationDate);

	}

	public void removeNotification(String notification) {
		notificationTable.removeNotification(notification);
	}

	public void removeExpiredNotifications() {
		notificationTable.removeExpiredNotification();

	}

	public NotificationTable getNotificationTable() {
		return notificationTable;
	}

	public ArrayList<Notification> getAllNotifications() {
		return notificationTable.getAllNotification();

	}

	// **********************************************************//
	// **********************************************************//
	// *** Survey Table Functions ****//
	// **********************************************************//
	// **********************************************************//
	/** Getting results from survey table **/
	public SurveyTable getSurveyTable() {
		return surveyTable;
	}

	public ArrayList<Integer> getResults() {
		return surveyTable.getResults();
	}

	public void removeAll() {
		surveyTable.removeAll();
	}

	public void addQuestion(String question, Date StartDate) {
		surveyTable.addQuestion(question, StartDate);
	}

	public void addResults(int result) {
		surveyTable.addResults(result);
	}

	public String getQuestion() {
		return surveyTable.getQuestion();
	}

	public int getID() {
		return surveyTable.getID();
	}

	public SurveyTableLables getSurveyTableLables() {
		return surveyTableLables;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
