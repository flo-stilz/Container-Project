package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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

import model.Container;
import model.Application;
import model.Journey;

public class JourneySectionPanels implements PropertyChangeListener {

	// panels for search and view of the journeys
	private JPanel journeySearch;
	private JPanel viewJourneys;
	// storage of the matching Journeys
	private ArrayList<Journey> wJourneys = new ArrayList<Journey>();
	// indicates which button has been pressed
	private boolean showAllCommand;
	private Application application;
	private TopMain topmain;
	// input in the search text field
	private String keyword;
	// indicates whether the past buttons has been pressed or the active ones
	private boolean isPast;
	
	public ArrayList<Journey> getwJourneys() {
		return wJourneys;
	}

	public JPanel getJourneySearch() {
		return journeySearch;
	}

	public JPanel getViewJourneys() {
		return viewJourneys;
	}

	public JourneySectionPanels(final Application application, final TopMain topmain) {
		
		this.application = application;
		this.topmain = topmain;
		
		// Initialisation of the different panels
		journeySearch = new JPanel();
		journeySearch.setPreferredSize(new Dimension(800, 600));
		
		viewJourneys = new JPanel(new BorderLayout());
		viewJourneys.setPreferredSize(new Dimension(800, 600));
			
		// different search possibilities in the journeySearch panel
		searchActiveJourneys(application, topmain);
			
		showAll(application, topmain, false);
			
		searchJourneys(application, topmain, true);
			
		showAll(application, topmain, true);	
		
		// depending on the user also gives the option to sign up goods
		if (topmain instanceof ClientMain) {
			signUpGoods(application, topmain);
		}
	}
	// sets wJourneys either to the past or to the active container list
	// past if input b was true
	public void showAll(final Application application, final TopMain topmain, final boolean b) {
		JButton showAll = new JButton("Show All");
		journeySearch.add(showAll);
		showAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Journey> journeys = new ArrayList<Journey>();
				if (b) {
					journeys = application.getJourneyContainerDat().getPastJourneys();
				}
				else {
					journeys = application.getJourneyContainerDat().getActiveJourneys();	
				}
				showAllCommand = true;
				isPast = b;
				ArrayList<Journey> result = filterJourneysForClients(application, topmain, journeys);
				if (topmain instanceof ClientMain) {
					wJourneys = result;
				}
				else {
					wJourneys = result;
				}
				checksSearchEntryC(application, topmain);
			}
		});
	}
	// checks whether there was a match for the search and if not outputs an error frame depending on the button pressed in search
	// if wJourneys is not empty it will fast forward it to the display of the journeys and switches to the viewJourneys panel
	public void checksSearchEntryC(final Application application, final TopMain topmain) {
		if (wJourneys.size() == 0) {
			if (showAllCommand) {
				new ErrorFrame();
			}
			else {
				new ErrorFrame(keyword);
			}
		}
		else {
			displayJourneys();
			topmain.getCl().show(topmain.getCards(), "viewJourneys");
		}
	}
	
	// filters the journey list depending on the user
	public ArrayList<Journey> filterJourneysForClients(final Application application, final TopMain topmain, ArrayList<Journey> unfiltered) {
		if (topmain instanceof ClientMain) {
			ArrayList<Journey> result = new ArrayList<Journey>();
			result.addAll(application.findClientJourneys(unfiltered));
			return result;
		}
		else {
			return unfiltered;
		}
	}

	// filters the past journeys matching the keyword
	public void searchJourneys(final Application application, final TopMain topmain, final boolean b) {
		
		String type;
		if (b) {
			type = "Past";
		}
		else {
			type = "Active";
		}
		JLabel searchlbl = new JLabel(type + " Journeys");
		journeySearch.add(searchlbl);
		final JTextField searchjourneyTxt = new JTextField();
		searchjourneyTxt.setPreferredSize(new Dimension(100, 25));
		journeySearch.add(searchjourneyTxt);
		
		JButton searchButton = new JButton("Search");
		journeySearch.add(searchButton);
		// sets the wJourneys to the result of the filtering process
		searchButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				isPast = b;
				showAllCommand = false;
				
				ArrayList<Journey> jList;
				if (b) {
					jList = application.getJourneyContainerDat().getPastJourneys();
				}
				else {
					jList = application.getJourneyContainerDat().getActiveJourneys();
				}
				
				ArrayList<Journey> result = new ArrayList<Journey>(filterJourneysForClients(application, topmain, jList));
				keyword = searchjourneyTxt.getText();
				wJourneys = application.findJourneys(keyword, result);
				checksSearchEntryJ(application, topmain);
			}
		});
	}

	// sets wJourneys equal to the result filtering process of the active journey list
	public void searchActiveJourneys(final Application application, final TopMain topmain) {
		
		JLabel journeyActive = new JLabel("Active Journeys");
		final JTextField searchActive = new JTextField();
		searchActive.setPreferredSize(new Dimension(100, 25));
		journeySearch.add(journeyActive);
		journeySearch.add(searchActive);
		
		JButton searchActiveButton = new JButton("Search");
		journeySearch.add(searchActiveButton);
		
		searchActiveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				isPast = false;
				showAllCommand = false;
				ArrayList<Journey> result = new ArrayList<Journey>(filterJourneysForClients(application, topmain, application.getJourneyContainerDat().getActiveJourneys()));
				keyword = searchActive.getText();
				application.findJourneys(keyword, result);
				wJourneys = application.findJourneys(keyword, result);;
				checksSearchEntryJ(application, topmain);
			}
		});
	}
	
	// Change current location of a journey
	
	public void changeloc(final Application application) {
		
		JPanel locationUpdate = new JPanel(new BorderLayout());
		viewJourneys.add(locationUpdate, BorderLayout.SOUTH);
		JPanel updateLocation = new JPanel(new BorderLayout());
		locationUpdate.add(updateLocation, BorderLayout.CENTER);
		// sums up all active journey id's in another list
		String[] options = new String[application.getJourneyContainerDat().getActiveJourneys().size()];
		int i = 0;
		for (Journey j : application.getJourneyContainerDat().getActiveJourneys()) {
			options[i] = j.getId();
			i++;
		}
		// creates combo box with the the options given above
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
				// filters for the journey which was selected in the combo box id
				result.addAll(application.findJourneys(id.getSelectedItem().toString(), application.getJourneyContainerDat().getActiveJourneys()));
				Journey j = result.get(0);
				// changes the actual location of the journeyin the model
				application.updateCurrentLocation(j, newcurrentLocation);
				// also checks whether the specific journey has ended
				application.endOfJourney(j);
			}
		});
		locationUpdate.add(update, BorderLayout.SOUTH);
	}
	
	// gives the client the option to sign up new goods
	public void signUpGoods(final Application application, final TopMain topmain) {
		
		JPanel journeySearchRest = new JPanel(new BorderLayout());
		journeySearch.add(journeySearchRest);
		// preferred would be a picture instead of space!!!
		JPanel space = new JPanel();
		space.setPreferredSize(new Dimension(350,320));
		journeySearchRest.add(space, BorderLayout.NORTH);
		JPanel signUp = new JPanel(new BorderLayout());
		journeySearchRest.add(signUp, BorderLayout.CENTER);
		JLabel lbl = new JLabel("Sign up your goods for a new journey here!");
		lbl.setPreferredSize(new Dimension(100,70));
		signUp.add(lbl, BorderLayout.NORTH);
//		String[] options = new String[application.getJourneyContainerDat().getActiveJourneys().size()];
//		int i = 0;
//		for (Journey j : application.getJourneyContainerDat().getActiveJourneys()) {
//			options[i] = j.getId();
//			i++;
//		}
		JPanel inputs = new JPanel();
		inputs.setLayout(new BoxLayout(inputs, BoxLayout.Y_AXIS));
		signUp.add(inputs, BorderLayout.CENTER);
		
		// text fields to enter the necessary information for the sign up
		
		// container content
		final JTextField content = new JTextField();
		content.setPreferredSize(new Dimension(100, 25));
		inputs.add(new JLabel("Input content: "));
		inputs.add(content);
		
		// journey origin
		final JTextField origin = new JTextField();
		origin.setPreferredSize(new Dimension(100, 25));
		inputs.add(new JLabel("Input origin: "));
		inputs.add(origin);
		
		// journey destination
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
				// actual sign up of the container
				application.createJourney(newOrigin, newDestination, newContent, application.getCurrentUser().getCompany());
			}
		});
		signUp.add(confirm, BorderLayout.SOUTH);
	}
	
	// checks whether wJourneys is empty and if so creates an error frame otherwise switches the panel to viewJourneys
	public void checksSearchEntryJ(final Application application, final TopMain topmain) {
		if (wJourneys.size() == 0) {
			if (showAllCommand) {
				new ErrorFrame();
			}
			else {
				new ErrorFrame(keyword);
			}
		}
		else {
			displayJourneys();
			topmain.getCl().show(topmain.getCards(), "viewJourneys");
		}
	}
	
	// view Journeys
	// creates the table with the information given in wJourneys
	public void displayJourneys() {
		viewJourneys.removeAll();
		// gives the option to change the location if the current user is the admin
		if (topmain instanceof CompanyMain) {
			changeloc(application);
		}
		
		DefaultTableModel tableModel = new DefaultTableModel();
		setTableLabel();
		JTable table = new JTable(tableModel);
		// column names of the table
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
		// add the needed information for each journey into the rows of the table
		for (Journey j : wJourneys) {
			ArrayList<String> containerids = filterClientContainers(application, topmain, j);
			tableModel.insertRow(0, new Object[] {j.getId(),j.getOrigin(),j.getDestination(),j.getCurrentLocation(), containerids});
		}
		viewJourneys.add(new JScrollPane(table), BorderLayout.CENTER);
	}
	// sets the labels depending on which button has been chosen in the journeySearch panel
	// also makes use of the keyword if the search button has been chosen
	public void setTableLabel() {
		JLabel label = new JLabel("");
		if (isPast == false && showAllCommand) {
			label = new JLabel("All active containers");
			Font f = label.getFont();
			label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
		}
		else if ( isPast && showAllCommand) {
			label = new JLabel("All past containers");
			Font f = label.getFont();
			label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
		}
		else if (isPast == false && showAllCommand == false) {
			label = new JLabel("Active containers related to " + keyword);
			Font f = label.getFont();
			label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
		}
		else if (isPast && showAllCommand == false) {
			label = new JLabel("Past containers related to " + keyword);
			Font f = label.getFont();
			label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
		}
		viewJourneys.add(label, BorderLayout.NORTH);
	}
	
	// filters the containerids to the corresponding journey in order to display it on the table
	public ArrayList<String> filterClientContainers(Application application, TopMain topmain, Journey j){
		ArrayList<String> containerids = new ArrayList<String>();
		if ( topmain instanceof CompanyMain) {
			for (Container c : j.getContainers()) {
				containerids.add(c.getContainerId());
			}
		}
		
		else if ( topmain instanceof ClientMain) {
			for (Container c : j.getContainers()) {
				if (c.getCompany().contentEquals(application.getCurrentUser().getCompany())) {
				containerids.add(c.getContainerId());
				}
			}
		}
		return containerids;
	}

	// updates wJourneys after the notification from the application class due to the observer pattern
	public void propertyChange(PropertyChangeEvent evt) {

		Application dat = ((Application)evt.getSource());
		if (wJourneys.size()!= 0) {
			if ((isPast && (evt.getPropertyName().contentEquals("history")))) {
				
				ArrayList<Journey> jList = new ArrayList<Journey>(filterJourneysForClients(dat, topmain, dat.getJourneyContainerDat().getPastJourneys()));
				showAllOrSearch(jList, dat);
			}
			else if (isPast == false && (evt.getPropertyName().contentEquals("journey"))) {
				ArrayList<Journey> jList = new ArrayList<Journey>(filterJourneysForClients(dat, topmain, dat.getJourneyContainerDat().getActiveJourneys()));
				showAllOrSearch(jList, dat);
			}
			displayJourneys();
			topmain.getMain1().revalidate();
		}
	}
	// filters the journeys depending on the command button pressed in the journeySearch panel
	public void showAllOrSearch(ArrayList<Journey> jList, Application dat) {
		if (showAllCommand) {
			wJourneys = jList;
		}
		else {
			wJourneys = dat.findJourneys(keyword,jList);
		}
	}
	
}