import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
// pickPastOrActiveJourneys
// checkContainerListForPast
// chooseContainerOptions
// chooseContainerForPlot
// GetAllContainerMeasurements
// showAllOrSearch
// propertyChange


public class ContainerSelectionPanels implements PropertyChangeListener{

	private JPanel containerSearch;
	private JPanel viewContainers;
	private ArrayList<Journey> wJourneys = new ArrayList<Journey>();
	private ArrayList<Container> wContainers = new ArrayList<Container>();
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
	private Database database;
	private TopMain topmain;
	private boolean showAllCommand;
	private boolean isPast;
	
	public ArrayList<Container> getwContainers() {
		return wContainers;
	}

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
		
		this.database = database;
		this.topmain = topmain;
		
		containerSearch = new JPanel();
		containerSearch.setPreferredSize(new Dimension(800, 600));
		
		viewContainers = new JPanel(new BorderLayout());
		viewContainers.setPreferredSize(new Dimension(800, 600));
		
		plotPanel = new JPanel(new GridLayout(0,2));
		plotPanel.setPreferredSize(new Dimension(800, 600));
		
		csp = this;
		
		// Search Containers
		
		// Filter among active Containers
		
		searchActiveContainers(database, topmain);
		
		// Show all Containers at the current status
		
		showAllActiveContainers(database, topmain);
		
		// Filter among past Containers
		
		searchForPastContainers(database, topmain);
		
		// Show all past Containers at their final status
		
		showAllPastContainers(database, topmain);
		
	}

	public void showAllActiveContainers(final Database database, final TopMain topmain) {
		JButton showAll = new JButton("Show All");
		containerSearch.add(showAll);
		showAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				showAllCommand = true;
				isPast = false;
				ArrayList<Journey> result = filterJourneysForClients(database, topmain, database.getJourney());
				if (topmain instanceof ClientMain) {
					wContainers = database.getAllContainers(true, result);
				}
				else {
					wContainers = database.getAllContainers(false, result);
				}
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
				showAllCommand = false;
				isPast = false;
				keyword = searchActiveContainer.getText();
				ArrayList<Journey> result = filterJourneysForClients(database, topmain, database.getJourney());
				
				wContainers = database.findContainer(keyword, result);
				checksSearchEntryC(database, topmain);
			}

		});
	}
	
	public void checksSearchEntryC(final Database database, final TopMain topmain) {
		if (wContainers.size() == 0) {
			if (showAllCommand) {
				new ErrorFrame();
			}
			else {
				new ErrorFrame(keyword);
			}
		}
		else {
			displayContainers();
			topmain.getCl().show(topmain.getCards(), "viewContainers");
		}
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
	
	public void showAllPastContainers(final Database database, final TopMain topmain) {
		JButton showAllPast = new JButton("Show All");
		containerSearch.add(showAllPast);
		showAllPast.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				showAllCommand = true;
				isPast = true;
				ArrayList<Journey> result = filterJourneysForClients(database, topmain, database.getHistory());
				wContainers = database.getAllContainers(true, result);
				checksSearchEntryC(database, topmain);
			}
		});
	}
	
	public void searchForPastContainers(final Database database, final TopMain topmain) {
		JLabel containeritempast = new JLabel("History of Containers");
		containerSearch.add(containeritempast);
		final JTextField searchPastContainers = new JTextField();
		searchPastContainers.setPreferredSize(new Dimension(100, 25));
		containerSearch.add(searchPastContainers);
		
		JButton searchPast = new JButton("Search");
		containerSearch.add(searchPast);
		searchPast.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				showAllCommand = false;
				isPast = true;
				keyword = searchPastContainers.getText();
				ArrayList<Journey> result = filterJourneysForClients(database, topmain, database.getHistory());
				
				wContainers = database.findContainer(keyword, result);
				checksSearchEntryC(database, topmain);
			}
		});
	}
	
	public void additionalInformation(Database database, TopMain topmain) {
		
		extraOptions = new JPanel(new BorderLayout());
		viewContainers.add(extraOptions, BorderLayout.CENTER);
		
		final JComboBox<String> id = chooseContainerOptions(database);
		extraOptions.add(id, BorderLayout.NORTH);
		if (topmain instanceof CompanyMain && (isPast == false)) {
			updateData(database, id);
		}
		showPlots(database, id, topmain);
	}

	public JComboBox<String> chooseContainerOptions(Database database) {
		ArrayList<Container> op;
		if (isPast) {
			ArrayList<Journey> filtered = new ArrayList<Journey>(filterJourneysForClients(database,topmain, database.getHistory()));
			if (showAllCommand) {
				op = new ArrayList<Container>(database.getfilteredContainers(true, filtered));
			}
			else {
				op = new ArrayList<Container>(database.findContainer(keyword, filtered));
			}
		}
		else {
			ArrayList<Journey> filtered = new ArrayList<Journey>(filterJourneysForClients(database,topmain, database.getJourney()));
			if (showAllCommand) {
				op = new ArrayList<Container>(database.getfilteredContainers(false, filtered));
			}
			else {
				op = new ArrayList<Container>(database.findContainer(keyword, filtered));
			}
		}
		String[] options = new String[op.size()];
		int i = 0;
		for (Container c : op) {
			options[i] = c.getContainerId();
			i++;
		}
		// remove duplicates from the check options
		Set<String> filter = new HashSet<String>(Arrays.asList(options));
		options = filter.toArray(new String[]{});
		
		final JComboBox<String> id = new JComboBox<String>(options);
		return id;
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
//				database.addData(c, newTemp, newPressure, newHum);
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
				Container c = chooseContainerForPlot(database, id.getSelectedItem().toString());
				
				 createPlots(database, topmain, c);
				
				 topmain.getCl().show(topmain.getCards(), "plotPanel");
			}

		});
		
	}
	
	public Container chooseContainerForPlot(Database database, String id) {
		if (isPast) {
			ArrayList<Container> result = database.findContainer(id, database.getHistory());
			Container c = result.get(0);
			Container con = new Container(c);
			GetAllContainerMeasurements(database, con, id);
			return con;
		}
		else {
			ArrayList<Container> result = database.findContainer(id, database.getJourney());;
			Container c = result.get(0);
			return c;
		}
	}
	
	public void createPlots(final Database database, final TopMain topmain, Container c) {
		if (checkBoxTemp.isSelected()) {
			
			ArrayList<Integer> data = c.getTempList();
			 
			 tempPlot = new plot("Temperature", csp, topmain);
			 tempPlot.linePlot(data);;
			 c.addObserver(tempPlot);
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
			 c.addObserver(presPlot);
			 
		 }
		 if (checkBoxHum.isSelected()) {
			 
			 ArrayList<Integer> data = c.getHumList();
			 
			 humPlot = new plot("Humidity", csp, topmain);
			 humPlot.linePlot(data);;
			 c.addObserver(humPlot);
//			 JPanel humPanel = humPlot.getChartPanel();
//			 plotPanel.add(humPanel);
			 
		 }
		 if (checkBoxAllinOne.isSelected()) {
			 ArrayList<Integer> temp = c.getTempList();
			 ArrayList<Integer> pres = c.getPressureList();
			 ArrayList<Integer> hum = c.getHumList();
			 
			 cPlot = new comparisonlinePlots("Comparison line plot", temp, pres, hum, csp, topmain);
//			 updateComparisonPlot(cPlot);
			 c.addObserver(cPlot);
			 
		 }
		 if (checkBoxBarPlot.isSelected()) {
			 ArrayList<Integer> temp = c.getTempList();
			 ArrayList<Integer> pres = c.getPressureList();
			 ArrayList<Integer> hum = c.getHumList();
			 
			 bPlot = new barPlots("Bar plot", temp, pres, hum, csp, topmain);
			 c.addObserver(bPlot);
//			 updateBarPlot(bPlot);
		 }
		 updateAllPlots(topmain);
	}

	// possibly keyword instead of id ?
	public void GetAllContainerMeasurements(final Database database, Container c, String id) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Integer> result2 = new ArrayList<Integer>();
		ArrayList<Integer> result3 = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> totalresult = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<ArrayList<Integer>> measurement: database.containerInternalStatusHistory(id, database.getHistory())) {
			result.addAll(measurement.get(0));
			result2.addAll(measurement.get(1));
			result3.addAll(measurement.get(2));
		}
		c.setTempList(result);
		c.setPressureList(result2);
		c.setHumList(result3);
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
	
	public void displayContainers() {
		
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
	 

	public void propertyChange(PropertyChangeEvent evt) {

		Database dat = ((Database)evt.getSource());;
		if (wContainers.size()!= 0) {
			if ((isPast && (evt.getPropertyName().contentEquals("history")))) {
				ArrayList<Journey> jList = dat.getHistory();
				showAllOrSearch(jList, dat, true);
			}
			else if (isPast == false && (evt.getPropertyName().contentEquals("journey"))) {
				ArrayList<Journey> jList = dat.getJourney();
				showAllOrSearch(jList, dat, false);
			}
			displayContainers();
			topmain.getMain1().revalidate();
		}
	}

	public void showAllOrSearch(ArrayList<Journey> jList, Database dat, boolean isPast) {
		if (showAllCommand) {
			ArrayList<Journey> result = filterJourneysForClients(database, topmain, jList);
			wContainers = dat.getAllContainers(isPast, result);
		}
		else {
			ArrayList<Journey> result = new ArrayList<Journey>();
			result.addAll(database.findClientJourneys(topmain.getUserText(), jList));
			wContainers = dat.findContainer(keyword,result);
		}
	}


}
