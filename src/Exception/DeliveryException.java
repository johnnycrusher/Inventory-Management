package Exception;

@SuppressWarnings("serial")
public class DeliveryException extends Exception {

	public DeliveryException() {
		super();
	}
	
	/**
	 * A method to pass a exception message to the user
	 * @param message
	 */
	public DeliveryException(String message) {
		super(message);
	}


}
