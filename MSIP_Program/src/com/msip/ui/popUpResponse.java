package com.msip.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class popUpResponse extends JFrame {

	public int popUp() {
		JFrame frame = new JFrame();
		frame.setSize(400, 240);
		Object[] options = { "Student", "Admin", "Neither, Log Out" };
		int n = JOptionPane.showOptionDialog(frame, "How Do you Want to Login As?: ",
				"MESSAGE", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[2]);
		return n;
	}
}
