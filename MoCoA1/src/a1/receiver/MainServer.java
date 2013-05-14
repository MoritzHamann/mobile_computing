package a1.receiver;

import java.util.ArrayList;
import java.util.HashMap;

public class MainServer {
	private static HashMap<String, ArrayList<Integer>> log = new HashMap<String, ArrayList<Integer>>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Thread receiver = new Thread(new ServerReceiver(7000, "192.168.1.1", log));
		receiver.start();
		}
	
	public void testname() throws Exception {
		
	}
}
