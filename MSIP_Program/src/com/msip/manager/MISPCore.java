package com.msip.manager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.msip.external.SerialPort;
import com.msip.external.Utility;
import com.msip.model.Admin;
import com.msip.model.Student;
import com.msip.model.Student.ParcipitationState;
import com.msip.ui.AdminToolsPanel;
import com.msip.ui.GlobalUI;
import com.msip.ui.LoginPanel;
import com.msip.ui.WelcomePanel;

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
	private LoginPanel loginPanel;
	private WelcomePanel welcomePanel;
	private SurveyTable surveyTable;

	public MISPCore() {

		if (Global.ISPI) {
			serialport = new SerialPort(this);
		}
		dbConnector = new DBConnector();
		adminTable = new AdminTable();
		studentTable = new StudentTable();
		loginTable = new LoginTable();
		notificationTable = new NotificationTable();
		surveyTable = new SurveyTable();
	}

	/**
	 * @param contentPane
	 */
	private void addComponentToPane(Container contentPane) {

		// Create the cards
		welcomePanel = new WelcomePanel(this);
		loginPanel = new LoginPanel(this, welcomePanel);
		JPanel adminToolsPanel = new AdminToolsPanel(this);

		// Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		cards.add(loginPanel, GlobalUI.LoginPanel);
		cards.add(adminToolsPanel, GlobalUI.AdminToolsPanel);
		cards.add(welcomePanel, GlobalUI.WelcomePanel);

		contentPane.add(cards, BorderLayout.CENTER);
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
			studentTable.modify(student.getkNumber(), student.getMajor());
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
		studentTable.modify(student.getkNumber(), student.getMajor());
		// TODO can modify Name of student too
	}

	public ParcipitationState isStudentActive(Integer Knumber) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date lastWeek = c.getTime();

		Calendar c2 = Calendar.getInstance();
		c2.setTime(lastWeek);
		c2.add(Calendar.DAY_OF_WEEK, -14);
		Date twoWeeks = c2.getTime();
		Date now = new Date();

		if ((twoWeeks.before(now)) && (lastWeek.before(now))) {

			if ((0 <= loginTable.getParticipation(Knumber).size())
					&& (loginTable.getParticipation(Knumber).size() <= LOW_BOUNDARY)) {
				return ParcipitationState.LOW_ACTIVE_STUDENT;
			} else if ((LOW_BOUNDARY <= loginTable.getParticipation(Knumber)
					.size())
					&& (loginTable.getParticipation(Knumber).size() <= MEDIAN_BOUNDARY)) {
				return ParcipitationState.MEDIAN_ACTIVE_STUDENT;
			} else {
				return ParcipitationState.HIGH_ACTIVE_STUDENT;
			}
		}
		return null;
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
		adminTable.modify(admin.getkNumber(), admin.getpHash());
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

	public void removeNotification(Date expirationDate) {
		notificationTable.removeFromNotificationTable(expirationDate);
	}

	public void removeExpiredNotifications() {
		notificationTable.removeExpiredNotification();

	}

	// **********************************************************//
	// **********************************************************//
	// *** Survey Table Functions ****//
	// **********************************************************//
	// **********************************************************//
	/** Getting results from survey table **/
	public ArrayList<Integer> getResults() {
		return surveyTable.getResults();
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
