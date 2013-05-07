package a1.receiver;


public class MainServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Thread receiver = new Thread(new ServerReceiver());
		receiver.start();

	}

}
