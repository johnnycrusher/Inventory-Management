package EntryPoint;

import java.io.IOException;

import CSV.CSVMachine;
import Exception.CSVFormatException;
import Exception.StockException;

public class ApplicationEntryPoint {

	public static void main(String[] args) throws StockException {
		// TODO Auto-generated method stub
		try {
			//WORKING METHOD CURRENTLY PRINTS RESULTS ALONG WITH CREATING THE INIT STOCK
			CSVMachine.readItemProperties();
			
			//Test
			CSVMachine.readManifest();
			
			//CSVMachine.writeManifest(manifest)
			CSVMachine.writeLineToManifest("Test");

		} catch (CSVFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
