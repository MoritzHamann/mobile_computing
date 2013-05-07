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
	private HashMap<String, ArrayList<Integer>> log = new HashMap<String, ArrayList<Integer>>();

	@Override
	public void run() {
		try {
			DatagramSocket serverSocket = new DatagramSocket(7000);
			// allocate space for received datagram
			byte[] data = new byte[1400];
			DatagramPacket packet = new DatagramPacket(data, data.length);

			while (true) {
				// receive one datagram
				serverSocket.receive(packet);
				System.out.println(packet.getLength());

				a1.ressources.Package p = deserializePackage(data);
				switch (p.getId()) {
				case 0:
					// send cts
					Package ctsPackage = new Package();
					ctsPackage.setId(1);
					ctsPackage.setDestIP(p.getSourceIP());
					ctsPackage.setSourceIP("127.0.0.1");
					ByteArrayOutputStream byteStream = new ByteArrayOutputStream(
							5000);
					ObjectOutputStream os = new ObjectOutputStream(
							new BufferedOutputStream(byteStream));
					os.flush();
					os.writeObject(ctsPackage);
					os.flush();
					// retrieves byte array

					byte[] sendBuf = byteStream.toByteArray();
					packet = new DatagramPacket(sendBuf, sendBuf.length,
							InetAddress.getByName("255.255.255.255"), 7000);
					serverSocket.send(packet);
					break;
				case 1:
					break;
				case 2:
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