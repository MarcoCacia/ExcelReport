package it.synclab.rilmanager.core.function;

public class IsNotADirectoryException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public IsNotADirectoryException() {
		super();
	}
	
	public IsNotADirectoryException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public IsNotADirectoryException(String message) {
		super(message);
	}
	
	public IsNotADirectoryException(Throwable cause) {
		super(cause);
	}
	
}
