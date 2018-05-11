package Stock;

import java.util.HashMap;

import Exception.StockException;

public class Stock {
	
	HashMap<Item, Integer> stock;
	

	public Stock() {
		// TODO Auto-generated constructor stub
		stock = new HashMap<Item,Integer>(); 
	}
	
	public void add(Item item, int quantity) {
		stock.put(item, quantity);
	}
	
	public Item getItem(Item item) throws StockException {
		if(!stock.containsKey(item)) {
			throw new StockException("This item is not in the stock list");
		}
		Item stockItem = stock.get(item).keySet();
		return null;
	}
	
	public int getItemQuantity(Item item) throws StockException {
		if(!stock.containsKey(item)) {
			throw new StockException("Cannot return item quantity as it doesn't exist");
		}
		int itemQuantity = stock.get(item);
		return itemQuantity;
	}
	
	public HashMap<Item, Integer> returnStockList() throws StockException{
		if(stock.isEmpty()) {
			throw new StockException("Cannot return Stock List as it is empty");
		}
		return stock;
	}
	
	public void remove(Item item, int quantity) throws StockException{
		int currentValue = stock.get(item);
		if(currentValue < quantity) {
			throw new StockException("Cannot remove item due to remove quantity is greater then item quantity");
		}
		int newQuantityValue = currentValue - quantity;
		stock.put(item, newQuantityValue);
	}
}
