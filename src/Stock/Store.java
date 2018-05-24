/**
 * A Store class to provide store inventory related features to the project
 * @author Tom
 */
package Stock;

import Exception.StockException;

public class Store {
	//Declare a startCapital value for the store
	static int startCapital = 100000;
	
	//Declare some variables
	int capital;
	String storeName;
	Stock stockInventory;
	
	//Singleton Store creation
	private static Store store = new Store();

   /**
    * A private constructor method which prevents any other
    * class from recreating a store object
    */
   private Store() {
	   this.capital = startCapital;
	   this.storeName = null;
	   stockInventory = new Stock();
   }

   /**
    * Static getInstance method to return the store object
    * @return store
    */
   public static Store getInstance() {
      return store;
   }
	
	/**
	 * A method to set the inventory to a stock object
	 * @param inventory
	 * @throws StockException
	 */
	protected void setInventory(Stock stock) throws StockException{
		this.stockInventory = stock;
	}
	
	/**
	 * A method to add inventory to the Store's stock object
	 * 
	 * @param inventory
	 * @throws StockException
	 */
	protected void addInventory(Stock inventory) throws StockException{
		//For every item in the stock object
		for (Item key : inventory.returnStockList().keySet()) {
			//Check if item is already in the store's stock
		    if (this.stockInventory.returnStockList().containsKey(key)) {
		    	//If it is, add to the store's item quantity
		        this.stockInventory.addQuantity(key.getItemName(), this.stockInventory.returnStockList().get(key) + inventory.returnStockList().get(key));
		    } else {
		    	//If not, add the item
		    	this.stockInventory.addItem(key, inventory.returnStockList().get(key));
		    }
		}
	}
	
	/**
	 * Getter method to return the inventory, which ensures the inventory isn't empty before returning
	 * @return stockInventory
	 * @throws StockException
	 */
	protected Stock getInventory() throws StockException{
		if (this.stockInventory.getNumberOfItems() == 0) {
			throw new StockException("No inventory");
		} else {
			return this.stockInventory;
		}
	}
	
	/**
	 * A getter method to return the capital
	 * @return capital
	 */
	protected int getCapital() {
		return this.capital;
	}
	
	/**
	 * A setter method to add to the capital, while protecting from invalid input
	 * @param profit
	 * @throws StockException
	 */
	protected void addCapital(int profit) throws StockException{
		if (profit <= 0) {
			throw new StockException("invaid profit input!");
		} else {
			//Add profit to capital
			this.capital += profit;
		}

	}
	
	/**
	 * A setter method to subtract from capital, while protecting from invalid input
	 * @param cost
	 * @throws StockException
	 */
	protected void subtractCapital(int cost) throws StockException{
		if (cost <= 0) {
			//If invalid input, throw exception
			throw new StockException("invaid cost input!");
		} else {
			if (cost < this.capital) {
				//remove cost from capital
				this.capital -= cost;
			} else {
				//throw exception if capital is lower than cost
				throw new StockException("Not enough Capital!");
			}			
		}
	}

	/**
	 * Setter method to assign the name, while protecting from invalid input
	 * @param storeName
	 * @throws StockException
	 */
	protected void setName(String storeName) throws StockException {
		if (storeName != "") {
			this.storeName = storeName;			
		} else {
			throw new StockException("Invalid storeName input!");
		}
	}
	
	/**
	 * A getter method to get the storeName, while checking storeName isn't null
	 * @return storeName
	 * @throws StockException
	 */
	protected String getName() throws StockException {
		if (this.storeName != null) {
			return this.storeName;
		} else {
			throw new StockException("storeName NULL!");
		}
	}
}
