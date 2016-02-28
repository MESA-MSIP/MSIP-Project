/**
 * 
 */
package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.msip.manager.MISPCore;

/**
 * @author juanz
 *
 */
public class NotificationsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNotifications;
	private JTable tableNotifications;

	public NotificationsPanel(MISPCore msipCore) {

		/**
		 * Create the panel.
		 */

		setPreferredSize(new Dimension(700, 380));
		setLayout(new BorderLayout(0, 0));

		tableNotifications = new JTable();
		add(tableNotifications, BorderLayout.CENTER);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		add(horizontalStrut, BorderLayout.WEST);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		add(horizontalStrut_1, BorderLayout.EAST);

		Component verticalStrut = Box.createVerticalStrut(20);
		add(verticalStrut, BorderLayout.NORTH);

		JPanel panelNotificationInput = new JPanel();
		panelNotificationInput.setPreferredSize(new Dimension(10, 100));
		add(panelNotificationInput, BorderLayout.SOUTH);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(307, 65, 75, 29);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNotification();
			}

		});
		panelNotificationInput.setLayout(null);
		panelNotificationInput.add(btnAdd);

		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(394, 65, 93, 29);
		panelNotificationInput.add(btnRemove);

		textFieldNotifications = new JTextField();
		textFieldNotifications.setBounds(6, 6, 289, 63);
		panelNotificationInput.add(textFieldNotifications);
		textFieldNotifications.setColumns(10);

	}

	private void addNotification() {

		// TODO Auto-generated method stub

	}

	// TODO Auto-generated constructor stub

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
