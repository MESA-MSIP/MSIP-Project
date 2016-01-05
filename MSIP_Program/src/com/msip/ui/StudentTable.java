package com.msip.ui;

import java.util.ArrayList;

import javax.swing.JTable;
import com.msip.model.Student;
import com.msip.model.StudentTableModel;

public class StudentTable extends JTable {

	private static final long serialVersionUID = 1L;

	public StudentTable(ArrayList<Student> students) {
		super(new StudentTableModel(students));
		
		this.setFillsViewportHeight(true);
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setResizingAllowed(false);
	}
}
