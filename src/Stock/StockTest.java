package Stock;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import Delivery.Manifest;
import Delivery.Truck;
import Exception.DeliveryException;
import Exception.StockException;

/**
 * This class represents the Unit Test for the Stock Object.
 * @author Tom
 * @version 1.0
 *
 */

public class StockTest {
		// Generate a Random Object
		private static Random random = new Random();
				
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
		 * @author John Huynh
		 */
		private static String randomItemName() {
			return itemNames[random.nextInt(itemNames.length)];
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
		
		
		/**This method task is the generate random Item name with no duplicates
		 * @param number - number of random names it should create
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

	
		/* Test 0: Declaring a Stock object
		 */
		Stock stock;
		
		@Before
		public void setUpStock() {
			stock = null;
		}
		
		/*
		 * Test 1: Constructing a basic Stock object.
		 * [This test obliges you to add a constructor with two parameters]
		 * 
		 */
		@Test
		public void testConstruction() {			
			stock = new Stock();
		}
		
		/*
		 * Test 2: Adding and getting an item object from the stock
		 * [This test obliges you to add a getter and setter
		 * method for the stock's items]
		 */
		@Test
		public void testStockItems() throws StockException {
			String itemName = randomItemName();
			double manufactureCost = randomDouble(0,100);
			double sellCost = randomDouble(0,100);
			int reorderPoint = randomInteger(0, 500);
			int reorderAmount = randomInteger(0, 100);
			int temperature = randomInteger(-40,10);
			
			Item item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			
			int quantity = randomInteger(0,100);
			
			stock = new Stock();
			
			
			stock.addItem(item, quantity);
			
			Item itemObj = stock.getItem(itemName);

			assertEquals("Wrong Item Returned", item, itemObj);			
		}
		
		/*
		 * Test 3: Adding quantity to an item object from the stock
		 * [This test obliges you to add an adder
		 * method for the stock's items]
		 */
		@Test
		public void testStockAddQuantity() throws StockException {
			String itemName = randomItemName();
			double manufactureCost = randomDouble(0,100);
			double sellCost = randomDouble(0,100);
			int reorderPoint = randomInteger(0, 500);
			int reorderAmount = randomInteger(0, 100);
			int temperature = randomInteger(-40,10);
			
			Item item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			
			int quantity = randomInteger(0,100);
			
			
			
			stock = new Stock();
			
			
			stock.addItem(item, quantity);
			stock.addQuantity(item.getItemName(), quantity);
			
			int testItemQuantity = stock.getItemQuantity(itemName);
			
			//Maybe something like Item itemObj = stock.getItem(itemparam,quantityparam?);
			
			assertEquals("Wrong Item Returned", quantity * 2, testItemQuantity);			
		}
		
		/*
		 * Test 4: Get the stock item quantity
		 * [This test obliges you to add a getter method for the stock's item quantity]
		 */
		@Test
		public void testStockItemQuantity() throws StockException{
			int quantity = randomInteger(0,100);
			
			String itemName = randomItemName();
			double manufactureCost = randomDouble(0,100);
			double sellCost = randomDouble(0,100);
			int reorderPoint = randomInteger(0, 500);
			int reorderAmount = randomInteger(0, 100);
			int temperature = randomInteger(-40,10);
			
			Item item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			
			stock = new Stock();
			
			
			stock.addItem(item, quantity);
			
			int itemQuantity = stock.getItemQuantity(itemName);
			
			assertEquals("Wrong Quantity Returned", quantity, itemQuantity);			
		}
		
		/*
		 * Test 5: Get the stock list
		 * [This test obliges you to add a getter method for the stock's collection of items]
		 */
		@Test
		public void testStockItemList()throws StockException {

			
			stock = new Stock();		
			
			int itemNumber = randomInteger(1,10);
			
			int itemQuantity;
			
			HashMap<Item, Integer> testStock = new HashMap<Item, Integer>();
			ArrayList<String> itemNames = generateItemNames(itemNumber);
			for(int index = 0; index < itemNumber; index++) {
				double manufactureCost = randomDouble(0,100);
				double sellCost = randomDouble(0,100);
				int reorderPoint = randomInteger(0, 500);
				int reorderAmount = randomInteger(0, 100);
				int temperature = randomInteger(-40,10);
				
				
				itemQuantity = randomInteger(0,100);
				Item item = new Item(itemNames.get(index), manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
				
				testStock.put(item, itemQuantity);
				stock.addItem(item, itemQuantity);
			}	
			assertEquals("Wrong Stock Returned", testStock, stock.returnStockList());	
		}
		
		/*
		 * Test 6: Remove Item from Stock
		 * [This test obliges you to add a remove() method for the stock's collection of items]
		 * Accepts two params remove(Item item, Int quantity)
		 */
		public void testStockItemRemove() throws StockException{
			stock = new Stock();		
			
			String itemName = randomItemName();
			double manufactureCost = randomDouble(0,100);
			double sellCost = randomDouble(0,100);
			int reorderPoint = randomInteger(0, 500);
			int reorderAmount = randomInteger(0, 100);
			int temperature = randomInteger(-40,10);
			
			int itemQuantity = randomInteger(1,300);
			Item item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			
			int expectedQuantity = itemQuantity - 1;
			stock.addItem(item, itemQuantity);
			stock.remove(itemName,1);
			assertEquals("Wrong Quantity Returned", expectedQuantity, stock.getItemQuantity(itemName));	
		}
		
		/*
		 * Test 7: Test removing Item from Stock when there is no stock
		 */
		@Test (expected = StockException.class)
		public void removeItemExceptionTest() throws StockException {
			stock = new Stock();
			stock.remove(randomItemName(),1);
		}
		
		/*
		 * Test 8: Test returnItemList when there are no items
		 */
		@Test (expected = StockException.class)
		public void returnItemListExceptionTest() throws StockException {
			stock = new Stock();
			stock.returnStockList();
		}
		
		/*
		 * Test 9: Test getItem with invalid item given
		 */
		@Test (expected = StockException.class)
		public void getItemExceptionTest() throws StockException {
			stock = new Stock();
			
			int itemQuantity = randomInteger(0,100);
			
			String itemName = randomItemName();
			double manufactureCost = randomDouble(0,100);
			double sellCost = randomDouble(0,100);
			int reorderPoint = randomInteger(0, 500);
			int reorderAmount = randomInteger(0, 100);
			int temperature = randomInteger(-40,10);
			
			Item item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			
			String invalidItem = "Wrong type";
			
			stock.addItem(item, itemQuantity);
			stock.getItem(invalidItem);
		}
		
		/*
		 * Test 10: Test stockQuantity with invalid item
		 */
		@Test (expected = StockException.class)
		public void stockQuantityExceptionTest() throws StockException {
			stock = new Stock();		
			int itemQuantity = randomInteger(0,100);
			
			String itemName = randomItemName();
			double manufactureCost = randomDouble(0,100);
			double sellCost = randomDouble(0,100);
			int reorderPoint = randomInteger(0, 500);
			int reorderAmount = randomInteger(0, 100);
			int temperature = randomInteger(-40,10);
			
			Item item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			String invalidItem = "Wrong type";
			
			stock.addItem(item, itemQuantity);
			stock.getItemQuantity(invalidItem);
		}
}
