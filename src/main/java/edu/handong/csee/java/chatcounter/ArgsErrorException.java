package edu.handong.csee.java.chatcounter;
/**
 * This class if for exception handling
 * @author jssjp
 */
public class ArgsErrorException extends Exception {
	/**
	 * @param message Message to display in error
	 * @author joonseokshim0326
	 */
	public ArgsErrorException(String message) {
		super(message);
	}
}