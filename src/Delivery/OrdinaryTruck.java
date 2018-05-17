/**
 * 
 */
package Delivery;

import java.util.ArrayList;
import java.util.HashMap;

import Stock.Stock;

/**
 * @author John
 *
 */
public class OrdinaryTruck extends Truck {

	ArrayList<Stock> ordinaryTruck;
	/**
	 * 
	 */
	public OrdinaryTruck() {
		// TODO Auto-generated constructor stub
		ordinaryTruck = new ArrayList<Stock>();
	}
	
	public void add(Stock storeObj) {
		ordinaryTruck.add(storeObj);
	}
	
	public Stock getStock(int index) {
		Stock ordinaryTruckStock = ordinaryTruck.get(index);
		return ordinaryTruckStock;
	}
	
	public void remove() {
		ordinaryTruck.removeAll(ordinaryTruck);
	}
	public int getQuantity(String item) {
		int itemQty;
		for(int index = 0; index < ordinaryTruck.size(); index++) {
			itemQty = ordinaryTruck.get(index).get(item);
		}
		return itemQty;
	}
	private int getCargoAmount() {
		return ordinaryTruck.get(0).getNumOfItems();
	}
	
	public double getCost() {
		int cargoAmmount = getCargoAmount();
		double cost = 750 + (0.25 * cargoAmmount);
		return cost;
	}

}
