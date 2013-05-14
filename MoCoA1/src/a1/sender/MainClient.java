package a1.sender;

public class MainClient {
	public static Monitor monitor = new Monitor();
	private String destIP;
	private String sourceIP;
	private int lambda;
	private int rtsTimeout;
	private int ctsTimeout;

	public MainClient(String sourceIP, String destIP, int lambda,
			int rtsTimeout, int ctsTimeout) {
		this.destIP = destIP;
		this.lambda = lambda;
		this.rtsTimeout = rtsTimeout;
		this.sourceIP = sourceIP;
		this.ctsTimeout = ctsTimeout;

	}

	public void startClient() {
		Thread clientSender = new Thread(new ClientSender(sourceIP, destIP,
				7000, rtsTimeout, lambda, monitor));
		Thread clientReceiver = new Thread(new ClientReceiver(sourceIP, 7000,
				ctsTimeout, monitor));
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
