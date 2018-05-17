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
	public Truck() {
		// TODO Auto-generated constructor stub
	}
	
	
	public abstract void add(Stock storeObj) throws DeliveryException;
	public abstract Stock getStock();
	public abstract void remove();
	public abstract int getQuantity() throws StockException;
	public abstract double getCost() throws StockException;
	
	

}
