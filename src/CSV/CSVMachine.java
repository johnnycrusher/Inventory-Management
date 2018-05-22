/**
 * 
 */
package CSV;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import Delivery.Manifest;
import Delivery.Truck;
import Exception.CSVFormatException;
import Exception.DeliveryException;
import Exception.StockException;
import Stock.Item;
import Stock.Stock;

/**
 *This class provides CSV features to the project
 *@author Tom
 */
public class CSVMachine {
	private static final String STRING_ARRAY_SAMPLE = "./string-array-sample.csv";
	private static final String ITEM_PROPERTIES_CSV_FILE_PATH = "item_properties.csv";
	private static final String MANIFEST_CSV_FILE_PATH = "manifest.csv";
	/*private static final String SALES_LOG_0_CSV_FILE_PATH = "sales_log_0.csv";
	private static final String SALES_LOG_1_CSV_FILE_PATH = "sales_log_1.csv";
	private static final String SALES_LOG_2_CSV_FILE_PATH = "sales_log_2.csv";
	private static final String SALES_LOG_3_CSV_FILE_PATH = "sales_log_3.csv";
	private static final String SALES_LOG_4_CSV_FILE_PATH = "sales_log_4.csv";*/

	//This method is used to read item_properties.csv and convert the contents into a stock object. This is primarily used for initialization
	public static Stock readItemProperties() throws CSVFormatException, IOException, StockException{
		//set up item property variables
		String item_Name;
		double cost;
		double price;
		int reorderPoint;
		int reorderAmount;
		int temperature;
		int ordinaryTemp = 11;
		
		//Create the initStock object
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
	            	System.out.println(item_Name);
	            	cost = Integer.parseInt(nextRecord[1]);
	            	price = Integer.parseInt(nextRecord[2]);
	            	reorderPoint = Integer.parseInt(nextRecord[3]);
	            	reorderAmount = Integer.parseInt(nextRecord[4]);
	            	
	            	//Check if a temperature for the item is specified, 
	            	if (nextRecord.length > 5) {
                		temperature = Integer.parseInt(nextRecord[5]);
                    } else { 	//Else assign the item's temperature to 11 (meaning ordinary)
                    	temperature = ordinaryTemp;
                    }
	            	
	            	//TESTING PURPOSES
	            	System.out.println(Double.toString(cost));
                	System.out.println(Double.toString(price));
                	System.out.println(Integer.toString(reorderPoint));
                	System.out.println(Integer.toString(reorderAmount));
                	System.out.println(Integer.toString(temperature));
                	
                	//Create an item with the property variables
                	Item item = new Item(item_Name,cost,price,reorderPoint,reorderAmount,temperature);
	            	
                	//Add the item into the initStock object
                	initStock.add(item,0);
	            }
	        }
		//Return the initial stock object
		return initStock;
	}
	
	//A testing method which writes a line to Manifest.csv
	public static void writeLineToManifest(String string) throws CSVFormatException, IOException{
        try (Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE));
        		CSVWriter csvWriter = new CSVWriter(writer,
								                    CSVWriter.DEFAULT_SEPARATOR,
								                    CSVWriter.NO_QUOTE_CHARACTER,
								                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
								                    CSVWriter.RFC4180_LINE_END);)
        {
       		csvWriter.writeNext(new String[]{string});
        }
	}
	
	//A Write method to generate a CSV manifest document. The method accepts manifest object and returns true upon successful writing
	public static void writeManifest(Manifest manifest) throws CSVFormatException, IOException, StockException, DeliveryException{
        try (Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE));
        		CSVWriter csvWriter = new CSVWriter(writer,
								                    CSVWriter.DEFAULT_SEPARATOR,
								                    CSVWriter.NO_QUOTE_CHARACTER,
								                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
								                    CSVWriter.RFC4180_LINE_END);)
        {
        	for (int index=0; index < manifest.getAllTrucks().size() - 1; index++) {
        		//Get the truck object
        		Truck truck = manifest.getAllTrucks().get(index);
        		
        		//Get the truck's stock object
        		Stock truckStock = truck.getStock();
        		
        		//Get the truckStockList from the stock object
        		HashMap<Item, Integer> truckStockList = truckStock.returnStockList();
        		
        		//check if the truck is a refrigerated truck
        		if (truck.getTemp() > 10) { 
        			// the truck is refrigerated
        			csvWriter.writeNext(new String[] {">Refrigerated"});
        			//iterate through every item in the truck's cargo
        			for (Item item : truckStockList.keySet()) {
        				//Write the item and it's quantity to the .csv file
        				csvWriter.writeNext(new String[] {item.getItemName(),Integer.toString( truckStock.getItemQuantity(item.getItemName()))});
        			}
        		} else { 
        			//the truck is an ordinary truck
        			csvWriter.writeNext(new String[] {">Ordinary"});
        			//iterate through every item in the truck's cargo
        			for (Item item : truckStockList.keySet()) {
        				//Write the item and it's quantity to the .csv file
        				csvWriter.writeNext(new String[] {item.getItemName(),Integer.toString( truckStock.getItemQuantity(item.getItemName()))});
        			}
        		}
        	}
        }
	}
	
	//TO DO
	public String readManifest() throws CSVFormatException, IOException{
		//set up item property variables
		
		//String item_Name;
		//int quantity;
		//try hook the CSV reader
		try (Reader reader = Files.newBufferedReader(Paths.get(MANIFEST_CSV_FILE_PATH));
	         CSVReader csvReader = new CSVReader(reader);) 
			{
	            // Reading Records One by One in a String array
	            List<String[]> buffer = csvReader.readAll();
	            buffer.toString().split(">");
	            
	            System.out.println(buffer.toString());
	            return buffer.toString();
			}
	}
	
	//TO DO
	public String readSalesLog() throws CSVFormatException{
		
		return null;
	}
}
