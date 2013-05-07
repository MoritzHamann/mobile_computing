package a1.sender;

public class MainClient {
	public static Monitor monitor = new Monitor();
	
	public static void main(String[] args) {
		Thread clientSender = new Thread(new ClientSender("192.168.1.2",
				"192.168.1.1", 7000, 100, 10, monitor));
		Thread clientReceiver = new Thread(new ClientReceiver(
				"192.168.1.2", 7000, monitor));
		clientReceiver.start();
		clientSender.start();
	}
	
	
	
	public static class Monitor {
		boolean cts = false;

		public synchronized boolean isCts() {
			return cts;
		}

		public synchronized void setCts(boolean cts) {
			this.cts = cts;
		}
	}
}
