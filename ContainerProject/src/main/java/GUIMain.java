import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUIMain {

	static Database database = new Database(); 
	static private JFrame failedLogin;
	static private JFrame failedReg;
	static private String name = "Mads M�ller";
	static private String pass = "1";
	
	/*
	 * This method is used for registering a new client.
	 * When the frame is called, text fields can be filled out with the necessary info
	 * and this will be used with the createClient method from database. 
	 */
	public static void register(final Database database) {
		
		final JFrame registration = new JFrame("Registration");
		JPanel info = new JPanel();
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		registration.add(new JLabel("Register Here:"), BorderLayout.NORTH);
		registration.add(info, BorderLayout.CENTER);
		
		// info input
		
		info.add(new JLabel("Insert your company/username:"));
		final JTextField user = new JTextField();
		user.setPreferredSize(new Dimension(100, 25));
		info.add(user);
		
		info.add(new JLabel("Insert your reference person:"));
		final JTextField ref = new JTextField();
		ref.setPreferredSize(new Dimension(100, 25));
		info.add(ref);
		
		info.add(new JLabel("Insert your e-mail:"));
		final JTextField mail = new JTextField();
		mail.setPreferredSize(new Dimension(100, 25));
		info.add(mail);
		
		info.add(new JLabel("Insert your address:"));
		final JTextField address = new JTextField();
		address.setPreferredSize(new Dimension(100, 25));
		info.add(address);
		
		info.add(new JLabel("Insert your password:"));
		final JPasswordField passwordReg = new JPasswordField();
		passwordReg.setPreferredSize(new Dimension(100, 25));
		info.add(passwordReg);
		
		//Register button
		
		JButton enter = new JButton("Register");
		registration.add(enter, BorderLayout.SOUTH);
		enter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				String passTextReg = new String(passwordReg.getPassword());
				
				/*
				 * the user inputed company name is tested to see if it already exist in the database,
				 * or if the password is shorter than 5 characters long.
				 * If the inputs fails one of these requirements it will open a frame indicating a failed registration.
				 */
				if ((database.search(user.getText()).size() == 0)
						&& (passTextReg.length()>4)) {
					
					database.createClient(user.getText(), address.getText(), mail.getText(), ref.getText(), passTextReg);
					registration.dispose();
					// possibly add another frame to confirm registration
				}
				
				else {
					failreg();
				}
			}
		});
		registration.pack();
		registration.setVisible(true);
	}
	
	/*
	 * Fail() is called if the inputed username and password does not match or exist
	 * and will open a new frame.
	 */
	public static void fail() {
		
		failedLogin = new JFrame("Failed to login");
		
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
	
	/*
	 * failreg() is called if the inputed information during registration is invalid.
	 */
	public static void failreg() {
		
		failedReg = new JFrame("Failed to register");
		
		JLabel lbl2 = new JLabel(" The username was not available or password has less than 5 signs!! >;^(  ");
		
		
		
		JButton b2 = new JButton("OK");
		b2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				failedReg.dispose();
			}
		});
		failedReg.add(lbl2, BorderLayout.NORTH);
		failedReg.add(b2, BorderLayout.SOUTH);
		failedReg.pack();
		failedReg.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		final JFrame LoginFrame = new JFrame("Client Login");
			
		JLabel login = new JLabel("  Login:");
	
		JPanel usernamePanel = new JPanel();
		JLabel userLbl = new JLabel("Username: ");			
		final JTextField username = new JTextField("Mads M�ller");
		username.setPreferredSize(new Dimension(100, 25));
		usernamePanel.add(userLbl, BorderLayout.LINE_START);
		usernamePanel.add(username, BorderLayout.LINE_END);
			
		JPanel passwordPanel = new JPanel();
		JLabel passLbl = new JLabel("Password: ");
		final JPasswordField password = new JPasswordField("1");
		password.setPreferredSize(new Dimension(100, 25));
		passwordPanel.add(passLbl, BorderLayout.LINE_START);
		passwordPanel.add(password, BorderLayout.LINE_END);		
			
		JPanel choices = new JPanel();
		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent e) {
				String passtext = new String(password.getPassword());
				String userText = username.getText();
				
				if (userText.equals(name) && passtext.equals(pass)) {
					new CompanyMain(username.getText(), database, LoginFrame);
//					company.dispose();
				}
				else if ((database.search(userText).size())!= 0) {
						
					Client client = database.search(userText).get(0);
						
					if (client.getPassword().contentEquals(passtext)) {
						new ClientMain(userText, database, LoginFrame);
//						LoginFrame.dispose();
					}
					else {
						fail();
					}
				}
				else {		
					fail();
						
				}
					
			
			}
		});
			
		JButton register = new JButton("Register");
		register.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent e) {
				register(database);
			}
		});
			
			
		JPanel loginField = new JPanel();
			
		LoginFrame.add(login, BorderLayout.NORTH);
		LoginFrame.add(choices, BorderLayout.SOUTH);		
		LoginFrame.add(loginField, BorderLayout.CENTER);
		choices.add(confirm);
		choices.add(register);
		loginField.add(usernamePanel, BorderLayout.PAGE_START);
		loginField.add(passwordPanel, BorderLayout.PAGE_END);
		LoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LoginFrame.pack();
		LoginFrame.setVisible(true);
	
		}
}
