import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class JourneySectionPanels {

	private JPanel journeySearch;
	private JPanel viewJourneys;
	private ArrayList<Journey> wJourneys;
	
	public JPanel getJourneySearch() {
		return journeySearch;
	}

	public JPanel getViewJourneys() {
		return viewJourneys;
	}

	public JourneySectionPanels(final Database database, final CompanyMain companymain) {
		
		// Search Journeys
		
		journeySearch = new JPanel();
		journeySearch.setPreferredSize(new Dimension(800, 600));
		
		viewJourneys = new JPanel(new BorderLayout());
		viewJourneys.setPreferredSize(new Dimension(800, 600));
		
		
		final JTextField search = new JTextField();
		search.setPreferredSize(new Dimension(100, 25));
		journeySearch.add(search);
		
		JButton searchButton = new JButton("Search");
		journeySearch.add(searchButton);
		
		searchButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String keyword = search.getText();
				ArrayList<Journey> result = new ArrayList<Journey>();
				result.addAll(database.findUsingLoop(keyword));
				wJourneys = result;
				displayJourneys(companymain, database);
			}
		});
		
		JButton showAll = new JButton("Show All");
		journeySearch.add(showAll);
		showAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				wJourneys = database.getJourney();
				displayJourneys(companymain, database);
			}

		});
		
	}
	
	private void changeloc(final Database database) {
		
		JPanel updateLocation = new JPanel(new BorderLayout());
		viewJourneys.add(updateLocation, BorderLayout.CENTER);
		String[] options = new String[database.getJourney().size()];
		int i = 0;
		for (Journey j : database.getJourney()) {
			options[i] = j.getId();
			i++;
		}
		final JComboBox<String> id = new JComboBox<String>(options);
		updateLocation.add(id, BorderLayout.NORTH);
		
		final JTextField loc = new JTextField();
		loc.setPreferredSize(new Dimension(100, 25));
		updateLocation.add(loc, BorderLayout.CENTER);
		
		JButton update = new JButton("Update Location");
		update.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String newcurrentLocation = loc.getText();
				ArrayList<Journey> result = new ArrayList<Journey>();
				result.addAll(database.findUsingLoop(id.getSelectedItem().toString()));
				Journey j = result.get(0);
				j.updateCurrentLocation(newcurrentLocation);
			}
		});
		viewJourneys.add(update, BorderLayout.SOUTH);
	}
	
	private void displayJourneys(CompanyMain companymain, Database database) {
		viewJourneys.removeAll();
		
		changeloc(database);
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		String[] columnNames = {
				"ID",
                "Origin",
                "Destination",
                "cur. Location",
                "Container ID's"
                };
		
		for (String s : columnNames) {
			tableModel.addColumn(s);
		}
		
		for (Journey j : wJourneys) {
			tableModel.insertRow(0, new Object[] {j.getId(),j.getOrigin(),j.getDestination(),j.getCurrentLocation(), j.getContainerList().toString()});
		}
		viewJourneys.add(new JScrollPane(table), BorderLayout.NORTH);
		companymain.getCl().show(companymain.getCards(), "viewJourneys");
	}
}
