package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.msip.db.NotificationTable;
import com.msip.manager.MISPCore;
import com.msip.model.Notification;
import com.toedter.calendar.JDateChooser;

/**
 * @author Celina
 *
 */
public class NotificationsPanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private JTextArea textAreaNotifications;
	private JTable tableNotifications;
	private JDateChooser startDateChooser;
	private Date selectedStartDate;
	private JDateChooser expirationDateChooser;
	private Date selectedExpirationDate;
	private JButton btnRemove;
	private JButton btnAdd;
	private MISPCore manager;
	private DefaultTableModel model;
	private ArrayList<String> notiArray = new ArrayList<String>();
	private int rowIndex;
	private AdminToolsPanel adminToolsPanel;
	private NotificationTable notificationTable;
	private JScrollPane notificationScrollPane;
	private NotificationsPanel notificationsPanel;

	public NotificationsPanel(MISPCore msipCore, AdminToolsPanel adminToolsPanel) {
		this.setManager(msipCore);
		this.setAdminToolsPanel(adminToolsPanel);

		notificationTable = this.manager.getNotificationTable();
		notificationsPanel = this;

		setPreferredSize(new Dimension(700, 380));
		setLayout(new BorderLayout(0, 0));

		tableNotifications = createJTable();

		tableNotifications.getTableHeader().setReorderingAllowed(false);

		notificationScrollPane = new JScrollPane(tableNotifications);
		add(notificationScrollPane, BorderLayout.CENTER);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		add(horizontalStrut, BorderLayout.WEST);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		add(horizontalStrut_1, BorderLayout.EAST);

		Component verticalStrut = Box.createVerticalStrut(20);
		add(verticalStrut, BorderLayout.NORTH);

		JPanel panelNotificationInput = new JPanel();
		panelNotificationInput.setPreferredSize(new Dimension(10, 130));
		add(panelNotificationInput, BorderLayout.SOUTH);

		btnAdd = new JButton("Add");
		btnAdd.setEnabled(false);
		btnAdd.setBounds(276, 85, 100, GlobalUI.BUTTONHEIGHT);
		btnAdd.setFont(GlobalUI.GlobalFont);
		btnAdd.setPreferredSize(new Dimension(100, GlobalUI.BUTTONHEIGHT));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNotification();
				textAreaNotifications.setText(null);

			}

		});
		panelNotificationInput.setLayout(null);
		panelNotificationInput.add(btnAdd);

		btnRemove = new JButton("Remove");
		btnRemove.setFont(GlobalUI.GlobalFont);
		btnRemove.setPreferredSize(new Dimension(100, GlobalUI.BUTTONHEIGHT));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteNotification();
			}
		});
		btnRemove.setBounds(396, 85, 100, GlobalUI.BUTTONHEIGHT);
		panelNotificationInput.add(btnRemove);
		textAreaNotifications = new JTextArea();
		textAreaNotifications.setPreferredSize(new Dimension(4, 40));
		textAreaNotifications.setWrapStyleWord(true);
		textAreaNotifications.setLineWrap(true);
		textAreaNotifications.addKeyListener(this);

		textAreaNotifications.setAlignmentX(SwingConstants.LEFT);
		textAreaNotifications.setAlignmentY(SwingConstants.NORTH);

		textAreaNotifications.addFocusListener(new FocusListener() {//
					public void focusGained(FocusEvent e) {

					}

					public void focusLost(FocusEvent e) {

					}

				});
		JScrollPane noteScrollPane = new JScrollPane(textAreaNotifications);
		noteScrollPane.setBounds(21, 35, 355, 39);
		panelNotificationInput.add(noteScrollPane);

		// Start Date
		startDateChooser = new JDateChooser();
		startDateChooser.setBounds(386, 35, 137, 39);
		panelNotificationInput.add(startDateChooser);
		// Adds todays date.
		Date date1 = new Date();
		startDateChooser.setDate(date1);
		selectedStartDate = startDateChooser.getDate();
		startDateChooser.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
						// When the user picks a date it sets it to the text box
						// and retrieves that date.
						Date prevStartDate = selectedStartDate;
						selectedStartDate = startDateChooser.getDate();

						if (selectedStartDate == null) {
							getAdminToolsPanel().setStatusMsg(
									"Please set a correct start date.");
							selectedStartDate = prevStartDate;
						}
					}
				});

		expirationDateChooser = new JDateChooser();
		expirationDateChooser.setBounds(538, 35, 137, 39);
		panelNotificationInput.add(expirationDateChooser);
		// sets current date
		Date date = new Date();
		expirationDateChooser.setDate(date);

		JLabel lblEnterANew = new JLabel("Enter a new notification:");
		lblEnterANew.setBounds(21, 0, 355, 39);
		lblEnterANew.setFont(GlobalUI.LableFont);
		panelNotificationInput.add(lblEnterANew);

		JLabel lblStartDate = new JLabel("Start Date:");
		lblStartDate.setBounds(386, 0, 137, 39);
		lblStartDate.setFont(GlobalUI.LableFont);
		panelNotificationInput.add(lblStartDate);

		JLabel lblEndDate = new JLabel("End Date:");
		lblEndDate.setBounds(538, 0, 137, 39);
		lblEndDate.setFont(GlobalUI.LableFont);
		panelNotificationInput.add(lblEndDate);

		selectedExpirationDate = expirationDateChooser.getDate();
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				updateTable();
			}

			public void componentHidden(ComponentEvent e) {
				updateTable();
			}
		});

		SimpleDateFormat formatter = new SimpleDateFormat("MMM/dd/yyyy");
		String tomorrowsDateString = ZonedDateTime.now().plusDays(1)
				.format(DateTimeFormatter.ofPattern("MMM/dd/yyyy"));
		Date tomorrowsDate = null;
		try {
			tomorrowsDate = formatter.parse(tomorrowsDateString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		expirationDateChooser.setDate(tomorrowsDate);
		expirationDateChooser.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
						// When the user picks a date it sets it to the text box
						// and retrieves that date.
						Date prevExpirationDate = selectedExpirationDate;
						selectedExpirationDate = expirationDateChooser
								.getDate();

						if (selectedExpirationDate == null) {
							getAdminToolsPanel().setStatusMsg(
									"Please set a correct expiration date.");
							selectedExpirationDate = prevExpirationDate;
						}
					}
				});
	}

	/**
	 * @return the manager
	 */
	public MISPCore getManager() {
		return manager;
	}

	/**
	 * @param manager
	 *            the manager to set
	 */
	public void setManager(MISPCore manager) {
		this.manager = manager;
	}

	/**
	 * @param adminToolsPanel
	 *            the adminToolsPanel to set
	 */
	public void setAdminToolsPanel(AdminToolsPanel adminToolsPanel) {
		this.adminToolsPanel = adminToolsPanel;
	}

	/**
	 * @return the adminToolsPanel
	 */
	public AdminToolsPanel getAdminToolsPanel() {
		return adminToolsPanel;
	}

	// **********************************************************//
	// **********************************************************//
	// *** Add Notification Functions ****//
	// **********************************************************//
	// **********************************************************//
	private void addNotification() {
		if (textAreaNotifications.getText().length() == 0) {

		} else {
			model = (DefaultTableModel) tableNotifications.getModel();

			DateFormat dateStart = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			DateFormat dateEnd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String reportDate = dateStart.format(selectedStartDate);
			String reportEndDate = dateEnd.format(selectedExpirationDate);
			String note = textAreaNotifications.getText().trim();
			String st[] = { note, reportDate, reportEndDate };
			model.addRow(st);

			manager.addNotification(note, selectedStartDate,
					selectedExpirationDate);
			// adds the notification to an array list.
			notiArray.add(note);
		}
	}

	// **********************************************************//
	// **********************************************************//
	// *** Delete Notification Functions ****//
	// **********************************************************//
	// **********************************************************//

	private void deleteNotification() {

		if (tableNotifications.isRowSelected(tableNotifications
				.getSelectedRow())) {
			// removes the specific notification from the DB table.
			manager.removeNotification(notiArray.get(rowIndex));
			// removes the specific notification from the arraylist to match the
			// size of the table.
			notiArray.remove(rowIndex);

			// clears expired notifications
			model = (DefaultTableModel) tableNotifications.getModel();
			if (tableNotifications.getSelectedRow() != -1) {
				// remove selected row from the model
				model.removeRow(tableNotifications.getSelectedRow());
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		String data = textAreaNotifications.getText();
		if (data.isEmpty()) {
			btnAdd.setEnabled(false);
		} else {
			btnAdd.setEnabled(true);
		}
	}

	public void removeUpdatedNotification(String notificationText) {
		manager.removeNotification(notificationText);
		// clears expired notifications
		model = (DefaultTableModel) tableNotifications.getModel();
		if (tableNotifications.getSelectedRow() != -1) {
			// remove selected row from the model
			model.removeRow(tableNotifications.getSelectedRow());
		}

	}

	public void updateNotifications(String notficationText, Date startDate,
			Date endDate) {
		model = (DefaultTableModel) tableNotifications.getModel();
		DateFormat dateStart = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat dateEnd = new SimpleDateFormat("MM/dd/yyyy");
		String reportDate = dateStart.format(startDate);
		String reportEndDate = dateEnd.format(endDate);
		String st[] = { notficationText, reportDate, reportEndDate };
		model.addRow(st);

	}

	public JTable createJTable() {
		final JTable newTable = new JTable(new DefaultTableModel(
				new Object[][] {}, new String[] { "Notifications:",
						"Start Date:", "Expiration Date:" }) {
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}

		});
		newTable.setColumnSelectionAllowed(false);
		newTable.getTableHeader().setFont(GlobalUI.GlobalFont);
		newTable.setFont(GlobalUI.GlobalFont);
		newTable.setRowSelectionAllowed(true);
		newTable.setRowHeight(35);
		newTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		newTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// returns the index of a selected notification.
				rowIndex = newTable.getSelectedRow();

				System.out.println(rowIndex);
			}

		});

		return newTable;

	}

	/**
	 * Updates the notification Table in the NotificationPanel.
	 */
	public void updateTable() {
		ArrayList<Notification> notifications = notificationTable
				.getAllNotification();
		ArrayList<String> notificationText = new ArrayList<String>();
		DefaultTableModel dm = (DefaultTableModel) tableNotifications
				.getModel();
		dm.getDataVector().removeAllElements();
		for (int i = 0; i < notifications.size(); i++) {
			Notification n = notifications.get(i);
			updateNotifications(n.getNotification(), n.getStartDate(),
					n.getExpirationDate());
			notificationText.add(n.getNotification());
		}
		notiArray = notificationText;
		tableNotifications.revalidate();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}
}
