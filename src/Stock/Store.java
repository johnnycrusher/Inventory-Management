package Stock;

import java.util.HashMap;

import Exception.StockException;

public class Store {
	HashMap<String,Integer> stockInventory = new HashMap<String,Integer>();
	static int startCapital = 100000;
	
	int capital;
	String storeName;
	
	//Singleton Store creation
	private static Store store = new Store();

   /* A private Constructor prevents any other
    * class from instantiating.
    */
   private Store() {
	   this.capital = startCapital;
	   this.storeName = null;
   }

   /* Static 'instance' method */
   public static Store getInstance() {
      return store;
   }

   /* Other methods protected by singleton-ness */
   protected static void demoMethod( ) {
      System.out.println("demoMethod for singleton");
   }
	
	//Set the inventory and protect from invalid input
	protected void setInventory(HashMap<String, Integer> inventory) throws StockException{
		this.stockInventory = inventory;
	}
	
	//Set the inventory and protect from invalid input
	protected void addInventory(HashMap<String, Integer> inventory) throws StockException{
		for (String key : inventory.keySet()) {
		    if (this.stockInventory.containsKey(key)) {
		        this.stockInventory.put(key, this.stockInventory.get(key) + 1);
		    } else {
		    	this.stockInventory.put(key, inventory.get(key));
		    }
		}
	}
	
	//Get the inventory and ensure inventory is not empty
	protected HashMap<String,Integer> getInventory() throws StockException{
		if (this.stockInventory.isEmpty()) {
			throw new StockException("No inventory");
		} else {
			return this.stockInventory;
		}
	}
	
	//return the capital
	protected int getCapital() {
		return this.capital;
	}
	
	//Set the capital and protect from invalid input
	protected void addCapital(int profit) throws StockException{
		if (profit <= 0) {
			throw new StockException("invaid profit input!");
		} else {
			//Add profit to capital
			this.capital += profit;
		}

	}
	
	//Set the capital and protect from invalid input
	protected void subtractCapital(int cost) throws StockException{
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
	protected void setName(String storeName) throws StockException {
		if (storeName != "") {
			this.storeName = storeName;			
		} else {
			throw new StockException("Invalid storeName input!");
		}
	}
	
	//get the storeName and check storeName isn't null
	protected String getName() throws StockException {
		if (this.storeName != null) {
			return this.storeName;
		} else {
			throw new StockException("storeName NULL!");
		}
	}
}
