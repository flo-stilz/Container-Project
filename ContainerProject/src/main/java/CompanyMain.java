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

import model.Application;
import model.Simulator;


public class CompanyMain extends TopMain{
	
	private ClientSectionPanels c;
	private Application application;
//	private JPanel options;
//	private JPanel cards;
//	private CardLayout cl;
//
//	public JPanel getCards() {
//		return cards;
//	}
//
//	public CardLayout getCl() {
//		return cl;
//	}

	public CompanyMain(final Application application, final JFrame login) {
		super(application, login);
		
		this.application = application;
//		final JFrame company = new JFrame("Company Overview");

		// CardLayout
//		cards = new JPanel(new CardLayout());
//		
//		options(database, main, company);
//		
//		cl = (CardLayout)(cards.getLayout());
//		
//		
//		company.add(options, BorderLayout.WEST);
//		company.add(cards, BorderLayout.EAST);
//		cl.show(cards, "menu");
//		company.pack();		
//		company.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		company.setVisible(true);
	}
	
	@Override
	public void options(Application application, JFrame login) {
		setOptions(new JPanel());
		getOptions().setLayout(new BoxLayout(getOptions(), BoxLayout.Y_AXIS));
		
		JButton menu = new JButton("Menu");
		menu.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		getOptions().add(menu);
		menuButton(application, login, menu);
		
		JButton clients = new JButton("View Clients");
		clients.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		getOptions().add(clients);
		clientButton(application, login, clients);
		
		
		JButton journeys = new JButton("View Journeys");
		journeys.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		getOptions().add(journeys);
		journeyButton(application, login, journeys);
		
		
		JButton containers = new JButton("View Containers");
		containers.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		getOptions().add(containers);
		containerButton(application, login, containers);
		
		
		JButton simulation = new JButton("Start Simulation");
		simulation.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		getOptions().add(simulation);
		simulationButton(application, login, simulation);
		
	
	} 
	
//	public void menuButton(Database database, JFrame main, JButton menu, JFrame company) {
//		final JPanel menupanel = new JPanel();
//		menupanel.setPreferredSize(new Dimension(800, 600));
//		menupanel.setBackground(Color.RED);
//		
//		getCards().add(menupanel, "menu");
//		
//		JLabel lbl = new JLabel("Description");
//		menupanel.add(lbl, BorderLayout.NORTH);
//		logOutButton(database, main, company, menupanel);
//		
//		menu.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				cl.show(cards, "menu");
//			}
//		});
//	}
	
	public void clientButton(final Application application, JFrame login, JButton clients) {
		c = new ClientSectionPanels(application, this);
		getCards().add(c.getClientSearch(), "clientSearch");
		getCards().add(c.getViewClients(), "viewClients");
		clients.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				removeListeners();
				application.addObserver(c);
				getCl().show(getCards(), "clientSearch");
			}
		});
		
	}
	
//	public void journeyButton(Database database, JFrame main, JButton journeys) {
//		// journey section
//		
//		JourneySectionPanels j = new JourneySectionPanels(database, this);
//		cards.add(j.getJourneySearch(), "journeySearch");
//		cards.add(j.getViewJourneys(), "viewJourneys");
//		
//		journeys.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				
//				cl.show(cards,  "journeySearch");
//			}
//		});
//		
//
//		
//	}
	
//	public void containerButton(Database database, JFrame main, JButton containers) {
//	// container section
//	
//		ContainerSelectionPanels cont = new ContainerSelectionPanels(database, this);
//		cards.add(cont.getContainerSearch(), "containerSearch");
//		cards.add(cont.getViewContainers(), "viewContainers");
//		cards.add(cont.getPlotPanel(), "plotPanel");
//		
//		containers.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//						
//				cl.show(cards,  "containerSearch");
//				}
//		});
//			
//	}
	
	public void simulationButton(final Application application, JFrame login, JButton simulation) {	
		// simulation section
		
		JPanel sim = new JPanel();
		sim.setPreferredSize(new Dimension(800, 600));
		getCards().add(sim, "sim");
		
		JLabel lbldays = new JLabel("amount of days");
		final JTextField days = new JTextField();
		days.setPreferredSize(new Dimension(100, 25));
		
		JButton start = new JButton("Press Start");
		
		start.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Simulator simulation = new Simulator();
				simulation.simulation(application, Integer.parseInt(days.getText()));

			}
		});
		sim.add(lbldays);
		sim.add(days);
		sim.add(start);
		
		simulation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				removeListeners();
				getCl().show(getCards(), "sim");
			}
		});
	}
	
//	public void logOutButton(Database database, final JFrame main, final JFrame company, JPanel menupanel) {
//		// Logout as company user
//		
//		JButton logout = new JButton("Logout");
//		menupanel.add(logout);
//		logout.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				main.setVisible(true);
//				company.dispose();
//			}
//		});
//	}
	
	@Override
	public void removeListeners() {
		application.removeObserver(getM());
		application.removeObserver(getJ());
		application.removeObserver(getCont());
		application.removeObserver(c);
	}
	
	
}