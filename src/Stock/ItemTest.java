package Stock;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {
	
	private static Random random = new Random();
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
	
	private static String randomItemName() {
		return itemNames[random.nextInt(itemNames.length)];
	}
	private static int randomInteger(int min,int max) {
		return random.nextInt((min-max) + 1) + min;
	}
	private static int generateRandomTemerature(int min, int max) {
		int decider = random.nextInt()%2;
		if(decider == 0) {
			return random.nextInt((min-max) + 1) + min;
		}else {
			return (Integer) null;
		}
		
		
	}

	Item item;
	
	@Before
	public void setUpItem() {
		item = null;
	}
	
	@Test
	public void testConstructor() {
		String itemName = randomItemName();
		int manufactureCost = randomInteger(0,100);
		int sellCost = randomInteger(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
	}
	
	@Test
	public void testItemName() {
		String itemName = randomItemName();
		int manufactureCost = randomInteger(0,100);
		int sellCost = randomInteger(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
		
		assertEquals("item name incorrect ",itemName, item.getItemName());
	}
	
	@Test
	public void testManufactureCost() {
		String itemName = randomItemName();
		int manufactureCost = randomInteger(0,100);
		int sellCost = randomInteger(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
		
		assertEquals("manufacture cost incorrect", manufactureCost, item.getManufactureCost());
	}
	
	@Test
	public void testSellCost() {
		String itemName = randomItemName();
		int manufactureCost = randomInteger(0,100);
		int sellCost = randomInteger(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
		
		assertEquals("sell cost incorrect", sellCost, item.getSellCost);
	}
	
	@Test
	public void testReorderPoint() {
		String itemName = randomItemName();
		int manufactureCost = randomInteger(0,100);
		int sellCost = randomInteger(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
		
		assertEquals("reorder point incorrect", reorderPoint, item.getReorderPoint);
	}
	
	@Test
	public void testReorderAmmount() {
		String itemName = randomItemName();
		int manufactureCost = randomInteger(0,100);
		int sellCost = randomInteger(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
		
		assertEquals("reorder ammount incorrect", reorderPoint, item.getReorderAmmount);
	}
	
	@Test
	public void testTemperature() {
		String itemName = randomItemName();
		int manufactureCost = randomInteger(0,100);
		int sellCost = randomInteger(0,100);
		int reorderPoint = randomInteger(0, 500);
		int reorderAmount = randomInteger(0, 100);
		int temperature = randomInteger(-40,10);
		
		item = new Item(itemName, manufactureCost, sellCost, reorderPoint, reorderAmount, temperature);
		
		assertEquals("temperature incorrect", temperature, item.getTemperature);
	}
}
