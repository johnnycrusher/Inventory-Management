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
	
	@Override
	public void add(Stock stockObj) throws DeliveryException, StockException{
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
		
		int numOfItems = stockObj.getNumberOfItems();
		
		
		if(numOfItems > 1000) {
			throw new DeliveryException("Cannot add as stock due to exceeding 800 items");
		}
		cargoStock = stockObj;
	}
	
	@Override
	public Stock getStock() throws DeliveryException {
		if(cargoStock == null) {
			throw new DeliveryException("There is no cargo in the Truck");
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
	
	@Override
	public double getCost() throws StockException {
		int cargoAmmount = getCargoAmount();
		double cost = 750 + (0.25 * cargoAmmount);
		return cost;
	}
}
