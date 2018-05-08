package Delivery;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import Exception.DeliveryException;


/** This class represents the unit test for the Manifest object.
 * @author John Huynh
 *@version 1.0
 */
public class ManifestTest {
	
	// Generate a Random Object
	private static Random random = new Random();
	
	//Generate an array of dry food items
	private static String[] dryFoodItemNames = new String[] {
			"rice",
			"breans",
			"pasta",
			"biscuits",
			"nuts",
			"chips",
			"chocolate",
			"bread",
	};
	//generates an array of refridgerated food items
	private static String[] refrideratedFoodItemNames = new String[] {
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
	
	/** This method generates 5 randomly selected dry food items and 
	 *  5 randomly selected refridgerated foods and then stores them
	 *  in another array list
	 * @return the randomly generated cargo items
	 */
	public static ArrayList<String> generateRandomFoodItem() {
		ArrayList<String> dryFoodList = new ArrayList<String>();
		ArrayList<String> refridratedFoodList = new ArrayList<String>();
		ArrayList<String> randomCargoList = new ArrayList<String>();
		for(int index = 0; index < dryFoodItemNames.length ; index++) {
			dryFoodList.add(dryFoodItemNames[index]);
		}
		for(int index = 0; index < refrideratedFoodItemNames.length; index++) {
			refridratedFoodList.add(refrideratedFoodItemNames[index]);
		}
		int randomIndex;
		for(int index = 0; index < 5; index++) {
			randomIndex = randomInteger(0,dryFoodList.size()-1);
			String randomDryFood = dryFoodList.get(randomIndex);
			dryFoodList.remove(randomIndex);
			randomCargoList.add(randomDryFood);
		}
		for(int index = 5; index < 10; index++) {
			randomIndex = randomInteger(0,refridratedFoodList.size()-1);
			String randomRefridgeratedFood = refridratedFoodList.get(randomIndex);
			refridratedFoodList.remove(randomIndex);
			randomCargoList.add(randomRefridgeratedFood);
		}
		return randomCargoList;
		
	}
	
	/** This method generates the item properties they are
	 * a randomly generated integer to represent the reorder ammount
	 * and a randomly generates integers the temperatues
	 * and those are stored in a hashset.
	 * @param type - the type of food (dry/refridgerated)
	 * @return the randomly generates item properties
	 */
	public ArrayList<Integer> generateRandomItemProperties(String type ) {
		ArrayList<Integer> itemProperties = new ArrayList<Integer>();
		int randomReorderAmount = randomInteger(100,350);
		int randomCost = randomInteger(1, 30);
		itemProperties.add(randomReorderAmount);
		itemProperties.add(randomCost);
		if(type.equals("refridgerated")) {
			int randomTemperature = randomInteger(-20, 10);
			itemProperties.add(randomTemperature);
		}
		return itemProperties;
	}	
	
	
	/**This method combines the randomly generated food items and
	 * the randomly generates items properties and stores them in
	 * a hashmap.
	 * @return a hashmap containing the item name and the item properties.
	 */
	public HashMap<String,ArrayList<Integer>> generateRandomCargoList() {
		HashMap<String, ArrayList<Integer>> cargoList = new HashMap<String, ArrayList<Integer>>();
		ArrayList<String> randomCargoList = generateRandomFoodItem();
		ArrayList<Integer> itemProperties;
		for (int index = 0; index < 5; index++) {
			itemProperties = generateRandomItemProperties("dry");
			cargoList.put(randomCargoList.get(index), itemProperties);
		}
		for(int index = 5; index < randomCargoList.size(); index++) {
			itemProperties = generateRandomItemProperties("refridgerated");
			cargoList.put(randomCargoList.get(index),itemProperties);
		}
		return cargoList;
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
	public void testAddingCargo() throws DeliveryException{
		HashMap<String, Set<Integer>> cargoList = generateRandomCargoList();
		manifest = new Manifest();
		manifest.addCargo(cargoList);
		HashMap<String, Set<Integer>> cargoListReturned =  manifest.getCargo();
		assertEquals("Cargo Object returned is not identical",cargoList,cargoListReturned);
	}
	/* Test 3: Test getting cargo list when it doesn't exist
	 */
	@Test (expected = DeliveryException.class)
	public void testGetCargoListWhenNoCargo() throws DeliveryException{
		manifest = new Manifest();
		HashMap<String, Set<Integer>> cargoList = manifest.getCargo();
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
		Trucks refridgeratedTruck = new RefridgeratedTruck();
		manifest = new Manifest();
		manifest.addTruck(refridgeratedTruck);
		Truck refridgeratedTruckObject = manifest.getTruck(0);
		assertEquals("Identical truck object was not retuned", refridgeratedTruck, refridgeratedTruckObject);
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
	/*Test 7: Test if refridgerated trucks can be removed
	 */
	@Test (expected = DeliveryException.class)
	public void testDeleteRefridgeratedTruck() throws DeliveryException{
		Truck refridgeratedTruck = new RefridgeratedTruck();
		manifest = new Manifest();
		manifest.addTruck(refridgeratedTruck);
		manifest.removeTruck(0);
		manifest.getTruck(0);
	}
	/*Test 8: Test if all trucks are return in manifest object
	 */
	@Test 
	public void testMultipleTrucksManifest() throws DeliveryException{
		
		manifest = new Manifest();
		Truck refridgeratedTruck = new RefridgeratedTruck();
		Truck ordinaryTruck = new OrdinaryTruck();
		ArrayList<Truck> truckList = new ArrayList<Truck>();
		truckList.add(refridgeratedTruck);
		truckList.add(ordinaryTruck);
		
		manifest.addTruck(ordinaryTruck);
		manifest.addTruck(refridgeratedTruck);
		ArrayList<Truck> returnedTruckList = manifest.getAllTrucks();
		assertEquals("Identical truck object were not returned",truckList,returnedTruckList);
	}
	/* Test 9: Test get truck object when it doesn't exist
	 */
	@Test (expected = DeliveryException.class)
	public void testGetNonExsitingTruck() throws DeliveryException{
		manifest = new Manifefst();
		manifest.getTruck(0);
	}
	/* Test 10: Test if all sum of all cargo is correct
	 */
	@Test
	public void testSumCostOfCargo() throws DeliveryException{
		manifest = new Manifest();
		HashMap<String,ArrayList<Integer>> cargoList =  generateRandomCargoList();
		int totalCost = 0;
		final int costIndex = 1;
		for(String key: cargoList.keySet()) {
			totalCost += cargoList.get(key).get(costIndex);
		}
		manifest.addCargo(cargoList);
		int cargoCost = manifest.getCargoCost();
		
		assertEquals("Cargo Cost was not the same value",totalCost, cargoCost);
	}
	/*Test 11: Test sum of all cargo when there is no cargo
	 */
	@Test (expected = DeliveryException.class)
	public void testCargoSumWhenNoCargo() throws DeliveryException{
		manifest = new Manifest();
		HashMap<String,ArrayList<Integer>> cargoList =  generateRandomCargoList();
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
		HashMap<String, Set<Integer>> cargoList = generateRandomCargoList();

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
