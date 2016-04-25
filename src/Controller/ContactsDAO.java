package Controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Controller.ConnectionManager;
import Model.Bean;

public class ContactsDAO {
	private final String CONN_STRING = "jdbc:mysql://localhost/crud";
	private final String USERNAME = "root";
	private final String PASSWORD = "";

	private Connection con;
	private Statement stm;

	public void connect() {
		try {
			con = ConnectionManager.connection(CONN_STRING, USERNAME, PASSWORD,
					ConnectionManager.MYSQL);
			stm = con.createStatement();
			System.out.println("Connected");
		} catch (ClassNotFoundException e) {
			System.out.println("Error loading driver: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Error connecting: " + e.getMessage());
		}
	}

	public void close() {
		try {
			stm.close();
			con.close();
			System.out.println("Disconnected");
		} catch (SQLException e) {
			System.err.println("Error connecting: " + e.getMessage());
		}
	}

	public void create(Bean bean) {
		connect();
		try {
			stm.execute("INSERT INTO javacrud(name,bdate,phone,email) VALUES('"
					+ bean.getName()
					+ "', '"
					+ bean.getBdate()
					+ "', '"
					+ bean.getPhone() + "', '" + bean.getEmail() + "')");
		} catch (SQLException e) {
			System.err.println("Error inserting user: " + e.getMessage());
		} finally {
			close();
		}
	}

	public Bean read(int id) {
		connect();
		ResultSet rs;
		Bean temp = null;
		try {
			rs = stm.executeQuery("SELECT * FROM javacrud WHERE id LIKE '"
					+ id + "%';");
			while (rs.next()) {
				temp = new Bean(rs.getInt("id"), rs.getString("name"),
						rs.getString("bdate"), rs.getString("phone"),
						rs.getString("email"));
			}
			return temp;

		} catch (SQLException e) {
			System.err.println("Error fetching user: " + e.getMessage());
		} finally {
			close();
		}
		return null;
	}

	public List<Bean> readAll() {
		connect();
		List<Bean> list = new ArrayList<Bean>();
		ResultSet rs;
		Bean temp = null;
		try {
			rs = stm.executeQuery("SELECT * FROM javacrud");
			while (rs.next()) {
				temp = new Bean(rs.getInt("id"), rs.getString("name"),
						rs.getString("bdate"), rs.getString("phone"),
						rs.getString("email"));
				list.add(temp);
			}
			return list;
		} catch (SQLException e) {
			System.err.println("Error fetching users: " + e.getMessage());
			return null;
		} finally {
			close();
		}
	}

	public void update(Bean bean) {
		connect();
		try {
			stm.executeUpdate("UPDATE javacrud SET name = '"
					+ bean.getName() + "', bdate ='" + bean.getBdate()
					+ "', phone = '" + bean.getPhone() + "', email ='"
					+ bean.getEmail() + "' WHERE  id = " + bean.getId() + ";");
		} catch (SQLException e) {
			System.err.println("Error updating user: " + e.getMessage());
		} finally {
			close();
		}

	}

	public void delete(int id) {
		connect();
		try {
			stm.execute("DELETE FROM javacrud WHERE id = '" + id + "';");
		} catch (SQLException e) {
			System.err.println("Error deleting user: " + e.getMessage());
		} finally {
			close();
		}
	}
	
	public ArrayList<Object[]> search(String search){ // TODO!!
		connect();
		ArrayList<Object[]> searchList = new ArrayList<Object[]>();
		ResultSet rs;
		try {
			rs = stm.executeQuery("SELECT * FROM javacrud WHERE name LIKE '%" + search + "%'");
//			Bean searchBean = null;
			while (rs.next()) {
				searchList.add(new Object[]{rs.getInt("id"), rs.getString("name"),
						rs.getString("bdate"), rs.getString("phone"),
						rs.getString("email")});
			}
//			return temp;
		} catch (SQLException e) {
			System.err.println("Error searching user: " + e.getMessage());
		} finally {
			close();
		}
		
		return searchList;
	}

}