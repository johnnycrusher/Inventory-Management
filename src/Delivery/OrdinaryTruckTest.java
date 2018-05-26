package Delivery;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import Exception.DeliveryException;
import Exception.StockException;
import Stock.Item;
import Stock.Stock;

public class OrdinaryTruckTest {
	// Generate a Random Object
	private static Random random = new Random();
			
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
	 * and a specified maximum value
	 * @param min the specified minimum number that can be generated
	 * @param max the specified maximum number that can be generated
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

	/**Generates a stock object with a certain number of objects
	 * @param quanitity the total quantity of the stock object
	 * @return stock - returns the stock object
	 * @throws StockException
	 */
	private static Stock generateFixedStock(int quanitity) throws StockException {
		//generate stock object and important paramerters
		int numOfItems =  3;
		Stock stock = new Stock();
		int maxQuanitityForItem = quanitity;
		int itemQuantity=0;
		//generate random item names
		ArrayList<String> itemName = generateItemNames(numOfItems);
		for(int index = 0; index < numOfItems; index++ ) {
			//generate random item propertes
			double manufactureCost = randomDouble(0,100);
			double sellCost = randomDouble(0,100);
			int reorderPoint = randomInteger(0, 500);
			int reorderAmount = randomInteger(0, 100);
			int  temperature = 11;
			
			//if last item make it equal to the make quantity
			if(index == 2) {
				itemQuantity = maxQuanitityForItem;	
			}else { //if not last item then generate a random quantity
				itemQuantity = randomInteger(0,maxQuanitityForItem);
				maxQuanitityForItem -= itemQuantity;
			}
			//add the item to the stock object
			Item item = new Item(itemName.get(index), manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			
			stock.addItem(item, itemQuantity);
		}
		return stock;
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
	public void addStockTest() throws StockException, DeliveryException {
		
		Stock stock;
		ordinaryTruck = new OrdinaryTruck();

		stock = generateFixedStock(1);

		ordinaryTruck.add(stock);
	}
	
	/*
	 * Test 3: Test getting stock from truck object
	 * [This test obliges you to add a get method for the truck object]
	 */
	@Test
	public void getStockTest() throws StockException, DeliveryException {
		
		ordinaryTruck = new OrdinaryTruck();
		
		Stock stock = generateRandomStock("dry");	
		
		ordinaryTruck.add(stock);
		
		assertEquals("Wrong Stock Returned", stock, ordinaryTruck.getStock());	
	}

	/*
	 * Test 4: Test getting current quantity
	 * [This test obliges you to add a getQuantity method for the truck object]
	 */
	@Test
	public void getQuantityTest() throws StockException, DeliveryException {
		int randQuantity = randomInteger(10,90);
		
		ordinaryTruck = new OrdinaryTruck();
		
		Stock stock = generateFixedStock(randQuantity);
		
		ordinaryTruck.add(stock);
		
		assertEquals("Wrong Stock Level Returned", randQuantity, ordinaryTruck.getQuantity());	
	}
	
	/*
	 * Test 5: Test getting cost in dollars
	 * [This test obliges you to add a getCost method for the truck object]
	 */
	@Test
	public void getCostTest() throws StockException, DeliveryException {
		double expectedCost;
		int randQuantity = randomInteger(1,40);
		
		ordinaryTruck = new OrdinaryTruck();
		
		Stock stock = generateFixedStock(randQuantity);
		
		ordinaryTruck.add(stock);
		
		expectedCost = (750 + (0.25 * randQuantity));
		
		assertEquals("Wrong Stock Returned", expectedCost, ordinaryTruck.getCost(),0.1);	
	}
	
	/*
	 * Test 6: Test removing stock from ordinary truck object
	 * [This test obliges you to add a remove method for the truck object]
	 */
	public void removeStockTest() throws DeliveryException, StockException {
		
		ordinaryTruck = new OrdinaryTruck();
		
		Stock stock = generateRandomStock("dry");
		
		ordinaryTruck.add(stock);
		
		ordinaryTruck.removeStock();
		
		ordinaryTruck.getStock();
	}
	
	/*
	 * Test 7: Test exception for putting refrigerated item in ordinary truck
	 */
	@Test (expected = DeliveryException.class)
	public void wrongItemTest() throws DeliveryException, StockException {
		ordinaryTruck = new OrdinaryTruck();
		
		Stock stock = null;
		stock = generateRandomStock("refrigetrated");
		
		ordinaryTruck.add(stock);
	}
}
