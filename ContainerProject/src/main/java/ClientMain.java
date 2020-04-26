import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientMain extends TopMain{
	
	private client currentClient;
	private JourneySectionPanels j;
	private ContainerSelectionPanels cont;

	public JourneySectionPanels getJ() {
		return j;
	}

	public ContainerSelectionPanels getCont() {
		return cont;
	}
	
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

	public ClientMain(String userText, final Database database, final JFrame login) {
		super(userText, database, login);
		System.out.println("client");


//		final JFrame client = new JFrame("Client Overview");
//
//		// CardLayout
//		cards = new JPanel(new CardLayout());
////		
//		options(database, main, client);
////		
//		cl = (CardLayout)(cards.getLayout());
////		
////		
//		client.add(options, BorderLayout.WEST);
//		client.add(cards, BorderLayout.EAST);
//		cl.show(cards, "menu");
//		client.pack();		
//		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		client.setVisible(true);
	}
	
	@Override
	public void options(Database database, JFrame login) {
		currentClient = database.search(getUserText()).get(0);
		setOptions(new JPanel());
		getOptions().setLayout(new BoxLayout(getOptions(), BoxLayout.Y_AXIS));
		
		JButton menu = new JButton("Profile");
		menu.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		getOptions().add(menu);
		menuButton(database, login, menu);
		
//		JButton clients = new JButton("View Clients");
//		clients.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
//		options.add(clients);
//		clientButton(database, main, clients);
		
		
		JButton journeys = new JButton("My Journeys");
		journeys.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		getOptions().add(journeys);
		journeyButton(database, login, journeys);
		
		
		JButton containers = new JButton("My Containers");
		containers.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		getOptions().add(containers);
		containerButton(database, login, containers);
		
		
//		JButton simulation = new JButton("Start Simulation");
//		simulation.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
//		options.add(simulation);
//		simulationButton(database, main, simulation);
		
	
	} 
	@Override
	public void menuButton(Database database, JFrame login, JButton menu) {
		final JPanel menupanel = new JPanel( new BorderLayout());
		menupanel.setPreferredSize(new Dimension(800, 600));
		menupanel.setBackground(Color.RED);
		
		getCards().add(menupanel, "menu");
		
		JPanel clientDetails = new JPanel();
		clientDetails.setLayout(new BoxLayout(clientDetails, BoxLayout.Y_AXIS));
		
		JLabel lbl = new JLabel("Description");
		Font f = lbl.getFont();
		lbl.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
		JLabel company = new JLabel("Company: " + currentClient.getCompany());
		JLabel ref = new JLabel("Reference person: " + currentClient.getName());
		JLabel mail = new JLabel("E-mail: " + currentClient.getEmail());
		JLabel address = new JLabel("Address: " + currentClient.getAddress());
		JLabel id = new JLabel("Company id: " + currentClient.getCompany());
		clientDetails.add(lbl);
		clientDetails.add(company);
		clientDetails.add(ref);
		clientDetails.add(mail);
		clientDetails.add(address);
		clientDetails.add(id);
		menupanel.add(clientDetails, BorderLayout.CENTER);
		
		logOutButton(database, login, menupanel);
		
		menu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getJ().getwJourneys().clear();
				getCont().getwContainers().clear();
				getCl().show(getCards(), "menu");
			}
		});
	}
	
//	public void clientButton(Database database, JFrame main, JButton clients) {
//		ClientSectionPanels c = new ClientSectionPanels(database, this);
//		cards.add(c.getClientSearch(), "clientSearch");
//		cards.add(c.getViewClients(), "viewClients");
//		clients.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				
//				cl.show(cards, "clientSearch");
//			}
//		});
//		
//	}
	@Override
	public void journeyButton(Database database, JFrame login, JButton journeys) {
		// journey section
		
		j = new JourneySectionPanels(database, this);
		database.addObserver(j);
		getCards().add(j.getJourneySearch(), "journeySearch");
		getCards().add(j.getViewJourneys(), "viewJourneys");
		
		journeys.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getJ().getwJourneys().clear();
				getCont().getwContainers().clear();
				getCl().show(getCards(),  "journeySearch");
			}
		});
		

		
	}
	
	@Override
	public void containerButton(Database database, JFrame login, JButton containers) {
	// container section
	
		cont = new ContainerSelectionPanels(database, this);
		database.addObserver(cont);
		getCards().add(cont.getContainerSearch(), "containerSearch");
		getCards().add(cont.getViewContainers(), "viewContainers");
		getCards().add(cont.getPlotPanel(), "plotPanel");
		
		containers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getJ().getwJourneys().clear();
				getCont().getwContainers().clear();
				getCl().show(getCards(),  "containerSearch");
				}
		});
			
	}
	
//	public void simulationButton(final Database database, JFrame main, JButton simulation) {	
//		// simulation section
//		
//		JPanel sim = new JPanel();
//		sim.setPreferredSize(new Dimension(800, 600));
//		cards.add(sim, "sim");
//		
//		JLabel lbldays = new JLabel("amount of days");
//		final JTextField days = new JTextField();
//		days.setPreferredSize(new Dimension(100, 25));
//		
//		JButton start = new JButton("Press Start");
//		
//		start.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				Simulator simulation = new Simulator();
//				simulation.Simulation(database, Integer.parseInt(days.getText()));
//			}
//		});
//		sim.add(lbldays);
//		sim.add(days);
//		sim.add(start);
//		
//		simulation.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				cl.show(cards, "sim");
//			}
//		});
//	}
	
//	public void logOutButton(Database database, final JFrame main, final JFrame company, JPanel menupanel) {
//		// Logout as company user
//		
//		JButton logout = new JButton("Logout");
//		JPanel top = new JPanel(new BorderLayout());
//		menupanel.add(top, BorderLayout.NORTH);
//		top.add(logout, BorderLayout.EAST);
//		logout.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				main.setVisible(true);
//				company.dispose();
//			}
//		});
//	}
}
