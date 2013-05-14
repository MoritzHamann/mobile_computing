package a1.sender;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import a1.sender.MainClient.Monitor;

public class ClientReceiver implements Runnable {
	private String sourceIP;
	private int port;
	private Monitor monitor = null;
	private int ctsTimeout;

	public ClientReceiver(String sourceIP, int port, int ctsTimeout,
			Monitor monitor) {
		this.sourceIP = sourceIP;
		this.port = port;
		this.monitor = monitor;
		this.ctsTimeout = ctsTimeout;
	}

	@Override
	public void run() {
		try {
			DatagramSocket serverSocket = new DatagramSocket(port);
			byte[] data = new byte[1472];
			while (true) {
				DatagramPacket packet = new DatagramPacket(data, data.length);
				// receive one datagram
				serverSocket.receive(packet);
				a1.ressources.Package p = deserializePackage(packet.getData());
				System.out.println("ID des empfangenen Pakets " + p.getId()
						+ " von " + p.getSourceIP());
				switch (p.getId()) {
				case 0:
					break;
				case 1:
					// notify
					if (p.getDestIP().equals(sourceIP)) {
						synchronized (monitor) {
							System.out.println("CTS erhalten setze CTS = True");
							monitor.setCts(true);
							monitor.notify();
						}
					} else {
						System.out.println("CTS nicht an mich erhalten warte");
						synchronized (monitor) {
							monitor.wait(ctsTimeout);
						}
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private a1.ressources.Package deserializePackage(byte[] data)
			throws IOException, ClassNotFoundException {
		ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(
				byteStream));
		a1.ressources.Package p = (a1.ressources.Package) is.readObject();
		is.close();
		return p;
	}
}
