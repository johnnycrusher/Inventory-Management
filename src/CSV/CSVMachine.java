/**
 * 
 */
package CSV;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.CSVReader;

import Exception.CSVFormatException;
import Exception.StockException;
import Stock.Item;
import Stock.Stock;

/**
 *This class provides CSV features to the project
 *@author Tom
 */
public class CSVMachine {
	
	private static final String ITEM_PROPERTIES_CSV_FILE_PATH = "item_properties.csv";
	/*private static final String MANIFEST_CSV_FILE_PATH = "manifest.csv";
	private static final String SALES_LOG_0_CSV_FILE_PATH = "sales_log_0.csv";
	private static final String SALES_LOG_1_CSV_FILE_PATH = "sales_log_1.csv";
	private static final String SALES_LOG_2_CSV_FILE_PATH = "sales_log_2.csv";
	private static final String SALES_LOG_3_CSV_FILE_PATH = "sales_log_3.csv";
	private static final String SALES_LOG_4_CSV_FILE_PATH = "sales_log_4.csv";*/

	//This method is used to read item_properties.csv and convert the contents into a stock object. This is primarily used for initialization
	public Stock readItemProperties() throws CSVFormatException, IOException, StockException{
		//set up item property variables
		String item_Name;
		double cost;
		double price;
		int reorderPoint;
		int reorderAmount;
		
		//not sure if this is an elegant solution
		int ordinaryTemp = 11;
		
		int temperature = 10;
		
		Stock initStock = new Stock();
		
		//try hook the CSV reader
		try (Reader reader = Files.newBufferedReader(Paths.get(ITEM_PROPERTIES_CSV_FILE_PATH));
	         CSVReader csvReader = new CSVReader(reader);) 
			{
	            // Reading Records One by One in a String array
	            String[] nextRecord;
	            
	            while ((nextRecord = csvReader.readNext()) != null) {
	                //Store the item properties in variables
	            	item_Name = nextRecord[0];
	            	cost = Integer.parseInt(nextRecord[1]);
	            	price = Integer.parseInt(nextRecord[2]);
	            	reorderPoint = Integer.parseInt(nextRecord[3]);
	            	reorderAmount = Integer.parseInt(nextRecord[4]);
	            	
	            	//Test if there is a temperature record in the line
	                if (nextRecord[5] != null) {
	                	temperature = Integer.parseInt(nextRecord[5]);
	                	Item item = new Item(item_Name,cost,price,reorderPoint,reorderAmount,temperature);
	                	initStock.add(item,1);
	                } else {
	                	Item item = new Item(item_Name,cost,price,reorderPoint,reorderAmount,ordinaryTemp);
		                initStock.add(item,1);	
	                }
	            }
	        }
		return initStock;
	}
	
	public String writeManifest() throws CSVFormatException{
		return null;
	}
	
	public String readManifest() throws CSVFormatException{
		return null;
	}
	
	public String readSalesLog() throws CSVFormatException{
		return null;
	}
}
