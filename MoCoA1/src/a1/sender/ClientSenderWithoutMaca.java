package a1.sender;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import a1.ressources.Package;

public class ClientSenderWithoutMaca implements Runnable {

	private int lambda;
	private String destIP;
	private String sourceIP;
	private int port;
	private int seqNumber = 1;
	private boolean notInterrupted = true;

	public int getSeqNumber() {
		return seqNumber;
	}

	public ClientSenderWithoutMaca(String sourceIP, String destIP, int port,
			int lambda) {
		this.sourceIP = sourceIP;
		this.destIP = destIP;
		this.lambda = lambda;
		this.port = port;
	}

	@Override
	public void run() {
		DatagramSocket clientSocket = null;
		try {
			clientSocket = new DatagramSocket();
			clientSocket.setBroadcast(true);
		} catch (SocketException e2) {
			e2.printStackTrace();
		}
		// send one datagram
		while (notInterrupted) {
			try {
				synchronized (this) {
					// number between 0 and < 1
					double random = Math.random();
					long waitingTime;
					do {
						random = Math.random();
						// calculate waiting time until it is not 0
						waitingTime = (long) ((-1.0 / lambda)
								* Math.log(random) * 1000);
					} while (waitingTime <= 0);
					System.out.println("Warte vor Senden für " + waitingTime);
					wait(waitingTime);
				}

			} catch (InterruptedException e1) {
				notInterrupted = false;
			}
			try {
				Package dataPackage = new Package();
				dataPackage.setSeqNumber(seqNumber);
				dataPackage.setId(2);
				dataPackage.setDestIP(destIP);
				dataPackage.setSourceIP(sourceIP);
				DatagramPacket sendPackage = createBytePackage(dataPackage);
				clientSocket.send(sendPackage);
				seqNumber++;
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private DatagramPacket createBytePackage(Package p) throws IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(1472);
		ObjectOutputStream os = new ObjectOutputStream(
				new BufferedOutputStream(byteStream));
		os.flush();
		os.writeObject(p);
		os.flush();
		// retrieves byte array
		byte[] sendBuf = byteStream.toByteArray();
		DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length,
				InetAddress.getByName("192.168.1.255"), port);
		os.close();
		return packet;
	}
}
