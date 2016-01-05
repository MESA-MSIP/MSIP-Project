package com.msip.external;

	import java.io.File;
	import java.io.FileWriter;
	import java.io.IOException;
	import com.csvreader.CsvWriter;

	public class CSVMaker {
		private String firstColumn;//Name of the first column.
		private String secondColumn;//Name of the second column.
		private String thirdColumn;//Name of the third column.

		/**
		 * Makes header names.
		 * @param firstColumn
		 * @param secondColumn
		 * @param thirdColumn
		 */
		public void makeCSVHeader(String firstColumn, String secondColumn,
				String thirdColumn) {
			this.firstColumn = firstColumn;
			this.secondColumn = secondColumn;
			this.thirdColumn = thirdColumn;
		}
		
	/**
	 * Creates the csv file.
	 * @param studentName
	 * @param timesPresent
	 * @param dates
	 * @param fileName
	 * @throws IOException
	 */
		public void CSVFile(String studentName, String timesPresent, String[] dates, String fileName){
			
			//TODO were will the csv file save to.
			String file = "C:/Users/estev_000/Desktop/" + fileName + ".csv";
			boolean alreadyExists = new File(file).exists();
			
			try {
				
				//Set to true so when we append more data to the same file, the data would not be overwritten.
				CsvWriter csvOutput = new CsvWriter(new FileWriter(file, true), ',');
				//checks if the file name already exists.
				//If it doesn't it adds new headers to the csv file.
				if (!alreadyExists) {
					csvOutput.write(firstColumn);
					csvOutput.write(secondColumn);
					csvOutput.write(thirdColumn);
					csvOutput.endRecord();//Indicates end
				}
				
				csvOutput.write(studentName);
				csvOutput.write(timesPresent);
				csvOutput.write(dates[0]);
				csvOutput.endRecord();
				
				for (int i = 1; i < dates.length; i++) {
					csvOutput.write("");
					csvOutput.write("");
					csvOutput.write(dates[i]);
					csvOutput.endRecord();
				}
				
				csvOutput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			

		}

		public static void main(String[] args) throws IOException {

			CSVMaker csv = new CSVMaker();
			String[] dates = { "12/3/15", "12/4/15", "12/5/15", "12/6/15" };
			String[] dates2 = { "1/3/16", "1/4/16" };
			csv.makeCSVHeader("name", "number of times present", "Dates present");
			csv.CSVFile("Fernando Estevez", "4", dates, "newData");
			csv.CSVFile("fernando1", "2", dates2, "newData");
		}

	}




