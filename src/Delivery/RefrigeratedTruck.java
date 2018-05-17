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
public class RefrigeratedTruck extends Truck {

	Stock cargoStock;
	
	/**
	 * 
	 */
	public RefrigeratedTruck() {
		cargoStock = null;
	}

	@Override
	public void add(Stock stockObj) throws DeliveryException {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getQuantity(String item) {
		return 0;
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getQuantity() throws StockException {
		// TODO Auto-generated method stub
		return 0;
	}

}
