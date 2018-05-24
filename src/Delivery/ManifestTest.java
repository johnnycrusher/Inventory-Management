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
				temperature = 11;
			}
			int itemQty = randomInteger(0,500);
			Item item = new Item(itemNames.get(index), manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			stock.add(item, itemQty);
		}
		return stock;
	}
	
	private static Stock generateReorderStock() throws StockException {
		Stock stock = new Stock();
		String itemName;
		int temperature;
		ArrayList<String> itemNames = generateItemNames(10);
		for(int index = 0; index < 10; index++) {
			double manufactureCost = randomDouble(0,100);
			double sellCost = randomDouble(0,100);
			int reorderPoint = randomInteger(0, 250);
			int reorderAmount = randomInteger(0, 500);
			if(index < 5) {
				temperature = randomInteger(-20, 10);
			}else {
				temperature = 11;
			}
			int itemQty = randomInteger(0,249);
			Item item = new Item(itemNames.get(index), manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			stock.add(item, itemQty);
		}
		return stock;
	}
	
	private static Stock generateStaticImportedStock() throws StockException {
		Stock importedStock = new Stock();
		Item rice = new Item("rice",2,3,225,300,11);
		Item beans = new Item("beans",4,6,450,525,11);
		Item pasta = new Item("pasta",3,4,125,250,11);
		Item icecream = new Item("icecream",8,14,175,250,-20);
		Item ice = new Item("ice",2,5,225,325,-10);
		Item frozenmeat = new Item("frozen meat",10,14,450,575,-14);
		
		importedStock.add(rice, 100);
		importedStock.add(beans, 200);
		importedStock.add(pasta, 100);
		importedStock.add(icecream, 100);
		importedStock.add(ice, 100);
		importedStock.add(frozenmeat, 100);
		return importedStock;
	}
	
	private static ArrayList<Stock> generateStaticOptimisedStock() throws StockException{
		Stock importedStock = new Stock();
		Item rice = new Item("rice",2,3,225,300,11);
		Item beans = new Item("beans",4,6,450,525,11);
		Item pasta = new Item("pasta",3,4,125,250,11);
		Item icecream = new Item("icecream",8,14,175,250,-20);
		Item ice = new Item("ice",2,5,225,325,-10);
		Item frozenmeat = new Item("frozen meat",10,14,450,575,-14);
		
		importedStock.add(rice, 100);
		importedStock.add(beans, 200);
		importedStock.add(pasta, 100);
		importedStock.add(icecream, 100);
		importedStock.add(ice, 100);
		importedStock.add(frozenmeat, 100);
		
		ArrayList<Stock> cargoStockList = new ArrayList<Stock>();
		Stock T1Stock = new Stock();
		T1Stock.add(icecream, 250);
		T1Stock.add(frozenmeat, 550);
		Stock T2Stock = new Stock();
		T2Stock.add(frozenmeat, 25);
		T2Stock.add(ice, 325);
		T2Stock.add(pasta, 250);
		T2Stock.add(beans, 200);
		Stock T3Stock = new Stock();
		T3Stock.add(beans, 325);
		T3Stock.add(rice, 300);
		
		cargoStockList.add(T1Stock);
		cargoStockList.add(T2Stock);
		cargoStockList.add(T3Stock);
		return cargoStockList;
	}
	
	private static ArrayList<Truck> generateStaticOptimisedTruckStock() throws StockException, DeliveryException{
		ArrayList<Stock> cargoStockList = generateStaticOptimisedStock();
		ArrayList<Truck> arrayOfTrucks = new ArrayList<Truck>();
		Truck refridgeratedTruckOne = new RefrigeratedTruck();
		Truck refridgeratedTruckTwo = new RefrigeratedTruck();
		Truck ordinaryTruckOne = new OrdinaryTruck();
		
		refridgeratedTruckOne.add(cargoStockList.get(0));
		refridgeratedTruckTwo.add(cargoStockList.get(1));
		ordinaryTruckOne.add(cargoStockList.get(2));
		
		arrayOfTrucks.add(refridgeratedTruckOne);
		arrayOfTrucks.add(refridgeratedTruckTwo);
		arrayOfTrucks.add(ordinaryTruckOne);
		return arrayOfTrucks;
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
	public void testAddingStockStock() throws DeliveryException, StockException{
		Stock cargoList = generateRandomStock();
		manifest = new Manifest();
		manifest.addItemStock(cargoList);
		Stock cargoListReturned =  manifest.getImportStock();
		assertEquals("Cargo Object returned is not identical",cargoList,cargoListReturned);
	}
	
	/* Test 3: get Cargo Item that needs to be ordered
	 */
	@Test
	public void testGetCargo() throws StockException {
		Stock importedStock = generateReorderStock();
		Stock cargoStock = new Stock();
		
		for (Map.Entry<Item,Integer> entry : importedStock.returnStockList().entrySet()) {
			Item currentItem = entry.getKey();
			int  itemQuantity = entry.getValue();
			int itemReorderPoint = entry.getKey().getReorderPoint();
			int itemReorderAmmount = entry.getKey().getReorderAmount();
			if(itemQuantity <= itemReorderPoint) {
				cargoStock.add(currentItem, itemReorderAmmount);
			}
		}
		manifest = new Manifest();
		manifest.addItemStock(importedStock);
		Stock returnCargo = manifest.getCargoStock();
		
		assertEquals("Cargo Object returned is not the same", cargoStock,returnCargo);
	}
	/*Test 4 Get imported Stock when there is no imported stock into manifest obj
	 */
	@Test (expected = DeliveryException.class)
	public void testGetImportedStockWhenNoCargo() throws DeliveryException{
		manifest = null;
		manifest = new Manifest();
		manifest.getImportStock();
	}

	/* Test 5: Test if all sum of all cargo is correct
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
		manifest.addItemStock(cargoList);
		double cargoCost = manifest.getCargoCost();
		
		assertEquals("Cargo Cost was not the same value",totalCost, cargoCost,0.1);
	}
	/*Test 6: Test sum of all cargo when there is no cargo
	 */
	@Test (expected = DeliveryException.class)
	public void testCargoSumWhenNoCargo() throws DeliveryException{
		manifest = new Manifest();
		int cargoCost = manifest.getCargoCost();
	}
	/*Test 7: Test cargo optimisiation
	 */
	@Test
	public void testCargoOptimisation() throws StockException {
		manifest = new Manifest();
		Stock importedStock = generateStaticImportedStock();
		
		ArrayList<Stock> cargoStockList = generateStaticOptimisedStock();
		
		manifest.addItemStock(importedStock);
		manifest.sortStock();
		ArrayList<Stock> manifestCargo = manifest.getOptimisedCargo();
		
		
		assertEquals("Optimised cargo is not the same",cargoStockList,manifestCargo);
		
	}
	
	/* Test 8: Test getting optimised cargo when no cargo is inputed
	 */
	@Test (expected = DeliveryException.class)
	public void testGetOptimisedCargoWhenNoCargo() throws DeliveryException{
		manifest = new Manifest();
		manifest.getOptimisedCargo();
	}
	
	/* Test 9: Test getting trucks with optimised cargo loaded into it
	 */
//	@Test
//	public void testGetTrucksWithOptimisedCargo() throws StockException {
//		Stock importedStock = generateStaticImportedStock();
//		ArrayList<Stock> cargoStockList = generateStaticOptimisedStock();
//		
//		
//		ArrayList<Truck> arrayOfTrucks = new ArrayList<Truck>();
//		Truck refridgeratedTruckOne = new RefrigeratedTruck();
//		Truck refridgeratedTruckTwo = new RefrigeratedTruck();
//		Truck ordinaryTruckOne = new OrdinaryTruck();
//		
//		refridgeratedTruckOne.add(cargoStockList.get(0));
//		refridgeratedTruckTwo.add(cargoStockList.get(1));
//		ordinaryTruckOne.add(cargoStockList.get(2));
//		
//		arrayOfTrucks.add(refridgeratedTruckOne);
//		arrayOfTrucks.add(refridgeratedTruckTwo);
//		arrayOfTrucks.add(ordinaryTruckOne);
//		
//		Manifest manifest = new Manifest();
//		
//		manifest.addItemStock(importedStock);
//		manifest.sortStock();
//		manifest.createTrucks();
//		manifest.loadCargoToTrucks();
//		ArrayList<Truck> returnedTruckArray = manifest.getAllTrucks();
//		
//		assertEquals("Array of Truck Objects returned is not the same",arrayOfTrucks,returnedTruckArray);
//		
//	}
	/*Test 10: Get Total Manifest Cost
	 */
	@Test
	public void testManifiestCost() throws DeliveryException, StockException{
		Stock cargoList = generateStaticImportedStock();
		ArrayList<Truck> optimisedTruckLoadedStock = generateStaticOptimisedTruckStock();
		double totalCost = 0;
		for(int index = 0; index < optimisedTruckLoadedStock.size(); index++) {
			double cargoCost = optimisedTruckLoadedStock.get(index).getStockCost();
			double truckCost = optimisedTruckLoadedStock.get(index).getCost();
			totalCost += cargoCost + truckCost;
		}
		Manifest manifest = new Manifest();
		manifest.addItemStock(cargoList);
		manifest.createTrucks();
		manifest.sortStock();
		manifest.loadCargoToTrucks();
		double manifestCost = manifest.getManifestCost();
		
		assertEquals("Manifest Cost does not match", totalCost, manifestCost, 0.1);
	}
	
	/* Test 11: Get total cost when no cargo has been loaded
	 */
	@Test (expected = DeliveryException.class)
	public void testTotalCostNoCargo() throws DeliveryException, StockException{
		manifest = new Manifest();
		double manifestCost = manifest.getManifestCost();
	}
}
