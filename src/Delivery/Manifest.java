/**
 * Manifest Class 
 * @author Tom
 */
package Delivery;

import java.util.ArrayList;
import java.util.HashMap;

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
