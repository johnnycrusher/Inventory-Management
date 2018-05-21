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
	HashMap<Item,Integer> stockManifest;
	HashMap<String,Integer> nameManifest;
	
	HashMap<String,Integer> totalManifest;
	
	HashMap<String,Integer> ordinaryItemManifest;
	HashMap<String,Integer> refrigeratedItemManifest;
	
	ArrayList<Truck> truckList;
	
	final static int coldCapacity = 800;
	final static int ordinaryCapcity = 1000;
	
	
	Stock ordinaryStock;
	Stock coldStock;
	
	//constructor method to create a Manifest object
	public Manifest() {
		ordinaryStock = new Stock();
		coldStock = new Stock();
		truckList = new ArrayList<Truck>();
		
		stockManifest = new HashMap<Item,Integer>();
		nameManifest = new HashMap<String,Integer>();
		
		totalManifest = new HashMap<String,Integer>();
		
		ordinaryItemManifest = new HashMap<String,Integer>();
		refrigeratedItemManifest = new HashMap<String,Integer>();
	}
	
	//adder method to add cargo to a manifest in the form of a hashmap
	public void addCargo(HashMap<String,Integer> cargo){
		for (String key : cargo.keySet()) {
		    if (nameManifest.containsKey(key)) {
		        this.nameManifest.put(key, this.nameManifest.get(key) + cargo.get(key));
		    } else {
		    	this.nameManifest.put(key, cargo.get(key));
		    }
		}
	}
	
	//adder method to add cargo to a manifest in the form of a stock object
	public void addStock(Stock stock) throws StockException{
		for (Item key : stock.returnStockList().keySet()) {
		    if (stockManifest.containsKey(key)) {
		        this.stockManifest.put(key, this.stockManifest.get(key) + stock.getItemQuantity(key.getItemName()));
		    } else {
		    	this.stockManifest.put(key, stock.getItemQuantity(key.getItemName()));
		    }
		}
	}
	
	//adder method to add cargo to a manifest in the form of a stock object and sort cold from ordinary
	public void addItemStock(Stock stock) throws StockException{
		
		for (Item key : stock.returnStockList().keySet()) {
			
			if (key.isCold()){
		        this.coldStock.add(key, stock.getItemQuantity(key.getItemName()));  
			} else {
				this.ordinaryStock.add(key, stock.getItemQuantity(key.getItemName()));  
			}
		}
	}
	
	public void sortStock() throws StockException {
		for( int index = 0; index < determineColdTruckCount(); index++) {
			Stock refrigeratedStock = new Stock();
			for(int j = 0; j < coldStock.returnStockList().size(); j++) {
				String coldestName = determineColdestItem();
				Item itemObj = coldStock.getItem(coldestName);
				int itemQuantity = coldStock.getItemQuantity(coldestName);
				int refrigeratedStockQuantity = refrigeratedStock.getNumberOfItems();
				int numberOfSpaceLeft = 0;
				if((refrigeratedStockQuantity + itemQuantity) <= coldCapacity) {
					refrigeratedStock.add(itemObj, itemQuantity);
					coldStock.remove(coldestName, itemQuantity);
				}else {
					numberOfSpaceLeft = coldCapacity - refrigeratedStockQuantity; 
					refrigeratedStock.add(itemObj, numberOfSpaceLeft);
					coldStock.remove(coldestName, numberOfSpaceLeft);
					break;
				}
			}
		}
	}
	
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
	
	private int determineColdTruckCount() {
		int numberOfColdItems = coldStock.getNumberOfItems();
		double numberOfColdTrucks = Math.ceil((double)numberOfColdItems/coldCapacity);
		return (int) numberOfColdTrucks;
	}
	
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
	//NEED TO FIGURE OUT METHOD OF SORTING WHETHER DONE HERE OR IN CSV MACHINE
	//NEED TO FIGURE OUT HOW THE FORMATTING OF A MANIFEST OBJECT IS DONE??????
	//HASHMAP, TRUCK COLLECTION WHICH HAS A FUNCTION WHICH RETURNS ALL COLD ITEMS 
	//AND A METHOD WHICH RETURNS ALL ORDINARY ITEMS??
	
	//Getter method to return total cargo
	public Stock getCargo(){	
		return null;
	}

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
