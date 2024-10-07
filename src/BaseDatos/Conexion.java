package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
		
	private Connection conn;

	public Connection getConexion() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			String url = "jdbc:mysql://localhost:3306/taskmaster?useSSL=false";//aqui poner el nombre de nuestra baseDatos
			String usuario = "root";
			String contrasenia = "";
			conn = (Connection) DriverManager.getConnection(url, usuario, contrasenia);
		}
		catch (ClassNotFoundException e){
			System.out.println("No encuentra el controlador");
			e.getStackTrace();
		}
		catch (SQLException e){
			System.out.println("Error en la conexion");
			e.getStackTrace();
		}
		return conn;
	}	
}

//statement para insertar
//resulset para coger datos con select
