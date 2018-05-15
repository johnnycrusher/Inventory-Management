package Stock;

import java.util.HashMap;

import Exception.StockException;

public class Store {
	HashMap<String,Integer> stockInventory = new HashMap<String,Integer>();
	static int startCapital = 100000;
	
	int capital;
	String storeName;
	
	//Construct a store object with starting capital
	public Store() {
		this.capital = startCapital;
		this.storeName = null;
	}
	
	
	/*NOTE TO SELF
	 * I DON'T THINK THIS IS THE WAY INVENTORY IS HANDLED BY THE STORE
	 * I feel like the store accepts a hashmap and iterates thru it accepting certain items from it,
	 * as opposed to currently just setting itself to the given hashmap
	 * Think of a delivery coming in, your stock doesn't just become the small delivery you just got,
	 * it adds the delivery to your even bigger hashmap
	 * 
	 * I need to make it add the hashmaps contents to the inventory hashmap within the store.
	 */
	
	//Set the inventory and protect from invalid input
	public void setInventory(HashMap<String, Integer> inventory) throws StockException{
		this.stockInventory = inventory;
	}
	
	//Get the inventory and ensure inventory is not empty
	public HashMap<String,Integer> getInventory() throws StockException{
		if (this.stockInventory.isEmpty()) {
			throw new StockException("No inventory");
		} else {
			return this.stockInventory;
		}
	}
	
	//return the capital
	public int getCapital() {
		return this.capital;
	}
	
	//Set the capital and protect from invalid input
	public void addCapital(int profit) throws StockException{
		if (profit <= 0) {
			throw new StockException("invaid profit input!");
		} else {
			//Add profit to capital
			this.capital += profit;
		}

	}
	
	//Set the capital and protect from invalid input
	public void subtractCapital(int cost) throws StockException{
		if (cost <= 0) {
			throw new StockException("invaid cost input!");
		} else {
			if (cost < this.capital) {
				//remove cost from capital
				this.capital -= cost;
			} else {
				throw new StockException("Not enough Capital!");
			}			
		}
	}

	//Set the name and protect from invalid input
	public void setName(String storeName) throws StockException {
		if (storeName != "") {
			this.storeName = storeName;			
		} else {
			throw new StockException("Invalid storeName input!");
		}
	}
	
	//get the storeName and check storeName isn't null
	public String getName() throws StockException {
		if (this.storeName != null) {
			return this.storeName;
		} else {
			throw new StockException("storeName NULL!");
		}
	}
}
