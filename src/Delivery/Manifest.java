/**
 * Manifest Class 
 * @author Tom
 */
package Delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	
	/**constructor method to create a Manifest object
	 *
	 */
	public Manifest() {
		//Instantiate a manifest with new Stock and ArrayList<> objects
		ordinaryStock = new Stock();
		coldStock = new Stock();
		totalStock = new Stock();
		truckList = new ArrayList<Truck>();
		cargoStock = new ArrayList<Stock>();
	}
			
	/**adder method to add cargo to a manifest in the form of a stock object and sort cold from ordinary
	*/
	public void addItemStock(Stock stock) throws StockException{
		//Keep record of the total stock
		totalStock = stock;
		
		//Assign each item in the given stock object to either coldStock, or ordinaryStock
		for (Item key : stock.returnStockList().keySet()) {
			
			if (key.isCold()){
		        this.coldStock.add(key, stock.getItemQuantity(key.getItemName()));  
			} else {
				this.ordinaryStock.add(key, stock.getItemQuantity(key.getItemName()));  
			}
		}
	}
	
	/**
	 * A method which optimizes truck cargo by analysis of stock
	 * @throws StockException
	*/
	public void sortStock() throws StockException {
		//Get ordinaryStock hashmap
		HashMap<Item,Integer> ordinaryItems = null;
		
		//Optimise each required refrigerated truck
		for( int index = 0; index < determineColdTruckCount(); index++) {
			//Create refrigerated stock item for each refrigerated truck
			Stock refrigeratedTruckStock = new Stock();
			for(int item = 0; item < coldStock.returnStockList().size(); item++) {
				
				//Get the name of the coldest current item in the coldStock
				String coldestName = determineColdestItem();
				
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
					refrigeratedTruckStock.add(itemObj, itemQuantity);
					
					//Remove items from stock
					coldStock.remove(coldestName, itemQuantity);
				}else { //If the entirety of the stock can't fit in one truck, fill truck as much as possible
					//Determine space left
					numberOfSpaceLeft = coldCapacity - refrigeratedStockQuantity; 
					
					//Add items to truck
					refrigeratedTruckStock.add(itemObj, numberOfSpaceLeft);
					
					//remove items from stock
					coldStock.remove(coldestName, numberOfSpaceLeft);
					
					// We can either disregard this and insert into truck object or we could keep and store this
					// Ideally keep the cargoStock array list as we can use that to compare our optimisation method
					cargoStock.add(refrigeratedTruckStock);
					
					break;
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
						refrigeratedTruckStock.add(itemObj, itemQuantity);
						
						//Remove items from stock
						coldStock.remove(item.getItemName(), itemQuantity);
					}else { //If the entirety of the stock can't fit in one truck, fill truck as much as possible
						//Determine space left
						numberOfSpaceLeft = coldCapacity - refrigeratedStockQuantity; 
						
						//Add items to truck
						refrigeratedTruckStock.add(itemObj, numberOfSpaceLeft);
						
						//remove items from stock
						coldStock.remove(item.getItemName(), numberOfSpaceLeft);
						
						// We can either disregard this and insert into truck object or we could keep and store this
						// Ideally keep the cargoStock array list as we can use that to compare our optimisation method
						cargoStock.add(refrigeratedTruckStock);
						
						break;
					}
				}
			}
		}
		
		//For Ordinary Items
		//For each required ordinary truck
		for( int index = 0; index < determineOrdinaryTruckCount(); index++) {
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
				
				//Check if itemQuantity can fit in the truck
				if((ordinaryStockQuantity + itemQuantity) <= ordinaryCapacity) {
					//Add items to truck
					ordinaryTruckStock.add(itemObj, itemQuantity);
					
					//Remove items from stock
					ordinaryStock.remove(itemObj.getItemName(), itemQuantity);
				}else { //If the entirety of the stock can't fit in one truck, fill truck as much as possible
					//Determine space left
					numberOfSpaceLeft = ordinaryCapacity - ordinaryStockQuantity; 
					
					//Add items to truck
					ordinaryTruckStock.add(itemObj, numberOfSpaceLeft);
					
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

	/**A method which determines the required temperature of a truck object 
	 * by finding the coldest item temperature in the stock object
	 * @throws StockException
	*/
	private String determineColdestItem() throws StockException {
		int currentLowestTemp = 11;
		int itemObjectTemp = 11;
		String objectName = null;
		for(Map.Entry<Item, Integer> entry : coldStock.returnStockList().entrySet()) {
			Item itemObject = entry.getKey();
			int itemQuantity = entry.getValue();
			if (itemQuantity > 0) {
				try {
					itemObjectTemp = itemObject.getTemperature();
				}catch(StockException e) {
					itemObjectTemp = 11;
				}
				if(itemObjectTemp < currentLowestTemp) {
					currentLowestTemp = itemObjectTemp;
					objectName = itemObject.getItemName();
				}
			}
		}
		return objectName;
	}
	
	/**A manifest initialisation method which creates the required trucks and adds them to the truckList
	 * @throws StockException
	*/
	public void createTrucks() throws StockException {		
		for (int i=0; i < determineColdTruckCount(); i++) {
			Truck truck = new RefrigeratedTruck();
			truckList.add(truck);
		}
		for (int i=0; i < determineOrdinaryTruckCount(); i++) {
			Truck truck = new OrdinaryTruck();
			truckList.add(truck);
		}
	}
	
	/**
	 * A method which determins the required number of cold trucks
	 * @returns int numberOfColdTrucks
	 */
	private int determineColdTruckCount() {
		int numberOfColdItems = coldStock.getNumberOfItems();
		double numberOfColdTrucks = Math.ceil((double)numberOfColdItems/coldCapacity);
		return (int) numberOfColdTrucks;
	}
	
	/**
	 * A method which determins the required number of ordinary trucks
	 * @returns int numberOfOrdinaryTrucks
	 */
	private int determineOrdinaryTruckCount() {
		int numberOfColdItems = coldStock.getNumberOfItems();
		int numberOfOrdinaryItems = ordinaryStock.getNumberOfItems();
		
		int totalItems = numberOfColdItems + numberOfOrdinaryItems;
		
		int remainingOrdinaryItems = totalItems - (determineColdTruckCount() * coldCapacity);
		double numberOfOrdinaryTrucks = 0;
		if(remainingOrdinaryItems > 0) {
			numberOfOrdinaryTrucks = Math.ceil((double)remainingOrdinaryItems/ordinaryCapcity);
			return (int) numberOfOrdinaryTrucks;
		}else {
			return 0;
		}
	}
	
	/**Getter method to return total cargo
	 * @returns Stock totalStock
	*/
	public Stock getCargo(){	
		return totalStock;
	}

	//Adder method to add truck objects to the ArrayList<Truck>
	public void addTruck(Truck truck) {
		this.truckList.add(truck);
	}

	//Remove method to remove truck from truckList at given index
	public void removeTruck(int i) {
		this.truckList.remove(i);
	}

	//getter method to retrieve truck at given index
	public Truck getTruck(int i) {
		return this.truckList.get(i);
	}
	
	//getter method to return truck arrayList
	public ArrayList<Truck> getAllTrucks() {
		return truckList;
	}
}
