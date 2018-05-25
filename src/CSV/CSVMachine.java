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
	 * @param filePath
	 * @returns initStock
	 * @throws CSVFormatException
	 * @throws IOException
	 * @throws StockException
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
        	e.printStackTrace();
        	//Throw IOException to CSVFormatException
        	throw new CSVFormatException(e.getMessage());
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
				e.printStackTrace();
	        	throw new CSVFormatException(e.getMessage());
	        }
		//Return the initial manifest HashMap
		return manifest;
	}
	
	public static Manifest readManifestV2(String filePath, Stock inventory) throws CSVFormatException, IOException, DeliveryException, StockException{
		//try hook the CSV reader
		Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVReader csvReader = new CSVReader(reader);
		List<String[]> records = csvReader.readAll();
		Manifest manifest = new Manifest();
		boolean newTruck = false;
		Stock currentStock = new Stock();
		Stock totalStock = new Stock();
		int TruckCounter = 0;
		boolean firstTruck = true;
		
		for(int index = 0; index < records.size(); index++) {
			if(records.get(index).length == 1) {
				String TruckObject = records.get(index)[0];
				if(TruckObject.equals(">Refrigerated")) {
					newTruck = true;
					Truck refrigeratedTruck = new RefrigeratedTruck();
					manifest.addTruck(refrigeratedTruck);
					TruckCounter++;
				}else {
					Truck OrdinaryTruck = new OrdinaryTruck();
					manifest.addTruck(OrdinaryTruck);
					newTruck = true;
					TruckCounter++;
				}
			}else if(records.get(index).length == 2) {
				if(newTruck) {
					if(!firstTruck) {
						manifest.addCargoDirectlyToTruck(currentStock, TruckCounter - 2);
						currentStock = new Stock();
					}else {
						firstTruck = false;
						currentStock = new Stock();
					}
				}
				if(!(index == records.size()-1)) {
					newTruck = false;
					String itemName = records.get(index)[0];
					int itemQty = Integer.parseInt(records.get(index)[1]);
					Item itemObject = inventory.getItem(itemName);
					currentStock.addItem(itemObject, itemQty);
					try {
						totalStock.addItem(itemObject, itemQty);
					}catch(StockException error) {
						totalStock.addQuantity(itemName, itemQty);
					}
				}else {
					String itemName = records.get(index)[0];
					int itemQty = Integer.parseInt(records.get(index)[1]);
					Item itemObject = inventory.getItem(itemName);
					currentStock.addItem(itemObject, itemQty);
					try {
						totalStock.addItem(itemObject, itemQty);
					}catch(StockException error) {
						totalStock.addQuantity(itemName, itemQty);
					}
					manifest.addCargoDirectlyToTruck(currentStock, TruckCounter - 1);
					manifest.importTotalStock(totalStock);
				}
			}
		}	
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
	        	throw new CSVFormatException(e.getMessage());
	        }
		//Return the initial manifest HashMap
		return salesLog;
	}
}
