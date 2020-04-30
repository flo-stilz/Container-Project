package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import model.Application;

public class MenuSectionPanels implements PropertyChangeListener{
	
	private TopMain topmain;
	private JPanel menupanel;
	private JFrame login;

	public JPanel getMenupanel() {
		return menupanel;
	}
	
//	public MenuSectionPanels(final Database database, final ClientMain clientmain, JFrame login) {
//
//		this.clientmain = clientmain;
//		menupanel = new JPanel( new BorderLayout());
//		menupanel.setPreferredSize(new Dimension(800, 600));
//		menupanel.setBackground(Color.RED);
//		
//		
//		menuPanel(database, login, menupanel);
//	}

	public MenuSectionPanels(final Application application, final TopMain topmain, JFrame login) {

		this.topmain = topmain;
		this.login = login;
		
		menupanel = new JPanel( new BorderLayout());
		menupanel.setPreferredSize(new Dimension(800, 600));
		menupanel.setBackground(Color.RED);
	
		menuPanel(application);
	}
	

	
	public void menuPanel(Application application) {
		menupanel.removeAll();
		if (topmain instanceof ClientMain) {

			
			JPanel clientDetails = new JPanel();
			clientDetails.setLayout(new BoxLayout(clientDetails, BoxLayout.Y_AXIS));
			
			JLabel lbl = new JLabel("Description");
			Font f = lbl.getFont();
			lbl.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
			JLabel company = new JLabel("Company: " + ((ClientMain) topmain).getCurrentClient().getCompany());
			JLabel ref = new JLabel("Reference person: " + ((ClientMain) topmain).getCurrentClient().getName());
			JLabel mail = new JLabel("E-mail: " + ((ClientMain) topmain).getCurrentClient().getEmail());
			JLabel address = new JLabel("Address: " + ((ClientMain) topmain).getCurrentClient().getAddress());
			JLabel id = new JLabel("Company id: " + ((ClientMain) topmain).getCurrentClient().getId());
			clientDetails.add(lbl);
			clientDetails.add(company);
			clientDetails.add(ref);
			clientDetails.add(mail);
			clientDetails.add(address);
			clientDetails.add(id);
			menupanel.add(clientDetails, BorderLayout.CENTER);
		}
		if ( topmain instanceof CompanyMain) {
			
		}
		
		logOutButton(application, login, menupanel);
	}
	
	public void logOutButton(final Application application, final JFrame login, final JPanel menupanel) {
		// Logout as company user
		
		final JButton profile = new JButton("Profile");
		ImageIcon img = new ImageIcon("src/main/resources/profile.png");
		Image image = img.getImage(); // transform it 
		Image newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		img = new ImageIcon(newimg);  // transform it back
	    profile.setIcon(img);
	    
		JPanel top = new JPanel(new BorderLayout());
		menupanel.add(top, BorderLayout.NORTH);
		top.add(profile, BorderLayout.EAST);
		final JPopupMenu menu = new JPopupMenu("Profile Options");
		
		
		JMenuItem logout = new JMenuItem("Logout");
		if(topmain instanceof ClientMain) {
			JMenuItem setDetails = new JMenuItem("Update profile details");
			menu.add(setDetails);
			setDetails.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					updateClientDetails(application);
				}		
			});
		}
		
		menu.add(logout);

		profile.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				Component b=(Component)e.getSource();
				Point p=b.getLocationOnScreen();
				menu.show(profile, 0, 0);;
				menu.setLocation(p.x,p.y+b.getHeight());
				
			}
		});
		
		logout.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				login.setVisible(true);
				
				topmain.getMain1().dispose();
			}
		});
		
		
	}


	public void updateClientDetails(final Application application) {
		final JFrame updateDetails = new JFrame("Update details");
		updateDetails.setPreferredSize(new Dimension(300, 200));
		
		JPanel textFields = new JPanel();
		textFields.setLayout(new BoxLayout(textFields, BoxLayout.Y_AXIS));
			
//			JPanel company = new JPanel();
//			company.add(new JLabel("Company name: "));
//			JTextField companyText = new JTextField("companyname");
//			companyText.setPreferredSize(new Dimension(100, 25));
//			company.add(companyText);
//			textFields.add(company);
			
		JPanel refname = new JPanel();
		refname.add(new JLabel("Reference name: "));
		final JTextField refnameText = new JTextField(((ClientMain) topmain).getCurrentClient().getName());
		refnameText.setPreferredSize(new Dimension(100, 25));
		refname.add(refnameText);
		textFields.add(refname);
			
		JPanel email = new JPanel();
		email.add(new JLabel("Email: "));
		final JTextField emailText = new JTextField(((ClientMain) topmain).getCurrentClient().getEmail());
		emailText.setPreferredSize(new Dimension(100, 25));
		email.add(emailText);
		textFields.add(email);
			
		JPanel address = new JPanel();
		address.add(new JLabel("Address: "));
		final JTextField addressText = new JTextField(((ClientMain) topmain).getCurrentClient().getAddress());
		addressText.setPreferredSize(new Dimension(100, 25));
		address.add(addressText);
		textFields.add(address);
		
		JPanel password = new JPanel();
		password.add(new JLabel("Password: "));
		final JPasswordField passwordText = new JPasswordField(((ClientMain) topmain).getCurrentClient().getPassword());
		passwordText.setPreferredSize(new Dimension(100, 25));
		password.add(passwordText);
		textFields.add(password);
			
		JButton confirm = new JButton("Confirm changes");
		confirm.addActionListener(new ActionListener() {
				
			public void actionPerformed(ActionEvent e) {
				String name = refnameText.getText();
				String mail = emailText.getText();
				String address = addressText.getText();
				String password = new String(passwordText.getPassword());
					
				application.updateClientName(((ClientMain) topmain).getCurrentClient(), name);
				application.updateClientMail(((ClientMain) topmain).getCurrentClient(),mail);
				application.updateClientAddress(((ClientMain) topmain).getCurrentClient(),address);
				application.updateClientPassword(((ClientMain) topmain).getCurrentClient(), password);
					
				updateDetails.dispose();
				
					
			}
		});
			
		updateDetails.add(textFields, BorderLayout.CENTER);
		updateDetails.add(confirm, BorderLayout.SOUTH);
		updateDetails.pack();
		updateDetails.setVisible(true);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		Application dat = ((Application)evt.getSource());;
		menuPanel(dat);
		topmain.getMain1().revalidate();
		
	}
}