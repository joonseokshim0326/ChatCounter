package edu.handong.csee.java.chatcounter;
/**
 * @author jssjp
 * 2018. 6. 15.
 * The thread that parses the file.
 */
public class ReadFileThread implements Runnable {
	int index;

	public ReadFileThread(int index) {
		this.index = index;
	}

	public void run() {
		System.out.println(this.index + " thread start.");
		try {
			Input.readAllFileStack();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(this.index + " thread end.");
	}
}
