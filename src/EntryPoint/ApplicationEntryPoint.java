/**
 * A class to act as the application's entry point
 * @author Tom
 */
package EntryPoint;

import Stock.Store;

public class ApplicationEntryPoint {
	
	/**
	 * The EntryPoint's Main method which initializes the store object
	 * @param args
	 */
	public static void main(String[] args) {
		//Create the instance of the Store()
		Store.getInstance();
	}
}
