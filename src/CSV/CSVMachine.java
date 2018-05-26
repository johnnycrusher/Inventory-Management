/**
 *This class provides CSV features to the project
 *@author Tom
 *@version 1.0
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
import Delivery.OrdinaryTruck;
import Delivery.RefrigeratedTruck;
import Delivery.Truck;
import Exception.CSVFormatException;
import Exception.DeliveryException;
import Exception.StockException;
import Stock.Item;
import Stock.Stock;

public class CSVMachine {
	/**
	 * This method is used to read item_properties.csv and convert the contents into a stock object. This is primarily used for initialization
	 * @param filePath used to specify the file location that needs to be read from
	 * @return initStock - returns a stock object of alls the item properties read from the file
	 * @throws CSVFormatException throws an error when the csv format is different
	 * @throws IOException throws an error when there is an IO error
	 * @throws StockException throws an error when there a problem dealing with a stock object
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
//	            	System.out.println(item_Name);
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

                	//Create an item with the property variables
                	Item item = new Item(item_Name,cost,price,reorderPoint,reorderAmount,temperature);
	            	
                	//Add the item into the initStock object
                	initStock.addItem(item,0);
	            }
	        } catch (IOException e) { //Catch an invalid file exception
	        	//Throw IOException to CSVFormatException
	        	e.printStackTrace();
	        	throw new CSVFormatException(e.getMessage());
	        }
		//Return the initial stock object
		return initStock;
	}
	
	/**A Write method to generate a CSV manifest document. 
	* The method accepts manifest object and writes to the CSV document
	*@author Tom
	*@param manifest the manifest object that needs to be written
	*@param filePath the file path of where the manifest csv needs to be saved
	*@throws CSVFormatException throws an error when there a csv format error
	*@throws IOException throws an error when there is an IO error
	*@throws StockException throws an error when there a problem that occurs with the stock
	*@throws DeliveryException throws an error when there is a problem with the delivery object
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
        	e.printStackTrace();
        	//Throw IOException to CSVFormatException
        	throw new CSVFormatException(e.getMessage());
        }
	}
	
	
	/** Reads a manifest and creates a manifest object that adhears to manifest csv
	 * @param filePath the file path of the manifest csv
	 * @param inventory the intial inventory that will be used to import stock
	 * @return manifest - the manifest object that is outputed from the manifest csv
	 * @throws CSVFormatException throws an error when there a csv format error
	 * @throws IOException throws an error when there  is an IO error
	 * @throws DeliveryException throws an error when there is a delivery error
	 * @throws StockException throws an error when there is a stock error
	 */
	public static Manifest readManifest(String filePath, Stock inventory) throws CSVFormatException, IOException, DeliveryException, StockException{
		//try hook the CSV reader
		Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVReader csvReader = new CSVReader(reader);
        //read the csv file and store all the entries into a list<String[]>
		List<String[]> manifestRecords = csvReader.readAll();
		//create a manifest object
		Manifest manifest = new Manifest();
		
		//define the stock objects
		Stock currentStock = new Stock();
		Stock totalStock = new Stock();
		int truckCounter = 0;
		
		//intialise important booleans
		boolean newTruck = false;
		boolean firstTruck = true;
		
		//for loop to scan in all whole manifestRecord list
		for(int index = 0; index < manifestRecords.size(); index++) {
			//if the current csv line has exactly 1 entry then it creates a truck object
			if(manifestRecords.get(index).length == 1) {
				//gets the string of the CSV file
				String truckObject = manifestRecords.get(index)[0];
				//determines if its a refrigerated truck or oridinary truck
				if(truckObject.equals(">Refrigerated")) {
					//define it as a new truck
					newTruck = true;
					//create a refrigerated truck object and add it to the manifest
					Truck refrigeratedTruck = new RefrigeratedTruck();
					manifest.addTruck(refrigeratedTruck);
					//increment truck counter
					truckCounter++;
				}else if(truckObject.equals(">Ordinary")) {
					//define it as a new truck
					newTruck = true;
					//create a refrigerated truck object and add it to the manifest
					Truck OrdinaryTruck = new OrdinaryTruck();
					manifest.addTruck(OrdinaryTruck);
					//increment truck counter
					truckCounter++;
				}else {
					throw new CSVFormatException("Truck number " + truckCounter + " is an invalid truck type");
				}
			//if the current csv line has exactly 2 entries then it creates a item object and stores it in a stock obj
			}else if(manifestRecords.get(index).length == 2) {
				//if its a new truck then add cargo 
				if(newTruck) {
					//if not first truck then add the previous stock to the truck and create new stock object
					if(!firstTruck) {
						manifest.addCargoDirectlyToTruck(currentStock, truckCounter - 2);
						currentStock = new Stock();
					}else {
						//if first truck then create new stock
						firstTruck = false;
						currentStock = new Stock();
					}
				}
				//if not the last record
				if(!(index == manifestRecords.size()-1)) {
					//set new truck to false
					newTruck = false;
					//get the item name and properties and quantity
					String itemName = manifestRecords.get(index)[0];
					int itemQty = Integer.parseInt(manifestRecords.get(index)[1]);
					Item itemObject = inventory.getItem(itemName);
					
					//add the item and quantity to the stock object
					currentStock.addItem(itemObject, itemQty);
					try {
						//add the item to the stock object
						totalStock.addItem(itemObject, itemQty);
					}catch(StockException error) {
						//if it already exist then add the quantity
						totalStock.addQuantity(itemName, itemQty);
					}
				//if it is the last record
				}else {
					//get the item name and properties and quantity
					String itemName = manifestRecords.get(index)[0];
					int itemQty = Integer.parseInt(manifestRecords.get(index)[1]);
					Item itemObject = inventory.getItem(itemName);
					//add the item and quantity to the stock object
					currentStock.addItem(itemObject, itemQty);
					try {
						//add the item to the stock object
						totalStock.addItem(itemObject, itemQty);
					}catch(StockException error) {
						//if it already exist then add the quantity
						totalStock.addQuantity(itemName, itemQty);
					}
					//load the stock onto the truck
					manifest.addCargoDirectlyToTruck(currentStock, truckCounter - 1);
					//import the total stock 
					manifest.importTotalStock(totalStock);
				}
			}else {
				throw new CSVFormatException("Row " + index + " does not adhear the the manifest format");
			}
		}	
		return manifest;
	}
	
	/**
	 * A method which returns a hashmap of all item names and their variables which exist within the manifest.csv
	 * @param filePath the file path of the sales log csv
	 * @return a HashMap containing Item names and their quantities
	 * @throws CSVFormatException throws an error when the sales log isn't in the correct format
	 * @throws IOException throws an error when there is an IO problem
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
	        	throw new CSVFormatException(e.getMessage());
	        }
		//Return the initial manifest HashMap
		return salesLog;
	}
}
