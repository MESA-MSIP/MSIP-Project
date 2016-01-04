package com.msip.ui;

import javax.swing.JTable;

import com.msip.model.AdminTableModel;

public class AdminTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	public AdminTable() {
		setModel(new AdminTableModel());
	}

}
