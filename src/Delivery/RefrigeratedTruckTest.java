package Delivery;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;
import java.lang.*;

import org.junit.Before;
import org.junit.Test;

import RefrigeratedTruck;
import Exception.DeliveryException;
import Exception.StockException;
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
	
	
	private static ArrayList<String> generateItemNames(int number) {
		ArrayList<String> foodList = new ArrayList<String>();
		ArrayList<String> randomCargoList = new ArrayList<String>();
		for(int index = 0; index < itemNames.length ; index++) {
			foodList.add(itemNames[index]);
		}
		int randomIndex;
		for(int index = 0; index < number; index++) {
			randomIndex = randomInteger(0,foodList.size()-1);
			String randomDryFood = foodList.get(randomIndex);
			foodList.remove(randomIndex);
			randomCargoList.add(randomDryFood);
		}
		return randomCargoList;
	}

	
	private static Stock generateRandomStock(String type) throws StockException {
		Stock stock = new Stock();
		int temperature;
		ArrayList<String> itemNames = generateItemNames(5);
		for(int index = 0; index < 5; index++) {
			double manufactureCost = randomDouble(0,100);
			double sellCost = randomDouble(0,100);
			int reorderPoint = randomInteger(0, 500);
			int reorderAmount = randomInteger(0, 100);
			if(type.equals("refridgetrated")) {
				temperature = randomInteger(-20,10);
			}else {
				temperature = 11;
			}
			
			int itemQty = randomInteger(0,500);
			Item item = new Item(itemNames.get(index), manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			stock.addItem(item, itemQty);
		}
		return stock;
	}

	
	private static Stock generateFixedStock(int quanitity) throws StockException {
		int numOfItems =  3;
		Stock stock = new Stock();
		int maxQuanitityForItem = quanitity;
		int itemQuantity=0;
		ArrayList<String> itemName = generateItemNames(numOfItems);
		for(int index = 0; index < numOfItems; index++ ) {
			double manufactureCost = randomDouble(0,100);
			double sellCost = randomDouble(0,100);
			int reorderPoint = randomInteger(0, 500);
			int reorderAmount = randomInteger(0, 100);
			int  temperature = 11;
			
			if(index == 2) {
				itemQuantity = maxQuanitityForItem;	
			}else {
				itemQuantity = randomInteger(0,maxQuanitityForItem);
				maxQuanitityForItem -= itemQuantity;
			}
			
			Item item = new Item(itemName.get(index), manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			
			stock.addItem(item, itemQuantity);
		}
		return stock;
	}
	
	private static Stock generateFixedTempStock(int temp) throws StockException {
		int numOfItems = 3;
		int counter = 5;
		int temperature = 11;
		Stock stock = new Stock();
		ArrayList<String> itemName = generateItemNames(numOfItems);
		for(int index = 0; index < numOfItems; index++ ) {
			double manufactureCost = randomDouble(0,100);
			double sellCost = randomDouble(0,100);
			int reorderPoint = randomInteger(0, 500);
			int reorderAmount = randomInteger(0, 100);
			int itemQuantity = randomInteger(0,200);
			if(index < 2) {
				temperature = temp + counter;
				counter += counter;
			}else {
				temperature = temp;
			}
			Item item = new Item(itemName.get(index), manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			
			stock.addItem(item, itemQuantity);
		}
		return stock;
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
	public void addStockTest() throws StockException, DeliveryException {
		
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = generateFixedStock(700);		
		
		refrigeratedTruck.add(stock);
	}
	
	/*
	 * Test 3: Test getting stock from truck object
	 * [This test obliges you to add a get method for the truck object]
	 */
	@Test
	public void getStockTest() throws DeliveryException, StockException{
		
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = generateFixedStock(700);		
		
		refrigeratedTruck.add(stock);
		
		assertEquals("Wrong Stock Returned", stock, refrigeratedTruck.getStock());	
	}

	/*
	 * Test 4: Test getting current quantity
	 * [This test obliges you to add a getQuantity method for the truck object]
	 */
	@Test
	public void getQuantityTest() throws StockException, DeliveryException {
		int randQuantity = randomInteger(1,10);
		
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = generateFixedStock(randQuantity);
		
		refrigeratedTruck.add(stock);
		
		assertEquals("Wrong Stock Level Returned", randQuantity, refrigeratedTruck.getQuantity());	
	}
	
	/*
	 * Test 5: Test getting temp of refrigerated truck
	 * [This test obliges you to add a getTemp() method for the truck object]
	 */
	@Test
	public void getTempTest() throws StockException, DeliveryException {
		int randTemp = randomInteger(-40,10);
		
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = generateFixedTempStock(randTemp);		

		refrigeratedTruck.add(stock);
		
		assertEquals("Wrong Temp Returned", randTemp, refrigeratedTruck.getTemp());	
	}
	
	
	/*
	 * Test 7: Test getting cost in dollars
	 * [This test obliges you to add a getCost() method for the truck object]
	 */
	@Test
	public void getCostTest() throws StockException, DeliveryException {
		double expectedCost;
		int currentTemp = randomInteger(-20,10);
		float exponent;
		
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = generateFixedTempStock(currentTemp);
		
		refrigeratedTruck.add(stock);

		exponent = currentTemp / 5;
		expectedCost = (900 + (200 * Math.pow(0.7, exponent)));
		
		assertEquals("Wrong Stock Returned", expectedCost, refrigeratedTruck.getCost(),0.1);	
	}	
	
	/*
	 * Test 8: Test removing stock from refrigerated truck object
	 * [This test obliges you to add a remove method for the truck object]
	 */
	@Test (expected = DeliveryException.class)
	public void removeStockTest() throws DeliveryException, StockException {
		
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = generateRandomStock("refrigerated");
		
		refrigeratedTruck.add(stock);
		
		refrigeratedTruck.removeStock();
		
		refrigeratedTruck.getStock();
	}
	
	/*
	 * Test 10: Test exception for exceeding capacity in refrigerated truck
	 * [This test should throw an exception as the truck.add() should not accept items once at full capacity]
	 */
	@Test (expected = DeliveryException.class)
	public void exceedCapacityTest() throws DeliveryException, StockException {
		refrigeratedTruck = new RefrigeratedTruck();
		
		Stock stock = generateFixedStock(900);
		
		refrigeratedTruck.add(stock);
	}
}
