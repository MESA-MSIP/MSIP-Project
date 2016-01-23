package com.msip.ui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import com.msip.model.StudentTableModel;

public class StudentTable extends JTable {

	private static final long serialVersionUID = 1L;

	public StudentTable(StudentTableModel studentModel) {
		super(studentModel);
		
		this.setFillsViewportHeight(true);
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setResizingAllowed(false);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
}
