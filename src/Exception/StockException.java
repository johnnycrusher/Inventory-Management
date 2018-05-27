package Exception;

@SuppressWarnings("serial")
public class StockException extends Exception {

	public StockException() {
		super();
	}

	/**
	 * A method to pass a exception message to the user
	 * @param message
	 */
	public StockException(String message) {
		super(message);
	}
}
