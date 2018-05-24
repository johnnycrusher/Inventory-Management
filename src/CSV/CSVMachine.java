/**
 *This class provides CSV features to the project
 *@author Tom
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

public class CSVMachine {
	
	/**This method is used to read item_properties.csv and convert the contents into a stock object. This is primarily used for initialization
	* @author Tom
	* @param String filePath
	* @returns initStock, a Stock object which contains all items in the item_properties.csv and the starting quantity of 0
	*/
	public static Stock readItemProperties(String filePath) throws CSVFormatException, IOException, StockException{
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
		try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
	         CSVReader csvReader = new CSVReader(reader);) 
			{
	            // Reading Records One by One in a String array
	            String[] nextRecord;
	            
	            //While the reader has another record, read the record
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
//	            	System.out.println(Double.toString(cost));
//                	System.out.println(Double.toString(price));
//                	System.out.println(Integer.toString(reorderPoint));
//                	System.out.println(Integer.toString(reorderAmount));
//                	System.out.println(Integer.toString(temperature));
                	
                	//Create an item with the property variables
                	Item item = new Item(item_Name,cost,price,reorderPoint,reorderAmount,temperature);
	            	
                	//Add the item into the initStock object
                	initStock.add(item,0);
	            }
	        } catch (IOException e) { //Catch an invalid file exception
	        	//Throw IOException to CSVFormatException
	        	throw new CSVFormatException(e.toString());
	        }
		//Return the initial stock object
		return initStock;
	}
	
	/**A Write method to generate a CSV manifest document. 
	* The method accepts manifest object and writes to the CSV document
	*@author Tom
	*@param manifest
	*@param filePath
	*@throws CSVFormatException
	*@throws IOException
	*@throws StockException
	*@throws DeliveryException
	*/
	public static void writeManifest(Manifest manifest, String filePath) throws CSVFormatException, IOException, StockException, DeliveryException{
        try (Writer writer = Files.newBufferedWriter(Paths.get(filePath));
        		CSVWriter csvWriter = new CSVWriter(writer,
								                    CSVWriter.DEFAULT_SEPARATOR,
								                    CSVWriter.NO_QUOTE_CHARACTER,
								                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
								                    CSVWriter.RFC4180_LINE_END);)
        {
        	for (int index=0; index < manifest.getAllTrucks().size(); index++) {
        		//Get the truck object
        		Truck truck = manifest.getAllTrucks().get(index);
        		
        		//Get the truck's stock object
        		Stock truckStock = truck.getStock();
        		
        		//Get the truckStockList from the stock object
        		HashMap<Item, Integer> truckStockList = truckStock.returnStockList();
        		
        		//check if the truck is a refrigerated truck
        		if (truck.getTemp() <= 10) { 
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
        } catch (IOException e) { //Catch an invalid file exception
        	//Throw IOException to CSVFormatException
        	throw new CSVFormatException(e.toString());
        }
	}
	
	/**
	 * A method which returns a hashmap of all item names and their variables which exist within the manifest.csv
	 * @param String filePath
	 * @returns a HashMap containing Item names and their quantities
	 * @throws CSVFormatException
	 * @throws IOException
	 */
	public static HashMap<String, Integer> readManifest(String filePath) throws CSVFormatException, IOException{
		//set up item property variables
		String item_Name = null;
		int quantity = 0;
		
		//Create the manifest's HashMap
		HashMap<String, Integer> manifest = new HashMap<String, Integer>();		
		 
		//try hook the CSV reader
		try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
	         CSVReader csvReader = new CSVReader(reader);) 
			{
	            // Reading Records One by One in a String array
	            String[] nextRecord;
	            
	            while ((nextRecord = csvReader.readNext()) != null) {
	                if (nextRecord.length > 1) {
	                	//Store the item properties in variables
		            	item_Name = nextRecord[0];
		            	quantity = Integer.parseInt(nextRecord[1]);
		            	
		            	//Check if item already exist in manifest
		            	if (manifest.containsKey(item_Name)) {
		            		//If it does, add to quantity
		            		manifest.put(item_Name, manifest.get(item_Name) + quantity);
		            	} else {
		            		//Add to manifest
			            	manifest.put(item_Name, quantity);
		            	}
	                }
	            	
	            }
			} catch (IOException e) { //Catch an invalid file exception
	        	//Throw IOException to CSVFormatException
	        	throw new CSVFormatException(e.toString());
	        }
		//Return the initial manifest HashMap
		return manifest;
	}
	
	/**
	 * A method which returns a hashmap of all item names and their variables which exist within the manifest.csv
	 * @param String filePath
	 * @returns a HashMap containing Item names and their quantities
	 * @throws CSVFormatException
	 * @throws IOException
	 */
	public static HashMap<String, Integer> readSalesLog(String filePath) throws CSVFormatException, IOException{
		//set up item property variables
		String item_Name = null;
		int quantity = 0;
		
		//Create the manifest's HashMap
		HashMap<String, Integer> salesLog = new HashMap<String, Integer>();		
		 
		//try hook the CSV reader
		try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
	         CSVReader csvReader = new CSVReader(reader);) 
			{
	            // Reading Records One by One in a String array
	            String[] nextRecord;
	            
	            while ((nextRecord = csvReader.readNext()) != null) {
                	//Store the item properties in variables
	            	item_Name = nextRecord[0];
	            	quantity = Integer.parseInt(nextRecord[1]);

	            	//Add to salesLog
	            	salesLog.put(item_Name, quantity);
	            }
			} catch (IOException e) { //Catch an invalid file exception
	        	//Throw IOException to CSVFormatException
	        	throw new CSVFormatException(e.toString());
	        }
		//Return the initial manifest HashMap
		return salesLog;
	}
}
