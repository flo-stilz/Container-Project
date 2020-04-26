import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class JourneySectionPanels implements PropertyChangeListener {

	private JPanel journeySearch;
	private JPanel viewJourneys;
	private ArrayList<Journey> wJourneys = new ArrayList<Journey>();
	private boolean showAllCommand;
	private Database database;
	private TopMain topmain;
	private String keyword;
	
	public ArrayList<Journey> getwJourneys() {
		return wJourneys;
	}

	public JPanel getJourneySearch() {
		return journeySearch;
	}

	public JPanel getViewJourneys() {
		return viewJourneys;
	}

	public JourneySectionPanels(final Database database, final TopMain topmain) {
		
		this.database = database;
		this.topmain = topmain;
		
		journeySearch = new JPanel();
		journeySearch.setPreferredSize(new Dimension(800, 600));
		
		viewJourneys = new JPanel(new BorderLayout());
		viewJourneys.setPreferredSize(new Dimension(800, 600));
			
		searchActiveJourneys(database, topmain);
			
		showAllActiveJourneys(database, topmain);
			
		journeyPastSearch(database, topmain);
			
		showAllPast(database, topmain);	
	}
	
	public ArrayList<Journey> filterActiveJourneysForClient(final Database database, final TopMain topmain) {
		if (topmain instanceof ClientMain) {
			Set<Journey> clientJourneys = database.findClientJourneys(topmain.getUserText(),database.getJourney());
			ArrayList<Journey> result = new ArrayList<Journey>();
			result.addAll(clientJourneys);
			return result;
		}
		else {
			return database.getJourney();
		}
		
	}

	public void showAllPast(final Database database, final TopMain topmain) {
		JButton showAllPast = new JButton("Show All");
		journeySearch.add(showAllPast);
		showAllCommand = true;
		showAllPast.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ArrayList<Journey> result = filterPastJourneysForClient(database, topmain);
				wJourneys = result;
				displayJourneys();
			}

		});
	}

	public void journeyPastSearch(final Database database, final TopMain topmain) {
		JLabel journeyPast = new JLabel("Journey's History");
		journeySearch.add(journeyPast);
		final JTextField searchjourneyPastTxt = new JTextField();
		searchjourneyPastTxt.setPreferredSize(new Dimension(100, 25));
		journeySearch.add(searchjourneyPastTxt);
		
		JButton journeyPastSearch = new JButton("Search");
		journeySearch.add(journeyPastSearch);
		showAllCommand = false;
		journeyPastSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ArrayList<Journey> result = filterPastJourneysForClient(database, topmain);
				keyword = searchjourneyPastTxt.getText();
				wJourneys = database.findUsingLoop(keyword, result);
				displayJourneys();
			}
		});
	}

	public ArrayList<Journey> filterPastJourneysForClient(Database database, TopMain topmain) {
		if (topmain instanceof ClientMain) {
			Set<Journey> clientJourneys = database.findClientJourneys(topmain.getUserText(),database.getHistory());
			ArrayList<Journey> result = new ArrayList<Journey>();
			result.addAll(clientJourneys);
			return result;
		}
		else {
			return database.getHistory();
		}
	}

	public void showAllActiveJourneys(final Database database, final TopMain topmain) {
		
		JButton showAllActive = new JButton("Show All");
		journeySearch.add(showAllActive);
		showAllCommand = true;
		showAllActive.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ArrayList<Journey> result = filterActiveJourneysForClient(database, topmain);
				wJourneys = result;
				displayJourneys();
			}

		});
	}

	public void searchActiveJourneys(final Database database, final TopMain topmain) {
		
		JLabel journeyActive = new JLabel("Active Journeys");
		final JTextField searchActive = new JTextField();
		searchActive.setPreferredSize(new Dimension(100, 25));
		journeySearch.add(journeyActive);
		journeySearch.add(searchActive);
		
		JButton searchActiveButton = new JButton("Search");
		journeySearch.add(searchActiveButton);
		showAllCommand = false;
		
		searchActiveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ArrayList<Journey> result = filterActiveJourneysForClient(database, topmain);
				keyword = searchActive.getText();
				database.findUsingLoop(keyword, result);
				wJourneys = database.findUsingLoop(keyword, result);;
				displayJourneys();
			}
		});
	}
	
	// Change current location of a journey
	
	public void changeloc(final Database database) {
		
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
				result.addAll(database.findUsingLoop(id.getSelectedItem().toString(), database.getJourney()));
				Journey j = result.get(0);
				j.updateCurrentLocation(newcurrentLocation);
			}
		});
		viewJourneys.add(update, BorderLayout.SOUTH);
	}
	
	public void signUpGoods(final Database database, final TopMain topmain) {
		
		JPanel signUp = new JPanel(new BorderLayout());
		viewJourneys.add(signUp, BorderLayout.CENTER);
		String[] options = new String[database.getJourney().size()];
		int i = 0;
		for (Journey j : database.getJourney()) {
			options[i] = j.getId();
			i++;
		}
		JPanel inputs = new JPanel();
		inputs.setLayout(new BoxLayout(inputs, BoxLayout.Y_AXIS));
		signUp.add(inputs, BorderLayout.CENTER);
		
		final JTextField content = new JTextField();
		content.setPreferredSize(new Dimension(100, 25));
		inputs.add(new JLabel("Input content: "));
		inputs.add(content);
		
		final JTextField origin = new JTextField();
		origin.setPreferredSize(new Dimension(100, 25));
		inputs.add(new JLabel("Input origin: "));
		inputs.add(origin);
		
		final JTextField destination = new JTextField();
		destination.setPreferredSize(new Dimension(100, 25));
		inputs.add(new JLabel("Input destination: "));
		inputs.add(destination);
		
		
		JButton confirm = new JButton("Confirm new journey");
		confirm.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String newContent = content.getText();
				String newOrigin = origin.getText();
				String newDestination = destination.getText();
				database.createJourney(newOrigin, newDestination, newContent, topmain.getUserText());
			}
		});
		viewJourneys.add(confirm, BorderLayout.SOUTH);
	}
	
	// view Journeys
	
	public void displayJourneys() {
		viewJourneys.removeAll();
		if (topmain instanceof CompanyMain) {
			changeloc(database);
		}
		else if (topmain instanceof ClientMain) {
			signUpGoods(database, topmain);
		}
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
			ArrayList<String> containerids = filterClientContainers(database, topmain, j);
			tableModel.insertRow(0, new Object[] {j.getId(),j.getOrigin(),j.getDestination(),j.getCurrentLocation(), containerids});
		}
		viewJourneys.add(new JScrollPane(table), BorderLayout.NORTH);
		topmain.getCl().show(topmain.getCards(), "viewJourneys");
	}
	
	public ArrayList<String> filterClientContainers(Database database, TopMain topmain, Journey j){
		ArrayList<String> containerids = new ArrayList<String>();
		if ( topmain instanceof CompanyMain) {
			for (Container c : j.getContainerList()) {
				containerids.add(c.getContainerId());
			}
		}
		
		else if ( topmain instanceof ClientMain) {
			for (Container c : j.getContainerList()) {
				if (c.getCompany().contentEquals(topmain.getUserText())) {
				containerids.add(c.getContainerId());
				}
			}
		}
		return containerids;
	}
	
	public boolean checkJourneyListForPast(Database database) {
		String journeyid = wJourneys.get(0).getId();
		return (database.findUsingLoop(journeyid, database.getJourney()).size() == 0);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		Database dat = ((Database)evt.getSource());
		if (wJourneys.size()!= 0) {
			if ((checkJourneyListForPast(dat) && (evt.getPropertyName().contentEquals("history")))) {
				
				ArrayList<Journey> jList = filterPastJourneysForClient(dat, topmain);
				showAllOrSearch(jList, dat);
			}
			else if (checkJourneyListForPast(dat) == false && (evt.getPropertyName().contentEquals("journey"))) {
				ArrayList<Journey> jList = filterActiveJourneysForClient(dat, topmain);
				showAllOrSearch(jList, dat);
			}
			displayJourneys();
			topmain.getMain1().revalidate();
		}
	}

	public void showAllOrSearch(ArrayList<Journey> jList, Database dat) {
		if (showAllCommand) {
			wJourneys = jList;
		}
		else {
			wJourneys = dat.findUsingLoop(keyword,jList);
		}
	}
	
}
