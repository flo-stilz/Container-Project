package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Container;
import model.Application;
import model.Client;


public class ClientSectionPanels implements PropertyChangeListener {

	// panels for the search and the display of the clients
	private JPanel clientSearch;
	private JPanel viewClients;
	// stores the matching clients to the given input in the search panel
	private ArrayList<Client> wClients = new ArrayList<Client>();
	// indicates whether the search or the showAll button were pressed
	private boolean showAllCommand;
	// input in the textfield
	private String keyword;
	private CompanyMain companymain;
	
	public ArrayList<Client> getwClients() {;
		return wClients;
	}

	public ClientSectionPanels(final Application application, final CompanyMain companymain) {
		
		this.companymain = companymain;
		// Search the clients
		
		// Initialisation of the clientSearch panel and the viewClients panel
		clientSearch = new JPanel();
		clientSearch.setPreferredSize(new Dimension(800, 600));
		
		viewClients = new JPanel(new BorderLayout());
		viewClients.setPreferredSize(new Dimension(800, 600));
		
		// search text field and search button
		final JTextField search = new JTextField();
		search.setPreferredSize(new Dimension(100, 25));
		clientSearch.add(search);
		
		JButton searchButton = new JButton("Search");
		clientSearch.add(searchButton);
		// sets wClients to the matching results
		searchButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				keyword = search.getText();
				showAllCommand = false;
				wClients = new ArrayList<Client>(application.searchClient(keyword));
				checksSearchEntryC(application);
			}
		});
		// sets wClients to all clients
		JButton showAll = new JButton("Show All");
		clientSearch.add(showAll);
		showAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				showAllCommand = true;
				wClients = new ArrayList<Client>(application.getClientDat().getClients());
				checksSearchEntryC(application);
			}
		});

	}
	// checks whether an input exists and if not creates an error frame
	// also depends on the button pressed
	// otherwise forwards it to the viewClients panel
	public void checksSearchEntryC(final Application application) {
		if (wClients.size() == 0) {
			if (showAllCommand) {
				new ErrorFrame();
			}
			else {
				new ErrorFrame(keyword);
			}
		}
		else {
			displayClients(application);
			companymain.getCl().show(companymain.getCards(), "viewClients");
		}
	}
	
	// display the clients
	
	// shows table of wClients
	public void displayClients(Application application) {
		
		// removes all previous components of viewClients
		viewClients.removeAll();
		DefaultTableModel tableModel = new DefaultTableModel();
		JLabel label = new JLabel("All Clients");
		viewClients.add(label, BorderLayout.NORTH);
		JTable table = new JTable(tableModel);
		// column names of the table
		String[] columnNames = {
				"Company",
                "Ref. Person",
                "e-mail",
                "address", 
                "Client id",
                "Active containers"
                };
		
		for (String s : columnNames) {
			tableModel.addColumn(s);
		}
		
		// goes through each client and inserts the corresponding information into the row
		for (Client c : wClients) {
			ArrayList<String> containerids = new ArrayList<String>();
			ArrayList<Container> containers = application.findContainer(c.getCompany(), application.getJourneyContainerDat().getActiveJourneys());
			for (Container x : containers) {
				containerids.add(x.getContainerId());
			}
		
			tableModel.insertRow(0, new Object[] {c.getCompany(),c.getName(),c.getEmail(),c.getAddress(), c.getId(), containerids});
		}
		viewClients.add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	
	public JPanel getViewClients() {
		return viewClients;
	}

	public JPanel getClientSearch() {
		return clientSearch;
	}

	// udpates client after being notified from the application class that a change has occured
	public void propertyChange(PropertyChangeEvent evt) {

		Application dat = ((Application)evt.getSource());
		if (wClients.size()!= 0) {
			if (evt.getPropertyName().contentEquals("clients")) {
				showAllOrSearch(dat);
			}
			displayClients(dat);
			companymain.getMain1().revalidate();
		}
	}
	
	// filters clients depending on which button has been pressed previously
	public void showAllOrSearch(Application dat) {
		if (showAllCommand) {
			wClients = new ArrayList<Client>(dat.getClientDat().getClients());
		}
		else {
			wClients = new ArrayList<Client>(dat.searchClient(keyword));
		}
	}
}