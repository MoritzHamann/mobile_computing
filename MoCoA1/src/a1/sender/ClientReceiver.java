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

	public ClientReceiver(String sourceIP, int port, Monitor monitor) {
		this.sourceIP = sourceIP;
		this.port = port;
		this.monitor = monitor;
	}

	@Override
	public void run() {
		try {
			DatagramSocket serverSocket = new DatagramSocket(port);
			byte[] data = new byte[1472];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			while (true) {
				// receive one datagram
				serverSocket.receive(packet);
				a1.ressources.Package p = deserializePackage(data);
				switch (p.getId()) {
				case 1:
					// notify
					if (p.getDestIP().equals(sourceIP)) {
						synchronized (monitor) {
							monitor.setCts(true);
							monitor.notify();
						}
					} else {
						synchronized (monitor) {
							monitor.wait(2000);
						}
					}
					break;
				default:
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
		a1.ressources.Package o = (a1.ressources.Package) is.readObject();
		is.close();
		return o;
	}
}
