package model;



public class Client {
	private String company;
	private String address;
	private String email;
	private String name;
	private String password;
	private int id;
	private static int count = 69420;
	
	public Client(String company, String address, String email, String name, String password) {
		count = count +1;
		this.company = company;
		this.address = address;
		this.email = email;
		this.name = name;
		this.password = password;
		id=count;
	}
	
	//for persistency layer
	public Client() {
	}
	
		
	public void setCompany(String company) {
		this.company = company;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static void setCount(int count) {
		Client.count = count;
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
	public void updateAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void updateEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void updatePassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void updateName(String name) {
		this.name = name;
	}




	public int getId() {
		return id;
	}
}
	
