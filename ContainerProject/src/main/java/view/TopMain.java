package view;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import model.Application;

public class TopMain {
	

	private JFrame main1;
	private JPanel options;
	private JPanel cards;
	private CardLayout cl;
	private JourneySectionPanels j;
	private ContainerSelectionPanels cont;
	private MenuSectionPanels m;
	private Application application;
	
	public void setJ(JourneySectionPanels j) {
		this.j = j;
	}

	public JourneySectionPanels getJ() {
		return j;
	}

	public ContainerSelectionPanels getCont() {
		return cont;
	}

	public MenuSectionPanels getM() {
		return m;
	}

	public void setCont(ContainerSelectionPanels cont) {
		this.cont = cont;
	}

	public JPanel getCards() {
		return cards;
	}

	public CardLayout getCl() {
		return cl;
	}
	public JFrame getMain1() {
		return main1;
	}

	public void setMain1(JFrame main1) {
		this.main1 = main1;
	}

	public JPanel getOptions() {
		return options;
	}

	public void setOptions(JPanel options) {
		this.options = options;
	}

	public TopMain(final Application application, final JFrame login) {
		
		this.application = application;
		main1 = new JFrame("Company Overview");

		// CardLayout
		cards = new JPanel(new CardLayout());
		
		options(application, login);
		
		cl = (CardLayout)(cards.getLayout());
		
		
		main1.add(options, BorderLayout.WEST);
		main1.add(cards, BorderLayout.EAST);
		cl.show(cards, "menu");
		main1.pack();		
		main1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main1.setVisible(true);
	}
	
	
	public void options(Application application, JFrame login) {
		options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
		
		JButton menu = new JButton("Menu");
		menu.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		options.add(menu);
		menuButton(application, login, menu);
		
		JButton journeys = new JButton("View Journeys");
		journeys.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		options.add(journeys);
		journeyButton(application, login, journeys);
		
		
		JButton containers = new JButton("View Containers");
		containers.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		options.add(containers);
		containerButton(application, login, containers);
			
	} 
	
	public void menuButton(final Application application, JFrame login, JButton menu) {
		m = new MenuSectionPanels(application, this, login);
		application.addObserver(m);
		getCards().add(m.getMenupanel(), "menu");
		
		menu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				removeListeners();
				application.addObserver(m);
				getCl().show(getCards(), "menu");
			}
		});
	}
	

	
	public void journeyButton(final Application application, JFrame login, JButton journeys) {
		// journey section
		
		j = new JourneySectionPanels(application, this);
		cards.add(j.getJourneySearch(), "journeySearch");
		cards.add(j.getViewJourneys(), "viewJourneys");
		
		journeys.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				removeListeners();
				application.addObserver(j);
				cl.show(cards,  "journeySearch");
			}
		});
		

		
	}

	public void containerButton(final Application application, JFrame login, JButton containers) {
	// container section
	
		cont = new ContainerSelectionPanels(application, this);
		cards.add(cont.getContainerSearch(), "containerSearch");
		cards.add(cont.getViewContainers(), "viewContainers");
		cards.add(cont.getPlotPanel(), "plotPanel");
		
		containers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeListeners();
				application.addObserver(cont);
				cl.show(cards,  "containerSearch");
				}
		});
			
	}
	
	public void removeListeners() {
		application.removeObserver(m);
		application.removeObserver(j);
		application.removeObserver(cont);
		cont.getContainerPlot().getObs().clear();
	}
	
}