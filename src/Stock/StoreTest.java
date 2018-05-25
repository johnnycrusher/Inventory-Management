package Stock;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import Exception.StockException;
import sun.security.jca.GetInstance;


/** This class represents the Unit test for the Store Object
 * @author John
 *
 */
public class StoreTest {
	
	private static Random random = new Random();
	//Store Names
	private static String[] storeNames = new String[] {
			"Woolworths",
			"Coles",
			"IGA",
			"ALDI",
			"Sims",
			"Harris Farm Markets",
			"Food For Less",
			"Drakes Supermarkets",
			"BI-LO",
			"Franklins",
			"Moran & Cato",
			"Ritchies Stores",
			"Supabarn Supermarkets",
			"Thomas Dux Grocer",
			"Foodland"	
	};
	
	//Generate an array of dry food items
		private static String[] itemNames = new String[] {
				"rice",
				"breans",
				"pasta",
				"biscuits",
				"nuts",
				"chips",
				"chocolate",
				"bread",
				"mushroom",
				"tomatoes",
				"lettuce",
				"grapes",
				"asparagus",
				"celery",
				"chicken",
				"beef",
				"fish",
				"yoghurt",
				"milk",
				"cheese",
				"ice cream",
				"ice",
				"frozen meat",
				"frozen vegetable mix"
		};
		
		/** This method generates 5 randomly selected dry food items and 
		 *  5 randomly selected refridgerated foods and then stores them
		 *  in another array list
		 * @return the randomly generated cargo items
		 */
		public static ArrayList<String> generateFoodItems() {
			ArrayList<String> itemNamesList = new ArrayList<String>();
			for(int index = 0; index < itemNames.length ; index++) {
				itemNamesList.add(itemNames[index]);
			}
			return itemNamesList;
		}
				
		/**This method combines the randomly generated food items and
		 * the randomly generates items properties and stores them in
		 * a hashmap.
		 * @return a hashmap containing the item name and the item properties.
		 */
		public Stock generateStockInventory() {
			HashMap<String, Integer> stockInventory = new HashMap<String,Integer>();
			ArrayList<String> itemNames = generateFoodItems();
			for (int index = 0; index < itemNames.size();index++) {
				stockInventory.put(itemNames.get(index), randomInteger(50,500));
			}
			
			return stockInventory;
		}
	
	/**
	 * This method adds the contents of one hashmap (inventory) to another, iterating the int value
	 * @author Tom
	 */
	private void addInventory(HashMap<String, Integer> baseInventory,HashMap<String, Integer> newInventory) throws StockException{
		for (String key : newInventory.keySet()) {
		    if (baseInventory.containsKey(key)) {
		        baseInventory.put(key, baseInventory.get(key) + 1);
		    } else {
		    	baseInventory.put(key, newInventory.get(key));
		    }
		}
	}
		
	/**
	 * This method generates a random store name string from list
	 * of stores names in the array.
	 * @return the random storeName
	 */
	private static String randomStoreName() {
		return storeNames[random.nextInt(storeNames.length)];
	}
	
	/**
	 * This method generates a random integer from a specified minimum value
	 * and a specified maximum value
	 * @param min - the minimum value that the random number can generate
	 * @param max - the maximum value that the random number can generate
	 * @return the random number that is generated
	 */
	private static int randomInteger(int min,int max) {
		return random.nextInt((max - min) + 1) + min;
	}

	Store store;	
	/* Test 0: Declaring a Store object
	 */
	@Before
	public void setupStore() {
		store = null;
	}
	/*Test 1: Test Store Constructor
	 */
	@Test
	public void testConstructor() {
		Store.getInstance();
	}
	/* Test 2 get starting capital
	 */
	@Test
	public void testStartingCapital() {
		Store store = Store.getInstance();
		int startingCapital = store.getCapital();
		int storeStartingCapital = store.getCapital();
		
		assertEquals("Starting Capital is not the same", startingCapital,storeStartingCapital);
	}
	/*Test 3: get capital after manifest is paid;
	 */
	@Test
	public void testSubtractingCapital() throws StockException {
		Store store = Store.getInstance();
		
		int startingCaptial = store.getCapital();
		
		int substratedAmount = randomInteger(1000, 10000);
		int resultingCapital = startingCaptial - substratedAmount;
		
		
		
		store.subtractCapital(substratedAmount);
		int storeCurrentCapital = store.getCapital();
		
		assertEquals("Subtactracted Capital is not the same", resultingCapital, storeCurrentCapital);
	}
	/*Test 4: get capital after making profit from sales
	 */
	@Test
	public void testProfitCapital() throws StockException {
		Store store = Store.getInstance();
		
		int startingCapital = store.getCapital();
		int addedAmount = randomInteger(1000,10000);
		int resultingCapital = startingCapital + addedAmount;
		
		
		store.addCapital(addedAmount);
		int storeCurrentCapital = store.getCapital();
		
		assertEquals("added Capital is not the same", resultingCapital, storeCurrentCapital, 0.1);
	}
	/*Test 5: test setting store name
	 */
	@Test
	public void testSetName() throws StockException {
		String storeName = randomStoreName();
		Store store = Store.getInstance();
		
		store.setName(storeName);
		String returnedStoreName = store.getName();
		
		assertEquals("store name is not the same",storeName, returnedStoreName);
	}
	/*Test 6: Get store name when there is no name
	 */
	@Test (expected = StockException.class)
	public void testGetNonExistingStoreName() throws StockException {
		Store store = Store.getInstance();
		store.getName();
	}
	/*Test 7: Setting store inventory
	 */
	@Test
	public void testUpdateStoreInventory() throws StockException {
		//emulating a sample inventory input from the stocks class
		HashMap<String,Integer> stockInventory = generateStockInventory();
		
		Store store = Store.getInstance();
		
		store.setInventory(stockInventory);
		HashMap<String,Integer> storeObjInventory = store.getInventory();
		assertEquals("stock items are not the same", stockInventory,storeObjInventory);
	}
	
	/*Test 7: Adding to store inventory
	 */
	@Test
	public void testAddStoreInventory() throws StockException {
		//emulating a sample inventory input from the stocks class
		HashMap<String,Integer> stockInventory = generateStockInventory();
		//Generating an inventory to add
		HashMap<String,Integer> addInventory = generateStockInventory();
		
		Store store = Store.getInstance();
		
		store.setInventory(stockInventory);
		
		HashMap<String,Integer> storeTestInventory = store.getInventory();
		
		//Adding to the test inventory
		addInventory(storeTestInventory,addInventory);
		//Adding to the Store inventory
		store.addInventory(addInventory);
		
		HashMap<String,Integer> storeObjInventory = store.getInventory();
		
		assertEquals("stock items are not the same", storeTestInventory,storeObjInventory);
	}
	
	/*Test 8: get stock inventory when no stock is instantiated
	 */
	@Test (expected = StockException.class)
	public void testGetNonExistingInventory() throws StockException {
		Store store = Store.getInstance();
		HashMap<String,Integer> storeObjInventory = new HashMap<String,Integer>();
		store.setInventory(storeObjInventory);
		store.getInventory();
	}
	
	
	/* Test 9: Entered a negative number when entering cost
	 */
	@Test (expected = StockException.class)
	public void testGetCapitalWhenNegtativeCostGiven() throws StockException {
		int negativeAddedAmount = randomInteger(-10000,-1000);
		
		Store store = Store.getInstance();
		
		store.subtractCapital(negativeAddedAmount);
	}
	
	/* Test 10: Entered a negative number when entering profits from sales
	 */
	//Assuming profits are always made from sales
	@Test (expected = StockException.class)
	public void testGetCapitalWhenNegtativeMoneyFromSalesProfit() throws StockException {
		int negativeAddedAmount = randomInteger(-10000,-1000);
		
		Store store = Store.getInstance();
		
		store.addCapital(negativeAddedAmount);
	}
	
	/*Test 11: When manifest cost ammount exceeds current capital 
	 */
	@Test (expected = StockException.class)
	public void testManifestCostExceedsRemaindedCapital() throws StockException {
		//Assume Capital is current at 100000 (starting capital)
		int subtractedAmmount = randomInteger(100001, 1000000);
		Store store = Store.getInstance();
		
		store.subtractCapital(subtractedAmmount);
		store.getCapital();
	}
	
}
