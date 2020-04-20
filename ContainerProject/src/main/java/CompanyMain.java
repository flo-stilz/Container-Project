import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class CompanyMain implements PropertyChangeListener{
	
	ClientSectionPanels c;
	JPanel cards;
	CardLayout cl;

	public CompanyMain(Database database) {
		
		final JFrame company = new JFrame("Company Overview");
		
		JPanel options = new JPanel();
		final JPanel rest = new JPanel();
		rest.setPreferredSize(new Dimension(800, 600));
		rest.setBackground(Color.RED);
		final JPanel rest2 = new JPanel(new CardLayout());
		rest2.setPreferredSize(new Dimension(800, 600));
		rest2.setBackground(Color.BLUE);
		cards = new JPanel(new CardLayout());
		cards.add(rest, "rest");
		cards.add(rest2, "rest2");
		
		JButton clients = new JButton("View Clients");
		clients.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		JButton journeys = new JButton("View Journeys");
		journeys.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		JButton containers = new JButton("View Containers");
		containers.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		JButton simulation = new JButton("Start Simulation");
		simulation.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
	
		
		
		// menu options panel
		
		// client section
		
		ClientSectionPanels c = new ClientSectionPanels(database);
		cards.add(c.getClientSearch(), "clientsearch");
//		cards.add(c.getViewClients(), "viewclients");
//		cards.add(c.getData(), "data");
		
		// CardLayout
		cl = (CardLayout)(cards.getLayout());
		
		clients.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				cl.show(cards, "clientsearch");
			}
		});
		
		
		
		// journey section
		
		clients.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		// container section
		
		
		
		// simulation section
		
		
		
		// rest panel
		
		JButton con = new JButton("Continue");
		JLabel lbl = new JLabel("Description");
		rest.add(lbl, BorderLayout.NORTH);
		rest.add(con, BorderLayout.PAGE_END);
		con.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cl.show(cards, "rest2");
			}
			
		});
		
		
		
		options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
		company.add(options, BorderLayout.WEST);
		company.add(cards, BorderLayout.EAST);
		cl.show(cards, "rest");
		options.add(clients);
		options.add(journeys);
		options.add(containers);
		options.add(simulation);
		company.pack();		
		company.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		company.setVisible(true);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		cards.add(c.getViewClients(), "viewclients");
		cl = (CardLayout)(cards.getLayout());
		cl.show(cards, "viewclients");
		System.out.println("hello");
		
	}
	
}
