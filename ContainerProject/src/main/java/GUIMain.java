import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIMain {
	
	private static Database database = new Database(); 
	
	public static void main(String[] args) {
		
	final JFrame init = new JFrame("ContainerTracker2020");
	
	
	JPanel top = new JPanel();
	top.setPreferredSize(new Dimension(250, 50));
//	JPanel bottom = new JPanel();
//	bottom.setPreferredSize(new Dimension(250, 50));
	JPanel panel = new JPanel();
//	panel.setBackground(Color.GREEN);
	panel.setPreferredSize(new Dimension(400,200));
	
	JLabel lbl = new JLabel("Please choose if you are a client or not? are you??");
	
	JButton bClient = new JButton("Client");
	bClient.setPreferredSize(new Dimension(100, 50));
	bClient.addActionListener(new ActionListener() {
		private int c = 0;
		public void actionPerformed(ActionEvent e) {
			new ClientLoginFrame(database, init);
//			init.dispose();
		}	
	});
	JButton bCompany = new JButton("Company");
	bCompany.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			new CompanyLoginFrame(database, init);
//			init.dispose();
			
		}
		
	});
		

	bCompany.setPreferredSize(new Dimension(100, 50));
	
	
	init.add(panel, BorderLayout.CENTER);
//	init.add(bottom,BorderLayout.SOUTH);
	panel.add(bClient, BorderLayout.WEST);
	panel.add(bCompany, BorderLayout.LINE_END);
	top.add(lbl, BorderLayout.LINE_START);
	init.add(top, BorderLayout.NORTH);
	init.pack();
	init.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	init.setVisible(true);
	}
}
