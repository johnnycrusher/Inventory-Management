package Delivery;
/**
 * 
 */


import java.util.HashMap;
import java.util.Map;
import java.lang.*;

import Exception.DeliveryException;
import Exception.StockException;
import Stock.Item;
import Stock.Stock;

/**A sub class of the Truck Object to
 * show the refrigerated truck functionality
 * @author John Huynh
 * @version 1.0
 */
public class RefrigeratedTruck extends Truck {
	//set a truck temperature to 11 which will be overwrite
	//when truck is created
	private int truckTemperature = 11;


	/**Intialises the refrigerated Truck object with
	 * no cargo 
	 */
	public RefrigeratedTruck() {
		cargoStock = null;
	}

	@Override
	public void add(Stock stockObj) throws DeliveryException, StockException {
		HashMap<Item, Integer> stockList =  stockObj.returnStockList();
		int numOfItems = stockObj.getNumberOfItems(); 
		if(numOfItems > 800) {
			throw new DeliveryException("Cannot add as stock due to exceeding 800 items");
		}
		cargoStock = stockObj;
		truckTemperature = findLowestTemp();
	}

	@Override
	public double getCost() throws StockException {
		double exponent = truckTemperature/(double)5;
		double cost = (900 + (200 * Math.pow(0.7,exponent)));
		return cost;
	}
	@Override
	public int getTemp() throws StockException {
		return truckTemperature;
	}

	/**Private helper method used to find the lowest temperature
	 * in the cargo stock
	 * @return currentLowestTemp - the lowest temperate item in the cargo
	 * @throws StockException
	 */
	private int findLowestTemp() throws StockException {
		//gets hashmap of the cargo object
		HashMap<Item,Integer> stockList = cargoStock.returnStockList();
		//set an intalise lowest temp that will always be beaten
		int currentLowestTemp = 11;
		//set an item temp variable to store item temperature
		int itemTemp = 0;
		boolean refrigeratedItemExist = false;
		//loops and find the lowest item temperature
		for(Map.Entry<Item, Integer> currentItem : stockList.entrySet()) {
			//find the item temperature
			itemTemp = currentItem.getKey().getTemperature();

			//compare to check if new item is lower then current lowest temp
			//if so then replace as new current lowest temp
			if(itemTemp < currentLowestTemp) {
				currentLowestTemp = itemTemp;
			}
		}
		//returns the lowest temp
		return currentLowestTemp;
	}
}
