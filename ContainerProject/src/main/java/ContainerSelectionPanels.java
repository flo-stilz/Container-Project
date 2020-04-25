import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

// Was added:
// SearchContainerEntryC
// SearchContainerEntryJ


public class ContainerSelectionPanels {

	private JPanel containerSearch;
	private JPanel viewContainers;
	private ArrayList<Journey> wJourneys;
	private ArrayList<Container> wContainers;
	private String keyword;
	private JPanel extraOptions;
	private JPanel plotPanel;
	private ContainerSelectionPanels csp;
	private barPlots bPlot;
	private comparisonlinePlots cPlot;
	private JCheckBox checkBoxTemp;
	private JCheckBox checkBoxPres;
	private JCheckBox checkBoxHum;
	private JCheckBox checkBoxAllinOne;
	private JCheckBox checkBoxBarPlot;
	private plot tempPlot;
	private plot presPlot;
	private plot humPlot;
	
	public JPanel getPlotPanel() {
		return plotPanel;
	}

	public JPanel getContainerSearch() {
		return containerSearch;
	}

	public JPanel getViewContainers() {
		return viewContainers;
	}
	private JFrame main1;

	public ContainerSelectionPanels(final Database database, final TopMain topmain) {
		
		this.main1 = main1;
		containerSearch = new JPanel();
		containerSearch.setPreferredSize(new Dimension(800, 600));
		
		viewContainers = new JPanel(new BorderLayout());
		viewContainers.setPreferredSize(new Dimension(800, 600));
		
		plotPanel = new JPanel(new GridLayout(0,2));
		plotPanel.setPreferredSize(new Dimension(800, 600));
		
		csp = this;
		
		// Search Containers
		
		// Container History
		
		searchContainerPast(database, topmain);
		
		// Filter among past Containers
		
		searchForItemsInContainerPast(database, topmain);
		
		// Filter among active Containers
		
		searchActiveContainers(database, topmain);
		
		// Show all Containers at the current status
		
		showAllActiveContainers(database, topmain);
		
	}

	public void showAllActiveContainers(final Database database, final TopMain topmain) {
		JButton showAll = new JButton("Show All");
		containerSearch.add(showAll);
		showAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ArrayList<Journey> result = filterActiveContainersforClients(database, topmain);
				wContainers = database.getAllContainers();
				checksSearchEntryC(database, topmain);
			}
		});
	}

	public void searchActiveContainers(final Database database, final TopMain topmain) {
		JLabel activecontainer = new JLabel("Active Container");
		containerSearch.add(activecontainer);
		final JTextField searchActiveContainer = new JTextField();
		searchActiveContainer.setPreferredSize(new Dimension(100, 25));
		containerSearch.add(searchActiveContainer);
		
		JButton search2 = new JButton("Search");
		containerSearch.add(search2);
		search2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				keyword = searchActiveContainer.getText();
				ArrayList<Journey> result = filterActiveContainersforClients(database, topmain);
				
				wContainers = database.findContainer(keyword, result);
				checksSearchEntryC(database, topmain);
			}

		});
	}
	
	public void checksSearchEntryC(final Database database, final TopMain topmain) {
		if (wContainers.size() == 0) {
			// Window created
		}
		else {
			displayContainers(database, topmain);
		}
	}
	
	public void checksSearchEntryJ(final Database database, final TopMain topmain) {
		if (wJourneys.size() == 0) {
			// Window created
		}
		else {
			displayJourneys(database, topmain);
		}
	}
	
	public ArrayList<Journey> filterActiveContainersforClients(final Database database, final TopMain topmain) {
		if (topmain instanceof ClientMain) {
			ArrayList<Journey> result = new ArrayList<Journey>();
			result.addAll(database.findClientJourneys(topmain.getUserText(), database.getJourney()));
			return result;
		}
		else {
			return database.getJourney();
		}
	}

	public void searchContainerPast(final Database database, final TopMain topmain) {
		JLabel containerpast = new JLabel("Container's History");
		containerSearch.add(containerpast);
		final JTextField searchContainerevol = new JTextField();
		searchContainerevol.setPreferredSize(new Dimension(100, 25));
		containerSearch.add(searchContainerevol);
		
		JButton search = new JButton("Search");
		containerSearch.add(search);
		search.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				keyword = searchContainerevol.getText();
				ArrayList<Journey> results = new ArrayList<Journey>();
				results.addAll(database.findJourneysFromContainers(keyword));
				ArrayList<Journey> result = filterJourneysForClients(database, topmain, results);
				wJourneys = result;
				checksSearchEntryJ(database, topmain);
			}
		});
	}
	
	public ArrayList<Journey> filterJourneysForClients(final Database database, final TopMain topmain, ArrayList<Journey> unfiltered) {
		if (topmain instanceof ClientMain) {
			ArrayList<Journey> result = new ArrayList<Journey>();
			result.addAll(database.findClientJourneys(topmain.getUserText(), unfiltered));
			return result;
		}
		else {
			return unfiltered;
		}
	}
	
	public void searchForItemsInContainerPast(final Database database, final TopMain topmain) {
		JLabel containeritempast = new JLabel("Filtered past containers");
		containerSearch.add(containeritempast);
		final JTextField searchPastContainers = new JTextField();
		searchPastContainers.setPreferredSize(new Dimension(100, 25));
		containerSearch.add(searchPastContainers);
		
		JButton searchPast = new JButton("Search");
		containerSearch.add(searchPast);
		searchPast.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				keyword = searchPastContainers.getText();
				ArrayList<Journey> result = filterPastContainersforClients(database, topmain);
				
				wContainers = database.findContainer(keyword, result);
				checksSearchEntryC(database, topmain);
			}
		});
	}

	public ArrayList<Journey> filterPastContainersforClients(final Database database, final TopMain topmain) {
		if (topmain instanceof ClientMain) {
			ArrayList<Journey> result = new ArrayList<Journey>();
			result.addAll(database.findClientJourneys(topmain.getUserText(), database.getHistory()));
			return result;
		}
		else {
			return database.getHistory();
		}
	}
	
	public void additionalInformation(Database database, TopMain topmain) {
		
		extraOptions = new JPanel(new BorderLayout());
		viewContainers.add(extraOptions, BorderLayout.CENTER);
		String[] options = new String[database.getActiveContainers().size()];
		int i = 0;
		for (Container c : database.getActiveContainers()) {
			options[i] = c.getContainerId();
			i++;
		}
		
		final JComboBox<String> id = new JComboBox<String>(options);
		extraOptions.add(id, BorderLayout.NORTH);
		
		if (topmain instanceof CompanyMain && checkContainerListForPastOrActive(database)) {
			updateData(database, id);
		}
		showPlots(database, id, topmain);
	}
	
	// Using the first item in the container list to check whether the containers are active
	
	public boolean checkContainerListForPastOrActive(Database database) {
		Container firstContainer = wContainers.get(0);
		String journeyid = firstContainer.getId();
		return (database.findUsingLoop(journeyid, database.getJourney()).size() == 0);
	}
	
	// Give the company the option to update current Containers measurements
	
	public void updateData(final Database database, final JComboBox<String> id) {
		
		JPanel dataUpdate = new JPanel(new BorderLayout());
		extraOptions.add(dataUpdate, BorderLayout.WEST);
		
		JPanel dataIn = new JPanel();
		dataIn.setLayout(new BoxLayout(dataIn, BoxLayout.Y_AXIS));
		dataUpdate.add(dataIn, BorderLayout.NORTH);
		
		final JTextField temp = new JTextField();
		temp.setPreferredSize(new Dimension(100, 25));;
		dataIn.add(new JLabel("New Temperature:"));
		dataIn.add(temp);
		
		final JTextField pressure = new JTextField();
		pressure.setPreferredSize(new Dimension(100, 25));
		dataIn.add(new JLabel("New Pressure:"));
		dataIn.add(pressure);
		
		final JTextField hum = new JTextField();
		hum.setPreferredSize(new Dimension(100, 25));
		dataIn.add(new JLabel("New Humidity:"));
		dataIn.add(hum);
		
		JButton update = new JButton("Update Data");
		update.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Integer newTemp = Integer.parseInt(temp.getText());
				Integer newPressure = Integer.parseInt(pressure.getText());
				Integer newHum = Integer.parseInt(hum.getText());
				ArrayList<Container> result = new ArrayList<Container>();
				result.addAll(database.findContainer(id.getSelectedItem().toString(), database.getJourney()));
				Container c = result.get(0);
				database.addData(c, newTemp, newPressure, newHum);
			}
		});
		dataUpdate.add(update, BorderLayout.SOUTH);
	}
	
	public void showPlots(final Database database, final JComboBox<String> id, final TopMain topmain) {
		
		JPanel choosePlots = new JPanel(new BorderLayout());
		extraOptions.add(choosePlots, BorderLayout.EAST);
		
		checkBoxTemp = new JCheckBox("Temperature Plot");
		checkBoxPres = new JCheckBox("Pressure Plot");
		checkBoxHum = new JCheckBox("Humidity Plot");
		checkBoxAllinOne = new JCheckBox("All in One");
		checkBoxBarPlot = new JCheckBox("Bar Plot");
		
		JPanel checkOptions = new JPanel(new GridLayout(0,2));
		checkOptions.add(checkBoxTemp);
		checkOptions.add(checkBoxPres);
		checkOptions.add(checkBoxHum);
		checkOptions.add(checkBoxAllinOne);
		checkOptions.add(checkBoxBarPlot);
		
		choosePlots.add(checkOptions, BorderLayout.CENTER);
		
		JButton show = new JButton("Show the Plots");
		choosePlots.add(show, BorderLayout.SOUTH);
		show.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				// get Container
				ArrayList<Container> result = new ArrayList<Container>();
				result.addAll(database.findContainer(id.getSelectedItem().toString(), database.getJourney()));
				Container c = result.get(0);
				
				 createPlots(database, topmain, c);
				
				 topmain.getCl().show(topmain.getCards(), "plotPanel");
			}

		});
		
	}
	
	public void createPlots(final Database database, final TopMain topmain, Container c) {
		if (checkBoxTemp.isSelected()) {
			 ArrayList<Integer> data = c.getTempList();
			 
			 tempPlot = new plot("Temperature", csp, topmain);
			 tempPlot.linePlot(data);;
			 database.addObserver(tempPlot);
//			 JPanel tempPanel = tempPlot.getChartPanel();
//			 plotPanel.add(tempPanel);
			 
			 // plot.plot(data);
			 // where plot.plot is the function that will displays the data
			 // data is the corresponding arraylist for the plot
		 }
		 if (checkBoxPres.isSelected()) {
			 ArrayList<Integer> data = c.getPressureList();
			 
			 presPlot = new plot("Pressure", csp, topmain);
			 presPlot.linePlot(data);;
			 database.addObserver(presPlot);
			 
		 }
		 if (checkBoxHum.isSelected()) {
			 ArrayList<Integer> data = c.getHumList();
			 
			 humPlot = new plot("Humidity", csp, topmain);
			 humPlot.linePlot(data);;
			 database.addObserver(humPlot);
//			 JPanel humPanel = humPlot.getChartPanel();
//			 plotPanel.add(humPanel);
			 
		 }
		 if (checkBoxAllinOne.isSelected()) {
			 ArrayList<Integer> temp = c.getTempList();
			 ArrayList<Integer> pres = c.getPressureList();
			 ArrayList<Integer> hum = c.getHumList();
			 
			 cPlot = new comparisonlinePlots("Comparison line plot", temp, pres, hum, csp, topmain);
//			 updateComparisonPlot(cPlot);
			 database.addObserver(cPlot);
			 
		 }
		 if (checkBoxBarPlot.isSelected()) {
			 ArrayList<Integer> temp = c.getTempList();
			 ArrayList<Integer> pres = c.getPressureList();
			 ArrayList<Integer> hum = c.getHumList();
			 
			 bPlot = new barPlots("Bar plot", temp, pres, hum, csp, topmain);
			 database.addObserver(bPlot);
//			 updateBarPlot(bPlot);
		 }
		 updateAllPlots(topmain);
	}

	public void updateLinePlots(plot linePlot) {
		JPanel linePanel = linePlot.getChartPanel();
		plotPanel.add(linePanel);
	}

	
	public void updateAllPlots(TopMain topmain) {
		plotPanel.removeAll();
		if (checkBoxTemp.isSelected()) {
			// updateTempPlot
			updateLinePlots(tempPlot);
		}
		if (checkBoxPres.isSelected()) {
			// updatePresPlot
			updateLinePlots(presPlot);
		}
		if (checkBoxHum.isSelected()) {
			// updateHumPlot
			updateLinePlots(humPlot);
		}
		if (checkBoxAllinOne.isSelected()) {
			updateComparisonPlot(cPlot);
		}
		if (checkBoxBarPlot.isSelected()) {
			updateBarPlot(bPlot);
		}
		topmain.getMain1().revalidate();
	}
	
	public void updateComparisonPlot(comparisonlinePlots cPlot) {
		JPanel comparisonplot = cPlot.getChartPanel();
		 plotPanel.add(comparisonplot);
	}
	
	public void updateBarPlot(barPlots bPlot) {
		JPanel barplot = bPlot.getChartPanel();
		plotPanel.add(barplot);
	}

	
	public void displayJourneys(Database database, TopMain topmain) {
		
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
		topmain.getCl().show(topmain.getCards(), "viewContainers");
	}
	
	public void displayContainers(Database database, TopMain topmain) {
		
		viewContainers.removeAll();
		additionalInformation(database, topmain);
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		String[] columnNames = {
				"Container ID",
				"Journey ID",
                "company",
                "content",
                "cur. Location",
                "cur. Temp",
                "cur. Pressure",
                "cur. Hum",
                "temp. Data",
                "pressure Data",
                "hum. Data"
                };
		
		for (String s : columnNames) {
			tableModel.addColumn(s);
		}
		for (Container c : wContainers) {
			
			formRowForContainer(tableModel, c, database); 
			
		}
		viewContainers.add(new JScrollPane(table), BorderLayout.NORTH);
		topmain.getCl().show(topmain.getCards(), "viewContainers");
	}

	public void formRowForContainer(DefaultTableModel tableModel, Container c, Database database) {
		
		// checks whether the measurement lists are empty and whether a container belongs to the active Journey list
		if ((c.isEmpty()) || (database.findUsingLoop(c.getId(),database.getJourney()).size() == 0)) {
			tableModel.insertRow(0, new Object[] {c.getContainerId(), c.getId(), c.getCompany(), c.getContent(), c.getCurrentLocation(), "N/A", "N/A", "N/A", c.getTempList(), c.getPressureList(), c.getHumList()});
		}
		else {
			tableModel.insertRow(0, new Object[] {c.getContainerId(), c.getId(), c.getCompany(), c.getContent(), c.getCurrentLocation(), c.getTempList().get(c.getTempList().size()-1), c.getPressureList().get(c.getPressureList().size()-1), c.getHumList().get(c.getHumList().size()-1), c.getTempList(), c.getPressureList(), c.getHumList()});
		}
	}
	
	public static void doesItemExist(ArrayList<Container> c) throws MyException {
			
			if (c.size() == 0) {
				throw new MyException("Nothing matches your search!");
			}
			else {
				System.out.println("All good");
			}
		}


}
