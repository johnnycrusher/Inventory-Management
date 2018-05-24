/**
 * The Stock class, supplying item object functionality to the project
 * @author Tom
 */
package Stock;

import Exception.StockException;

public class Item {
	//Instantiate some variables
	String itemName;
	
	double manufactureCost;
	double sellCost;
	
	int reorderPoint;
	int reorderAmount;
	int temperature;
	
	/**
	 * A constructor method to create an Item object
	 * @param itemName
	 * @param manufactureCost
	 * @param sellCost
	 * @param reorderPoint
	 * @param reorderAmount
	 * @param temperature
	 * @throws StockException
	 */
	public Item(String itemName, double manufactureCost, double sellCost, int reorderPoint, int reorderAmount, int temperature) throws StockException {
		//Set the itemName
		this.itemName = itemName;
		
		//Set the manufactureCost and protect from negative input
		if (manufactureCost < 0) {
			throw new StockException("Negative manufacture cost given!");
		} else {
			this.manufactureCost = manufactureCost;
		}
		
		//Set the sellCost and protect from negative input
		if (sellCost < 0) {
			throw new StockException("negative sell cost given!");
		} else {
			this.sellCost = sellCost;
		}
		
		//Set the reorderPoint and protect from negative input
		if (reorderPoint < 0) {
			throw new StockException("negative reorder point given!");
		} else {
			this.reorderPoint = reorderPoint;
		}
		
		//Set the reorderAmount and protect from negative input
		if (reorderAmount < 0) {
			throw new StockException("negative reorderAmount given!");
		} else {
			this.reorderAmount = reorderAmount;
		}
		
		//Set the temperature of an item
		if(temperature <= 10) {
			//for cold items
			this.temperature = temperature;
		}else {
			//for ordinary items, set temperature to 11 for simplicity
			this.temperature = 11;
		}
	}
	
	/**
	 * A getter method to return itemName
	 * @return itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * A getter method to return manufactureCost
	 * @return manufactureCost
	 */
	public double getManufactureCost() {
		return this.manufactureCost;
	}
	
	/**
	 * A getter method to return sellCost
	 * @return sellCost
	 */
	public double getSellCost() {
		return this.sellCost;
	}
	
	/**
	 * A getter method to return reorderPoint
	 * @return reorderPoint
	 */
	public int getReorderPoint() {
		return this.reorderPoint;
	}
	
	/**
	 * A getter method to return reorderAmount
	 * @return reorderAmount
	 */
	public int getReorderAmount() {
		return this.reorderAmount;
	}
	
	/**
	 * A method to determin if a given item is cold by evaluating this.temperature
	 * @return bool
	 * @throws StockException
	 */
	public boolean isCold() throws StockException {
		//If temp is less than 10, then item is cold
		if (this.temperature <= 10){
			return true;
		} else if (this.temperature == 11) {
			//Else if item is 11, therefore an ordinary item, return false
			return false;
		} else {
			//If any other value given an error has occured and an error should be thrown
			throw new StockException("Invalid Temperature Error");
		}
	}
	
	/**
	 * A getter method to return an item's temperature
	 * @return temperature
	 * @throws StockException
	 */
	public int getTemperature() throws StockException {
		//If temp is less than 10, then item is cold
		if (this.temperature <= 10) {
			return this.temperature;
		} else {
			//else throw an exception stating the item is not cold
			throw new StockException("This item is not a cold item");
		}
	}
}









