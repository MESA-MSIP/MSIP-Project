package com.msip.manager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import com.msip.db.DBConnector;
import com.msip.db.Global;
import com.msip.external.SerialPort;
import com.msip.ui.AdminToolsPanel;
import com.msip.ui.GlobalUI;
import com.msip.ui.LoginPanel;
import java.awt.Dimension;

/**
 * @author Juan Zepeda, Christian Martinez, Fernado Estevez
 *
 */
public class MISPCore {

	private JPanel cards; //a panel that uses CardLayout
	private SerialPort serialport;
	private DBConnector dbConnector;

	/**
	 * 
	 */
	public MISPCore() {

		if (Global.ISPI) {
			serialport = new SerialPort(this);
		}
		if (Global.ISDB) {
			dbConnector = new DBConnector();
		}
	}

	/**
	 * @param contentPane
	 */
	private void addComponentToPane(Container contentPane) {
         
        //Create the cards
        JPanel loginPanel = new LoginPanel(this);
        JPanel adminToolsPanel = new AdminToolsPanel(this);
         
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(loginPanel, GlobalUI.LoginPanel);
        cards.add(adminToolsPanel, GlobalUI.AdminToolsPanel);
         
        contentPane.add(cards, BorderLayout.CENTER);
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
	
	
	/**
	 * @return cards
	 */
	public JPanel getCards() {
		return cards;
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
