package view;
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

	public CompanyMain(final Application application, final JFrame login) {
		super(application, login);
		
		this.application = application;
	}
	
	/*
	 * Overrides the options menu so it contains view Clients and simulation as well.
	 */
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

	/*
	 * Creates a ClientSectionPanel, adds it as a card to cl, removes old listeners and then adds the observer to it.
	 * Shows the card if pressed.
	 */
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
	/*
	 * Creates a sim panel, adds it as a card to cl, removes old listeners and then adds the observer to it.
	 * Shows the card if pressed.
	 */
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
				application.simulation(Integer.parseInt(days.getText()));

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
	
	//Removes listeners.
	@Override
	public void removeListeners() {
		application.removeObserver(getM());
		application.removeObserver(getJ());
		application.removeObserver(getCont());
		application.removeObserver(c);
		getCont().getContainerPlot().getObs().clear();
	}
	
	
}