package edu.handong.csee.java.chatcounter;


import java.util.HashMap;

/**
 * This class counts the number of chats in a message file to make it a csv file.
 * @author jssjp
 * 2018. 5. 29.
 */
public class ChatCounter {

	/**
	 * key is Person name, value is Number of times said
	 */
	private static HashMap<String , Integer> result;

	/**
	 * @param args -c number of threads -i The folder adress where the chat file is located. -o The path to the csv file.
	 * @author joonseokshim0326
	 */
	public static void main(String[] args) {
		result = new HashMap<String , Integer>();
		try {
			if(validationCheck(args)) {
				result = Input.inputFiles(args[1], args[3]);
				Output.outFile(args[5], result);
			}else {
				throw new ArgsErrorException("Args are error");
			}
		} 
		catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Check that CLI parameters are correct.
	 * @param args
	 * @return 
	 * @throws ArgsErrorException 
	 */
	private static boolean validationCheck(String[] args) throws ArgsErrorException {
		if(args == null) {
			throw new ArgsErrorException("Args are error (Args are null.)");
		}
		if(args.length != 6) {
			throw new ArgsErrorException("Args are error (The number of args is not correct. 'ex) -c number of threads -i path -o file path')");
		}
		return true;
	}
}
