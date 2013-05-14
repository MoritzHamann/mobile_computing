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
	private static HashMap<String, ArrayList<Integer>> log = new HashMap<String, ArrayList<Integer>>();
	private static Thread receiver;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI window = new ServerGUI();
					window.frame.setVisible(true);
					receiver = new Thread(new ServerReceiver(7000,
							"192.168.1.1", log));
					receiver.start();
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
		frmtdtxtfldIpadresse.setText("");
		frmtdtxtfldIpadresse.setHorizontalAlignment(SwingConstants.LEFT);
		frame.getContentPane().add(frmtdtxtfldIpadresse);

		JLabel lblLambda = new JLabel("Lambda");
		frame.getContentPane().add(lblLambda);

		final JFormattedTextField frmtdtxtfldLambda = new JFormattedTextField();
		frmtdtxtfldLambda.setPreferredSize(new Dimension(40, 20));
		frame.getContentPane().add(frmtdtxtfldLambda);

		JLabel lblRtsDelay = new JLabel("RTS Delay");
		frame.getContentPane().add(lblRtsDelay);

		final JFormattedTextField frmtdtxtfldRts = new JFormattedTextField();
		frmtdtxtfldRts.setPreferredSize(new Dimension(20, 20));
		frmtdtxtfldRts.setMinimumSize(new Dimension(20, 20));
		frame.getContentPane().add(frmtdtxtfldRts);

		JButton btnNewButton_1 = new JButton("Stop");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				receiver.interrupt();
				while (receiver.getState() != Thread.State.TERMINATED) {

				}
				frame.dispose();
				System.out.println(log.size());
				for (String ip : log.keySet()) {
					System.out.println(ip);
					ArrayList<Integer> receivedPackages = log.get(ip);
					int lastPackage = 0;
					for (int i = 0; i < receivedPackages.size(); i++) {
						if (receivedPackages.get(i) >= lastPackage) {
							lastPackage = receivedPackages.get(i);
						}
					}
					System.out.println("Last Package " + lastPackage);
					System.out.println(ip + "Packetverlust = " + (lastPackage - receivedPackages.size()));
				}
				
				System.out.println("beende");
				System.exit(0);
			}
		});

		JButton btnNewButton_2 = new JButton("Start");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// receiver = new Thread(new ServerReceiver(7000,
				// "192.168.1.1"));
				// receiver.start();

			}
		});
		frame.getContentPane().add(btnNewButton_2);
		frame.getContentPane().add(btnNewButton_1);

		JTextArea textArea = new JTextArea();
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.setPreferredSize(new Dimension(500, 250));
		textArea.setEditable(false);
		frame.getContentPane().add(textArea);
	}

}
