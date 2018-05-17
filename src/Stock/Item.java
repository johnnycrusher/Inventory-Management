/**
 * 
 */
package Stock;

import Exception.StockException;

/**
 * @author Tom Nash
 *
 */
public class Item {
	String itemName;
	double manufactureCost;
	double sellCost;
	int reorderPoint;
	int reorderAmount;
	int temperature;
	
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
		
		if(temperature <= 10) {
			this.temperature = temperature;
		}else {
			this.temperature = 40;
		}
	}
	
	
	public String getItemName() {
		return itemName;
	}

	public double getManufactureCost() {
		return this.manufactureCost;
	}
	
	public double getSellCost() {
		return this.sellCost;
	}
	
	public int getReorderPoint() {
		return this.reorderPoint;
	}
	
	public int getReorderAmount() {
		return this.reorderAmount;
	}
	
	public int getTemperature() throws StockException {
		if (this.temperature <= 10) {
			return this.temperature;
		} else {
			throw new StockException("This item is not a cold item");
		}
	}
}









