package a1.sender;

public class MainClient {
	public static Monitor monitor = new Monitor();
	private String destIP;
	private String sourceIP;
	private int lambda;
	private int rtsTimeout;
	private int ctsTimeout;
	private Thread clientSender;
	private Thread clientReceiver;

	public MainClient(String sourceIP, String destIP, int lambda,
			int rtsTimeout, int ctsTimeout) {
		this.destIP = destIP;
		this.lambda = lambda;
		this.rtsTimeout = rtsTimeout;
		this.sourceIP = sourceIP;
		this.ctsTimeout = ctsTimeout;
	}

	public void startClient() {
		clientSender = new Thread(new ClientSender(sourceIP, destIP,
				7000, rtsTimeout, lambda, monitor));
		clientReceiver = new Thread(new ClientReceiver(sourceIP, 7000,
				ctsTimeout, monitor));
		clientReceiver.start();
		clientSender.start();
	}

	public void startClientWithoutMaca() {
		clientSender = new Thread(new ClientSenderWithoutMaca(sourceIP, destIP,
				7000, lambda));
		clientSender.start();
	}

	public Thread getClientSender() {
		return clientSender;
	}

	public Thread getClientReceiver() {
		return clientReceiver;
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
