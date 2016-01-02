package com.msip.manager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.msip.db.DBConnector;
import com.msip.db.Global;
import com.msip.external.SerialPort;
import com.msip.ui.CardFunctions;

// TODO Class will have instances of the UI, External, and DB.

// 1. Start Main UI window
// 2. When number is received from RS232 then fill in field in UI
// 3. UI should handle it appropriately.

// External object will need an instance of Manager
// UI will need a instance of Manager
public class Manager {

	private JFrame frame;
	private JPanel cardPanels;

	private SerialPort serialport;
	private DBConnector dbConnector;

	public Manager() {
		
		if (Global.ISPI) {
			serialport = new SerialPort(this);
		}
		if (Global.ISDB) {
			dbConnector = new DBConnector();
		}
		
		CardFunctions cf = new CardFunctions(this);
		cardPanels = cf.setupCards();
		frame = new JFrame();
		frame.setBounds(0, 0, 800, 420);
		frame.add(cardPanels);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager window = new Manager();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Called by UI to make an entry in the login entry DB.
	 * 
	 * @param kNumber
	 * @return if successful entry was made. 1 success and 0 failed.
	 */
	public int logStudent(int kNumber) {
		return 0;
		// TODO log student in the Login Table DB
	}

	/**
	 * Called by External Interface to set the number that was scanned UI then
	 * gets notified that a number was scanned
	 * 
	 * @param kNumber
	 */
	public void setScannedNumber(int kNumber) {
		// TODO call UI function to notify that a number was scanned
	}

	/**
	 * Called by UI to see if student exists in the Student DB.
	 * 
	 * @param kNumber
	 * @return If student exists return 1, if not 0.
	 */
	public int isStudent(int kNumber) {
		// TODO See function summary
		return 1;
	}

	/**
	 * Called by UI to see if admin exists in the admin table and correct
	 * password was entered.
	 * 
	 * @param kNumber
	 * @param pHash
	 * @return If Admin and password are correct return 1, if not 0.
	 */
	public int isAdmin(int kNumber, String pHash) {
		// TODO See function summary
		return 1;
	}
}
