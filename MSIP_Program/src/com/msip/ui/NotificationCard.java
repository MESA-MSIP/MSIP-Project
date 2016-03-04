package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.msip.db.NotificationTable;
import com.msip.manager.MISPCore;

public class NotificationCard extends JPanel {
	private static final long serialVersionUID = 1L;
	private MISPCore manager;

	/**
	 * Create the panel.
	 */

	public NotificationCard() {
		NotificationTable nt = new NotificationTable();

		/**
		 * Create the panel.
		 */

		setLayout(new BorderLayout(0, 0));

		Component horizontalStrut = Box.createHorizontalStrut(20);
		add(horizontalStrut, BorderLayout.WEST);

		Component verticalStrut = Box.createVerticalStrut(20);
		add(verticalStrut, BorderLayout.NORTH);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		add(verticalStrut_1, BorderLayout.SOUTH);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		add(horizontalStrut_1, BorderLayout.EAST);

		JTextPane txtpnNotification = new JTextPane();
		txtpnNotification.setText("Notification");
		add(txtpnNotification, BorderLayout.CENTER);
		DefaultStyledDocument document = (DefaultStyledDocument) txtpnNotification
				.getDocument();
		StyledDocument doc = txtpnNotification.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		try {
			for (int i = 0; i < nt.getAllNotification().size(); i++) {
				document.insertString(document.getEndPosition().getOffset(), nt
						.getAllNotification().get(i), null);
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JScrollPane notificationScrollPane = new JScrollPane(txtpnNotification);
		add(notificationScrollPane, BorderLayout.CENTER);
		CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
		cl.show(this.manager.getCards(), GlobalUI.LoginPanel);
	}

}
