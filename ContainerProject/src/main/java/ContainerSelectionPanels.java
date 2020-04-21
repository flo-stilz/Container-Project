import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

public class ContainerSelectionPanels {

	private JPanel containerSearch;
	private JPanel viewContainers;
	private ArrayList<Journey> wJourneys;
	private ArrayList<Container> wContainers;
	private String keyword;
	private JPanel extraOptions;
	private JPanel plotPanel;
	
	public JPanel getPlotPanel() {
		return plotPanel;
	}

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
		
		plotPanel = new JPanel(new GridLayout(0,2));
		plotPanel.setPreferredSize(new Dimension(800, 600));
		
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
				keyword = searchContainerevol.getText();
				wJourneys = database.containerJourneyHistory(keyword);
				displayJourneys(database, companymain);
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
				keyword = searchActiveContainer.getText();
				wContainers = database.findContainer(keyword);
				displayContainers(database, companymain);
			}
		});
		
		// Show all Containers at the current status
		
		JButton showAll = new JButton("Show All");
		containerSearch.add(showAll);
		showAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				wContainers = database.getAllContainers();
				displayContainers(database, companymain);
			}
		});
		
	}
	
	public void additionalInformation(Database database, CompanyMain companymain) {
		
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
		
		updateData(database, id);
		showPlots(database, id, companymain);
	}
	
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
				result.addAll(database.findContainer(id.getSelectedItem().toString()));
				Container c = result.get(0);
				database.addData(c, newTemp, newPressure, newHum);
			}
		});
		dataUpdate.add(update, BorderLayout.SOUTH);
	}
	
	public void showPlots(final Database database, final JComboBox<String> id, CompanyMain companymain) {
		
		JPanel choosePlots = new JPanel(new BorderLayout());
		extraOptions.add(choosePlots, BorderLayout.EAST);
		
		final JCheckBox checkBoxTemp = new JCheckBox("Temperature Plot");
		final JCheckBox checkBoxPres = new JCheckBox("Pressure Plot");
		final JCheckBox checkBoxHum = new JCheckBox("Humidity Plot");
		final JCheckBox checkBoxAllinOne = new JCheckBox("All in One");
		
		JPanel checkOptions = new JPanel(new GridLayout(0,2));
		checkOptions.add(checkBoxTemp);
		checkOptions.add(checkBoxPres);
		checkOptions.add(checkBoxHum);
		checkOptions.add(checkBoxAllinOne);
		
		choosePlots.add(checkOptions, BorderLayout.CENTER);
		
		JButton show = new JButton("Show the Plots");
		choosePlots.add(show, BorderLayout.SOUTH);
		show.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				// get Container
				ArrayList<Container> result = new ArrayList<Container>();
				result.addAll(database.findContainer(id.getSelectedItem().toString()));
				Container c = result.get(0);
				
				 if (checkBoxTemp.isSelected()) {
					 ArrayList<Integer> data = c.getTempList();
					 // plot.plot(data);
					 // where plot.plot is the function that will displays the data
					 // data is the corresponding arraylist for the plot
				 }
				 if (checkBoxPres.isSelected()) {
					 ArrayList<Integer> data = c.getPressureList();
					 
				 }
				 if (checkBoxHum.isSelected()) {
					 ArrayList<Integer> data = c.getHumList();
					 
				 }
				 if (checkBoxAllinOne.isSelected()) {
					 ArrayList<Integer> temp = c.getTempList();
					 ArrayList<Integer> pres = c.getPressureList();
					 ArrayList<Integer> hum = c.getHumList();
					 
				 }
				
				
			}
		});
		
	}
	
	public void displayJourneys(Database database, CompanyMain companymain) {
		
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
	
	public void displayContainers(Database database, CompanyMain companymain) {
		
		viewContainers.removeAll();
		additionalInformation(database, companymain);
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		String[] columnNames = {
				"Container ID",
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
			
			if (c.isEmpty()) {
				tableModel.insertRow(0, new Object[] {c.getContainerId(), c.getCompany(), c.getContent(), c.getCurrentLocation(), "N/A", "N/A", "N/A", c.getTempList(), c.getPressureList(), c.getHumList()});
			}
			else {
				tableModel.insertRow(0, new Object[] {c.getContainerId(), c.getCompany(), c.getContent(), c.getCurrentLocation(), c.getTempList().get(c.getTempList().size()-1), c.getPressureList().get(c.getPressureList().size()-1), c.getHumList().get(c.getHumList().size()-1), c.getTempList(), c.getPressureList(), c.getHumList()});
			}
		}
		viewContainers.add(new JScrollPane(table), BorderLayout.NORTH);
		companymain.getCl().show(companymain.getCards(), "viewContainers");
	}
}
