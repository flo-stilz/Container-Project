package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Application;


public class GUIMain {

	static Application application = new Application(); 
	static private JFrame failedLogin;
	static private JFrame failedReg;

	
	/*
	 * This method is used for registering a new client.
	 * When the frame is called, text fields can be filled out with the necessary info
	 * and this will be used with the createClient method from database. 
	 */
	public static void register(final Application application) {
		
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
				boolean validation = application.registrationValidation(user.getText(), ref.getText(), mail.getText(), address.getText(), passTextReg); 
				if (validation == true) {
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
		
		final JFrame LoginFrame = new JFrame("Login");
			
		ImageIcon img = new ImageIcon("src/main/resources/logo.png");
		Image image = img.getImage(); // transform it 
		Image newimg = image.getScaledInstance(400, 200,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		img = new ImageIcon(newimg);
		JLabel logo = new JLabel("");
		logo.setIcon(img);
	
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
				String passText = new String(password.getPassword());
				String userText = username.getText();
				
				if (application.loginValidation(userText, passText).equals("admin")) {
					new CompanyMain(application, LoginFrame);
//					company.dispose();
				}
				
				else if (application.loginValidation(userText, passText).equals("client")) {
						new ClientMain(application, LoginFrame);
//						LoginFrame.dispose();
				}
				
				else {		
					fail();		
				}
					
			
			}
		});
			
		JButton register = new JButton("Register");
		register.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent e) {
				register(application);
			}
		});
			
			
		JPanel loginField = new JPanel();
			
		LoginFrame.add(logo, BorderLayout.NORTH);
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