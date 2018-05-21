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

/**
 * @author John
 *
 */
public class RefrigeratedTruck extends Truck {
	
	/**
	 * 
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
	}

	@Override
	public double getCost() throws StockException {
		double exponent = findLowestTemp()/5;
		double cost = 900 + (200 * Math.pow(0.7,exponent));
		return cost;
	}
	
	public int getTemp() throws StockException {
		return findLowestTemp();
	}

	private int findLowestTemp() throws StockException {
		HashMap<Item,Integer> stockList = cargoStock.returnStockList();
		int currentLowestTemp = 11;
		int itemTemp = 0;
		boolean refrigeratedItemExist = false;
		for(Map.Entry<Item, Integer> entry : stockList.entrySet()) {
			try {
				itemTemp = entry.getKey().getTemperature();
			}catch(StockException e) {
				itemTemp = 11;
			}
			if(itemTemp < currentLowestTemp) {
				currentLowestTemp = itemTemp;
			}
		}
		return currentLowestTemp;
	}

}
