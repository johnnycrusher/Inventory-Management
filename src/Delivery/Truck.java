/** An abstract class that for Truck Objects
 * @author John Huynh
 * @version 1.0
 */
package Delivery;

import java.util.*;
import Exception.*;
import Stock.*;

public abstract class Truck {

	//Cargo Stock item
	Stock cargoStock;
	
	public Truck() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Removes the Truck cargo Stock from the truck
	 */
	public void removeStock() {
		//Set the cargo Stock to null
		cargoStock = null;
	}
	
	
	/** 
	 * A method that returns the number of items in a truck
	 * @return cargoStockNumOfItem - the number of cargo Items in the truck
	 * @throws StockException throws a stock error when there is a stock related problem
	 * @throws DeliveryException throws a delivery exception when there is no cargo hence change require quantity of items
	 */
	public int getQuantity() throws StockException, DeliveryException {
		if(cargoStock == null ||cargoStock.returnStockList().isEmpty()) {
			throw new DeliveryException("Can't return the quantity of items in the truck there are no objects");
		}
		return cargoStock.getNumberOfItems();
	}
	
	
	/**
	 * A method that gets the stock of the truck 
	 * @return the cargo that truck is holding
	 * @throws DeliveryException occurs when there is no cargo in the truck
	 * @throws StockException throws a stock excpetion when tehre is a stock related problem
	 */
	public Stock getStock() throws DeliveryException, StockException {
		if(cargoStock == null || cargoStock.returnStockList().isEmpty()) {
			throw new DeliveryException("There is no cargo in the Truck");
		}
		return cargoStock;
	}
	
	
	/**
	 * Determines the cost of the cargo
	 * @return costOfCargo - the cost of the cargo
	 * @throws StockException throws stock error when there is a stock related error
	 * @throws DeliveryException throws a DelieveryException when there is no cargo in truck
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
	
	/** 
	 * Adds an stock object as the truck cargo
	 * @param storeObj - the stock object that will be the truck cargo
	 * @throws DeliveryException occurs item exceeds cargo limit or refrigeated item in ordinary truck
	 * @throws StockException throws a stock exception when there is a stock related error
	 */
	public abstract void add(Stock storeObj) throws DeliveryException, StockException;
	
	/** 
	 * Gets the cost of the truck 
	 * @return cost - the cost of the truck in dollars
	 * @throws StockException throws a stock error when there is a stock related error
	 * @throws DeliveryException occurs when there is no cargo in the truck
	 */
	public abstract double getCost() throws StockException, DeliveryException;
	
	/** 
	 * Gets the set temperature of that specfic truck
	 * @return truckTemperature - the temperature of the truck
	 * @throws StockException throws a stock error when there is a stock related error
	 * @throws DeliveryException when there is no cargo in the truck
	 */
	public abstract int getTemp() throws StockException, DeliveryException;
}
