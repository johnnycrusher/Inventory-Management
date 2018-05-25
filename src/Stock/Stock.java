package Stock;

import java.util.HashMap;
import java.util.Map;

import Exception.StockException;

/**
 * @author John Huynh
 *
 */
public class Stock {
	//Intialise HashMap as the Collector for the Stock Obj
	HashMap<Item, Integer> stock;
	

	/**The Default Constructor for the stock objects
	 * this intialises the HashMap Used for Storing
	 */
	public Stock() {
		stock = new HashMap<Item,Integer>(); 
	}
	
	
	/** A method to add an Item to the Stock Object
	 * @param item - the Item being added to the stock object
	 * @param quantity - the quantity of the Item being added to the Stock Obejct
	 * @throws StockException - the Exception the stock object throws
	 */
	public void addItem(Item item, int quantity) throws StockException {
		//find the item name of the Item Obj
		String itemName = item.itemName;
		//Intialise a boolean to determine if the object already exist in the stock object
		boolean itemExist = false;
		for(Map.Entry<Item, Integer> currentItem : stock.entrySet()) {
			String stockItemName = currentItem.getKey().itemName;
			//Throw a Stock Exception if it detects a duplicate
			if(itemName.equals(stockItemName)) {
				itemExist = true;
				throw new StockException("This item is already in the stock list");
			}
		}
		//If the item doesn't exist in the stock objected it is then added to stock Obj
		if(itemExist == false) {
			stock.put(item, quantity);
		}
	}
	
	/** A method to added the itemQuantity to an already existing object
	 * @param itemName - item name of the object that requires adding
	 * @param quantity - quantity of the Item being added into stock Obj
	 * @throws StockException - throws a Stock Exception if it doesn't exist
	 */
	public void addQuantity(String itemName, int quantity) throws StockException {
		//initalise boolean to detect if obj exist
		boolean itemExist = false;
		
		for(Map.Entry<Item, Integer> currentItem : stock.entrySet()){
			
			Item itemObj = currentItem.getKey();
			String stockItemName = currentItem.getKey().getItemName();
			//Check if item name exist in the Stock Object
			if(itemName.equals(stockItemName)) {
				itemExist = true;
				//modifies the stock object with the new quantity
				stock.put(itemObj, getItemQuantity(stockItemName) + quantity);
				break;
			}
		}
		//Throws a Stock Exception Error if the Item doesn't Exist
		if (itemExist == false) {
			throw new StockException("THis item doesn't exist in stock can't add quantity");
		}
	}
	
	/**A method to get the Item from the Stock Object when intering the Item Name
	 * @param itemName - item name of the object that is being searched in Stock Obj
	 * @return key - the Item Object that is was searching for
	 * @throws StockException
	 */
	public Item getItem(String itemName) throws StockException {
		//Set an Item variable as null
		Item key = null;
		boolean detectItem = false;
		//Intialise a string Variable to store the itemName
		String keyValue;
		for(Map.Entry<Item,Integer> currentItem : stock.entrySet()) {
			//find the Item and the Item name
			key = currentItem.getKey();
			keyValue = currentItem.getKey().itemName;
			//checks if the Stock item name equals with the input item name
			if(keyValue.equals(itemName)) {
				detectItem = true;
				//exits out of the forloop as it knows the key value
				break;
			}
		}
		//Throws stock exception when stock is not in the list
		if(!detectItem) {
			throw new StockException("This item " + itemName + " is not in the stock list");
		}
		return key;
	}
	
	/**A method that gets the Item Quantity of the Object
	 * @param itemName
	 * @return itemQuantity - the quantity of the item it was searching for
	 * @throws StockException when the item its search for isn't in Stock Obj
	 */
	public int getItemQuantity(String itemName) throws StockException {
		//intialise Key item name and boolean to detect if it exist
		String keyItemName;
		int itemQuantity = 0;
		boolean detectedMatch = false;
		//Searches for the item using entry set
		for(Map.Entry<Item, Integer> currentItem: stock.entrySet()) {
			keyItemName = currentItem.getKey().itemName;
			itemQuantity = currentItem.getValue();
			//search if inputed item name equals the same in the stock Obj
			if(keyItemName.equals(itemName)) {
				//set detect match when it detects a match
				detectedMatch = true;
				break;
			}
		}
		//if it doesn't detect any match throws a Stock Exception 
		if(detectedMatch == false) {
			throw new StockException("Cannot get item quantity because item doesn't exist in stock");
		}
		//returns the item Quantity
		return itemQuantity;
	}
	
	/** A method used to return the HashMap of the Stock Object
	 * @return stock - The HashMap of the Stock Object
	 * @throws StockException when the stock object is empty
	 */
	public HashMap<Item, Integer> returnStockList() throws StockException{
		//Checks if the Stock Obj is empty if it is then throw a stock Exception
		if(stock.isEmpty()) {
			throw new StockException("Cannot return Stock List as it is empty");
		}
		//return the stock object
		return stock;
	}
	
	/**A method used to remove a specified quantity that needs to be removed from the stock Obj
	 * @param itemName - item name of the object that needs to be removed
	 * @param quantity - item Quantity of the object that needs to be removed
	 * @throws StockException occurs when remove more then what is already in stock object
	 */
	public void remove(String itemName, int quantity) throws StockException{
		//get the Item Quantity 
		int currentValue = getItemQuantity(itemName);
		//get the Item Object
		Item item = getItem(itemName);
		
		//Check if the current quantity of the object is greater then the remove quantity
		if(currentValue < quantity) {
			//throws exception if remove quantity is greater then stock quantity
			throw new StockException("Cannot remove item due to remove quantity is greater then item quantity");
		}
		//determines the new Quantity value of the Object
		int newQuantityValue = currentValue - quantity;
		//Overrides the previous item with new quantity
		stock.put(item, newQuantityValue);
	}
	
	/**A method used to return number of Items in the Stock Object
	 * @return numOfItems -  the number of Items in the Stock Object
	 */
	public int getNumberOfItems() {
		int numOfItems = 0;
		//looping to determine the sum of the quantity in the Stock Obj 
		for(Map.Entry<Item,Integer> item : stock.entrySet()) {
			numOfItems += item.getValue();
		}
		//returns number of Items
		return numOfItems;
	}
	
	/**A method used to convert the Stock Object into a Object[][] for JTable GUI
	 * @return object - An Object[][] for JTable GUI
	 * @throws StockException 
	 */
	public Object[][] convertStockIntoTable(){
		//Define the Object Size
		Object[][] object = new Object[stock.size()][7];
		//intialise the index
		int index = 0; 
		//loop and arranges all the Item Properties int their respect columns
		for(Map.Entry<Item,Integer> currentItem : stock.entrySet()) {
			//get the currentItem Key and Value
			Item item = currentItem.getKey();
			int itemQty = currentItem.getValue();
			String itemTemp = null;
			//Determine the temperature of the current item
			try {
				if(item.isCold()) {
					itemTemp = Integer.toString(item.getTemperature());
				}
			} catch (StockException e) {
				itemTemp = "";
			}

			//Set the item name as the first column of the Item Object
			object[index][0] = item.getItemName();
			//Set the item manufacture cost as the second column of the Item Object
			object[index][1] = Double.toString(item.getManufactureCost());
			//Set the item sell cost as the third column of the Item Object
			object[index][2] = Double.toString(item.getSellCost());
			//Set the item reorder point as the fourth column of the 
			object[index][3] = Integer.toString(item.getReorderPoint());
			//Set the item reorder ammount as the firth column
			object[index][4] = Integer.toString(item.getReorderAmount());
			//Set the item temperature as the sixth column
			object[index][5] = itemTemp;
			//Set the item quantity as the seventh column
			object[index][6] = Integer.toString(itemQty);
			//increment the index for the next row
			index++;
		}
		//return the object
		return object;
	}
}
