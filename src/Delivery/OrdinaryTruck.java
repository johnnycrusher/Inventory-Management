/**
 * 
 */
package Delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Exception.StockException;
import Stock.Item;
import Stock.Stock;

/**
 * @author John
 *
 */
public class OrdinaryTruck extends Truck {

	Stock cargoStock;
	/**
	 * 
	 */
	public OrdinaryTruck() {
		// TODO Auto-generated constructor stub
	}
	
	public void add(Stock storeObj) {
		cargoStock = storeObj;
	}
	
	public Stock getStock() {
		return cargoStock;
	}
	
	public void remove() {
		cargoStock = null;
	}
	
	public int getQuantity() throws StockException {
		return getCargoAmount();
	}
	private int getCargoAmount() throws StockException {
		
		HashMap<Item, Integer> stockList =  cargoStock.returnStockList();
		int numOfItems = 0;
		for(Map.Entry<Item,Integer> entry : stockList.entrySet()) {
			numOfItems += entry.getValue();
		}
		return numOfItems;
	}
	
	public double getCost() throws StockException {
		int cargoAmmount = getCargoAmount();
		double cost = 750 + (0.25 * cargoAmmount);
		return cost;
	}

}
