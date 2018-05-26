/**
 * The Item class, is in charge of create item object
 * which store important properties each item has
 * @author Tom
 * @version 1.0
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
	 * @param itemName the item name
	 * @param manufactureCost the manufacturing cost
	 * @param sellCost the sell cost
	 * @param reorderPoint the reorder point
	 * @param reorderAmount the reorder amount
	 * @param temperature the temperature of the object
	 * @throws StockException throws an error when there is a stock error
	 */
	public Item(String itemName, double manufactureCost, double sellCost, int reorderPoint, int reorderAmount, int temperature) throws StockException {
		//Set the itemName
		this.itemName = itemName;
		
		//Set the manufactureCost and protect from negative input
		if (manufactureCost < 0) {
			throw new StockException("The item " + itemName + " has a negative manufacture cost!");
		} else {
			this.manufactureCost = manufactureCost;
		}
		
		//Set the sellCost and protect from negative input
		if (sellCost < 0) {
			throw new StockException("The item " + itemName + " has a negative sell cost!");
		} else {
			this.sellCost = sellCost;
		}
		
		//Set the reorderPoint and protect from negative input
		if (reorderPoint < 0) {
			throw new StockException("The item " + itemName + " has a negative reorder point!");
		} else {
			this.reorderPoint = reorderPoint;
		}
		
		//Set the reorderAmount and protect from negative input
		if (reorderAmount < 0) {
			throw new StockException("The item " + itemName + " has a negative reorderAmount!");
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
	 * @return itemName - the name of the item
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * A getter method to return manufactureCost
	 * @return manufactureCost - the manufacuture cost
	 */
	public double getManufactureCost() {
		return this.manufactureCost;
	}
	
	/**
	 * A getter method to return sellCost
	 * @return sellCost - the sell cost
	 */
	public double getSellCost() {
		return this.sellCost;
	}
	
	/**
	 * A getter method to return reorderPoint
	 * @return reorderPoint - the reorder point
	 */
	public int getReorderPoint() {
		return this.reorderPoint;
	}
	
	/**
	 * A getter method to return reorderAmount
	 * @return reorderAmount - the reorder ammount
	 */
	public int getReorderAmount() {
		return this.reorderAmount;
	}
	
	/**
	 * A method to determin if a given item is cold by evaluating this.temperature
	 * @return boolean - a truck if the temp is under then false if it equal to 11
	 * 				anything else is an invalid temp
	 * @throws StockException throws an error if there is stock related errors
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
	 * @return temperature - the temperature of the item
	 */
	public int getTemperature() {
		//If temp is less than 10, then item is cold
		if (this.temperature <= 10) {
			return this.temperature;
		} else {
			return 11; //return the item's ordinary item temperature
		}
	}
}









