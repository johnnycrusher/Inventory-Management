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
		public HashMap<String,Integer> generateStockInventory() {
			HashMap<String, Integer> stockInventory = new HashMap<String,Integer>();
			ArrayList<String> itemNames = generateFoodItems();
			for (int index = 0; index < itemNames.size();index++) {
				stockInventory.put(itemNames.get(index), randomInteger(50,500));
			}
			
			return stockInventory;
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
	

	/**
	 * This method generates a random double from a specified minimum value
	 * and a specified maximum value
	 * @param min - the specified minimum number that can be generated
	 * @param max - the specified maximum number that can be generated
	 * @return randomDouble - the random number that was generated
	 */
	private static double randomDouble(double min, double max) {
		double randomDouble = random.nextDouble() * (max - min) + min;
		return randomDouble;
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
	public void testGetInstance() {
		store = Store.getInstance();
	}
	/* Test 2 get starting capital
	 */
	@Test
	public void testStartingCapital() {
		double startingCapital = 100000;
		store = Store.getInstance();
		double storeStartingCapital = store.getCapital();
		//Apparently assertEquals for doubles has been deprecated so a 0.1 delta will be added(may adjust in future)
		assertEquals("Starting Capital is not the same", startingCapital,storeStartingCapital, 0.1);
	}
	/*Test 3: get capital after manifest is paid;
	 */
	@Test
	public void testSubtractingCapital() {
		double startingCaptial = 100000;
		double substratedAmount = randomDouble(1000, 10000);
		double resultingCapital = startingCaptial - substratedAmount;
		
		store = store.getInstance();
		
		store.subtractCapital(substratedAmount);
		double storeCurrentCapital = store.getCapital();
		//Apparently assertEquals for doubles has been deprecated so a 0.1 delta will be added( may adjust in future)
		assertEquals("Subtactracted Capital is not the same", resultingCapital, storeCurrentCapital, 0.1);
	}
	/*Test 4: get capital after making profit from sales
	 */
	@Test
	public void testProfitCapital() {
		double startingCapital = 100000;
		double addedAmount = randomDouble(1000,10000);
		double resultingCapital = startingCapital + addedAmount;
		
		store = store.getInstance();
		store.addCapital(addedAmount);
		double storeCurrentCapital = store.getCapital();
		
		assertEquals("added Capital is not the same", resultingCapital, storeCurrentCapital, 0.1);
	}
	/*Test 5: test setting store name
	 */
	@Test
	public void testSetName() {
		String storeName = randomStoreName();
		store = store.getInstance();
		
		store.setName(storeName);
		String returnedStoreName = store.getName();
		
		assertEquals("store name is not the same",storeName, returnedStoreName);
	}
	/*Test 6: Get store name when there is no name
	 */
	@Test (expected = StockException.class)
	public void testGetNonExistingStoreName() {
		store = store.getInstance();
		String returnedStoreName = store.getName();
	}
	/*Test 7: Import store inventory
	 */
	@Test
	public void testUpdateStoreInventory() {
		//emulating a sample inventory input from the stocks class
		HashMap<String,Integer> stockInventory = generateStockInventory();
		
		store = store.getInstance();
		store.setInventory(stockInventory);
		HashMap<String,Integer> storeObjInventory = store.getInventory();
		assertEquals("stock items are not the same", stockInventory,storeObjInventory);
	}
	/*Test 8: get stock inventory when no stock is instantiated
	 */
	@Test (expected = StockException.class)
	public void testGetNonExistingInventory() {
		store = store.getInstance();
		store.getInventory();
	}
	/* Test 9: entering a negative number when paying for manifest
	 */
	//Not quite sure if we have to make a new exception since stock exception really doesn't fit
	@Test (expected = StockException.class)
	public void testGetCapitalWhenNegativeMoneyPaidForManifest() {
		double startingCapital = 100000;
		double negativeSubtractedAmmount = randomDouble(-10000,-1000);
		
		store = store.getInstance();
		store.subtractCapital(negativeSubtractedAmmount);
		double storeCapital =  store.getCapital();
	}
	/* Test 10: Entered a negative number when entering profits from sales
	 */
	//Assuming profits are always made from sales
	@Test (expected = StockException.class)
	public void testGetCapitalWhenNegtativeMoneyFromSalesProfit() {
		double negativeAddedAmount = randomDouble(-10000,-1000);
		
		store = store.getInstance();
		store.addCapital(negativeAddedAmount);
		double storeCapital = store.getCapital();
	}
	
	/*Test 11: When manifest cost ammount exceeds current capital 
	 */
	@Test (expected = StockException.class)
	public void testManifestCostExceedsRemaindedCapital() {
		//Assume Capital is current at 100000 (starting capital)
		double subtractedAmmount = randomDouble(100001, 1000000);
		store = getInstance();
		store.subtractCapital(subtractedAmmount);
		double storeCapital = store.getCapital();
	}
	
}
