import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TopMain {
	
	private String userText;
	private JFrame company;
	private JPanel options;
	private JPanel cards;
	private CardLayout cl;

	public JPanel getCards() {
		return cards;
	}

	public CardLayout getCl() {
		return cl;
	}
	public JFrame getCompany() {
		return company;
	}

	public void setCompany(JFrame company) {
		this.company = company;
	}

	public JPanel getOptions() {
		return options;
	}

	public void setOptions(JPanel options) {
		this.options = options;
	}

	public void setCards(JPanel cards) {
		this.cards = cards;
	}

	public void setCl(CardLayout cl) {
		this.cl = cl;
	}

	public TopMain(String userText, final Database database, final JFrame main) {
		
		this.userText = userText;
		System.out.println(userText);
		JFrame company = new JFrame("Company Overview");

		// CardLayout
		cards = new JPanel(new CardLayout());
		
		options(database, main, company);
		
		cl = (CardLayout)(cards.getLayout());
		
		
		company.add(options, BorderLayout.WEST);
		company.add(cards, BorderLayout.EAST);
		cl.show(cards, "menu");
		company.pack();		
		company.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		company.setVisible(true);
	}
	
	
	public void options(Database database, JFrame main, JFrame company) {
		options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
		
		JButton menu = new JButton("Menu");
		menu.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		options.add(menu);
		menuButton(database, main, menu, company);
		
		JButton journeys = new JButton("View Journeys");
		journeys.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		options.add(journeys);
		journeyButton(database, main, journeys);
		
		
		JButton containers = new JButton("View Containers");
		containers.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		options.add(containers);
		containerButton(database, main, containers);
			
	} 
	
	public void menuButton(Database database, JFrame main, JButton menu, JFrame company) {
		final JPanel menupanel = new JPanel();
		menupanel.setPreferredSize(new Dimension(800, 600));
		menupanel.setBackground(Color.RED);
		
		cards.add(menupanel, "menu");
		
		JLabel lbl = new JLabel("Description");
		menupanel.add(lbl, BorderLayout.NORTH);
		logOutButton(database, main, company, menupanel);
		
		menu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cl.show(cards, "menu");
			}
		});
	}
	

	
	public void journeyButton(Database database, JFrame main, JButton journeys) {
		// journey section
		
		JourneySectionPanels j = new JourneySectionPanels(database, this);
		cards.add(j.getJourneySearch(), "journeySearch");
		cards.add(j.getViewJourneys(), "viewJourneys");
		
		journeys.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				cl.show(cards,  "journeySearch");
			}
		});
		

		
	}
	
	public String getUserText() {
		return userText;
	}

	public void setUserText(String userText) {
		this.userText = userText;
	}

	public void containerButton(Database database, JFrame main, JButton containers) {
	// container section
	
		ContainerSelectionPanels cont = new ContainerSelectionPanels(database, this);
		cards.add(cont.getContainerSearch(), "containerSearch");
		cards.add(cont.getViewContainers(), "viewContainers");
		cards.add(cont.getPlotPanel(), "plotPanel");
		
		containers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
				cl.show(cards,  "containerSearch");
				}
		});
			
	}
	


	public void logOutButton(Database database, final JFrame main, final JFrame company, JPanel menupanel) {
		// Logout as company user
		
		JButton logout = new JButton("Logout");
		JPanel top = new JPanel(new BorderLayout());
		menupanel.add(top, BorderLayout.NORTH);
		top.add(logout, BorderLayout.EAST);
		logout.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				main.setVisible(true);
				company.dispose();
			}
		});
	}
	
}
