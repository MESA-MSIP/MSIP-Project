package com.msip.db;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class SurveyTableLables {
	private static final int Value1_INDEX = 0;
	private static final int Value2_INDEX = 1;
	private static final int Value3_INDEX = 2;
	private static final int Value4_INDEX = 3;
	private static final int Value5_INDEX = 4;

	public SurveyTableLables() {
		try {
			PreparedStatement createTable = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("CREATE TABLE IF NOT EXISTS SurveyLabels(ID INT AUTO_INCREMENT, Value1 VARCHAR(100), Value2 VARCHAR(100),"
							+ "Value3 VARCHAR(100), Value4 VARCHAR(100), Value5 VARCHAR(100), PRIMARY KEY(ID))");
			createTable.executeUpdate();
			System.out.println("Successfully created the Survey table labels");
		} catch (SQLException e) {
			System.out.println("Failed to create the survey tabel labels"
					+ "\n");
			e.printStackTrace();
		}
	}

	/**
	 * Adds the labels to the Survey label table.
	 * 
	 * @param labels
	 */
	public void addResults(ArrayList<String> labels) {
		try {
			PreparedStatement addLabels = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("INSERT INTO SurveyLabels VALUE(NULL, '"
							+ labels.get(0) + "', '" + labels.get(1) + "', '"
							+ labels.get(2) + "', '" + labels.get(3) + "', '"
							+ labels.get(4) + "');");
			addLabels.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns all the labels in an arraylist.
	 * 
	 * @return
	 */
	public ArrayList<String> getValueLables() {
		ArrayList<String> allLabels = new ArrayList<String>();
		try {
			PreparedStatement retrieveAllLabels = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("SELECT * FROM SurveyLabels;");
			ResultSet rs = retrieveAllLabels.executeQuery();
			while (rs.next()) {
				allLabels.add(Value1_INDEX, rs.getString("Value1"));
				allLabels.add(Value2_INDEX, rs.getString("Value2"));
				allLabels.add(Value3_INDEX, rs.getString("Value3"));
				allLabels.add(Value4_INDEX, rs.getString("Value4"));
				allLabels.add(Value5_INDEX, rs.getString("Value5"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allLabels;

	}

	/**
	 * removes all the labels from the table.
	 */
	public void clear() {
		try {
			PreparedStatement removeAll = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("DELETE FROM SurveyLabels;");
			removeAll.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
