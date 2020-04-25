
public class client {
	
	private String company;
	private String address;
	private String email;
	private String name;
	private int id;
	private static int count = 69420;
	
	public client() {
	}
	
	public client(String company, String address, String email, String name) {
		count = count +1;
		this.company = company;
		this.address = address;
		this.email = email;
		this.name = name;
		id=count;
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
	
	public String getName() {
		return name;
	}

	public void updateName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
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
	
	public void setId(int id) {
		this.id = id;
	}	
}
	
