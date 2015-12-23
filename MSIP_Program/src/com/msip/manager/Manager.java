package com.msip.manager;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.msip.ui.AdminTools;
import com.msip.ui.LoginWindow;



public class Manager {
	private JFrame frame;
	public static void main(String[] args) {
		// TODO Class will have instances of the UI, External, and DB.
		System.out.println("Hello Manger!");

		// 1. Start Main UI window
		// 2. When number is received from RS232 then fill in field in UI
		// 3. UI should handle it appropraitely.
	
	// Need Global instances of UI, External, and DB objects.
	// External object will need an instance of Manager
	// UI will need a instance of Manager

		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try {
					Manager window = new Manager();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		});
	}

		public Manager() {
			JPanel cards = new JPanel();
			cards.setLayout(new CardLayout());
			cards.add((new LoginWindow(this)), "login");
			cards.add(new AdminTools() , "admin");
			frame = new JFrame();
			frame.setBounds(0, 0, 800, 420);
			frame.add(cards);
		
	
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		// TODO are we going use a hash instead of Knumber. Just an idea?
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
