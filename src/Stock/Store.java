/**
 * A Store class to provide store inventory related features to the project
 * @author Tom
 */
package Stock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import Exception.StockException;

public class Store {
	//Declare a startCapital value for the store
	private static double startCapital = 100000;
	
	//Declare some variables
	double capital;
	String storeName;
	Stock stockInventory;
	
	//Singleton Store creation
	private static Store store;


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
	   if(store == null) {
		   store = new Store();
	   }
      return store;
   }
	
	/**
	 * A method to set the inventory to a stock object
	 * @param inventory
	 * @throws StockException
	 */
	public void setInventory(Stock stock) throws StockException{
		this.stockInventory = stock;
	}
	
	/** A method used to reset the capital to the Starting Capital
	 * @throws StockException
	 */
	public void resetCapital() throws StockException{
		capital = startCapital;
	}
	
	/**
	 * A method to add inventory to the Store's stock object
	 * 
	 * @param inventory
	 * @throws StockException
	 */
	public void addInventory(Stock inventory) throws StockException{
		HashMap<Item,Integer> inventoryHash = inventory.returnStockList();
		for (Map.Entry<Item, Integer> items: inventoryHash.entrySet()) {
			stockInventory.addQuantity(items.getKey().getItemName(), items.getValue());
		}
	}
	
	public void removeInventroy(HashMap<String,Integer> salesInventory) throws StockException{
		for(Map.Entry<String, Integer> items: salesInventory.entrySet()) {
			stockInventory.remove(items.getKey(), items.getValue());
		}
	}
	
	
	
	/**
	 * Getter method to return the inventory, which ensures the inventory isn't empty before returning
	 * @return stockInventory
	 * @throws StockException
	 */
	public Stock getInventory() throws StockException{
		boolean checkVar = stockInventory.returnStockList().isEmpty();
		int checkInt = stockInventory.getNumberOfItems();
		if (stockInventory.returnStockList().isEmpty()) {
			throw new StockException("No inventory");
		} else {
			return stockInventory;
		}
	}
	
	/**
	 * A getter method to return the capital
	 * @return capital
	 */
	public double getCapital() {
		double capitalValue = roundTo2DecimcalPlace(capital);
		return capitalValue;
	}
	
	/**
	 * A setter method to add to the capital, while protecting from invalid input
	 * @param profit
	 * @throws StockException
	 */
	public void addCapital(double profit) throws StockException{
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
	public void subtractCapital(double cost) throws StockException{
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
	public void setName(String storeName) throws StockException {
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
	public String getName() throws StockException {
		if (this.storeName != null) {
			return this.storeName;
		} else {
			throw new StockException("storeName NULL!");
		}
	}
	
	private static double roundTo2DecimcalPlace(double value) {
	    BigDecimal currencyValue = new BigDecimal(value);
	    currencyValue = currencyValue.setScale(2, RoundingMode.HALF_UP);
	    return currencyValue.doubleValue();
	}
}
