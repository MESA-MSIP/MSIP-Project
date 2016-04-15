package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.msip.manager.MISPCore;

public class NotificationCard extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextPane txtpnNotification;
	private MISPCore manager;
	private DefaultStyledDocument document;

	/**
	 * Create the panel.
	 */

	public NotificationCard(MISPCore manager) {

		/**
		 * Create the panel.
		 */

		this.setManager(manager);
		setLayout(new BorderLayout(0, 0));

		Component horizontalStrut = Box.createHorizontalStrut(20);
		add(horizontalStrut, BorderLayout.WEST);

		Component verticalStrut = Box.createVerticalStrut(20);
		add(verticalStrut, BorderLayout.NORTH);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		add(verticalStrut_1, BorderLayout.SOUTH);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		add(horizontalStrut_1, BorderLayout.EAST);

		txtpnNotification = new JTextPane();
		txtpnNotification.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtpnNotification.setEditable(false);
		txtpnNotification.setText("Notification");
		add(txtpnNotification, BorderLayout.CENTER);
		document = (DefaultStyledDocument) txtpnNotification.getDocument();
		StyledDocument doc = txtpnNotification.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		JScrollPane notificationScrollPane = new JScrollPane(txtpnNotification);
		add(notificationScrollPane, BorderLayout.CENTER);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateNotification();
			}
		});
	}

	public void setNoNotifications() {
		txtpnNotification.setText("No Notifications.");
	}

	public void setManager(MISPCore manager) {
		this.manager = manager;
	}

	public MISPCore getManager() {
		return manager;
	}

	public void updateNotification() {
		try {
			if (manager.getAllNotifications().size() > 0) {

				for (int i = 0; i < manager.getAllNotifications().size(); i++) {
					if (!isInDocumnet(manager.getAllNotifications().get(i).getNotification())) {
						document.insertString(document.getEndPosition()
								.getOffset(), manager.getAllNotifications()
								.get(i).getNotification()
								+ "\n\n", null);
					}
				}
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * returns true if the word all ready exist in document.
	 * @param word
	 * @return
	 */
	public boolean isInDocumnet(String word) {
		String txt = txtpnNotification.getText();
		String[] textArray = txt.split("\n");
		for (int i = 0; i < textArray.length; i++) {
			if (word.equals(textArray[i])) {
				return true;
			}
		}

		return false;

	}
}
