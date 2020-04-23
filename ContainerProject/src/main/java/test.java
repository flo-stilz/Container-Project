import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class test {

	public static void main(String[] args) {
		final JFrame frame = new JFrame("Test");
		final JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400,300));
		panel.setBackground(Color.BLUE);
		JPanel panel2 = new JPanel();
//		final JPanel cards = new JPanel(new CardLayout());
//		cards.add(panel);
//		cards.add(panel2);
//		final CardLayout cl = (CardLayout)(cards.getLayout());
		frame.add(panel, BorderLayout.NORTH);
		JButton button = new  JButton("press me");
		frame.add(button, BorderLayout.SOUTH);
//		cl.show(cards, "panel");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
//				panel.removeAll();
//				panel.setBackground(Color.BLACK);
				JPanel bar = new JPanel();
				bar.setPreferredSize(new Dimension(200,150));
				bar.setBackground(Color.RED);
				panel.add(bar);
				frame.revalidate();
//				cards.add(panel,"p");
//				cl.show(cards, "p");
			}
		});
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
