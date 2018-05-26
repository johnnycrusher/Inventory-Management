package Delivery;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

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
			"beans",
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
	 * @param min the minimum value that the random number can generate
	 * @param max the maximum value that the random number can generate
	 * @return the random number that is generated
	 */
	private static int randomInteger(int min,int max) {
		return random.nextInt((max - min) + 1) + min;
	}
	
	/**
	 * This method generates a random double from a specified minimum value
	 * and a specified mximum value
	 * @param min the specified minimum number that can be generated
	 * @param max the specified maximum number taht can be generated
	 * @return randomDouble - the random number that was generated
	 */
	private static double randomDouble(double min, double max) {
		double randomDouble = random.nextDouble() * (max - min) + min;
		return randomDouble;
	}
	
	/**This method task is the generate random Item name with no dupilcates
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
		
	/**This methods task is to generate a stock object with random item
	 * objects
	 * @return stock - the generated stock object that contains the random items
	 * @throws StockException throws a stock exception when there a stock error
	 */
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
			stock.addItem(item, itemQty);
		}
		return stock;
	}
	
	/**Generates a random stock item where all items are below the reorder threshold
	 * @return stock - the random stock object that contains the items below reorder threshold
	 * @throws StockException happens when there is a stock error
	 */
	private static Stock generateReorderStock() throws StockException {
		//generate stock item and intialise import variables
		Stock stock = new Stock();
		String itemName;
		int temperature;
		//generate random names
		ArrayList<String> itemNames = generateItemNames(10);
		for(int index = 0; index < 10; index++) {
			//generating random item properties
			double manufactureCost = randomDouble(0,100);
			double sellCost = randomDouble(0,100);
			int reorderPoint = randomInteger(0, 250);
			int reorderAmount = randomInteger(0, 500);
			if(index < 5) {
				temperature = randomInteger(-20, 10);
			}else {
				temperature = 11;
			}
			//generate item quantity below reorder point
			int itemQty = randomInteger(0,249);
			//create the item
			Item item = new Item(itemNames.get(index), manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
			// add stock item to arrayList
			stock.addItem(item, itemQty);
		}
		return stock;
	}
	
	/**This method task is to generate static imported stock
	 * @return importedStock - returns the importedStock
	 * @throws StockException throws an error when there is a stock error
	 */
	private static Stock generateStaticImportedStock() throws StockException {
		//generate items
		Stock importedStock = new Stock();
		Item rice = new Item("rice",2,3,225,300,11);
		Item beans = new Item("beans",4,6,450,525,11);
		Item pasta = new Item("pasta",3,4,125,250,11);
		Item icecream = new Item("icecream",8,14,175,250,-20);
		Item ice = new Item("ice",2,5,225,325,-10);
		Item frozenmeat = new Item("frozen meat",10,14,450,575,-14);
		
		//add the items in the stock object
		importedStock.addItem(rice, 100);
		importedStock.addItem(beans, 200);
		importedStock.addItem(pasta, 100);
		importedStock.addItem(icecream, 100);
		importedStock.addItem(ice, 100);
		importedStock.addItem(frozenmeat, 100);
		//return importedStock
		return importedStock;
	}
	
	/**A method that returns a staticly optimised stock
	 * @return cargoStockList - returns the optimised cargo stock
	 * @throws StockException throws an error when there is a stock error
	 */
	private static ArrayList<Stock> generateStaticOptimisedStock() throws StockException{
		
		//generate random items
		Stock importedStock = new Stock();
		Item rice = new Item("rice",2,3,225,300,11);
		Item beans = new Item("beans",4,6,450,525,11);
		Item pasta = new Item("pasta",3,4,125,250,11);
		Item icecream = new Item("icecream",8,14,175,250,-20);
		Item ice = new Item("ice",2,5,225,325,-10);
		Item frozenmeat = new Item("frozen meat",10,14,450,575,-14);
		
		importedStock.addItem(rice, 100);
		importedStock.addItem(beans, 200);
		importedStock.addItem(pasta, 100);
		importedStock.addItem(icecream, 100);
		importedStock.addItem(ice, 100);
		importedStock.addItem(frozenmeat, 100);
		
		//add the stock list in their appropriate cargo
		ArrayList<Stock> cargoStockList = new ArrayList<Stock>();
		Stock T1Stock = new Stock();
		T1Stock.addItem(icecream, 250);
		T1Stock.addItem(frozenmeat, 550);
		Stock T2Stock = new Stock();
		T2Stock.addItem(frozenmeat, 25);
		T2Stock.addItem(ice, 325);
		T2Stock.addItem(pasta, 250);
		T2Stock.addItem(beans, 200);
		Stock T3Stock = new Stock();
		T3Stock.addItem(beans, 325);
		T3Stock.addItem(rice, 300);
		
		//add the the cargo list array
		cargoStockList.add(T1Stock);
		cargoStockList.add(T2Stock);
		cargoStockList.add(T3Stock);
		
		return cargoStockList;
	}
	
	/**get the truck object that will contain the stock
	 * @return arrayOfTrucks - an array of trucks that contain their cargo
	 * @throws StockException throws a stock error when there are stock problems
	 * @throws DeliveryException throws a delivery error when there are delievery problems
	 */
	private static ArrayList<Truck> generateStaticOptimisedTruckStock() throws StockException, DeliveryException{
		//generate cargo stock list
		ArrayList<Stock> cargoStockList = generateStaticOptimisedStock();
		//create the array of trucks
		ArrayList<Truck> arrayOfTrucks = new ArrayList<Truck>();
		Truck refridgeratedTruckOne = new RefrigeratedTruck();
		Truck refridgeratedTruckTwo = new RefrigeratedTruck();
		Truck ordinaryTruckOne = new OrdinaryTruck();
		
		//add the cargo onto the trucks
		refridgeratedTruckOne.add(cargoStockList.get(0));
		refridgeratedTruckTwo.add(cargoStockList.get(1));
		ordinaryTruckOne.add(cargoStockList.get(2));
		
		//add the cargo to the trucks
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
	
	/* Test 2: Adding cargo
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
	 * Works but there seems to be an issue with hashmap being exactly equal
	 * most likely cause of doubles
	 */
	@Test
	public void testGetCargo() throws StockException, DeliveryException {
		Stock importedStock = generateRandomStock();
		Stock cargoStock = new Stock();
		
		for (Map.Entry<Item,Integer> entry : importedStock.returnStockList().entrySet()) {
			Item currentItem = entry.getKey();
			int  itemQuantity = entry.getValue();
			int itemReorderPoint = entry.getKey().getReorderPoint();
			int itemReorderAmmount = entry.getKey().getReorderAmount();
			if(itemQuantity <= itemReorderPoint) {
				cargoStock.addItem(currentItem, itemReorderAmmount);
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
	public void testSumCostOfCargo() throws DeliveryException, StockException{
		manifest = new Manifest();
		Stock importedStock = generateRandomStock();
		HashMap<Item,Integer> cargo = importedStock.returnStockList();
		double totalCost = 0;
		
		Stock cargoStock = new Stock();
		
		for (Map.Entry<Item,Integer> entry : cargo.entrySet()) {
			Item currentItem = entry.getKey();
			int  itemQuantity = entry.getValue();
			int itemReorderPoint = entry.getKey().getReorderPoint();
			int itemReorderAmmount = entry.getKey().getReorderAmount();
			if(itemQuantity <= itemReorderPoint) {
				cargoStock.addItem(currentItem, itemReorderAmmount);
			}
		}
		
		HashMap<Item,Integer> cargoHash = cargoStock.returnStockList();
		
		for(Map.Entry<Item,Integer> entry : cargoHash.entrySet()) {
			double cost = entry.getKey().getManufactureCost();
			int quantity = entry.getValue();
			totalCost += (cost * quantity);
		}
		manifest.addItemStock(importedStock);
		double cargoCost = manifest.getCargoCost();
		
		assertEquals("Cargo Cost was not the same value",totalCost, cargoCost,0.1);
	}
	/*Test 6: Test sum of all cargo when there is no cargo
	 */
	@Test (expected = DeliveryException.class)
	public void testCargoSumWhenNoCargo() throws DeliveryException, StockException{
		manifest = new Manifest();
		double cargoCost = manifest.getCargoCost();
	}
	/*Test 7: Test cargo optimisiation
	 * Works but Stock objects do not equal mostlikely due to all doubles not being
	 * the same
	 */
	@Test
	public void testCargoOptimisation() throws StockException, DeliveryException {
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
	 * Does match seems to be an error in hashmap ordering
	 */
	@Test
	public void testGetTrucksWithOptimisedCargo() throws StockException, DeliveryException {
		Stock importedStock = generateStaticImportedStock();
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
		
		Manifest manifest = new Manifest();
		
		manifest.addItemStock(importedStock);
		manifest.createTrucks();
		manifest.sortStock();
		manifest.loadCargoToTrucks();
		ArrayList<Truck> returnedTruckArray = manifest.getAllTrucks();
		
		assertEquals("Array of Truck Objects returned is not the same",arrayOfTrucks,returnedTruckArray);
	}
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
