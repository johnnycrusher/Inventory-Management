package Stock;

import java.util.HashMap;
import java.util.Map;

import Exception.StockException;

public class Stock {
	
	HashMap<Item, Integer> stock;
	

	public Stock() {
		// TODO Auto-generated constructor stub
		stock = new HashMap<Item,Integer>(); 
	}
	
	public void add(Item item, int quantity) throws StockException {
		String itemName = item.itemName;
		boolean itemExist = false;
		for(Map.Entry<Item, Integer> entry : stock.entrySet()) {
			String stockItemName = entry.getKey().itemName;
			if(itemName.equals(stockItemName)) {
				itemExist = true;
				throw new StockException("This item is already in the stock list");
			}
		}
		if(itemExist == false) {
			stock.put(item, quantity);
		}
	}
	
	public Item getItem(String item) throws StockException {
		Item key = null;
		String keyValue;
		for(Map.Entry<Item,Integer> entry :stock.entrySet()) {
			key = entry.getKey();
			keyValue = entry.getKey().itemName;
			if(keyValue.equals(item)) {
				break;
			}
		}
		if(key == null) {
			throw new StockException("This item is not in the stock list");
		}
		return key;
	}
	
	public int getItemQuantity(String itemName) throws StockException {
		String keyItemName;
		int itemQuantity = -11;
		boolean detectedMatch = false;
		for(Map.Entry<Item, Integer> entry: stock.entrySet()) {
			keyItemName = entry.getKey().itemName;
			itemQuantity = entry.getValue();
			if(keyItemName.equals(itemName)) {
				detectedMatch = true;
				break;
			}
		}
		if(detectedMatch == false) {
			throw new StockException("Cannot get item quantity because item doesn't exsist in stock");
		}
		return itemQuantity;
	}
	
	public HashMap<Item, Integer> returnStockList() throws StockException{
		if(stock.isEmpty()) {
			throw new StockException("Cannot return Stock List as it is empty");
		}
		return stock;
	}
	
	public void remove(String itemName, int quantity) throws StockException{
		int currentValue = getItemQuantity(itemName);
		Item item = getItem(itemName);
		if(currentValue < quantity) {
			throw new StockException("Cannot remove item due to remove quantity is greater then item quantity");
		}
		int newQuantityValue = currentValue - quantity;
		stock.put(item, newQuantityValue);
	}
	
	public int getNumberOfItems() {
		int numOfItems = 0;
		for(Map.Entry<Item,Integer> entry : stock.entrySet()) {
			numOfItems += entry.getValue();
		}
		return numOfItems;
	}
	
	public int getNumOfItems() {
		return stock.size();
	}
}
