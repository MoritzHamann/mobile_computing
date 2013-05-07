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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class SenderGUI {

	private JFrame frame;
	private Thread sender;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SenderGUI window = new SenderGUI();
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
	public SenderGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 599, 322);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblDestinationIp = new JLabel("Destination IP");
		frame.getContentPane().add(lblDestinationIp);
		
		final JFormattedTextField frmtdtxtfldIpadresse = new JFormattedTextField("???.???.???.???");
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
				frame.dispose();
				System.exit(0);
			}
		});
		
		JButton btnNewButton_2 = new JButton("Start");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String a = frmtdtxtfldIpadresse.getText();
		//		sender = new Thread(new ClientSender(frmtdtxtfldIpadresse.getText(), Integer.valueOf(frmtdtxtfldLambda.getText()), Integer.valueOf(frmtdtxtfldRts.getText())));
			//	sender.start();
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
