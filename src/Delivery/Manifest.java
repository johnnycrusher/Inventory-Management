/**
 * The Manifest class deals with creating manifest objects
 * and storing stock objects and the distrubution of stock
 * objects to trucks for delivery. 
 * @author Tom
 * @version 1.0
 */
package Delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Exception.DeliveryException;
import Exception.StockException;
import Stock.Item;
import Stock.Stock;

public class Manifest {
	//Capacity Declarations
	final static int coldCapacity = 800;
	final static int ordinaryCapacity = 1000;
	
	//ArrayList Declarations
	ArrayList<Truck> truckList;
	ArrayList<Stock> cargoStock;
	
	//Stock Declarations
	Stock ordinaryStock;
	Stock coldStock;
	Stock totalStock;
	Stock importedStock;
	
	/**
	 * Constructor method to create a Manifest object
	 */
	public Manifest() {
		//Instantiate a manifest with new Stock and ArrayList<> objects
		ordinaryStock = new Stock();
		coldStock = new Stock();
		totalStock = new Stock();
		truckList = new ArrayList<Truck>();
		cargoStock = new ArrayList<Stock>();
		importedStock = new Stock();
	}

	/**
	 * A method to add cargo to a manifest in the form of a stock object and sort cold from ordinary
	 * @param stock the stock object that is being added as the imported stock
	 * @throws StockException throws a stock error when import stock is empty
	 * @throws DeliveryException throws a delivery error when imported stock is empty
	 */
	public void addItemStock(Stock stock) throws StockException, DeliveryException{
		//Keep record of the total stock
		importedStock = stock;
		determineCargoStock();
		splitRefridgeratedFromOrdinary();
		
	}
	
	
	public void importTotalStock(Stock importedTotalStock) {
		totalStock = importedTotalStock;
	}
	
	/**
	 * A method which determine if an item needs to be reordered and added to the manifest's totalStock
	 * @throws StockException throws a stock error when there is a stock problem
	 * @throws DeliveryException throws a delevery error when there is no imported stock
	 */
	private void determineCargoStock() throws StockException, DeliveryException {
		if(importedStock.returnStockList().isEmpty()) {
			throw new DeliveryException("There is no imported Stock");
		}
		for (Map.Entry<Item,Integer> entry : importedStock.returnStockList().entrySet()) {
			Item currentItem = entry.getKey();
			int  itemQuantity = entry.getValue();
			int itemReorderPoint = entry.getKey().getReorderPoint();
			int itemReorderAmmount = entry.getKey().getReorderAmount();
			
			if(itemQuantity <= itemReorderPoint) {
				totalStock.addItem(currentItem, itemReorderAmmount);
			}
		}
	}
	
	/**
	 * A method to separate the refrigerated items from the ordinary items
	 * @throws StockException throws a stock exception when there is a stock error
	 * @throws DeliveryException throws a delivery exception when there is no cargo imported
	 */
	private void splitRefridgeratedFromOrdinary() throws StockException, DeliveryException {
		//Assign each item in the given stock object to either coldStock, or ordinaryStock
		if(totalStock.returnStockList().isEmpty()) {
			throw new DeliveryException("There is no cargo imported");
		}
		for (Item key : totalStock.returnStockList().keySet()) {
			if (key.isCold()){
		        this.coldStock.addItem(key, totalStock.getItemQuantity(key.getItemName()));  
			} else {
				this.ordinaryStock.addItem(key, totalStock.getItemQuantity(key.getItemName()));  
			}
		}
	}
	
	/**
	 * A method which optimizes truck cargo by analysis of stock
	 * @throws StockException throws a stock error when there is a stock problem
	 */
	public void sortStock() throws StockException {
		//Get ordinaryStock hashmap
		HashMap<Item,Integer> ordinaryItems = null;
		int coldTruckCount = determineColdTruckCount();
		
		//Optimise each required refrigerated truck
		for( int index = 0; index < coldTruckCount; index++) {
			//Create refrigerated stock item for each refrigerated truck
			Stock refrigeratedTruckStock = new Stock();
			for(int item = 0; item < coldStock.returnStockList().size()-1; item++) {
				
				//Get the name of the coldest current item in the coldStock
				String coldestName = determineColdestItem();
				if(!(coldestName == null)) {
				//Get the coldest Item object
				Item itemObj = coldStock.getItem(coldestName);
				
					//Get the quantity of the coldest item object
					int itemQuantity = coldStock.getItemQuantity(coldestName);
					
					//Get quantity of the stock
					int refrigeratedStockQuantity = refrigeratedTruckStock.getNumberOfItems();
					
					//Create a variable to monitor remaining space
					int numberOfSpaceLeft = 0;
					
					//Check if itemQuantity can fit in the truck
					if((refrigeratedStockQuantity + itemQuantity) <= coldCapacity) {
						//Add items to truck
						refrigeratedTruckStock.addItem(itemObj, itemQuantity);
						
						//Remove items from stock
						coldStock.remove(coldestName, itemQuantity);
						if(coldStock.getNumberOfItems() == 0 && ordinaryStock.getNumberOfItems() == 0) {
							cargoStock.add(refrigeratedTruckStock);
							break;
						}else if(coldStock.getNumberOfItems() == 0){
							cargoStock.add(refrigeratedTruckStock);
							break;
						}
					}else { //If the entirety of the stock can't fit in one truck, fill truck as much as possible
						//Determine space left
						numberOfSpaceLeft = coldCapacity - refrigeratedStockQuantity; 
						
						//Add items to truck
						refrigeratedTruckStock.addItem(itemObj, numberOfSpaceLeft);
						
						//remove items from stock
						coldStock.remove(coldestName, numberOfSpaceLeft);
						
						// We can either disregard this and insert into truck object or we could keep and store this
						// Ideally keep the cargoStock array list as we can use that to compare our optimisation method
						cargoStock.add(refrigeratedTruckStock);
						
						//Break out of the for loop
						break;
					}
				}
			}
			if (refrigeratedTruckStock.getNumberOfItems() < coldCapacity) {
				//Update ordinaryItems
				ordinaryItems = this.ordinaryStock.returnStockList();
				//for each Item in the ordinaryItems HashMap
				for(Item itemObj : ordinaryItems.keySet()) {
					
					//Get the item object
					Item item = this.ordinaryStock.getItem(itemObj.getItemName());
					
					//Get the quantity of the item object
					int itemQuantity = ordinaryStock.getItemQuantity(item.getItemName());
					
					//Get quantity of the stock
					int refrigeratedStockQuantity = refrigeratedTruckStock.getNumberOfItems();
					
					//Create a variable to monitor remaining space
					int numberOfSpaceLeft = 0;
					
					//Check if itemQuantity can fit in the truck
					if((refrigeratedStockQuantity + itemQuantity) <= coldCapacity) {
						//Add items to truck
						refrigeratedTruckStock.addItem(itemObj, itemQuantity);
						
						//Remove items from stock
						ordinaryStock.remove(item.getItemName(), itemQuantity);
						if( ordinaryStock.getNumberOfItems() == 0) {
							cargoStock.add(refrigeratedTruckStock);
							break;
						}
					}else { //If the entirety of the stock can't fit in one truck, fill truck as much as possible
						//Determine space left
						numberOfSpaceLeft = coldCapacity - refrigeratedStockQuantity; 
						
						//Add items to truck
						refrigeratedTruckStock.addItem(itemObj, numberOfSpaceLeft);
						
						//remove items from stock
						ordinaryStock.remove(item.getItemName(), numberOfSpaceLeft);
						
						// We can either disregard this and insert into truck object or we could keep and store this
						// Ideally keep the cargoStock array list as we can use that to compare our optimisation method
//						cargoStock.add(refrigeratedTruckStock);
						
						//Break out of for loop
						break;
					}
				}
			}
		}

		//For each required ordinary truck
		int ordinaryTruckCount = determineOrdinaryTruckCount();
		for( int index = 0; index < ordinaryTruckCount; index++) {
			//Create stock item for each ordinary truck
			Stock ordinaryTruckStock = new Stock();
			
			//Update ordinaryItems
			ordinaryItems = this.ordinaryStock.returnStockList();
			
			//For each Item in the ordinaryItems HashMap
			for(Item itemObj : ordinaryItems.keySet()) {
				//Get the item object
				Item item = this.ordinaryStock.getItem(itemObj.getItemName());
				
				//Get the quantity of the item object
				int itemQuantity = ordinaryStock.getItemQuantity(item.getItemName());
				
				//Get quantity of the stock
				int ordinaryStockQuantity = ordinaryTruckStock.getNumberOfItems();
				
				//Create a variable to monitor remaining space
				int numberOfSpaceLeft = 0;
				
				if(!(itemQuantity == 0)) {
					//Check if itemQuantity can fit in the truck
					if((ordinaryStockQuantity + itemQuantity) <= ordinaryCapacity) {
						//Add items to truck
						ordinaryTruckStock.addItem(itemObj, itemQuantity);
						
						//Remove items from stock
						ordinaryStock.remove(itemObj.getItemName(), itemQuantity);
						if(ordinaryStock.getNumberOfItems() == 0) {
							cargoStock.add(ordinaryTruckStock);
							break;
						}
					}else { //If the entirety of the stock can't fit in one truck, fill truck as much as possible
						//Determine space left
						numberOfSpaceLeft = ordinaryCapacity - ordinaryStockQuantity; 
						
						//Add items to truck
						ordinaryTruckStock.addItem(itemObj, numberOfSpaceLeft);
						
						//remove items from stock
						ordinaryStock.remove(itemObj.getItemName(), numberOfSpaceLeft);
						
						// We can either disregard this and insert into truck object or we could keep and store this
						// Ideally keep the cargoStock array list as we can use that to compare our optimisation method
						cargoStock.add(ordinaryTruckStock);
						
						break;
					}
				}		
			}
		}
	}

	/**
	 * A method which determines the required temperature of a truck object 
	 * by finding the coldest item temperature in the stock object
	 * @throws StockException throws a stock error when is a stock problem
	 * @returns objectName - the name of the coldest item
	 */
	private String determineColdestItem() throws StockException {
		//find the set the lowest temp to 11
		int currentLowestTemp = 11;
		int itemObjectTemp = 11;
		String objectName = null;
		//loop in the hashmap to evaluate keys and values
		for(Map.Entry<Item, Integer> entry : coldStock.returnStockList().entrySet()) {
			//get the item object
			Item itemObject = entry.getKey();
			//get item quantity
			int itemQuantity = entry.getValue();
			//if item Quantity is above zero log the temp
			if (itemQuantity > 0) {
				itemObjectTemp = itemObject.getTemperature();
				//compare wiht the current lowest temp if lower then replace as new low temp
				if(itemObjectTemp < currentLowestTemp) {
					currentLowestTemp = itemObjectTemp;
					objectName = itemObject.getItemName();
				}
			}
		}
		//return the item name of the lowest temp item
		return objectName;
	}
	/**
	 * A manifest initialisation method which creates the required trucks and adds them to the truckList
	 * @throws StockException throws a stock error when there is a stock problem
	 * @throws DeliveryException throws a delievery exception when there are no trucks required
	 */
	public void createTrucks() throws StockException, DeliveryException {
		int coldTruckCount = determineColdTruckCount();
		int ordinaryTruckCount = determineOrdinaryTruckCount();
		if(coldTruckCount == 0 && ordinaryTruckCount == 0) {
			throw new DeliveryException("No trucks are required due to no cargo");
		}
		for (int numOfRefridgeTrucks=0; numOfRefridgeTrucks < determineColdTruckCount(); numOfRefridgeTrucks++) {
			Truck truck = new RefrigeratedTruck();
			truckList.add(truck);
		}
		for (int numOfOrdinaryTrucks=0; numOfOrdinaryTrucks < determineOrdinaryTruckCount(); numOfOrdinaryTrucks++) {
			Truck truck = new OrdinaryTruck();
			truckList.add(truck);
		}
	}
	
	/**
	 * A method which determines the required number of cold trucks
	 * @returns numberOfColdTrucks - the number of cold trucks required
	 */
	private int determineColdTruckCount() {
		int numberOfColdItems = coldStock.getNumberOfItems();
		double numberOfColdTrucks = Math.ceil((double)numberOfColdItems/coldCapacity);
		return (int) numberOfColdTrucks;
	}
	
	/**
	 * A method which determines the required number of ordinary trucks
	 * @returns numberOfOrdinaryTrucks - the number of ordinary trucks required
	 */
	private int determineOrdinaryTruckCount() {
		int numberOfColdItems = coldStock.getNumberOfItems();
		int numberOfOrdinaryItems = ordinaryStock.getNumberOfItems();
		
		int totalItems = numberOfColdItems + numberOfOrdinaryItems;
		
		int remainingOrdinaryItems = totalItems - (determineColdTruckCount() * coldCapacity);
		double numberOfOrdinaryTrucks = 0;
		if(remainingOrdinaryItems > 0) {
			numberOfOrdinaryTrucks = Math.ceil((double)remainingOrdinaryItems/ordinaryCapacity);
			return (int) numberOfOrdinaryTrucks;
		}else {
			return 0;
		}
	}
	
	
	/**a getter method to get the cargo stock
	 * @return totalStock - the stock that needs to be reoreder
	 */
	public Stock getCargoStock() {
		return totalStock;
	}
	
	/**Get the stock that was imported into the manifest
	 * @return importedStock - returns the imported stock
	 * @throws DeliveryException when there is no imported cargo 
	 */
	public Stock getImportStock() throws DeliveryException{	
		if (importedStock.getNumberOfItems() == 0){
			throw new DeliveryException("No imported cargo");
		} else {
			return importedStock;
		}
	}
	
	/** Loads the optimised cargo into each of the trucks avaliable trucks
	 * @throws DeliveryException when optimsied cargo exceeds the truck capacity or adds a refrigerated item to ordinary
	 * @throws StockException throws an error when there is a stock error
	 */
	public void loadCargoToTrucks() throws DeliveryException, StockException {
		if(truckList.isEmpty() || cargoStock.isEmpty()) {
			throw new DeliveryException("there is no cargo or there are no trucks created");
		}
		for(int index = 0; index < truckList.size(); index++) {
			truckList.get(index).add(cargoStock.get(index));
		}
	}
	
	/**
	 * A method to return the cargoStock ArrayList
	 * @return ArrayList - the array list that stores optimised cargo
	 * @throws DeliveryException throws an delivery exception when there is no cargo to the cargoStock
	 */
	public ArrayList<Stock> getOptimisedCargo() throws DeliveryException{
		if(cargoStock.isEmpty()) {
			throw new DeliveryException("No cargo can't get optimised cargo");
		}
		return cargoStock;
	}


	/**Adder method to add truck objects to the ArrayList
	 * @param truck truck object that needs to be added to the arraylist
	 */
	public void addTruck(Truck truck) {
		this.truckList.add(truck);
	}
	
	/**A method used to add Stock objects directly into a truck
	 * @param stock the stock that is being imported into the truck
	 * @param index  the index of the truck in the arrayList
	 * @throws DeliveryException when the imported cargo exceeds the trucks capacity
	 *  or when refrigated item is being added to ordinary trucks
	 * @throws StockException throws a stock error when there is a stock problem
	 */
	public void addCargoDirectlyToTruck(Stock stock, int index) throws DeliveryException, StockException {
		truckList.get(index).add(stock);
	}
	
	/**
	 * A getter method to return truckList ArrayList
	 * @return truckList - the array list of truck
	 */
	public ArrayList<Truck> getAllTrucks() {
		return truckList;
	}
	
	
	/**Calculates the cost of the cargo inside all trucks
	 * @return totalCost - the total cost of the cargo
	 * @throws DeliveryException throws a delivery excpetion when it there is no cargo to get the cost for
	 * @throws StockException throws a stock error when there is a stock problem
	 */
	public double getCargoCost() throws DeliveryException, StockException {
		if(totalStock.getNumberOfItems() == 0) {
			throw new DeliveryException("Cannot Get Cost of Cargo as there is no cargo");
		}
		HashMap<Item,Integer> totalStockHash = totalStock.returnStockList();
		double totalCost = 0;
		for(Map.Entry<Item,Integer> entry : totalStockHash.entrySet()) {
			double cost = entry.getKey().getManufactureCost();
			int quantity = entry.getValue();
			totalCost += (cost * quantity);
		}
		return totalCost;
	}
	
	/**
	 * A method to calculate the manifest's totalCost for capital adjustment purposes
	 * @return totalCost - the total cost of the manifest including truck cost and cargo cost
	 * @throws StockException throws a stock exception when there is a stock problem
	 * @throws DeliveryException throws a delivery exception when there is no stock to calculate
	 */
	public double getManifestCost() throws StockException, DeliveryException {
		if(truckList.size() == 0 ) {
			throw new DeliveryException("Cannot get manifest cost when there are no Stock");
		}
		double totalCost = 0;
		double sumCargoCost = 0;
		double sumTruckCost = 0;
		for(int index = 0; index < truckList.size(); index++) {
			double cargoCost = truckList.get(index).getStockCost();
			sumCargoCost += cargoCost;
			double truckCost = truckList.get(index).getCost();
			sumTruckCost += truckCost;
		}
		totalCost = sumCargoCost + sumTruckCost;
		return totalCost;
	}
}
