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
		
		viewClients = new JPanel();
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
				displayclients(companymain);
			}
		});
		
		JButton showAll = new JButton("Show All");
		clientSearch.add(showAll);
		showAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				wClients = database.getClients();
				displayclients(companymain);
			}
		});

	}
	
	// display the clients
	
	public void displayclients(CompanyMain companymain) {
		
		viewClients.removeAll();
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		String[] columnNames = {
				"Company",
                "Ref. Person",
                "e-mail",
                "address"
                };
		
		for (String s : columnNames) {
			tableModel.addColumn(s);
		}
		
		for (client c : wClients) {
			tableModel.insertRow(0, new Object[] {c.getCompany(),c.getName(),c.getEmail(),c.getAddress()});
		}
		viewClients.add(new JScrollPane(table), BorderLayout.CENTER);
		companymain.getCl().show(companymain.getCards(), "viewClients");
	}
	
	
	public JPanel getViewClients() {
		return viewClients;
	}

	public JPanel getClientSearch() {
		return clientSearch;
	}
}
