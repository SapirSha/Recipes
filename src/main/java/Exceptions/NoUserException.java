package Exceptions;

public class NoUserException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public NoUserException(String msg) {
		super(msg);
	}
}
