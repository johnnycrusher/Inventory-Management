package Stock;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import Delivery.Manifest;
import Delivery.OrdinaryTruck;
import Delivery.Truck;
import Exception.DeliveryException;
import junit.Uber.Uber;

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

		
		/* Can a collection of items be created DONE
			Can you add items to the collection DONE
			Can return an item object DONE
			Can return an item's quantity DONE
			Can return a list of items and quantities;
			Can you remove items from the collection
			Can stock quantites be added based on how many are incoming
			can stock quantites be remove based on how many are being sold
			Is an exception thrown when invalid object given to collection*/

	
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
			int stockID = randomInteger(0,10);
			
			stock = new Stock(stockID);
		}
		
		/*
		 * Test 2: Getting the stockID and Quantity
		 * [This test obliges you to add a getter
		 * method for the stock's ID and Quantity]
		 */
		@Test
		public void testStockID() {
			int stockID = randomInteger(0,10);
			
			stock = new Stock(stockID);
			
			assertEquals(stockID, stock.getStockID());
		}
		
		
		/*
		 * Test 3: Adding and getting an item object in the stock
		 * [This test obliges you to add a getter and setter 
		 * method for the stock's items]
		 */
		@Test
		public void testStockItems() {
			int quantity = randomInteger(0,100);
			int stockID = randomInteger(0,100);
			
			stock = new Stock(stockID);
			
			Item item = new Item();
			
			stock.add(item, quantity);
			
			Item itemObj = stock.getItem(0);
			
			assertEquals("Wrong Item Returned", item, itemObj);			
		}
		
		/*
		 * Test 4: Get the stock item quantity
		 * [This test obliges you to add a getter method for the stock's item quantity]
		 */
		@Test
		public void testStockItemQuantity() {
			int quantity = randomInteger(0,100);
			int stockID = randomInteger(0,100);
			int itemQuantity;
			
			stock = new Stock(stockID);
			
			Item item = new Item();
			
			stock.add(item, quantity);
			
			itemQuantity = stock.getItemQuantity(item);
			
			assertEquals("Wrong Quantity Returned", quantity, itemQuantity);			
		}
		
		/*
		 * Test 5: Get the stock list
		 * [This test obliges you to add a getter method for the stock's collection of items]
		 */
		@Test
		public void testStockItemList() {
			int quantity = randomInteger(0,100);
			int stockID = randomInteger(0,100);
			int itemQuantity;
			
			stock = new Stock(stockID);
			
			Item item = new Item();
			
			stock.add(item, quantity);
			
			//NOT FINISHED
			
			assertEquals("Wrong Quantity Returned", quantity, itemQuantity);			
		}
		
		//Exception Tests to be added
		@Test
		public void exceptionTest0() throws StockException {}
		
		@Test
		public void exceptionTest1() throws StockException {}
		
		@Test
		public void exceptionTest2() throws StockException {}
		
}
