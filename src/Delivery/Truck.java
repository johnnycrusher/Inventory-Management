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

/** An abstract class that for Truck Objects
 * @author John Huynh
 * @version 1.0
 *
 */
public abstract class Truck {

	//Cargo Stock item
	Stock cargoStock;
	
	public Truck() {
		// TODO Auto-generated constructor stub
	}
	/**Removes the Truck cargo Stock from the truck
	 */
	public void removeStock() {
		//Set the cargo Stock to null
		cargoStock = null;
	}
	
	
	/** A method that returns the number of items in a truck
	 * @return - the number of cargo Items in the truck
	 * @throws StockException 
	 * @throws DeliveryException 
	 */
	public int getQuantity() throws StockException, DeliveryException {
		if(cargoStock == null ||cargoStock.returnStockList().isEmpty()) {
			throw new DeliveryException("Can't return the quantity of items in the truck there are no objects");
		}
		return cargoStock.getNumberOfItems();
	}
	
	
	/**A method that gets the stock of the truck 
	 * @return the cargo that truck is holding
	 * @throws DeliveryException occurs when there is no cargo in the truck
	 * @throws StockException 
	 */
	public Stock getStock() throws DeliveryException, StockException {
		if(cargoStock == null || cargoStock.returnStockList().isEmpty()) {
			throw new DeliveryException("There is no cargo in the Truck");
		}
		return cargoStock;
	}
	
	
	/**Determines the cost of the cargo
	 * @return returns the cost of the cargo
	 * @throws StockException 
	 * @throws DeliveryException 
	 */
	public double getStockCost() throws StockException, DeliveryException {
		if(cargoStock == null || cargoStock.returnStockList().isEmpty()) {
			throw new DeliveryException("Cannot get Cargo Cost as there is no cargo in the truck");
		}
		//get the HashMap collector from the cargo
		HashMap<Item, Integer> stockList =  cargoStock.returnStockList();
		//intialise cost as 0
		double costOfCargo = 0;
		//loop in the hashmap and grab the manufacture cost and multiply it with the quantity
		for(Map.Entry<Item,Integer> currentItem : stockList.entrySet()) {
			int itemQTY = currentItem.getValue();
			costOfCargo += (currentItem.getKey().getManufactureCost() * itemQTY);
		}
		//return the cost of the cargo
		return costOfCargo;
	}
	
	/** Adds an stock object as the truck cargo
	 * @param storeObj - the stock object that will be the truck cargo
	 * @throws DeliveryException occurs item exceeds cargo limit or refrigeated item in ordinary truck
	 * @throws StockException
	 */
	public abstract void add(Stock storeObj) throws DeliveryException, StockException;
	
	/** Gets the cost of the truck 
	 * @return cost - the cost of the truck in dollars
	 * @throws StockException
	 * @throws DeliveryException 
	 */
	public abstract double getCost() throws StockException, DeliveryException;
	
	/** gets the set temperature of that specfic truck
	 * @return truckTemperature - the temperature of the truck
	 * @throws StockException
	 */
	public abstract int getTemp() throws StockException, DeliveryException;
}
