package Delivery;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import Exception.DeliveryException;
import Stock.Item;
import Stock.Stock;

public class OrdinaryTruckTest {
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
		
	/* Test 0: Declaring a Truck object
	 */
	Truck ordinaryTruck;
	
	@Before
	public void setUpOrdinaryTruck() {
		ordinaryTruck = null;
	}
	
	/*
	 * Test 1: Constructing a basic Truck object.
	 * [This test obliges you to add a constructor]
	 * 
	 */
	@Test
	public void testConstruction() {			
		ordinaryTruck = new OrdinaryTruck();
	}
	
	/*
	 * Test 2: Test adding stock to the truck object
	 * [This test obliges you to add a Add() method for the truck object]
	 */
	@Test
	public void addStockTest() {
		
		ordinaryTruck = new OrdinaryTruck();
		
		Stock stock = new Stock();		
		
		ordinaryTruck.add(stock);
	}
	
	/*
	 * Test 3: Test getting stock from truck object
	 * [This test obliges you to add a get method for the truck object]
	 */
	@Test
	public void getStockTest() {
		
		ordinaryTruck = new OrdinaryTruck();
		
		Stock stock = new Stock();		
		
		ordinaryTruck.add(stock);
		
		assertEquals("Wrong Stock Returned", stock, ordinaryTruck.getStock());	
	}

	/*
	 * Test 4: Test getting current quantity
	 * [This test obliges you to add a getQuantity method for the truck object]
	 */
	@Test
	public void getQuantityTest() {
		int randQuantity = randomInteger(1,10);
		String itemName = randomItemName();
		
		ordinaryTruck = new OrdinaryTruck();
		
		Stock stock = new Stock();		
		
		Item item = new Item(itemName /*other vars*/);
		
		stock.add(item, randQuantity);
		
		ordinaryTruck.add(stock);
		
		assertEquals("Wrong Stock Level Returned", randQuantity, ordinaryTruck.getQuantity());	
	}
	
	/*
	 * Test 5: Test getting cost in dollars
	 * [This test obliges you to add a getCost method for the truck object]
	 */
	@Test
	public void getCostTest() {
		int expectedCost;
		int randQuantity = randomInteger(1,10);
		String itemName = randomItemName();
		
		ordinaryTruck = new OrdinaryTruck();
		
		Stock stock = new Stock();		
		
		Item item = new Item(itemName /*other vars*/);
		
		stock.add(item, randQuantity);
		
		ordinaryTruck.add(stock);
		
		expectedCost = (int) (750 + (0.25 * randQuantity));
		
		assertEquals("Wrong Stock Returned", expectedCost, ordinaryTruck.getCost());	
	}
	
	/*
	 * Test 6: Test removing stock from ordinary truck object
	 * [This test obliges you to add a remove method for the truck object]
	 */
	@Test (expected = DeliveryException.class)
	public void removeStockTest() throws DeliveryException {
		
		ordinaryTruck = new OrdinaryTruck();
		
		Stock stock = new Stock();		
		
		ordinaryTruck.add(stock);
		
		ordinaryTruck.remove(stock);
		
		ordinaryTruck.getStock();
	}
	
	/*
	 * Test 7: Test exception for putting refrigerated item in ordinary truck
	 */
	@Test (expected = DeliveryException.class)
	public void wrongItemTest() throws DeliveryException {
		int randQuantity = randomInteger(1,10);
		String itemName = randomItemName();
		int temp = randomInteger(-40,10);
		ordinaryTruck = new OrdinaryTruck();
		
		Stock stock = new Stock();		
		
		Item item = new Item(itemName, temp/*other vars*/);
		
		stock.add(item, randQuantity);
	}
}
