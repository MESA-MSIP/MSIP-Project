package com.msip.ui;

import javax.swing.JTable;

import com.msip.model.StudentTableModel;

public class StudentTable extends JTable {

	private static final long serialVersionUID = 1L;

	public StudentTable() {
		setModel(new StudentTableModel());
	}
}
