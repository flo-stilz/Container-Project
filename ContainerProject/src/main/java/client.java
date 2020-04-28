
/////////
////////7
////////
///////
public class client {
	private String company;
	private String address;
	private String email;
	private String name;
	private String password;
	private int id;
	private static int count = 69420;
	
	public client(String company, String address, String email, String name, String password) {
		count= count +1;
		this.company = company;
		this.address = address;
		this.email = email;
		this.name = name;
		this.password = password;
		id=count;
	}
	
		
	public String toString() {
		return getCompany();
	}
	public String getCompany() {
		return company;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}




	public int getId() {
		return id;
	}
}
	
