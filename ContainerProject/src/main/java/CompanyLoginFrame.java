import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CompanyLoginFrame {
	String name = "Mads Møller";
	String pass = "1";
	
	
	
	public CompanyLoginFrame(final Database database) {
		final JFrame company = new JFrame("Company Login");

		
		JLabel login = new JLabel("  Login:");

		JPanel usernamePanel = new JPanel();
		JLabel userLbl = new JLabel("Username: ");
		final JTextField username = new JTextField("Mads Møller");
		usernamePanel.add(userLbl, BorderLayout.LINE_START);
		usernamePanel.add(username, BorderLayout.LINE_END);
		
		JPanel passwordPanel = new JPanel();
		JLabel passLbl = new JLabel("Password: ");
		final JPasswordField password = new JPasswordField("1");
		passwordPanel.add(passLbl, BorderLayout.LINE_START);
		passwordPanel.add(password, BorderLayout.LINE_END);		
		

		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String passtext = new String(password.getPassword());
				if (username.getText().equals(name) && passtext.equals(pass)) {
					new CompanyMain(database);
					company.dispose();
				}
				else {
					final JFrame failedLogin = new JFrame("Failed to login");
					
					JLabel lbl = new JLabel(" The username or password was incorrect!! >;^(  ");
					
					
					
					JButton b = new JButton("OK");
					b.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							failedLogin.dispose();
						}
						
					});
					failedLogin.add(lbl, BorderLayout.NORTH);
					failedLogin.add(b, BorderLayout.SOUTH);
					failedLogin.pack();
					failedLogin.setVisible(true);
				}
				
			
			}
		});
		
		JPanel loginField = new JPanel();
		
		company.add(login, BorderLayout.NORTH);
		company.add(confirm, BorderLayout.SOUTH);
		company.add(loginField, BorderLayout.CENTER);
		loginField.add(usernamePanel, BorderLayout.PAGE_START);
		loginField.add(passwordPanel, BorderLayout.PAGE_END);
		company.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		company.pack();
		company.setVisible(true);
	}
}
