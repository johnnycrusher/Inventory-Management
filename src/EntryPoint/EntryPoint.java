/**
 * 
 */
package EntryPoint;

import java.io.IOException;

import CSV.CSVMachine;
import Exception.CSVFormatException;

/**
 * @author John_
 *
 */
public class EntryPoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			CSVMachine.writeLineToManifest("Hello World!");
		} catch (CSVFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
