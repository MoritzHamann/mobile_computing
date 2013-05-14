package a1.sender;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class ClientGUI {

	private JFrame frame;

	private JTextField textField;
	private JTextField textField_1;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
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
	public ClientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 861, 403);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblSource = new JLabel("Source");
		frame.getContentPane().add(lblSource);

		textField = new JTextField();
		textField.setText("192.168.1.2");
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblDestinationIp = new JLabel("Destination IP");
		frame.getContentPane().add(lblDestinationIp);

		final JFormattedTextField frmtdtxtfldIpadresse = new JFormattedTextField(
				"???.???.???.???");
		frmtdtxtfldIpadresse.setPreferredSize(new Dimension(120, 20));
		frmtdtxtfldIpadresse.setText("192.168.1.1");
		frmtdtxtfldIpadresse.setHorizontalAlignment(SwingConstants.LEFT);
		frame.getContentPane().add(frmtdtxtfldIpadresse);

		JLabel lblLambda = new JLabel("Lambda");
		frame.getContentPane().add(lblLambda);

		final JFormattedTextField frmtdtxtfldLambda = new JFormattedTextField();
		frmtdtxtfldLambda.setText("35");
		frmtdtxtfldLambda.setPreferredSize(new Dimension(40, 20));
		frame.getContentPane().add(frmtdtxtfldLambda);

		JLabel lblRtsDelay = new JLabel("RTS Delay");
		frame.getContentPane().add(lblRtsDelay);

		final JFormattedTextField frmtdtxtfldRts = new JFormattedTextField();
		frmtdtxtfldRts.setText("100");
		frmtdtxtfldRts.setPreferredSize(new Dimension(40, 20));
		frmtdtxtfldRts.setMinimumSize(new Dimension(20, 20));
		frame.getContentPane().add(frmtdtxtfldRts);

		JLabel lblCts = new JLabel("cts");
		frame.getContentPane().add(lblCts);

		textField_1 = new JTextField();
		textField_1.setText("1000");
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		textArea = new JTextArea();
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.setPreferredSize(new Dimension(500, 250));
		textArea.setEditable(false);
		frame.getContentPane().add(textArea);
		JButton btnNewButton_1 = new JButton("Stop");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("Beende");
				System.exit(0);
			}
		});

		JButton btnNewButton_2 = new JButton("Start");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sourceIP = textField.getText();
				String destIP = frmtdtxtfldIpadresse.getText();
				int lambda = Integer.valueOf(frmtdtxtfldLambda.getText());
				int rtsTimeout = Integer.valueOf(frmtdtxtfldRts.getText());
				int ctsTimeout = Integer.valueOf(textField_1.getText());
				MainClient client = new MainClient(sourceIP, destIP, lambda,
						rtsTimeout, ctsTimeout);
				client.startClient();
			}
		});
		frame.getContentPane().add(btnNewButton_2);
		frame.getContentPane().add(btnNewButton_1);
	}
}
