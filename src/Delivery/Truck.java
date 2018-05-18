/**
 * 
 */
package Delivery;

import Exception.DeliveryException;
import Exception.StockException;
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
	
	public abstract void add(Stock storeObj) throws DeliveryException, StockException;
	public abstract double getCost() throws StockException;
	public abstract int getTemp() throws StockException;
}
