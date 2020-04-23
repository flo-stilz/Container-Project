import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ClientSectionPanels {

	private JPanel clientSearch;
	private JPanel viewClients;
	private ArrayList<client> wClients;
	
	
	public ClientSectionPanels(final Database database, final CompanyMain companymain) {
		
		
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
				String keyword = search.getText();
				ArrayList<client> result = new ArrayList<client>();
				result.addAll(database.search(keyword));
				wClients = result;
				displayclients(companymain, database);
			}
		});
		
		JButton showAll = new JButton("Show All");
		clientSearch.add(showAll);
		showAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				wClients = database.getClients();
				displayclients(companymain, database);
			}
		});

	}
	
	// display the clients
	
	public void displayclients(CompanyMain companymain, Database database) {
		
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
		
		
		for (client c : wClients) {
			ArrayList<String> containerids = new ArrayList<String>();
			ArrayList<Container> containers = database.findContainer(c.getCompany(), database.getJourney());
			for (Container x : containers) {
				containerids.add(x.getContainerId());
			}
		
			tableModel.insertRow(0, new Object[] {c.getCompany(),c.getName(),c.getEmail(),c.getAddress(), c.getId(), containerids});
		}
		viewClients.add(new JScrollPane(table), BorderLayout.NORTH);
		companymain.getCl().show(companymain.getCards(), "viewClients");
	}
	
	
	public JPanel getViewClients() {
		return viewClients;
	}

	public JPanel getClientSearch() {
		return clientSearch;
	}
}
