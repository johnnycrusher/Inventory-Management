package Stock;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
	
	// Generate an array of item names
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
			"chicken"	
	};
	
	
	/**
	 * This method generates a random double from a specified minimum value
	 * and a specified maximum value
	 * @param min the specified minimum number that can be generated
	 * @param max the specified maximum number taht can be generated
	 * @return randomDouble - the random number that was generated
	 */
	private static double randomDouble(double min, double max) {
		double randomDouble = random.nextDouble() * (max - min) + min;
		return randomDouble;
	}
	
	
	/**This method task is the generate random Item name with no duplicates
	 * @param number number of random names it should create
	 * @return randomCargoList - an arrayList of random item names
	 */
	private static ArrayList<String> generateItemNames(int number) {
		//generate Array list for storage
		ArrayList<String> foodList = new ArrayList<String>();
		ArrayList<String> randomCargoList = new ArrayList<String>();
		//forloop for adding item names in foodList
		for(int index = 0; index < itemNames.length ; index++) {
			foodList.add(itemNames[index]);
		}
		//random index number
		int randomIndex;
		//
		for(int index = 0; index < number; index++) {
			//generate random number
			randomIndex = randomInteger(0,foodList.size()-1);
			//get the random food item
			String randomItemName = foodList.get(randomIndex);
			//remove the item from the list so it can't be selected again
			foodList.remove(randomIndex);
			//add it in the random cargo names
			randomCargoList.add(randomItemName);
		}
		return randomCargoList;
	}

	
	/**Generates a random stock object
	 * @param type the type of stock normal or refrigerated
	 * @return stock - returns the randomly generated stock
	 * @throws StockException throws an error when there is a stock error
	 */
	private static Stock generateRandomStock(String type) throws StockException {
		//create stock object
		Stock stock = new Stock();
		int temperature;
		//generate 5 random names
		ArrayList<String> itemNames = generateItemNames(5);
		//generate 5 random items with the specified type
		for(int index = 0; index < 5; index++) {
			double manufactureCost = randomDouble(0,100);
			double sellCost = randomDouble(0,100);
			int reorderPoint = randomInteger(0, 500);
			int reorderAmount = randomInteger(0, 100);
			if(type.equals("refrigetrated")) {
				temperature = randomInteger(-20,10);
			}else {
				temperature = 11;
			}
			//generate random quantity
			int itemQty = randomInteger(0,150);
			//add item to stock object
			Item item = new Item(itemNames.get(index), manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			stock.addItem(item, itemQty);
		}
		return stock;
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
	 * @param min the minimum value that the random number can generate
	 * @param max the maximum value that the random number can generate
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
	public void testStartingCapital() throws StockException {
		Store store = Store.getInstance();
		//needs to be reset due to having previous modified data from other test
		store.resetCapital();
		double startingCapital = store.getCapital();
		double storeStartingCapital = 100000;
		
		assertEquals("Starting Capital is not the same", startingCapital,storeStartingCapital,0.01);
	}
	/*Test 3: get capital after manifest is paid;
	 */
	@Test
	public void testSubtractingCapital() throws StockException {
		Store store = Store.getInstance();
		
		double startingCaptial = store.getCapital();
		
		double substratedAmount = randomInteger(1000, 10000);
		double resultingCapital = startingCaptial - substratedAmount;
		
		
		
		store.subtractCapital(substratedAmount);
		double storeCurrentCapital = store.getCapital();
		
		assertEquals("Subtactracted Capital is not the same", resultingCapital, storeCurrentCapital, 0.01);
	}
	/*Test 4: get capital after making profit from sales
	 */
	@Test
	public void testProfitCapital() throws StockException {
		Store store = Store.getInstance();
		
		double startingCapital = store.getCapital();
		double addedAmount = randomInteger(1000,10000);
		double resultingCapital = startingCapital + addedAmount;
		
		
		store.addCapital(addedAmount);
		double storeCurrentCapital = store.getCapital();
		
		assertEquals("added Capital is not the same", resultingCapital, storeCurrentCapital, 0.01);
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
		Stock stockInventory = generateRandomStock("dry");
		
		Store store = Store.getInstance();
		
		store.setInventory(stockInventory);
		Stock storeObjInventory = store.getInventory();
		assertEquals("stock items are not the same", stockInventory,storeObjInventory);
	}
	
	/*Test 7: Adding to store inventory
	 */
	@Test
	public void testAddStoreInventory() throws StockException {
		//emulating a sample inventory input from the stocks class
		Stock stockInventory = generateRandomStock("dry");
		//Generating an inventory to add
		
		
		
		
		Store store = Store.getInstance();
		
		store.setInventory(stockInventory);
		
		store.addInventory(stockInventory);
		
		HashMap<Item,Integer> stockInventoryHash = stockInventory.returnStockList();
		for (Map.Entry<Item, Integer> items: stockInventoryHash.entrySet()) {
			stockInventory.addQuantity(items.getKey().getItemName(), items.getValue());
		}
		
		Stock storeInventory = store.getInventory();
		
		assertEquals("stock items are not the same", storeInventory,storeInventory);
	}
	
	/*Test 8: get stock inventory when no stock is instantiated
	 */
	@Test (expected = StockException.class)
	public void testGetNonExistingInventory() throws StockException {
		Store store = Store.getInstance();
		Stock stock = new Stock();
		store.setInventory(stock);
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
