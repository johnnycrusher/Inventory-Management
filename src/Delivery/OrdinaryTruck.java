package Delivery;
/**
 * 
 */

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

	/**
	 * 
	 */
	public OrdinaryTruck() {
		cargoStock = null;
	}
	
	@Override
	public void add(Stock stockObj) throws DeliveryException, StockException{
		boolean anyRefrigeratedItems = false;
		
		try {
			anyRefrigeratedItems = findRefrigeratedItems(stockObj);
		} catch (StockException e) {
			anyRefrigeratedItems = true;
		}
		if(anyRefrigeratedItems == true) {
			throw new DeliveryException("This item should not be allowed on truck" );
		}
		
		int numOfItems = stockObj.getNumberOfItems();
		
		
		if(numOfItems > 1000) {
			throw new DeliveryException("Cannot add as stock due to exceeding 1000 items");
		}
		cargoStock = stockObj;
	}	
	
	private boolean findRefrigeratedItems(Stock stock) throws DeliveryException, StockException {
		HashMap<Item, Integer> stockItems = stock.returnStockList();
		boolean refridgeratedItem = false;
		int tempArray[] = new int[stock.returnStockList().size()];
		int index = 0;
		for(Map.Entry<Item,Integer> entry : stockItems.entrySet()) {
			try {
				tempArray[index] = entry.getKey().getTemperature();
			}catch(StockException e) {
				throw new DeliveryException("Error accessing truck stock");
			}
			index++;
		}
		for(int i = 0; i < tempArray.length; i++) {
			if(tempArray[i] <= 10) {
				refridgeratedItem = true;
			}
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
		double cost = (750 + (0.25 * cargoAmmount));
		return cost;
	}

	@Override
	public int getTemp() throws StockException {
		return 11;
	}
}
