package Delivery;
/**
 * 
 */

import java.util.HashMap;
import java.util.Map;

import Exception.DeliveryException;
import Exception.StockException;
import Stock.Item;
import Stock.Stock;

/** A sub class of the Truck Object to
 * show the Ordinary Trucks funtionality
 * @author John Huynh
 * @version 1.0
 */
public class OrdinaryTruck extends Truck {

	
	private final static int ordinaryCargoLimit = 1000;
	/**The Ordinary Truck Constructor that instantiates
	 * an empty cargo stock
	 */
	public OrdinaryTruck() {
		cargoStock = null;
	}
	
	
	@Override
	public void add(Stock stockObj) throws DeliveryException, StockException{
		//intalises a boolean variable to check stock
		boolean anyRefrigeratedItems = false;
		
		//determines if there are any refrigerated items if there are set to true
		try {
			anyRefrigeratedItems = findRefrigeratedItems(stockObj);
		} catch (StockException e) {
			anyRefrigeratedItems = true;
		}
		//if there is a refrigerated item then throw delivery exception
		if(anyRefrigeratedItems == true) {
			throw new DeliveryException("This item should not be allowed on truck" );
		}
		
		//get number of cargo item in the truck
		int numOfItems = stockObj.getNumberOfItems();
		
		//detects if number of items exceeds the ordinary truck limit
		if(numOfItems > ordinaryCargoLimit) {
			throw new DeliveryException("Cannot add as stock due to exceeding 1000 items");
		}
		//sets the cargo stock as the inputted stock object
		cargoStock = stockObj;
	}	
	
	/** private helper method to find if the stock object contains any 
	 * refrigerated items
	 * @param stock - the stock object that will be used to search stock object
	 * @return anyRefrigeratedItem - true or false value for if any refrigerated items in stock object
	 * @throws DeliveryException - when it can't access truck stock
	 * @throws StockException 
	 */
	private boolean findRefrigeratedItems(Stock stock) throws DeliveryException, StockException {
		//Hashmap of the stock object
		HashMap<Item, Integer> stockItems = stock.returnStockList();
		
		//Intialise a boolean variable for true of falce
		boolean anyRefridgeratedItem = false;
		//Intialise an array of tempertures
		int tempArray[] = new int[stock.returnStockList().size()];
		
		//set index to 0
		int index = 0;
		
		//Loop to get all the tempertures of any objects
		for(Map.Entry<Item,Integer> entry : stockItems.entrySet()) {
			try {
				tempArray[index] = entry.getKey().getTemperature();
			}catch(StockException e) {
				tempArray[index] = 11;
			}
			//increment index
			index++;
		}
		//search if any temperature is below 10 degrees
		for(int location = 0; location < tempArray.length; location++) {
			if(tempArray[location] <= 10) {
				anyRefridgeratedItem = true;
			}
		}
		//returns boolean variable
		return anyRefridgeratedItem;
	}
	
	@Override
	public double getCost() throws StockException {
		//determine the cargo amount
		int cargoAmmount = cargoStock.getNumberOfItems();
		//calculates the cost of the cargo Truck
		double cost = (750 + (0.25 * cargoAmmount));
		//returns the cost of the truck
		return cost;
	}

	@Override
	//This method isn't necessary but is required for the complier
	public int getTemp() throws StockException {
		return 11;
	}
}
