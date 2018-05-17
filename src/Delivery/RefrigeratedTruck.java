/**
 * 
 */
package Delivery;

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

	Stock cargoStock;
	int temperature = 40;
	
	/**
	 * 
	 */
	public RefrigeratedTruck() {
		cargoStock = null;
	}

	@Override
	public void add(Stock stockObj) throws DeliveryException, StockException {
		HashMap<Item, Integer> stockList =  stockObj.returnStockList();
		int numOfItems = 0;
		for(Map.Entry<Item,Integer> entry : stockList.entrySet()) {
			numOfItems += entry.getValue();
		}
		if(numOfItems > 800) {
			throw new DeliveryException("Cannot add as stock due to exceeding 800 items");
		}
		cargoStock = stockObj;
	}

	@Override
	public Stock getStock() throws DeliveryException {
		if(cargoStock == null) {
			throw new DeliveryException("Cannot Return an Empty Stock Item");
		}
		return cargoStock;
	}

	@Override
	public void remove() {
		cargoStock = null;	
	}

	@Override
	public int getQuantity() throws StockException {
		return getCargoAmount();
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
				itemTemp = 20;
			}
			if(itemTemp < currentLowestTemp) {
				currentLowestTemp = itemTemp;
			}
		}
		return currentLowestTemp;
	}
	
	private int getCargoAmount() throws StockException {
		
		HashMap<Item, Integer> stockList =  cargoStock.returnStockList();
		int numOfItems = 0;
		for(Map.Entry<Item,Integer> entry : stockList.entrySet()) {
			numOfItems += entry.getValue();
		}
		return numOfItems;
	}

}
