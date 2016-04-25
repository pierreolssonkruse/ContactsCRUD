package Controller;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  

public class ConnectionManager {
	   public static final int MYSQL = 0;  
	   private static final String MySQLDriver = "com.mysql.jdbc.Driver";  
	  
	   public static Connection connection(String url, String name, String password,  
	         int con) throws ClassNotFoundException, SQLException {  
	      switch (con) {        
	      case MYSQL:           
	         Class.forName(MySQLDriver);  
	         break;  
	      }  
	      return DriverManager.getConnection(url, name, password);  
	   }  
	
}