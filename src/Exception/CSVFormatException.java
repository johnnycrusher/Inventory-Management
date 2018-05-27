package Exception;

@SuppressWarnings("serial")
public class CSVFormatException extends Exception {
	
	public CSVFormatException() {
		super();
	}
	
	/**
	 * A method to pass a exception message to the user
	 * @param message
	 */
	public CSVFormatException(String message) {
		super(message);
	}

}
