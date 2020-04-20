import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClientSectionPanels {

	private JPanel clientsearch;
	private JPanel viewClients;
	private JPanel data;
	private ArrayList<client> wClients;
	
	final PropertyChangeSupport support = new PropertyChangeSupport(this); 
	
	void addObserver(PropertyChangeListener l) {
		support.addPropertyChangeListener(l);
	}
	
	public ClientSectionPanels(final Database database) {
		
		
		// Search the clients
		
		clientsearch = new JPanel();
		clientsearch.setPreferredSize(new Dimension(800, 600));
		
		JButton showAll = new JButton("Show All");
		clientsearch.add(showAll, BorderLayout.NORTH);
		showAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				wClients = database.getClients();
				displayclients();
				// switch panel
				support.firePropertyChange("some", null, null);
			}
		});
		
		
		// display the clients
		
		viewClients = new JPanel();
		viewClients.setPreferredSize(new Dimension(800, 600));
		viewClients.setBackground(Color.DARK_GRAY);
		
		// display data
		
		data = new JPanel();
		
		
	}
	
	public void displayclients() {
		
		viewClients = new JPanel();
		viewClients.setPreferredSize(new Dimension(800, 600));
		viewClients.setLayout(new BoxLayout(viewClients, BoxLayout.Y_AXIS));
		
		for (client c : wClients) {
			JLabel clients = new JLabel(c.getCompany());
			viewClients.add(clients);
			
			clients.addMouseListener(new MouseAdapter()  
			{  
			    public void mouseClicked(MouseEvent e)  
			    {  
			       // switch to data panel

			    }  
			}); 
		}
	}
	
	public JPanel getViewClients() {
		return viewClients;
	}

	public JPanel getData() {
		return data;
	}

	public JPanel getClientSearch() {
		return clientsearch;
	}
}
