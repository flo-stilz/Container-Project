package view;
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

import model.Container;
import model.Application;
import model.Journey;


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


public class ContainerSelectionPanels implements PropertyChangeListener, ShowAll{

	private JPanel containerSearch;
	private JPanel viewContainers;
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
	private Application application;
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

	public ContainerSelectionPanels(final Application application, final TopMain topmain) {
		
		this.application = application;
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
		
		searchActiveContainers(application, topmain);
		
		// Show all Containers at the current status
		
		showAll(application, topmain, application.getJourneyContainerDat().getActiveJourneys(), false);
		
		// Filter among past Containers
		
		searchForPastContainers(application, topmain);
		
		// Show all past Containers at their final status
		
		showAll(application, topmain, application.getJourneyContainerDat().getPastJourneys(), true);
		
	}

	public void showAll(final Application application, final TopMain topmain, final ArrayList<Journey> journeys, final boolean b) {
		JButton showAll = new JButton("Show All");
		containerSearch.add(showAll);
		showAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				showAllCommand = true;
				isPast = b;
				ArrayList<Journey> result = filterJourneysForClients(application, topmain, journeys);
				if (topmain instanceof ClientMain) {
					wContainers = application.getJourneyContainerDat().getAllContainers(true, result);
				}
				else {
					wContainers = application.getJourneyContainerDat().getAllContainers(isPast, result);
				}
				checksSearchEntryC(application, topmain);
			}
		});
	}

	public void searchActiveContainers(final Application application, final TopMain topmain) {
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
				ArrayList<Journey> result = filterJourneysForClients(application, topmain, application.getJourneyContainerDat().getActiveJourneys());
				
				wContainers = application.findContainer(keyword, result);
				checksSearchEntryC(application, topmain);
			}

		});
	}
	
	public void checksSearchEntryC(final Application application, final TopMain topmain) {
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
	
	public void showAllPastContainers(final Application application, final TopMain topmain) {
		JButton showAllPast = new JButton("Show All");
		containerSearch.add(showAllPast);
		showAllPast.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				showAllCommand = true;
				isPast = true;
				ArrayList<Journey> result = filterJourneysForClients(application, topmain, application.getJourneyContainerDat().getPastJourneys());
				wContainers = application.getJourneyContainerDat().getAllContainers(true, result);
				checksSearchEntryC(application, topmain);
			}
		});
	}
	
	public void searchForPastContainers(final Application application, final TopMain topmain) {
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
				ArrayList<Journey> result = filterJourneysForClients(application, topmain, application.getJourneyContainerDat().getPastJourneys());
				
				wContainers = application.findContainer(keyword, result);
				checksSearchEntryC(application, topmain);
			}
		});
	}
	
	public void additionalInformation(Application application, TopMain topmain) {
		
		extraOptions = new JPanel(new BorderLayout());
		viewContainers.add(extraOptions, BorderLayout.CENTER);
		
		final JComboBox<String> id = chooseContainerOptions(application);
		extraOptions.add(id, BorderLayout.NORTH);
		if (topmain instanceof CompanyMain && (isPast == false)) {
			updateData(application, id);
		}
		showPlots(application, id, topmain);
	}

	public JComboBox<String> chooseContainerOptions(Application application) {
		ArrayList<Container> op;
		if (isPast) {
			ArrayList<Journey> filtered = new ArrayList<Journey>(filterJourneysForClients(application,topmain, application.getJourneyContainerDat().getPastJourneys()));
			if (showAllCommand) {
				op = new ArrayList<Container>(application.getJourneyContainerDat().getfilteredContainers(filtered));
			}
			else {
				op = new ArrayList<Container>(application.findContainer(keyword, filtered));
			}
		}
		else {
			ArrayList<Journey> filtered = new ArrayList<Journey>(filterJourneysForClients(application,topmain, application.getJourneyContainerDat().getActiveJourneys()));
			if (showAllCommand) {
				op = new ArrayList<Container>(application.getJourneyContainerDat().getfilteredContainers(filtered));
			}
			else {
				op = new ArrayList<Container>(application.findContainer(keyword, filtered));
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
	
	public void updateData(final Application application, final JComboBox<String> id) {
		
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
				result.addAll(application.findContainer(id.getSelectedItem().toString(), application.getJourneyContainerDat().getActiveJourneys()));
				Container c = result.get(0);
//				database.addData(c, newTemp, newPressure, newHum);
				application.addData(c, newTemp, newPressure, newHum);
			}
		});
		dataUpdate.add(update, BorderLayout.SOUTH);
	}
	
	public void showPlots(final Application application, final JComboBox<String> id, final TopMain topmain) {
		
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
				
				// stop the containerselectionpanels from listening to the application layer
				application.removeObserver(csp);
				// get Container
				Container c = chooseContainerForPlot(application, id.getSelectedItem().toString());
				
				 createPlots(application, topmain, c);
				
				 topmain.getCl().show(topmain.getCards(), "plotPanel");
			}

		});
		
	}
	
	public Container chooseContainerForPlot(Application application, String id) {
		if (isPast) {
			ArrayList<Container> result = application.findContainer(id, application.getJourneyContainerDat().getPastJourneys());
			Container c = result.get(0);
			Container con = new Container(c);
			GetAllContainerMeasurements(application, con, id);
			return con;
		}
		else {
			ArrayList<Container> result = application.findContainer(id, application.getJourneyContainerDat().getActiveJourneys());;
			Container c = result.get(0);
			return c;
		}
	}
	
	public void createPlots(final Application application, final TopMain topmain, Container c) {
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
	public void GetAllContainerMeasurements(final Application application, Container c, String id) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Integer> result2 = new ArrayList<Integer>();
		ArrayList<Integer> result3 = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> totalresult = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<ArrayList<Integer>> measurement: application.containerInternalStatusHistory(id, application.getJourneyContainerDat().getPastJourneys())) {
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
		additionalInformation(application, topmain);
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
			
			formRowForContainer(tableModel, c, application); 
			
		}
		viewContainers.add(new JScrollPane(table), BorderLayout.NORTH);
	}

	public void formRowForContainer(DefaultTableModel tableModel, Container c, Application application) {

		// checks whether the measurement lists are empty and whether a container belongs to the active Journey list
		if ((c.isEmpty()) || (application.findUsingLoop(c.getId(),application.getJourneyContainerDat().getActiveJourneys()).size() == 0)) {
			tableModel.insertRow(0, new Object[] {c.getContainerId(), c.getId(), c.getCompany(), c.getContent(), c.getCurrentLocation(), "N/A", "N/A", "N/A", c.getTempList(), c.getPressureList(), c.getHumList()});
		}
		else {
			tableModel.insertRow(0, new Object[] {c.getContainerId(), c.getId(), c.getCompany(), c.getContent(), c.getCurrentLocation(), c.getTempList().get(c.getTempList().size()-1), c.getPressureList().get(c.getPressureList().size()-1), c.getHumList().get(c.getHumList().size()-1), c.getTempList(), c.getPressureList(), c.getHumList()});
		}
	}
	 

	public void propertyChange(PropertyChangeEvent evt) {

		Application dat = ((Application)evt.getSource());;
		if (wContainers.size()!= 0) {
			if ((isPast && (evt.getPropertyName().contentEquals("history")))) {
				ArrayList<Journey> jList = dat.getJourneyContainerDat().getPastJourneys();
				showAllOrSearch(jList, dat, true);
			}
			else if (isPast == false && (evt.getPropertyName().contentEquals("journey"))) {
				ArrayList<Journey> jList = dat.getJourneyContainerDat().getActiveJourneys();
				showAllOrSearch(jList, dat, false);
			}
			displayContainers();
			topmain.getMain1().revalidate();
		}
	}

	public void showAllOrSearch(ArrayList<Journey> jList, Application dat, boolean isPast) {
		if (showAllCommand) {
			ArrayList<Journey> result = filterJourneysForClients(application, topmain, jList);
			wContainers = dat.getJourneyContainerDat().getAllContainers(isPast, result);
		}
		else {
			ArrayList<Journey> result = new ArrayList<Journey>();
			result.addAll(application.findClientJourneys(jList));
			wContainers = dat.findContainer(keyword,result);
		}
	}


}