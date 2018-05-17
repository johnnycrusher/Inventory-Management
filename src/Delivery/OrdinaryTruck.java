/**
 * 
 */
package Delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Exception.DeliveryException;
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
		cargoStock = null;
	}
	
	public void add(Stock stockObj) throws DeliveryException{
		boolean anyRefridgeratedItems = false;
		
		try {
			anyRefridgeratedItems = findRefridgeratedItems(stockObj);
		} catch (StockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(anyRefridgeratedItems == true) {
			throw new DeliveryException("This item is not in the stock list");
		}
		cargoStock = stockObj;
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
	
	
	
	private boolean findRefridgeratedItems(Stock stock) throws DeliveryException, StockException {
		HashMap<Item, Integer> stockItems = stock.returnStockList();
		boolean refridgeratedItem = false;
		int temp = 40;
		for(Map.Entry<Item,Integer> entry : stockItems.entrySet()) {
			temp = entry.getKey().getTemperature();		

		}
		if(temp < 10) {
			refridgeratedItem =  true;
		}
		return refridgeratedItem;
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
