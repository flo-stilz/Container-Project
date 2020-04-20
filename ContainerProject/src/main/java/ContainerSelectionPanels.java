import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ContainerSelectionPanels {

	private JPanel containerSearch;
	private JPanel viewContainers;
	private ArrayList<Journey> wJourneys;
	private ArrayList<Container> wContainers;
	
	public JPanel getContainerSearch() {
		return containerSearch;
	}

	public JPanel getViewContainers() {
		return viewContainers;
	}

	public ContainerSelectionPanels(final Database database, final CompanyMain companymain) {
		
		containerSearch = new JPanel();
		containerSearch.setPreferredSize(new Dimension(800, 600));
		
		viewContainers = new JPanel(new BorderLayout());
		viewContainers.setPreferredSize(new Dimension(800, 600));
		
		// Search Containers
		
		// Container History
		
		JLabel containerpast = new JLabel("Container's History");
		containerSearch.add(containerpast);
		final JTextField searchContainerevol = new JTextField();
		searchContainerevol.setPreferredSize(new Dimension(100, 25));
		containerSearch.add(searchContainerevol);
		
		JButton search = new JButton("Search");
		containerSearch.add(search);
		search.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String keyword = searchContainerevol.getText();
				wJourneys = database.containerJourneyHistory(keyword);
				displayJourneys(database, keyword, companymain);
			}
		});
		
		// Filter among active Containers
		
		JLabel activecontainer = new JLabel("Active Container");
		containerSearch.add(activecontainer);
		final JTextField searchActiveContainer = new JTextField();
		searchActiveContainer.setPreferredSize(new Dimension(100, 25));
		containerSearch.add(searchActiveContainer);
		
		JButton search2 = new JButton("Search");
		containerSearch.add(search2);
		search2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String keyword = searchActiveContainer.getText();
				wContainers = database.findContainer(keyword);
				displayContainers(database, keyword, companymain);
			}
		});
		
	}
	
	public void displayJourneys(Database database, String keyword, CompanyMain companymain) {
		
		viewContainers.removeAll();
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		String[] columnNames = {
				"Journey ID",
                "Origin",
                "Destination",
                "cur. Location",
                "company",
                "content",
                "temp. Data",
                "pressure Data",
                "hum. Data"
                };
		
		for (String s : columnNames) {
			tableModel.addColumn(s);
		}

		for (Journey j : wJourneys) {

			tableModel.insertRow(0, new Object[] {j.getId(),j.getOrigin(),j.getDestination(),j.getCurrentLocation(), j.findContainers(keyword).get(0).getCompany(), j.findContainers(keyword).get(0).getContent(), j.findContainers(keyword).get(0).getTempList(), j.findContainers(keyword).get(0).getPressureList(), j.findContainers(keyword).get(0).getHumList()});
		}
		viewContainers.add(new JScrollPane(table), BorderLayout.NORTH);
		companymain.getCl().show(companymain.getCards(), "viewContainers");
	}
	
	public void displayContainers(Database database, String keyword, CompanyMain companymain) {
		
		viewContainers.removeAll();
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		String[] columnNames = {
				"Container ID",
                "company",
                "content",
                "cur. Location",
                "cur. Temp",
                "cur. Pressure",
                "cure. Hum",
                "temp. Data",
                "pressure Data",
                "hum. Data"
                };
		
		for (String s : columnNames) {
			tableModel.addColumn(s);
		}
		for (Container c : wContainers) {
			
			tableModel.insertRow(0, new Object[] {c.getContainerId(), c.getCompany(), c.getContent(), c.getCurrentLocation(), c.getTempList().get(c.getTempList().size()-1), c.getPressureList().get(c.getPressureList().size()-1), c.getHumList().get(c.getHumList().size()-1), c.getTempList(), c.getPressureList(), c.getHumList()});
		}
		viewContainers.add(new JScrollPane(table), BorderLayout.NORTH);
		companymain.getCl().show(companymain.getCards(), "viewContainers");
	}
}
