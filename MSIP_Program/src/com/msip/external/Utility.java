package com.msip.external;

import java.awt.Color;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JTextField;

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
}
