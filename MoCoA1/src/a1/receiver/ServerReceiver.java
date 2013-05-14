package a1.receiver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import a1.ressources.Package;

public class ServerReceiver implements Runnable {
	// private HashMap<String, ArrayList<Integer>> log = new HashMap<String,
	// ArrayList<Integer>>();
	private int port;
	private String sourceIP;
	private HashMap<String, ArrayList<Integer>> log;

	public ServerReceiver(int port, String sourceIP,
			HashMap<String, ArrayList<Integer>> log) {
		this.port = port;
		this.sourceIP = sourceIP;
		this.log = log;
	}

	@Override
	public void run() {
		try {
			DatagramSocket serverSocket = new DatagramSocket(port);
			serverSocket.setBroadcast(true);
			// allocate space for received datagram
			byte[] data = new byte[1472];
			while (!Thread.currentThread().isInterrupted()) {
				DatagramPacket packet = new DatagramPacket(data, data.length);
				// receive one datagram
				serverSocket.receive(packet);
				a1.ressources.Package p = deserializePackage(packet.getData());
				System.out.println("Packet Id des empfangenen Pakets "
						+ p.getId() + " von " + p.getSourceIP());
				switch (p.getId()) {
				case 0:
					// send cts
					System.out.println("sende cts");
					Package ctsPackage = new Package();
					ctsPackage.setId(1);
					ctsPackage.setDestIP(p.getSourceIP());
					ctsPackage.setSourceIP(sourceIP);
					ByteArrayOutputStream byteStream = new ByteArrayOutputStream(
							1472);
					ObjectOutputStream os = new ObjectOutputStream(
							new BufferedOutputStream(byteStream));
					os.flush();
					os.writeObject(ctsPackage);
					os.flush();
					os.close();
					// retrieves byte array
					byte[] sendBuf = byteStream.toByteArray();
					DatagramPacket sendPackage = new DatagramPacket(sendBuf,
							sendBuf.length,
							InetAddress.getByName("192.168.1.255"), port);
					serverSocket.send(sendPackage);
					System.out.println("Sende CTS für " + p.getDestIP()
							+ "an alle");
					break;
				case 1:
					break;
				case 2:
					System.out.println("Datenpaket erhalten von "
							+ p.getSourceIP());
					ArrayList<Integer> packages = log.get(p.getSourceIP());
					if (packages != null) {
						packages.add(p.getSeqNumber());
						log.put(p.getSourceIP(), packages);
					} else {
						packages = new ArrayList<Integer>();
						packages.add(p.getSeqNumber());
						log.put(p.getSourceIP(), packages);
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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