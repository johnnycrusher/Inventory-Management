package Stock;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import Exception.StockException;


/**
 * This class represents the Unit Test for the Item Object.
 * @author John
 * @version 1.0
 *
 */
public class ItemTest {
	// Generate a Random Object
	private static Random random = new Random();
	
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
	 * This method generates a random item name string from list
	 * of item names in the array.
	 * @return the random itemName
	 */
	private static String randomItemName() {
		return itemNames[random.nextInt(itemNames.length)];
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
	 * and a specified mximum value
	 * @param min - the specified minimum number that can be generated
	 * @param max - the specified maximum number taht can be generated
	 * @return randomDouble - the random number that was generated
	 */
	private static double randomDouble(double min, double max) {
		double randomDouble = random.nextDouble() * (max - min) + min;
		return randomDouble;
	}
	
	/**
	 * This method generates a random integer to represent the temperature.
	 * it has two values either an integer to represent temperature or a null value
	 * to represent dry food that require no refrigeration.
	 * @param min - the minimum number that the random number generator can generate
	 * @param max - the random number that the random number generator can generate
	 * @return the random integer
	 */
	private static int generateRandomTemerature(int min, int max) {
		int decider = random.nextInt()%2;
		if(decider == 0) {
			return random.nextInt((min-max) + 1) + min;
		}else {
			return (Integer) null;
		}
		
		
	}

	/* Test 0: Declaring a Item object
	 */
	Item item;
	
	@Before
	public void setUpItem() {
		item = null;
	}
	
	/* Test 1: Constructing a Item Object
	 */
	@Test
	public void testConstructor() throws StockException {
		String itemName = randomItemName();
		double manufactureCost = randomDouble(0,100);
		double sellCost = randomDouble(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
	}
	
	/* Test 2: Get the Item Name
	 */
	@Test
	public void testItemName() throws StockException {
		String itemName = randomItemName();
		double manufactureCost = randomDouble(0,100);
		double sellCost = randomDouble(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
		
		assertEquals("item name incorrect ",itemName, item.getItemName());
	}
	

	/* Test 3: Get the Manufacture Cost
	 */
	@Test
	public void testManufactureCost() throws StockException {
		String itemName = randomItemName();
		double manufactureCost = randomDouble(0,100);
		double sellCost = randomDouble(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
		
		assertEquals("manufacture cost incorrect", manufactureCost, item.getManufactureCost(),0);
	}
	
	/* Test 4: Get the Sell Cost
	 */
	@Test
	public void testSellCost() throws StockException {
		String itemName = randomItemName();
		double manufactureCost = randomDouble(0,100);
		double sellCost = randomDouble(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
		
		assertEquals("sell cost incorrect", sellCost, item.getSellCost(),0);
	}
	
	/* Test 5: Get the Reorder Point
	 */
	@Test
	public void testReorderPoint() throws StockException {
		String itemName = randomItemName();
		double manufactureCost = randomDouble(0,100);
		double sellCost = randomDouble(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
		
		assertEquals("reorder point incorrect", reorderPoint, item.getReorderPoint());
	}
	
	/* Test 6: Get the Reorder Amount
	 */
	@Test
	public void testReorderAmmount() throws StockException {
		String itemName = randomItemName();
		double manufactureCost = randomDouble(0,100);
		double sellCost = randomDouble(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
		
		assertEquals("reorder ammount incorrect", reorderAmount, item.getReorderAmount());
	}
	
	/* Test 7: Get the Temperature
	 */
	@Test
	public void testTemperature() throws StockException {
		String itemName = randomItemName();
		double manufactureCost = randomDouble(0,100);
		double sellCost = randomDouble(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
		
		assertEquals("temperature incorrect", temperature, item.getTemperature());
	}
	
	/* Test 8: Test if a negative number can be inputed in manufacture cost 
	 */
	@Test (expected = StockException.class)
	public void testNegativeManufactureCost() throws StockException {
		String itemName = randomItemName();
		double manufactureCost = randomDouble(-100,-1);
		double sellCost = randomDouble(0,30);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
	}
	
	/* Test 9: Test if a negative number can be inputed in sell cost
	 */
	@Test (expected = StockException.class)
	public void testNegativeSellCost() throws StockException {
		String itemName = randomItemName();
		double manufactureCost = randomDouble(0,100);
		double sellCost = randomDouble(-200,-1);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
			
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
	}
	
	/* Test 10: Test if a negative number can be inputed in reorderPoint
	 */
	@Test (expected = StockException.class)
	public void  testNegativeReorderPoint() throws StockException {
		String itemName = randomItemName();
		double manufactureCost = randomDouble(0,100);
		double sellCost = randomDouble(0,100);
		int reorderPoint = randomInteger(-500, -1);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
			
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
	}
	
	/*
	 *  Test 11: Test if a negative number can be inputed in reorderAmount
	 */
	
	@Test (expected = StockException.class)
	public void testNegativeReorderAmount() throws StockException {
		String itemName = randomItemName();
		double manufactureCost = randomDouble(0,100);
		double sellCost = randomDouble(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(-500, -1);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
	}
	
	/* Test 12: Test if a duplicate item has been created
	 */
	@Test (expected = StockException.class)
	public void testDuplicateItem() {
		
	}
}
