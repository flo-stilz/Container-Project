import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Container;
import model.client;

public class clientTable implements chartobserver {
	private String company;
	private String address;
	private String email;
	private String name;
	private String id;
	private client c;

	JFrame f;
	ArrayList<Container> clientcontainers= new ArrayList<Container>();
	
	
public clientTable   (client c, ArrayList<Container> containerWarehouse) {
	this.c=c;
address=c.getAddress();
email=c.getEmail();
name=c.getName();
company=c.getCompany();
id=Integer.toString((c.getId()));

for (Container cc : containerWarehouse) {
	if (cc.getCompany().contentEquals(company)) {
		System.out.println("this is the company"+ cc.getContent());
		clientcontainers.add(cc);
}
}
f=new JFrame();    
String[][] array = new String[clientcontainers.size()][6];
for (int i = 0; i < clientcontainers.size(); i++) {
    String row = clientcontainers.get(i).getContainerId()+" , "+clientcontainers.get(i).getContent();    
    array[i][5] = row;
    }
array [0][0]=name;
array[0][3]=company;
array[0][2]=email;
array[0][1]=address;
array[0][4]=id;


String column[]= {"Name","Address", "Email","Company","Client ID","Container ID and Contents"};  
JTable jt=new JTable(array,column);    
jt.setBounds(30,40,200,300);          
JScrollPane sp=new JScrollPane(jt);    
f.add(sp);          
f.setSize(600,150);    
f.setVisible(true);    
System.out.println("you got fucked");
try {
	TimeUnit.SECONDS.sleep(20);
} catch (InterruptedException e) {
	e.printStackTrace();
}

}



public void updateC(ArrayList<Container> containerWarehouse) {
	for (Container cc : containerWarehouse) {
		if (cc.getCompany().contentEquals(company)) {
			System.out.println("this is the company"+ cc.getContent());
			clientcontainers.add(cc);
	}
	}
	f=new JFrame();    
	String[][] array = new String[clientcontainers.size()][6];
	for (int i = 0; i < clientcontainers.size(); i++) {
	    String row = clientcontainers.get(i).getContainerId()+" , "+clientcontainers.get(i).getContent();    
	    array[i][5] = row;
	    }
	array [0][0]=name;
	array[0][1]=company;
	array[0][2]=email;
	array[0][3]=address;
	array[0][4]=id;


	String column[]= {"Name","Company", "Email","Address",  "Client ID","Container ID and Contents"};  
	JTable jt=new JTable(array,column);    
	jt.setBounds(30,40,200,300);          
	JScrollPane sp=new JScrollPane(jt);    
	f.add(sp);          
	f.setSize(300,400);    
	f.setVisible(true);    
	System.out.println("you got fucked");
	try {
		TimeUnit.SECONDS.sleep(20);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}

	
}

}
