package Delivery;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import Exception.DeliveryException;
import Stock.Item;
import Stock.Stock;

public class RefrigeratedTruckTest {
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
		
	/* Test 0: Declaring a refrigerated Truck object
	 */
	Truck refrigeratedTruck;
	
	@Before
	public void setUpRefrigeratedTruck() {
		refrigeratedTruck = null;
	}
	
	/*
	 * Test 1: Constructing a refrigerated Truck object.
	 * [This test obliges you to add a constructor]
	 * 
	 */
	@Test
	public void testConstruction() {			
		refrigeratedTruck = new RefrigeratedTruck();
	}
	
	/*
	 * Test 2: Test adding stock to the truck object
	 * [This test obliges you to add a Add() method for the truck object]
	 */
	@Test
	public void addStockTest() {
		
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = new Stock();		
		
		refrigeratedTruck.add(stock);
	}
	
	/*
	 * Test 3: Test getting stock from truck object
	 * [This test obliges you to add a get method for the truck object]
	 */
	@Test
	public void getStockTest() {
		
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = new Stock();		
		
		refrigeratedTruck.add(stock);
		
		assertEquals("Wrong Stock Returned", stock, refrigeratedTruck.getStock());	
	}

	/*
	 * Test 4: Test getting current quantity
	 * [This test obliges you to add a getQuantity method for the truck object]
	 */
	@Test
	public void getQuantityTest() {
		int randQuantity = randomInteger(1,10);
		String itemName = randomItemName();
		int temp = randomInteger(-40,10);
		
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = new Stock();		
		
		Item item = new Item(itemName, temp /*other vars*/);
		
		stock.add(item, randQuantity);
		
		refrigeratedTruck.add(stock);
		
		assertEquals("Wrong Stock Level Returned", randQuantity, refrigeratedTruck.getQuantity());	
	}
	
	/*
	 * Test 5: Test getting temp of refrigerated truck
	 * [This test obliges you to add a getTemp() method for the truck object]
	 */
	@Test
	public void getTempTest() {
		int randTemp = randomInteger(-40,10);
		int randQuantity = randomInteger(1,10);
		String itemName = randomItemName();
		
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = new Stock();		
		
		Item item = new Item(itemName, randTemp /*other vars*/);
		
		stock.add(item, randQuantity);
		
		refrigeratedTruck.add(stock);
		
		assertEquals("Wrong Temp Returned", randTemp, refrigeratedTruck.getTemp());	
	}
	
	/*
	 * Test 6: Test setting temp of refrigerated truck
	 * [This test obliges you to add a setTemp() method for the truck object]
	 */
	@Test
	public void setTempTest() {
		int randTemp = randomInteger(-40,10);
		
		refrigeratedTruck = new RefrigeratedTruck();
		
		refrigeratedTruck.setTemp(randTemp);
		
		assertEquals("Wrong Temp Returned", randTemp, refrigeratedTruck.getTemp());	
	}
	
	/*
	 * Test 7: Test getting cost in dollars
	 * [This test obliges you to add a getCost() method for the truck object]
	 */
	@Test
	public void getCostTest() {
		int expectedCost;
		int currentTemp = randomInteger(-40,10);
		float exponent;
		int randQuantity = randomInteger(1,10);
		String itemName = randomItemName();
		
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = new Stock();		
		
		Item item = new Item(itemName /*other vars*/);
		
		stock.add(item, randQuantity);
		
		refrigeratedTruck.add(stock);

		exponent = currentTemp / 5;
		expectedCost = (int) (900 + (200 * java.lang.Math.pow(0.7, exponent)));
		
		assertEquals("Wrong Stock Returned", expectedCost, refrigeratedTruck.getCost());	
	}	
	
	/*
	 * Test 8: Test removing stock from refrigerated truck object
	 * [This test obliges you to add a remove method for the truck object]
	 */
	@Test (expected = DeliveryException.class)
	public void removeStockTest() throws DeliveryException {
		
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = new Stock();		
		
		refrigeratedTruck.add(stock);
		
		refrigeratedTruck.remove(stock);
		
		refrigeratedTruck.getStock();
	}
	
	/*
	 * Test 9: Test exception for putting ordinary item in refrigerated truck
	 * [This test should throw an exception as the truck.add() should not accept items without a temp variable]
	 */
	@Test (expected = DeliveryException.class)
	public void wrongItemTest() throws DeliveryException {
		int randQuantity = randomInteger(1,10);
		String itemName = randomItemName();
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = new Stock();		
		
		Item item = new Item(itemName /*other vars but without Temp*/);
		
		stock.add(item, randQuantity);
		
		refrigeratedTruck.add(stock);
	}
	
	/*
	 * Test 10: Test exception for exceeding capacity in refrigerated truck
	 * [This test should throw an exception as the truck.add() should not accept items once at full capacity]
	 */
	@Test (expected = DeliveryException.class)
	public void exceedCapacityTest() throws DeliveryException {
		int randQuantity = 801;
		String itemName = randomItemName();
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = new Stock();		
		
		Item item = new Item(itemName /*other vars but without Temp*/);
		
		stock.add(item, randQuantity);
		
		refrigeratedTruck.add(stock);
	}
}
