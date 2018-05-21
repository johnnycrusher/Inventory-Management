package Delivery;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import OrdinaryTruck;
import Exception.DeliveryException;
import Exception.StockException;
import Stock.Item;
import Stock.Stock;


/** This class represents the unit test for the Manifest object.
 * @author John Huynh
 *@version 1.0
 */
public class ManifestTest {
	
	// Generate a Random Object
	private static Random random = new Random();
	
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
	
	
	/** This method generates a random integer inbetween a specified minimum value
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
	
	private static ArrayList<String> generateItemNames(int number) {
		ArrayList<String> foodList = new ArrayList<String>();
		ArrayList<String> randomCargoList = new ArrayList<String>();
		for(int index = 0; index < itemNames.length ; index++) {
			foodList.add(itemNames[index]);
		}
		int randomIndex;
		for(int index = 0; index < number; index++) {
			randomIndex = randomInteger(0,foodList.size()-1);
			String randomItemName = foodList.get(randomIndex);
			foodList.remove(randomIndex);
			randomCargoList.add(randomItemName);
		}
		return randomCargoList;
	}
		
	private static Stock generateRandomStock() throws StockException {
		Stock stock = new Stock();
		int temperature;
		String itemName;
		ArrayList<String> itemNames = generateItemNames(10);
		for(int index = 0; index < 10; index++) {
			
			double manufactureCost = randomDouble(0,100);
			double sellCost = randomDouble(0,100);
			int reorderPoint = randomInteger(0, 500);
			int reorderAmount = randomInteger(0, 100);
			if(index < 5) {
				temperature = randomInteger(-20, 10);
			}else {
				temperature = 40;
			}
			int itemQty = randomInteger(0,500);
			Item item = new Item(itemNames.get(index), manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			stock.add(item, itemQty);
		}
		return stock;
	}
	
	
	/* Test 0: Declaring a manifest object
	 */
	Manifest manifest;
	
	@Before
	public void setUpManifest() {
		manifest = null;
	}
	
	/* Test 1: Constructing a manifest object 
	 */
	@Test
	public void testConstructor() {
		manifest = new Manifest();
	}
	
	/* Test 2: Adding cargo (THIS TEST DOESN'T WORK AS MANIFEST HAS MULTIPLE STOCK OBJECTS
	 */
	@Test 
	public void testAddingCargo() throws DeliveryException, StockException{
		Stock cargoList = generateRandomStock();
		manifest = new Manifest();
		manifest.addItemStock(cargoList);
		Stock cargoListReturned =  manifest.getCargo();
		assertEquals("Cargo Object returned is not identical",cargoList,cargoListReturned);
	}
	/* Test 3: Test getting cargo list when it doesn't exist
	 */
	@Test (expected = DeliveryException.class)
	public void testGetCargoListWhenNoCargo() throws DeliveryException{
		manifest = new Manifest();
		Stock cargoList = manifest.getCargo();
	}
	/* Test 4: Test if Ordinary trucks can be added
	 */
	@Test 
	public void testAddingTrucks() throws DeliveryException{
		Truck ordinaryTruck = new OrdinaryTruck();
		manifest = new Manifest();
		manifest.addTruck(ordinaryTruck);
		Truck ordinaryTruckObject = manifest.getTruck(0);
		assertEquals("Identical truck object is not returned", ordinaryTruck, ordinaryTruckObject);
	}
	/*Test 5 :Test if refrigerated trucks can be added
	 */
	@Test 
	public void testRefrigeratedTrucks() throws DeliveryException{
		Truck refrigeratedTruck = new RefrigeratedTruck();
		manifest = new Manifest();
		manifest = null;
		manifest.addTruck(refrigeratedTruck);
		Truck refridgeratedTruckObject = manifest.getTruck(0);
		assertEquals("Identical truck object was not retuned", refrigeratedTruck, refridgeratedTruckObject);
	}
	/*Test 6: Test if ordinary trucks can be removed
	 */
	@Test (expected = DeliveryException.class)
	public void testDeleteOrdinaryTruck() throws DeliveryException {
		Truck ordinaryTruck = new OrdinaryTruck();
		manifest = new Manifest();
		manifest.addTruck(ordinaryTruck);
		manifest.removeTruck(0);
		manifest.getTruck(0);
	}
	/*Test 7: Test if refrigerated trucks can be removed
	 */
	@Test (expected = DeliveryException.class)
	public void testDeleteRefridgeratedTruck() throws DeliveryException{
		Truck refrigeratedTruck = new RefrigeratedTruck();
		manifest = new Manifest();
		manifest.addTruck(refrigeratedTruck);
		manifest.removeTruck(0);
		manifest.getTruck(0);
	}
	/*Test 8: Test if all trucks are return in manifest object
	 */
	@Test 
	public void testMultipleTrucksManifest() throws DeliveryException{
		
		manifest = new Manifest();
		Truck refrigeratedTruck = new RefrigeratedTruck();
		Truck ordinaryTruck = new OrdinaryTruck();
		ArrayList<Truck> truckList = new ArrayList<Truck>();
		truckList.add(refrigeratedTruck);
		truckList.add(ordinaryTruck);
		
		manifest.addTruck(ordinaryTruck);
		manifest.addTruck(refrigeratedTruck);
		ArrayList<Truck> returnedTruckList = manifest.getAllTrucks();
		assertEquals("Identical truck object were not returned",truckList,returnedTruckList);
	}
	/* Test 9: Test get truck object when it doesn't exist
	 */
	@Test (expected = DeliveryException.class)
	public void testGetNonExsitingTruck() throws DeliveryException{
		manifest = new Manifest();
		manifest.getTruck(0);
	}
	/* Test 10: Test if all sum of all cargo is correct
	 */
	@Test
	public void testSumCostOfCargo() throws DeliveryException{
		manifest = new Manifest();
		Stock cargoList = generateRandomStock();
		HashMap<Item,Integer> cargo = cargoList.returnStockList();
		double totalCost = 0;
		
		for(Map.Entry<Item,Integer> entry : cargo.entrySet()) {
			double cost = entry.getKey().getManufactureCost();
			totalCost += cost;
		}
		manifest.addCargo(cargoList);
		double cargoCost = manifest.getCargoCost();
		
		assertEquals("Cargo Cost was not the same value",totalCost, cargoCost,0.1);
	}
	/*Test 11: Test sum of all cargo when there is no cargo
	 */
	@Test (expected = DeliveryException.class)
	public void testCargoSumWhenNoCargo() throws DeliveryException{
		manifest = new Manifest();
		int cargoCost = manifest.getCargoCost();
	}
	/*Test 12: Test cargo optimisiation
	 */
	
	//need to figure out cargo optimisation algorithm
	
	/* Test 13: Returning the optimised cargo list
	 */
	//need to figure out optimise cargo algorithm
	
	
	
	/* Test 14: Test getting optimised cargo when no cargo is inputed
	 */
	@Test (expected = DeliveryException.class)
	public void testGetOptimisedCargoWhenNoCargo() throws DeliveryException{
		manifest = new Manifest();
		manifest.getOptimisedCargo();
	}
	/*Test 15: Get Total Manifest Cost
	 */
	@Test
	public void testManifiestCost() throws DeliveryException{
		Stock cargoList = generateRandomStock();

		manifest = new Manifest();
		
		// need to figure out cargo optimisation algorithm
		manifest.addCargo(cargoList);
		manifest.optimiseCargo();
		int manifestCost = manifest.getManifestCost();
	}
	
	/* Test 16: Get total cost when no cargo has been loaded
	 */
	@Test (expected = DeliveryException.class)
	public void testTotalCostNoCargo() throws DeliveryException{
		manifest = new Manifest();
		double manifestCost = manifest.getManifestCost();
	}
}
