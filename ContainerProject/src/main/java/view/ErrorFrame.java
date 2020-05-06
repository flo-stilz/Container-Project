package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ErrorFrame {

	// constructor that uses a keyword in case the object was created when pressing a search button in the different search panels
	// creates the label for the window
	public ErrorFrame(String keyword) {
		
		JLabel lbl = new JLabel(" Sorry, we couldnt find any results matching " + keyword);	
		createErrorFrame(lbl);
		
	}
	// constructor that doesnt use any input in case the object was created when pressing a showAll button in any of the search panels
	// creates the label for the window
	public ErrorFrame() {
		
		JLabel lbl = new JLabel(" Sorry, we couldnt find any results ");	
		createErrorFrame(lbl);
		
	}

	// creates the actual window with the given label created in the constructor of the object
	public void createErrorFrame(JLabel lbl) {
		
		final JFrame eFrame = new JFrame("Error");
		eFrame.setPreferredSize(new Dimension(330,100));
		
		JButton b = new JButton("OK");
		b.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				eFrame.dispose();
			}
		});
		eFrame.add(lbl, BorderLayout.NORTH);
		eFrame.add(b, BorderLayout.SOUTH);
		eFrame.pack();
		eFrame.setVisible(true);
	}
}
