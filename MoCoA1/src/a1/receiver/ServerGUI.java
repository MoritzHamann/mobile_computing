package a1.receiver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class ServerGUI {

	private JFrame frame;
	private JButton btnNewButton_1;
	private JTextArea textArea;
	/**
	 * ip as key and arrayList of sequence number for received packages
	 */
	private static HashMap<String, ArrayList<Integer>> log = new HashMap<String, ArrayList<Integer>>();
	private static Thread receiver;
	private static long startTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI window = new ServerGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 599, 322);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblDestinationIp = new JLabel("Destination IP");
		frame.getContentPane().add(lblDestinationIp);

		final JFormattedTextField frmtdtxtfldIpadresse = new JFormattedTextField(
				"???.???.???.???");
		frmtdtxtfldIpadresse.setPreferredSize(new Dimension(120, 20));
		frmtdtxtfldIpadresse.setText("192.168.1.1");
		frmtdtxtfldIpadresse.setHorizontalAlignment(SwingConstants.LEFT);
		frame.getContentPane().add(frmtdtxtfldIpadresse);

		JButton btnNewButton_2 = new JButton("Start");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sourceIP = frmtdtxtfldIpadresse.getText();
				receiver = new Thread(new ServerReceiver(7000, sourceIP, log));
				startTime = System.currentTimeMillis();
				receiver.start();
				// for time Measurement
				// endServer();
			}
		});

		frame.getContentPane().add(btnNewButton_2);
		btnNewButton_1 = new JButton("Stop");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// throws exception if called before start
				endServer();
			}
		});
		frame.getContentPane().add(btnNewButton_1);
		textArea = new JTextArea();
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.setPreferredSize(new Dimension(500, 250));
		textArea.setEditable(false);
		frame.getContentPane().add(textArea);
	}

	private void endServer() {
		// for measurement
		// while (System.currentTimeMillis() - startTime < 30000) {
		// }
		//get last Packages
		receiver.interrupt();
		int j = 0;
		while (receiver.getState() != Thread.State.TERMINATED) {
			// only working if something has been received therefore max break;
			if (j > 50) {
				break;
			}
			j++;
		}
		long endTime = System.currentTimeMillis();
		// frame.dispose();
		for (String ip : log.keySet()) {
			ArrayList<Integer> receivedPackages = log.get(ip);
			int lastPackage = 0;
			for (int i = 0; i < receivedPackages.size(); i++) {
				if (receivedPackages.get(i) >= lastPackage) {
					lastPackage = receivedPackages.get(i);
				}
			}
			// timespan
			long timeDuration = (endTime - startTime) / 1000;
			// absolute number of lost packages
			int lostPackages = lastPackage - receivedPackages.size();
			System.out.println("Zeitspanne: " + timeDuration);
			System.out.println(ip + " Letztes empfangene Paket (SeqNummer): "
					+ lastPackage);
			System.out.println(ip + " Packetverlust: " + lostPackages);
			System.out.println(ip + " Packetverlust pro Sekunde: "
					+ ((double) lostPackages / timeDuration));
			textArea.append("Zeitspanne: " + timeDuration + "\n");
			textArea.append(ip + " Letztes empfangene Paket (SeqNummer): "
					+ lastPackage + "\n");
			textArea.append(ip + " Packetverlust: " + lostPackages + "\n");
			textArea.append(ip + " Packetverlust pro Sekunde: "
					+ ((double) lostPackages / timeDuration) + "\n");
		}
		System.out.println("Beende");
		textArea.append("Beende");
	}
}
