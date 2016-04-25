package Model;

public class Bean {
	private int id;
	private String name;
	private String bdate;
	private String phone;
	private String email;

	public Bean(int id, String name, String bdate, String phone, String email) {
		this.id = id;
		this.name = name;
		this.bdate = bdate;
		this.phone = phone;
		this.email = email;
	}

	public Bean(String name, String bdate, String phone, String email) {
		this.name = name;
		this.bdate = bdate;
		this.phone = phone;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Bean [id=" + id + ", name=" + name + ", bdate=" + bdate
				+ ", phone=" + phone + ", email=" + email + "]";
	}

	public Bean() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBdate() {
		return bdate;
	}

	public void setBdate(String bdate) {
		this.bdate = bdate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}