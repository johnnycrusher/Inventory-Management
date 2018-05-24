package Stock;

import java.util.HashMap;
import java.util.Map;

import Exception.StockException;

public class Stock {
	
	HashMap<Item, Integer> stock;
	

	public Stock() {
		stock = new HashMap<Item,Integer>(); 
	}
	
	
	public void addItem(Item item, int quantity) throws StockException {
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
	
	public void addQuantity(String itemName, int quantity) throws StockException {
		boolean itemExist = false;
		for(Map.Entry<Item, Integer> entry : stock.entrySet()){
			Item itemObj = entry.getKey();
			String stockItemName = entry.getKey().getItemName();
			if(itemName.equals(stockItemName)) {
				//REWRITTEN BY TOM TO ALLOW THE ADDING OF STOCK. BEFORE EDIT WAS THROWING ERROR WHEN ADDING ALREADY EXISTING STOCK???
				itemExist = true;
				stock.put(itemObj, getItemQuantity(stockItemName) + quantity);
				break;
			}
		}
		if (itemExist == false) {
			throw new StockException("THis item doesn't exist in stock can't add quantity");
		}
	}
	
	public Item getItem(String item) throws StockException {
		Item key = null;
		String keyValue;
		for(Map.Entry<Item,Integer> entry : stock.entrySet()) {
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
}
