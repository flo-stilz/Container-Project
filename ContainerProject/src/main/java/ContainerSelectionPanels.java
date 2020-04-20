import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ContainerSelectionPanels {

	private JPanel containerSearch;
	private JPanel viewContainers;
	
	public JPanel getContainerSearch() {
		return containerSearch;
	}

	public JPanel getViewContainers() {
		return viewContainers;
	}

	public ContainerSelectionPanels(final Database database) {
		
		containerSearch = new JPanel();
		containerSearch.setPreferredSize(new Dimension(800, 600));
		
		viewContainers = new JPanel(new BorderLayout());
		viewContainers.setPreferredSize(new Dimension(800, 600));
		
		// Search Containers
		
		final JTextField searchContainer = new JTextField();
		searchContainer.setPreferredSize(new Dimension(100, 25));
		
		JButton search = new JButton("Search");
		search.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String keyword = searchContainer.getText();
				database.containerJourneyHistory(keyword);
			}
		});
	}
}
