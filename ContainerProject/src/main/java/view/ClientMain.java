package view;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import model.Application;
import model.Client;


public class ClientMain extends TopMain{
	
	// The currentClient for the given menu.
	private Client currentClient;
	
	public Client getCurrentClient() {
		return currentClient;
	}

	public ClientMain(final Application application, final JFrame login) {
		super(application, login);
		currentClient = application.getCurrentUser();
	}
	
	/*
	 * Overrides the options menu from TopMain to show my journeys and my containers instead.
	 * This restricts the clients options.
	 */
	@Override
	public void options(Application application, JFrame login) {
		setOptions(new JPanel());
		getOptions().setLayout(new BoxLayout(getOptions(), BoxLayout.Y_AXIS));
		
		JButton menu = new JButton("Profile");
		menu.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		getOptions().add(menu);
		menuButton(application, login, menu);
	
		JButton journeys = new JButton("My Journeys");
		journeys.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		getOptions().add(journeys);
		journeyButton(application, login, journeys);
		
		
		JButton containers = new JButton("My Containers");
		containers.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		getOptions().add(containers);
		containerButton(application, login, containers);
	} 

	/*
	 * Creates a JourneySectionPanel, adds the cards to cl, removes old listeners and then adds the observer to it.
	 * Shows the card if pressed.
	 */
	@Override
	public void journeyButton(final Application application, JFrame login, JButton journeys) {
		// journey section
		
		setJ(new JourneySectionPanels(application, this));
		getCards().add(getJ().getJourneySearch(), "journeySearch");
		getCards().add(getJ().getViewJourneys(), "viewJourneys");
		
		journeys.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				removeListeners();
				application.addObserver(getJ());
				getCl().show(getCards(),  "journeySearch");
			}
		});
	}
	
	/*
	 * Creates a ContainerselectionPanel, adds the cards to cl, removes old listeners and then adds the observer to it.
	 * Shows the card if pressed.
	 */
	@Override
	public void containerButton(final Application application, JFrame login, JButton containers) {
	// container section
	
		setCont(new ContainerSelectionPanels(application, this));
		getCards().add(getCont().getContainerSearch(), "containerSearch");
		getCards().add(getCont().getViewContainers(), "viewContainers");
		getCards().add(getCont().getPlotPanel(), "plotPanel");
		
		containers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeListeners();
				application.addObserver(getCont());
				getCl().show(getCards(),  "containerSearch");
				}
		});	
	}
}