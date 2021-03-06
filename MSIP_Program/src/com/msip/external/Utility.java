package com.msip.external;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.msip.db.LoginTable;
import com.msip.manager.MISPCore;
import com.msip.model.Student;
import com.msip.ui.GlobalUI;

public class Utility {

	/**
	 * Intended to hash the password then store its output
	 * 
	 * @param password
	 * @return hash of string
	 * @throws NoSuchAlgorithmException
	 */
	public static String getHashedPassword(String password) throws NoSuchAlgorithmException {
		return hashsha256(password);
	}

	public static String hashsha256(String password) throws NoSuchAlgorithmException {
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		byte[] passBytes = password.getBytes();
		byte[] passHash = sha256.digest(passBytes);
		return new String(passHash);
	}

	/**
	 * iterate over strings and show in the label every delayInMillis
	 * 
	 * @param label
	 * @param strings
	 * @param delayInMillis
	 */
	public static void iterateLabel(final JLabel label, final ArrayList<String> strings, long delayInMillis) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			int index = 0;

			public void run() {
				index++;

				if (index >= strings.size()) {
					index = 0;
				}
				fadingAnimation(label);
				label.setText(strings.get(index));
				label.setForeground(new Color(0, 0, 0, 255));
			}

		}, delayInMillis, delayInMillis);
	}

	/**
	 * Have label iterate every delayInMillis and show welcome in different
	 * languages
	 * 
	 * @param label
	 * @param delayInMillis
	 */
	public static void iterateWelcome(JLabel label, long delayInMillis) {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("Welcome!");
		strings.add("Aloha!");
		strings.add("Benvenuto!");
		strings.add("Welcome!");
		strings.add("Bienvenido!");
		strings.add("Shalom!");
		iterateLabel(label, strings, delayInMillis);
	}

	/**
	 * fade label out and in if not reset
	 * 
	 * @param label
	 */
	public static void fadingAnimation(JLabel label) {
		int alpha = 255;
		int increment = -10;

		for (int i = 0; i < alpha; i++) {
			try {
				Thread.sleep(20);
				alpha += increment;
				if (alpha >= 255) {
					alpha = 255;
					increment = -increment;
				}
				if (alpha <= 0) {
					alpha = 0;
					increment = -increment;
				}
				label.setForeground(new Color(0, 0, 0, alpha));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks for a valid knumber length
	 * 
	 * @param tf
	 * @return
	 */
	public static boolean isValidKNumberLength(JTextField tf) {
		if (tf.getText().length() == GlobalUI.kNumMax) {
			return true;
		}
		return false;
	}

	/**
	 * Checks to see if we have matching passwords
	 * 
	 * @param tf
	 * @param tf2
	 * @return
	 */
	public static boolean isValidPasswordFieldsMatch(JTextField tf, JTextField tf2) {
		if (tf.getText().compareTo(tf2.getText()) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Checks for the password strength and validity
	 * 
	 * @param tf
	 * @return
	 */
	public static boolean isValidPasswordStrength(JTextField tf) {
		boolean containsDigit = false;

		if (tf.getText() == null) {
			return false;
		}

		if (tf.getText().length() <= 8) {
			return false;
		}

		for (char ch : tf.getText().toCharArray()) {
			if (Character.isDigit(ch))
				containsDigit = true;
		}
		return containsDigit;

	}

	public static void ImportStudentsFromCSVFile(MISPCore mispCore, String absolutePath)
			throws IOException, SQLException {
		BufferedReader br = new BufferedReader(new FileReader(absolutePath));
		String line = "";
		try {

			// Read Header of File, first line
			br.readLine();

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] data = line.split(",");
				String lastName = data[0];
				String firstName = data[1];
				String major = data[2];
				int kNumber = removeKNumber(data[3]);
				
				mispCore.addStudent(new Student(firstName, lastName, kNumber, major));

			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void importLoginsFromCSVFile(String absolutePath, LoginTable table)
			throws IOException, SQLException, ParseException {
		
		BufferedReader br = new BufferedReader(new FileReader(absolutePath));
		String line = "";
		try {

			// Read Header of File, first line
			//LastName, First name, Week, ID, Date, Semester, Month, Knumber
			br.readLine();

			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] data = line.split(",");
				if(data.length == 0){
					break;
				}
				
				String lastName = (data[0]); // Last Name
				String fistName = (data[1]); // First Name
				int week = Integer.parseInt(data[2]); // Week
				int id = Integer.parseInt(data[3]); // ID
				String date = (data[4]); // Date //
				//http://www.mkyong.com/java/how-to-convert-string-to-date-java/
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date formatedDate = formatter.parse(date);
				
				
				String semster = (data[5]); // Semester
				String month = (data[6]); // Month
				int Knumber = removeKNumber(data[7]); // Knumber
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(formatedDate);
				cal.set(Calendar.MILLISECOND, 0);
				table.addUsingDates(Knumber, new Timestamp(cal.getTimeInMillis()));

			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static int removeKNumber(String string) throws NumberFormatException {

		int number = 0;
		if (string.startsWith("k") || string.startsWith("K")) {
			String knumber = string.substring(1);
			number = Integer.parseInt(knumber);
		}

		return number;
	}
}
