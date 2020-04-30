package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Container;
import model.Application;
import model.Client;


public class ClientSectionPanels implements PropertyChangeListener {

	private JPanel clientSearch;
	private JPanel viewClients;
	private ArrayList<Client> wClients = new ArrayList<Client>();
	private boolean showAllCommand;
	private String keyword;
	private CompanyMain companymain;
	
	public ArrayList<Client> getwClients() {;
		return wClients;
	}

	public ClientSectionPanels(final Application application, final CompanyMain companymain) {
		
		this.companymain = companymain;
		// Search the clients
		
		clientSearch = new JPanel();
		clientSearch.setPreferredSize(new Dimension(800, 600));
		
		viewClients = new JPanel(new BorderLayout());
		viewClients.setPreferredSize(new Dimension(800, 600));
		
		final JTextField search = new JTextField();
		search.setPreferredSize(new Dimension(100, 25));
		clientSearch.add(search);
		
		JButton searchButton = new JButton("Search");
		clientSearch.add(searchButton);
		
		searchButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				keyword = search.getText();
				showAllCommand = false;
				wClients = new ArrayList<Client>(application.search(keyword));
				checksSearchEntryC(application);
			}
		});
		
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
	
	public void displayClients(Application application) {
		
		viewClients.removeAll();
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
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
		
		
		for (Client c : wClients) {
			ArrayList<String> containerids = new ArrayList<String>();
			ArrayList<Container> containers = application.findContainer(c.getCompany(), application.getJourneyContainerDat().getActiveJourneys());
			for (Container x : containers) {
				containerids.add(x.getContainerId());
			}
		
			tableModel.insertRow(0, new Object[] {c.getCompany(),c.getName(),c.getEmail(),c.getAddress(), c.getId(), containerids});
		}
		viewClients.add(new JScrollPane(table), BorderLayout.NORTH);
	}
	
	
	public JPanel getViewClients() {
		return viewClients;
	}

	public JPanel getClientSearch() {
		return clientSearch;
	}

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
	
	public void showAllOrSearch(Application dat) {
		if (showAllCommand) {
			wClients = new ArrayList<Client>(dat.getClientDat().getClients());
		}
		else {
			wClients = new ArrayList<Client>(dat.search(keyword));
		}
	}
}