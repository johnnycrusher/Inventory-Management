package EntryPoint;

import java.io.IOException;

import CSV.CSVMachine;
import Exception.CSVFormatException;
public class ApplicationEntryPoint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			CSVMachine.writeLineToManifest("Hello");
		} catch (CSVFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
