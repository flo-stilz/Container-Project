import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyException extends Exception{

	public MyException(String message) {
		JFrame exception = new JFrame("Error");
		exception.add(new JLabel(message));
		exception.setVisible(true);
		}
	
//	public static void NullPointerException(ArrayList<Integer> a) throws MyException {
//		
//		if (a.size() == 0) {
//			throw new MyException("Item could not be found");
//		}
//		else {
//			System.out.println("All good");
//		}
//	}

}
