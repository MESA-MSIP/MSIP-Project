package com.msip.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;

public class SurveyTable {
	private String dateString = null;
	private static final int RESULT1_INDEX = 0;
	private static final int RESULT2_INDEX = 1;
	private static final int RESULT3_INDEX = 2;
	private static final int RESULT4_INDEX = 3;
	private static final int RESULT5_INDEX = 4;
	/**
	 * Creates the Survey table if it doesn't all ready exist.
	 */
	public SurveyTable() {
		try {
			PreparedStatement createTable = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("CREATE TABLE IF NOT EXISTS Survey(ID INT AUTO_INCREMENT, Question VARCHAR(400), StartDate DATE NOT NULL,"
							+ " Response1 INT, Response2 INT, Response3 INT, Response4 INT, Response5 INT, PRIMARY KEY(ID))");
			createTable.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the question to the Survey table.
	 * 
	 * @param question
	 */
	public void addQuestion(String question, Date startDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 dateString = formatter.format(startDate);
		try {	
			PreparedStatement addQuestion = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("INSERT INTO Survey VALUE(NULL, '"
							+ question + "', '" + dateString
							+ "' , 0, 0, 0, 0, 0);");
		addQuestion.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * returns the last inserted ID.
	 * @return
	 */
	private int getID(){
		ArrayList<Integer> id = new ArrayList<Integer>();
		try {
			PreparedStatement myID = (PreparedStatement) DBConnector.myConnection.prepareStatement("SELECT ID FROM SURVEY;");
			ResultSet rs = myID.executeQuery();
			while(rs.next()){
				id.add(rs.getInt("ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id.get(id.size() -1);
	}
	
	/**
	 * Adds the last question result to the survey table.
	 */
	public void addResults(int result) {		
		try {
			
			if(result == 1) {//when user chooses a rating of 1 it will update the response1 column
				PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
						.prepareStatement("UPDATE Survey SET Response1= Response1 + 1 WHERE ID='"+getID()+"';");
				insert.executeUpdate();
				
			} else if(result == 2) { //when user chooses a rating of 2 it will update the response2 column
				PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
						.prepareStatement("UPDATE Survey SET Response2= Response2 + 1 WHERE ID='"+getID()+"';");
				insert.executeUpdate();

			} else if(result == 3) {//when user chooses a rating of 3 it will update the response3 column

				PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
						.prepareStatement("UPDATE Survey SET Response3= Response3 + 1 WHERE ID='"+getID()+"';");
				insert.executeUpdate();
			} else if(result == 4) {//when user chooses a rating of 4 it will update the response4 column

				PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
						.prepareStatement("UPDATE Survey SET Response4= Response4 + 1 WHERE ID='"+getID()+"';");
				insert.executeUpdate();
				
			} else if(result == 5) {//when user chooses a rating of 5 it will update the response5 column
				PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
						.prepareStatement("UPDATE Survey SET Response5= Response5 + 1 WHERE ID='"+getID()+"';");
				insert.executeUpdate();
			} else {
				//TODO how to handle when 5 < x;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  returns the results in an array.
	 * the first index is set to response 1
	 * second index is set to response 2
	 * third index is set to response 3
	 * fourth index is set to response 4
	 * Fifth index is set to response 5.
	 * @return
	 */
	public ArrayList<Integer> getResults(){
		ArrayList<Integer> results = new ArrayList<Integer>();
		try{
			PreparedStatement retrieveResults = (PreparedStatement) DBConnector.myConnection.prepareStatement("SELECT * FROM Survey;");
			ResultSet rs = retrieveResults.executeQuery();
			while(rs.next()){
				results.add(RESULT1_INDEX , rs.getInt("Response1"));
				results.add(RESULT2_INDEX , rs.getInt("Response2"));
				results.add(RESULT3_INDEX , rs.getInt("Response3"));
				results.add(RESULT4_INDEX , rs.getInt("Response4"));
				results.add(RESULT5_INDEX , rs.getInt("Response5"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return results;
	}
	
	public String getQuestion(){
		String question = "";
		try{
			PreparedStatement retrieveQues = (PreparedStatement) DBConnector.myConnection.prepareStatement
					("SELECT * FROM Survey WHERE ID=" + getID() + ";");
			ResultSet rs = retrieveQues.executeQuery();
			while(rs.next()){
				question = rs.getString("Question");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return question;
		
	}
	
	/**
	 * Removes the survey question based on the date.
	 * 
	 * @param endDate
	 */
	public void removeFromSurveyTable(Date endDate) {
		try {
			PreparedStatement delete = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("DELETE FROM Survey WHERE Date ='"
							+ endDate + "';");
			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Clears the survey Table.
	 */
	public void removeAll(){
		//TODO Reset the ID to start from 0.
		try {
			PreparedStatement removeAll = (PreparedStatement) DBConnector.myConnection.prepareStatement("DELETE FROM Survey;");
			removeAll.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
