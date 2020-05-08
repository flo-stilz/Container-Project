package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;

import model.Container;
import model.Application;
import model.Journey;


public class ContainerSelectionPanels implements PropertyChangeListener{

	
	// search panel and display panel of the container
	private JPanel containerSearch;
	private JPanel viewContainers;
	// list of containers that have been searched for
	private ArrayList<Container> wContainers = new ArrayList<Container>();
	// input in the search field
	private String keyword;
	private JPanel extraOptions;
	private JPanel plotPanel;
	private ContainerSelectionPanels csp;
	private Application application;
	private TopMain topmain;
	// indicates that show all button was pressed if true
	private boolean showAllCommand;
	// indicates that past containers has been chosen if true
	private boolean isPast;
	// the chosen container which measurements should be plotted
	private Container containerPlot = new Container();

	public Container getContainerPlot() {
		return containerPlot;
	}

	public JPanel getContainerSearch() {
		return containerSearch;
	}

	public JPanel getViewContainers() {
		return viewContainers;
	}

	public JPanel getPlotPanel() {
		return plotPanel;
	}

	public ContainerSelectionPanels(final Application application, final TopMain topmain) {
		
		this.application = application;
		this.topmain = topmain;
		
		// creation of the search panel
		containerSearch = new JPanel();
		containerSearch.setPreferredSize(new Dimension(800, 600));
		
		// creation of the view panel
		viewContainers = new JPanel(new BorderLayout());
		viewContainers.setPreferredSize(new Dimension(800, 600));
		
		// creation of the plot panel
		plotPanel = new JPanel(new GridLayout(0,2));
		plotPanel.setPreferredSize(new Dimension(800, 600));
		
		// store current class object as csp
		csp = this;
		
		// Search Containers
		
		// Filter among active Containers
		
		searchContainers(application, topmain, false);
		
		// Show all Containers at the current status
		
		showAll(application, topmain, false);
		
		// Filter among past Containers
		
		searchContainers(application, topmain, true);
		
		// Show all past Containers at their final status
		
		showAll(application, topmain, true);
		
	}
	
	// sets wContainers either to past or to the active container list
	// past if b was true
	public void showAll(final Application application, final TopMain topmain, final boolean b) {
		JButton showAll = new JButton("Show All");
		containerSearch.add(showAll);
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
					wContainers = application.getJourneyContainerDat().getAllContainers(true, result);
				}
				else {
					wContainers = application.getJourneyContainerDat().getAllContainers(isPast, result);
				}
				checksSearchEntryC(application, topmain);
			}
		});
	}

	// filters the journeys regarding the input in the searchContainer TextField
	public void searchContainers(final Application application, final TopMain topmain, final boolean b) {
		
		String type;
		if (b) {
			type = "Past";
		}
		else {
			type = "Active";	
		}
		
		JLabel container = new JLabel(type + " Container");
		containerSearch.add(container);
		
		final JTextField searchContainer = new JTextField();
		searchContainer.setPreferredSize(new Dimension(100, 25));
		containerSearch.add(searchContainer);
		
		JButton search2 = new JButton("Search");
		containerSearch.add(search2);
		search2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				showAllCommand = false;
				isPast = b;
				keyword = searchContainer.getText();
				
				ArrayList<Journey> jList;
				if (b) {
					jList = application.getJourneyContainerDat().getPastJourneys();
				}
				else {
					jList = application.getJourneyContainerDat().getActiveJourneys();
				}
				ArrayList<Journey> result = filterJourneysForClients(application, topmain, jList);
				
				wContainers = application.findContainer(keyword, result);
				checksSearchEntryC(application, topmain);
			}

		});
	}
	
	// checks if anything matches the search and differentiates between search and showAll command
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
	
	// filters active or past journeys for the user
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
	
	// gives extra features depending on the user
	// creates combo box id to choose container 
	public void additionalInformation(final Application application, TopMain topmain) {
		
		extraOptions = new JPanel(new BorderLayout());
		viewContainers.add(extraOptions, BorderLayout.SOUTH);
		
		final JComboBox<String> id = chooseContainerOptions(application);
		containerPlot = chooseContainerForPlot(application, id.getSelectedItem().toString());
		id.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				containerPlot = chooseContainerForPlot(application, id.getSelectedItem().toString());
			}
		});
		extraOptions.add(id, BorderLayout.NORTH);
		if (topmain instanceof CompanyMain && (isPast == false)) {
			updateData(application, id);
		}
		showPlots(application, id, topmain);
	}

	// filters for the possible options in the combo box created in additionalInformation
	// hereby decides whether past or active and whether show all was chosen or search
	// afterwards removes the duplicates by using a set
	public JComboBox<String> chooseContainerOptions(Application application) {
		ArrayList<Container> op;
		if (isPast) {
			ArrayList<Journey> filtered = new ArrayList<Journey>(filterJourneysForClients(application,topmain, application.getJourneyContainerDat().getPastJourneys()));
			if (showAllCommand) {
				op = new ArrayList<Container>(application.getJourneyContainerDat().getFilteredContainers(filtered));
			}
			else {
				op = new ArrayList<Container>(application.findContainer(keyword, filtered));
			}
		}
		else {
			ArrayList<Journey> filtered = new ArrayList<Journey>(filterJourneysForClients(application,topmain, application.getJourneyContainerDat().getActiveJourneys()));
			if (showAllCommand) {
				op = new ArrayList<Container>(application.getJourneyContainerDat().getFilteredContainers(filtered));
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
				// performs the actual updating of the data
				application.addData(c, newTemp, newPressure, newHum);
			}
		});
		dataUpdate.add(update, BorderLayout.SOUTH);
	}
	
	// creates several check boxes for the different plot types
	// if check box is chosen then plot can be shown after pressing the show button
	public void showPlots(final Application application, final JComboBox<String> id, final TopMain topmain) {
		
		JPanel choosePlots = new JPanel(new BorderLayout());
		extraOptions.add(choosePlots, BorderLayout.EAST);
		
		// creation of the panel to store the check boxes
		JPanel checkOptions = new JPanel(new GridLayout(0,2));
		plotPanel.removeAll();
		// creation of the storage lists for the check boxes and the plots
		final ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
		final ArrayList<Graph> graphs = new ArrayList<Graph>();
		
		// creation of the actual check boxes and plots and stores them
		JCheckBox checkBoxTemp = new JCheckBox("Temperature Plot");
		LinePlot temp = new LinePlot("Temperature", csp, new TemperaturePlot());
		checkBoxes.add(checkBoxTemp);
		graphs.add(temp);
		
		JCheckBox checkBoxPres = new JCheckBox("Pressure Plot");
		LinePlot pres = new LinePlot("Pressure", csp, new PressurePlot());
		checkBoxes.add(checkBoxPres);
		graphs.add(pres);
		
		JCheckBox checkBoxHum = new JCheckBox("Humidity Plot");
		LinePlot hum = new LinePlot("Humidity", csp, new HumidityPlot());
		checkBoxes.add(checkBoxHum);
		graphs.add(hum);
		
		JCheckBox checkBoxAllinOne = new JCheckBox("All in One");
		comparisonlinePlots comp = new comparisonlinePlots("All in One", csp);
		checkBoxes.add(checkBoxAllinOne);
		graphs.add(comp);
		
		JCheckBox checkBoxBarPlot = new JCheckBox("Bar Plot");
		barPlots bar = new barPlots("BarPlot", csp);
		checkBoxes.add(checkBoxBarPlot);
		graphs.add(bar);
		
		// adding the check boxes to the panel
		checkOptions.add(checkBoxTemp);
		checkOptions.add(checkBoxPres);
		checkOptions.add(checkBoxHum);
		checkOptions.add(checkBoxAllinOne);
		checkOptions.add(checkBoxBarPlot);
		
		choosePlots.add(checkOptions, BorderLayout.CENTER);
		
		// creation of the button that switches to the plot panel
		JButton show = new JButton("Show the Plots");
		choosePlots.add(show, BorderLayout.SOUTH);
		show.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				// stop the ContainerSelectionPanels from listening to the application layer
				application.removeObserver(csp);
				
				if (containerPlot.isEmpty()) {
					
					new ErrorFrame();
				}
				else {
					
					// checks which plot has been selected and then creates the chart panel for each plot
					for (int i = 0; i<checkBoxes.size(); i++) {
						JCheckBox checkb = checkBoxes.get(i);
						Graph graph = graphs.get(i);
						if (checkb.isSelected()) {
							generatePlot(graph);
						}
					}
					checksPlot(application, topmain);

					topmain.getCl().show(topmain.getCards(), "plotPanel");
				}
			}
		});
	}

	// creates chart panel given a certain plot
	public void generatePlot(final Graph g) {
		
		ChartPanel chartPanel = g.plotCreation(containerPlot);
		updatePlot(chartPanel, chartPanel);

	}
	// removes old chart panel and adds new chart panel to the plot panel
	// refreshes the window afterwards
	public void updatePlot(ChartPanel newChartPanel, ChartPanel oldChartPanel) {
		plotPanel.remove(oldChartPanel);
		plotPanel.add(newChartPanel);
		topmain.getMain1().revalidate();
	}
	
	// checks if anything matches the search and differentiates between search and showAll command
		public void checksPlot(final Application application, final TopMain topmain) {
			if (containerPlot.isEmpty()) {
				new ErrorFrame();
			}
			else {
			}
		}
	
	// either picks the sum of all measurements of a certain container if past
	// or just finds a specific container from the active journeys
	public Container chooseContainerForPlot(Application application, String id) {
		ArrayList<Journey> jList;
		if (isPast) {
			jList = filterJourneysForClients(application, topmain, application.getJourneyContainerDat().getPastJourneys());
			Container con = application.containerInternalStatusHistory(id, jList);
			return con;
		}
		else {
			jList = filterJourneysForClients(application, topmain, application.getJourneyContainerDat().getActiveJourneys());
			ArrayList<Container> result = application.findContainer(id, jList);
			Container c = result.get(0);
			return c;
		}
	}

	// this method is building the table that shows all the matching containers
	public void displayContainers() {
		
		// first of all deletes all previous components of viewContainers
		viewContainers.removeAll();
		setTableLabel();
		additionalInformation(application, topmain);
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		// specifies the column names
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
		viewContainers.add(new JScrollPane(table), BorderLayout.CENTER);
	}

	// depending on which button has been pressed in the search containers it creates the fitting label
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
		viewContainers.add(label, BorderLayout.NORTH);
	}

	// inserts the to the column names corresponding information into the row
	public void formRowForContainer(DefaultTableModel tableModel, Container c, Application application) {

		// checks whether the measurement lists are empty and whether a container belongs to the active Journey list
		if ((c.isEmpty()) || (application.findJourneys(c.getId(),application.getJourneyContainerDat().getActiveJourneys()).size() == 0)) {
			tableModel.insertRow(0, new Object[] {c.getContainerId(), c.getId(), c.getCompany(), c.getContent(), c.getCurrentLocation(), "N/A", "N/A", "N/A", c.getTempList(), c.getPressureList(), c.getHumList()});
		}
		else {
			tableModel.insertRow(0, new Object[] {c.getContainerId(), c.getId(), c.getCompany(), c.getContent(), c.getCurrentLocation(), c.getTempList().get(c.getTempList().size()-1), c.getPressureList().get(c.getPressureList().size()-1), c.getHumList().get(c.getHumList().size()-1), c.getTempList(), c.getPressureList(), c.getHumList()});
		}
	}
	 
	// handles the notification from the observer pattern
	// updates the wContainers accordingly and rebuilds the tables in displayContainers()
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
	
	// checks whether the showAll button has been pressed or the search and updates the wContainers accordingly
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