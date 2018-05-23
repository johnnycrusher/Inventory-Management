/**
 * 
 */
package Delivery;

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
public abstract class Truck {

	/**
	 * 
	 */
	Stock cargoStock;
	public Truck() {
		// TODO Auto-generated constructor stub
	}
	public void remove() {
		cargoStock = null;
	}
	
	public int getQuantity() throws StockException {
		return cargoStock.getNumberOfItems();
	}
	
	public Stock getStock() throws DeliveryException {
		if(cargoStock == null) {
			throw new DeliveryException("There is no cargo in the Truck");
		}
		return cargoStock;
	}
	public double getStockCost() throws StockException {
		HashMap<Item, Integer> stockList =  cargoStock.returnStockList();
		int costOfCargo = 0;
		for(Map.Entry<Item,Integer> entry : stockList.entrySet()) {
			costOfCargo += entry.getKey().getManufactureCost();
		}
		return costOfCargo;
	}
	
	public abstract void add(Stock storeObj) throws DeliveryException, StockException;
	public abstract double getCost() throws StockException;
	public abstract int getTemp() throws StockException;
}
