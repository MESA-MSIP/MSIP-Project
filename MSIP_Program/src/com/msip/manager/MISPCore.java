package com.msip.manager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.msip.db.AdminTable;
import com.msip.db.DBConnector;
import com.msip.db.Global;
import com.msip.db.LoginTable;
import com.msip.db.StudentTable;
import com.msip.external.SerialPort;
import com.msip.model.Admin;
import com.msip.model.Student;
import com.msip.model.Student.ParcipitationState;
import com.msip.ui.AdminToolsPanel;
import com.msip.ui.GlobalUI;
import com.msip.ui.LoginPanel;

import java.awt.Dimension;

/**
 * @author Juan Zepeda, Christian Martinez, Fernando Estevez
 *
 */
public class MISPCore {

	private JPanel cards; // a panel that uses CardLayout
	private SerialPort serialport;
	private DBConnector dbConnector;
	private AdminTable adminTable;
	private StudentTable studentTable;
	private LoginTable loginTable;
	private static final int ACTIVE_STUDENT = 3;

	public MISPCore() {

		if (Global.ISPI) {
			serialport = new SerialPort(this);
		}
		dbConnector = new DBConnector();
		adminTable = new AdminTable();
		studentTable = new StudentTable();
		loginTable = new LoginTable();
	}

	/**
	 * @param contentPane
	 */
	private void addComponentToPane(Container contentPane) {

		// Create the cards
		JPanel loginPanel = new LoginPanel(this);
		JPanel adminToolsPanel = new AdminToolsPanel(this);

		// Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		cards.add(loginPanel, GlobalUI.LoginPanel);
		cards.add(adminToolsPanel, GlobalUI.AdminToolsPanel);

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
	 * Called by External Interface to set the number that was scanned UI then
	 * gets notified that a number was scanned
	 * 
	 * @param kNumber
	 */
	public void setScannedNumber(int kNumber) {
		LoginPanel.setScannedNumber(kNumber);
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
			studentTable.getInfo(kNumber);
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
		studentTable.add(student.getkNumber(), student.getFirstName(), student.getLastName(), student.getMajor());
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
		LoginTable login = new LoginTable();

		if (login.getParticipation(Knumber).size() >= ACTIVE_STUDENT) {
			return ParcipitationState.ACTIVE;
		} else {
			return ParcipitationState.INACTIVE;
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
			if(adminTable.getInfo(kNumber) == null){
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

	public void addAdmin(Admin admin) throws SQLException  {
		adminTable.add(admin.getkNumber(),admin.getFirstName(), admin.getLastName(), admin.getpHash());
		
	}
	
	/**
	 * Modify Admin in Database
	 * 
	 * @param student
	 * @throws SQLException 
	 */
	public void modifyAdmin(Admin admin) throws SQLException {
		adminTable.modify(admin.getkNumber(), admin.getpHash());
		//TODO can modify Names too.. 
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
