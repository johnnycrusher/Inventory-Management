/**
 * 
 */
package Delivery;

import java.util.HashMap;

import Exception.StockException;
import Stock.Item;
import Stock.Stock;

/**
 * @author Tom
 *
 */

public class Manifest {
	HashMap<Item,Integer> stockManifest;
	HashMap<String,Integer> nameManifest;
	
	HashMap<String,Integer> totalManifest;
	
	HashMap<String,Integer> ordinaryItemManifest;
	HashMap<String,Integer> refrigeratedItemManifest;
	
	//constructor method to create a Manifest object
	public Manifest() {
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
	
	//Getter method to return total cargo
	public HashMap<String,Integer> getCargo(HashMap<String,Integer> cargo){	
		return this.manifest;
	}

	public void addTruck(Truck refridgeratedTruck) {
		// TODO Auto-generated method stub
		
	}

	public void removeTruck(int i) {
		// TODO Auto-generated method stub
		
	}

	public Truck getTruck(int i) {
		// TODO Auto-generated method stub
		return null;
	}
}
