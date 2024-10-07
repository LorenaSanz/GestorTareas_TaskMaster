package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Statement;

import Clases.SesionIniciada;

public class GestionBD {
    private Conexion conexion = new Conexion();
    private Connection con;
    private Statement st;
    private ResultSet resultado;

    public GestionBD() {
        try {
            con = conexion.getConexion();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de la excepción
        }
    }

    public DefaultTableModel mostrarTareasFiltradas(String prioridad, String dni, String estado) throws SQLException {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("CODIGO");
        modelo.addColumn("TITULO");
        modelo.addColumn("DESCRIPCION");
        modelo.addColumn("FECHA FIN");
        modelo.addColumn("PRIORIDAD");
        modelo.addColumn("ASIGNADO A");
        modelo.addColumn("FIRMADO");

        String query = "SELECT * FROM Tareas WHERE ";

        // Construir la consulta SQL basada en los filtros proporcionados
        if (!prioridad.equals("Todas")) {
            query += "prioridad = ? AND ";
        }
        if (!dni.isEmpty()) {
            query += "empleadoAsignado = ? AND ";
        }
        if (estado.equals("Firmada")) {
            query += "dniEmpleado IS NOT NULL AND dniEmpleado != ''"; // Tareas firmadas
        } else if (estado.equals("Sin Firmar")) {
            query += "(dniEmpleado IS NULL OR dniEmpleado = '')"; // Tareas no firmadas
        } else {
            query += "1=1"; // Todas las tareas
        }

        try {
            PreparedStatement ps = con.prepareStatement(query);

            int index = 1;

            // Establecer parámetros de consulta basados en los filtros proporcionados
            if (!prioridad.equals("Todas")) {
                ps.setString(index++, prioridad);
            }
            if (!dni.isEmpty()) {
                ps.setString(index++, dni);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String titulo = rs.getString("titulo");
                String descripcion = rs.getString("descripcion");
                String fechaFin = rs.getString("fechaFin");
                String prioridadTarea = rs.getString("prioridad");
                String empleadoAsignado = rs.getString("empleadoAsignado");
                String firmado = rs.getString("dniEmpleado");
                modelo.addRow(new Object[]{codigo, titulo, descripcion, fechaFin, prioridadTarea, empleadoAsignado, firmado});
            }

            ps.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Error al ejecutar la consulta SQL: " + ex.getMessage());
        }

        return modelo;
    }


	public String getUserName(String dni) {
        String nombre = "";
        try {
            String sql = "SELECT nombre FROM usuarios WHERE dni = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, dni);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }
	public ResultSet getAllMessages() {
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT usuarios.nombre, tablon.mensaje FROM tablon INNER JOIN usuarios ON tablon.dniUsuario = usuarios.dni";
	        PreparedStatement pst = con.prepareStatement(sql);
	        rs = pst.executeQuery();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return rs;
	}
	public void saveMessageToDatabase(String dniUsuario, String mensaje) {
	    try {
	        String sql = "INSERT INTO tablon (dniUsuario, mensaje) VALUES (?, ?)";
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setString(1, dniUsuario);
	        pst.setString(2, mensaje);
	        pst.executeUpdate();
	        pst.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public boolean marcarRealizadaComun(String dni, String codigo) throws SQLException {
	    boolean actualizado = false;
	    Connection con = null;
	    PreparedStatement pstmt = null;

	    try {
	        con = conexion.getConexion();
	        String sql = "UPDATE tareas SET dniEmpleado = ? WHERE codigo = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, dni); // Establecer el nuevo DNI del empleado
	        pstmt.setString(2, codigo); // Establecer el código de la tarea como segundo parámetro

	        int confirmar = pstmt.executeUpdate();

	        if (confirmar == 1) {
	            actualizado = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Manejo de la excepción aquí (lanzamiento de una excepción personalizada o devolución de false)
	    } finally {
	        // Cerrar la conexión y la declaración en el bloque finally
	        if (pstmt != null) {
	            pstmt.close();
	        }
	        if (con != null) {
	            con.close();
	        }
	    }
	    return actualizado;
	}


	public DefaultTableModel mostrarTareasSinEmpleadoAsignado() throws SQLException {
	    DefaultTableModel modelo = new DefaultTableModel();
	    modelo.addColumn("CODIGO");
	    modelo.addColumn("TITULO");
	    modelo.addColumn("DESCRIPCION");
	    modelo.addColumn("FECHA FIN");
	    modelo.addColumn("PRIORIDAD");

	    String sql;
	    if (SesionIniciada.getLogeado() != null) {
	        // Si el DNI del empleado está disponible, se agrega una condición adicional para filtrar las tareas por empleado asignado nulo
	        sql = "SELECT tareas.codigo AS CODIGO, tareas.titulo AS TITULO, tareas.descripcion AS DESCRIPCION, tareas.fechaFin AS FECHA_FIN, "
	                + "tareas.prioridad AS PRIORIDAD "
	                + "FROM tareas "
	                + "WHERE tareas.empleadoAsignado IS NULL AND tareas.dniEmpleado IS NULL";
	    } else {
	        // Si el DNI del empleado no está disponible, se omitirá la condición de dniEmpleado
	        sql = "SELECT tareas.codigo AS CODIGO, tareas.titulo AS TITULO, tareas.descripcion AS DESCRIPCION, tareas.fechaFin AS FECHA_FIN, "
	                + "tareas.prioridad AS PRIORIDAD "
	                + "FROM tareas "
	                + "WHERE tareas.empleadoAsignado IS NULL";
	    }
	    try {
	        PreparedStatement pst = con.prepareStatement(sql);
	        ResultSet resultado = pst.executeQuery();
	        while (resultado.next()) {
	            Object[] fila = {
	                    resultado.getString("CODIGO"),
	                    resultado.getString("TITULO"),
	                    resultado.getString("DESCRIPCION"),
	                    resultado.getString("FECHA_FIN"),
	                    resultado.getString("PRIORIDAD")
	            };
	            modelo.addRow(fila);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
	    }
	    return modelo;
	}


	public DefaultTableModel mostrarTareasAsignadas(String dniEmpleado) throws SQLException {
	    DefaultTableModel modelo = new DefaultTableModel();
	    modelo.addColumn("CODIGO");
	    modelo.addColumn("TITULO");
	    modelo.addColumn("DESCRIPCION");
	    modelo.addColumn("FECHA FIN");
	    modelo.addColumn("PRIORIDAD");

	    String sql;
	    if (dniEmpleado != null) {
	        // Si el DNI del empleado está disponible, se filtran las tareas asignadas a ese empleado
	        sql = "SELECT tareas.codigo AS CODIGO, tareas.titulo AS TITULO, tareas.descripcion AS DESCRIPCION, tareas.fechaFin AS FECHA_FIN, "
	                + "tareas.prioridad AS PRIORIDAD "
	                + "FROM tareas "
	                + "WHERE tareas.empleadoAsignado = ? AND tareas.dniEmpleado IS NULL";
	    } else {
	        // Si el DNI del empleado no está disponible, se muestran todas las tareas
	        sql = "SELECT tareas.codigo AS CODIGO, tareas.titulo AS TITULO, tareas.descripcion AS DESCRIPCION, tareas.fechaFin AS FECHA_FIN, "
	                + "tareas.prioridad AS PRIORIDAD "
	                + "FROM tareas";
	    }
	    try {
	        PreparedStatement pst = con.prepareStatement(sql);
	        if (dniEmpleado != null) {
	            pst.setString(1, dniEmpleado);
	        }
	        ResultSet resultado = pst.executeQuery();
	        while (resultado.next()) {
	            Object[] fila = {
	                    resultado.getString("CODIGO"),
	                    resultado.getString("TITULO"),
	                    resultado.getString("DESCRIPCION"),
	                    resultado.getString("FECHA_FIN"),
	                    resultado.getString("PRIORIDAD")
	            };
	            modelo.addRow(fila);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
	    }
	    return modelo;
	}

	public String obtenerTextoAgenda(String fecha, String dniEmpleado) throws SQLException {
	    String textoExistente = "";
	    String sql = "SELECT texto FROM agenda WHERE fecha = ? AND dniEmpleado = ?";
	    
	    try (PreparedStatement pst = con.prepareStatement(sql)) {
	        pst.setString(1, fecha);
	        pst.setString(2, dniEmpleado);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            textoExistente = rs.getString("texto");
	        }
	    }
	    
	    return textoExistente;
	}

    // Método para actualizar la agenda para una fecha específica
    public void actualizarAgenda(String fecha, String nuevoTexto, String dniEmpleado) throws SQLException {
        String sql = "UPDATE agenda SET texto = ? WHERE fecha = ?";
        
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, nuevoTexto);
            pst.setString(2, fecha);
            pst.executeUpdate();
        }
    }

    // Método para agregar una entrada en la agenda para una fecha específica
    public void agregarAgenda(String fecha, String texto, String dniEmpleado) throws SQLException {
        String sql = "INSERT INTO agenda (fecha, texto, dniEmpleado) VALUES (?, ?, ?)";
        
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, fecha);
            pst.setString(2, texto);
            pst.setString(3, dniEmpleado);
            pst.executeUpdate();
        }
    }

    public boolean insertarFoto(String dniEmpleado, byte[] foto) throws SQLException {
        boolean insertado = false;
        Connection con = null; // Asegúrate de tener una instancia de conexión
        try {
            con = conexion.getConexion(); // Obtener la conexión
            String sql = "UPDATE empleados SET foto = ? WHERE dni = ?"; // Agregar la cláusula WHERE
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBytes(1, foto); // Asignar los bytes de la imagen al primer marcador de posición
            ps.setString(2, dniEmpleado); // Asignar el DNI del empleado al segundo marcador de posición
            int confirmar = ps.executeUpdate();
            if (confirmar == 1) {
                insertado = true;
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close(); // Asegurarse de cerrar la conexión en el bloque finally
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return insertado;
    }

	public boolean eliminarTarea(String codigo) throws SQLException {
	    int confirmar = 0;
	    con = conexion.getConexion();
	   
	    // Creamos dos consultas separadas para eliminar de las tablas correspondientes
	    String sqlEmpleados = "DELETE FROM tareas WHERE codigo = '" + codigo + "'";
	   
	   
	    
	    try {
	        st = (Statement) con.createStatement();
	       
	        // Ejecutamos la primera consulta para eliminar el empleado de la tabla empleados
	        confirmar += st.executeUpdate(sqlEmpleados);
	       
	       	       
	        st.close();
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	   
	    // Devolvemos true si al menos una fila fue eliminada en alguna de las consultas
	    return confirmar > 0;
	}

	public boolean editarFoto(String dni, String foto) throws SQLException {
	    boolean insertado = false;
	    con = conexion.getConexion();
	    String sql = "UPDATE empleados SET foto = '" + foto + "' WHERE dni IN (SELECT dni FROM usuarios WHERE dni = '" + dni + "')";
	    // Se utiliza una consulta UPDATE en lugar de INSERT para actualizar la contraseña existente del usuario identificado por su correo electrónico
	    try {
	        st = (Statement) con.createStatement();
	        int confirmar = st.executeUpdate(sql);
	        if (confirmar == 1) {
	            insertado = true;
	        }
	        st.close();
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return insertado;
	}

	public boolean editarContrasenna(String dni, String password) throws SQLException {
	    boolean insertado = false;
	    con = conexion.getConexion();
	    String sql = "UPDATE empleados SET password = '" + password + "' WHERE dni IN (SELECT dni FROM usuarios WHERE dni = '" + dni + "')";
	    // Se utiliza una consulta UPDATE en lugar de INSERT para actualizar la contraseña existente del usuario identificado por su correo electrónico

	    try {
	        st = (Statement) con.createStatement();
	        int confirmar = st.executeUpdate(sql);

	        if (confirmar == 1) {
	            insertado = true;
	        }
	        st.close();
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return insertado;
	}
	public boolean editarCargo(String dni, String cargo) throws SQLException {
	    boolean insertado = false;
	    con = conexion.getConexion();
	    String sql = "UPDATE empleados SET cargo = '" + cargo + "' WHERE dni IN (SELECT dni FROM usuarios WHERE dni = '" + dni + "')";
	    // Se utiliza una consulta UPDATE en lugar de INSERT para actualizar la contraseña existente del usuario identificado por su correo electrónico

	    try {
	        st = (Statement) con.createStatement();
	        int confirmar = st.executeUpdate(sql);

	        if (confirmar == 1) {
	            insertado = true;
	        }
	        st.close();
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return insertado;
	}
	public boolean editarEmail(String dni, String email) throws SQLException {
	    boolean insertado = false;
	    con = conexion.getConexion();
	    String sql = "UPDATE usuarios SET email = '" + email + "' WHERE dni IN (SELECT dni FROM usuarios WHERE dni = '" + dni + "')";
	    // Se utiliza una consulta UPDATE en lugar de INSERT para actualizar la contraseña existente del usuario identificado por su correo electrónico

	    try {
	        st = (Statement) con.createStatement();
	        int confirmar = st.executeUpdate(sql);

	        if (confirmar == 1) {
	            insertado = true;
	        }
	        st.close();
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return insertado;
	}
	public boolean editarTlf(String dni, String tlf) throws SQLException {
	    boolean insertado = false;
	    con = conexion.getConexion();
	    String sql = "UPDATE usuarios SET tlf = '" + tlf + "' WHERE dni IN (SELECT dni FROM usuarios WHERE dni = '" + dni + "')";
	    // Se utiliza una consulta UPDATE en lugar de INSERT para actualizar la contraseña existente del usuario identificado por su correo electrónico

	    try {
	        st = (Statement) con.createStatement();
	        int confirmar = st.executeUpdate(sql);

	        if (confirmar == 1) {
	            insertado = true;
	        }
	        st.close();
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return insertado;
	}
	
	public boolean registrarUsuario(String dni, String nombre, String apellido1, String apellido2, String fechaNac, String tlf, String email)
			throws SQLException {
		boolean insertado = false;
		con = conexion.getConexion();
		String sql = "insert into usuarios (dni,nombre,apellido1,apellido2,fechaNac,tlf,email) values ('" + dni + "','" + nombre+"','" + apellido1+"','" + apellido2+"','" + fechaNac+"','" + tlf+"','" + email+"')";
		
		try {
			st = (Statement) con.createStatement();
			int confirmar = st.executeUpdate(sql);
			
			if (confirmar == 1) {
				insertado = true;
			}
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertado;
	}


	public boolean insertarContrasenna(String email, String password) throws SQLException {
	    boolean insertado = false;
	    con = conexion.getConexion();
	    String sql = "UPDATE empleados SET password = '" + password + "' WHERE dni IN (SELECT dni FROM usuarios WHERE email = '" + email + "')";
	    // Se utiliza una consulta UPDATE en lugar de INSERT para actualizar la contraseña existente del usuario identificado por su correo electrónico

	    try {
	        st = (Statement) con.createStatement();
	        int confirmar = st.executeUpdate(sql);

	        if (confirmar == 1) {
	            insertado = true;
	        }
	        st.close();
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return insertado;
	}
	//
	public boolean generarEmpleado(String dni)
			throws SQLException {
		boolean insertado = false;
		con = conexion.getConexion();
		String sql = "insert into empleados (dni) values ('" + dni + "')";
		
		try {
			st = (Statement) con.createStatement();
			int confirmar = st.executeUpdate(sql);
			
			if (confirmar == 1) {
				insertado = true;
			}
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertado;
	}
	public boolean inicioSesionUsuario(String dni, String password) throws SQLException {
		boolean encontrado = false;
		con=conexion.getConexion();
		String sql="SELECT * FROM empleados WHERE dni='"+dni+"'and password='"+password+"' ";
		try{
			st=(Statement) con.createStatement();
			resultado= st.executeQuery(sql);
			while (resultado.next()){
				encontrado = true;
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return encontrado;
	}
	public boolean inicioSesionAdmin(String dni, String password) throws SQLException {
		boolean encontrado = false;
		con=conexion.getConexion();
		String sql="SELECT * FROM admin WHERE dni='"+dni+"'and password='"+password+"' ";
		try{
			st=(Statement) con.createStatement();
			resultado= st.executeQuery(sql);
			while (resultado.next()){
				encontrado = true;
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return encontrado;
	}

	public boolean insertarTareaComun(String codigo, String titulo, String descripcion, String fechaFin, String prioridad) throws SQLException {
		boolean insertado = false;
		con=conexion.getConexion();
		String sql="insert into tareas (codigo,titulo,descripcion,fechaFin,prioridad) values ('"+codigo+"','"+titulo+"','"+descripcion+"','"+fechaFin+"','"+prioridad+"')";
		try {
			st=(Statement) con.createStatement();
			int confirmar = st.executeUpdate(sql);
			if (confirmar == 1){
				insertado = true;
			}
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertado;
	}
	public boolean existeTarea(String codigo) throws SQLException {
	    boolean existe = false;
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	        con = conexion.getConexion();
	        String sql = "SELECT COUNT(*) FROM tareas WHERE codigo = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, codigo);
	        rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            existe = count > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Cerrar ResultSet, PreparedStatement y Connection en orden inverso
	        if (rs != null) {
	            rs.close();
	        }
	        if (stmt != null) {
	            stmt.close();
	        }
	        if (con != null) {
	            con.close();
	        }
	    }
	    
	    return existe;
	}
	public boolean insertarTareaAsignada(String codigo, String titulo, String descripcion, String fechaFin,String prioridad, String empleadoAsignado) throws SQLException {
		boolean insertado = false;
		con=conexion.getConexion();
		String sql="insert into tareas (codigo,titulo,descripcion,fechaFin,prioridad,empleadoAsignado) values ('"+codigo+"','"+titulo+"','"+descripcion+"','"+fechaFin+"','"+prioridad+"','"+empleadoAsignado+"')";
		try {
			st=(Statement) con.createStatement();
			int confirmar = st.executeUpdate(sql);
			if (confirmar == 1){
				insertado = true;
			}
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertado;
	}
	
	public boolean buscarUsuario(String dni) throws SQLException {
		boolean encontrado = false;
		con=conexion.getConexion();
		String sql="SELECT * FROM usuarios WHERE DNI='"+dni+"' ";
		try{
			st=(Statement) con.createStatement();
			resultado= st.executeQuery(sql);
			while (resultado.next()){
				encontrado = true;
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return encontrado;
	}
	public boolean mostrarUsuarioTarea() throws SQLException { //estos son los usuarios que le salen al admin para seleccionar
		boolean encontrado = false;
		con=conexion.getConexion();
		String sql="SELECT usuarios.dni, usuarios.nombre, usuarios.apellido1 FROM usuarios inner join empleados on usuarios.dni = empleados.dni";
		try{
			st=(Statement) con.createStatement();
			resultado= st.executeQuery(sql);
			while (resultado.next()){
				encontrado = true;
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return encontrado;
	}
	public DefaultTableModel mostrarUsuarios() throws SQLException {
	    DefaultTableModel modelo = new DefaultTableModel();
	    modelo.addColumn("DNI");
	    modelo.addColumn("NOMBRE");
	    modelo.addColumn("APELLIDO 1");
	    modelo.addColumn("APELLIDO 2");
	    modelo.addColumn("FECHA NAC.");
	    modelo.addColumn("TELEFONO");
	    modelo.addColumn("EMAIL");
	    modelo.addColumn("CARGO");
	    modelo.addColumn("PASSWORD");

	    con = conexion.getConexion();
	    String sql = "SELECT usuarios.dni, usuarios.nombre, usuarios.apellido1, usuarios.apellido2,"
	            + " usuarios.fechaNac, usuarios.tlf, usuarios.email, empleados.cargo, empleados.password"
	            + " FROM usuarios inner join empleados on usuarios.dni = empleados.dni";
	    try {
	        st = (Statement) con.createStatement();
	        resultado = st.executeQuery(sql);
	        while (resultado.next()) {
	            String password = resultado.getString("PASSWORD");
	            // Verificar si la contraseña no es nula antes de reemplazarla con asteriscos
	            if (password != null) {
	                String passwordAsteriscos = "*".repeat(password.length());
	                password = passwordAsteriscos;
	            }
	            Object[] fila = {
	                resultado.getString("DNI"),
	                resultado.getString("NOMBRE"),
	                resultado.getString("APELLIDO1"),
	                resultado.getString("APELLIDO2"),
	                resultado.getString("FECHANAC"),
	                resultado.getString("TLF"),
	                resultado.getString("EMAIL"),
	                resultado.getString("CARGO"),
	                password // Usar la contraseña con asteriscos solo si no es nula
	            };
	            modelo.addRow(fila);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return modelo;
	}
	public DefaultTableModel mostrarTareas() throws SQLException {
	    DefaultTableModel modelo = new DefaultTableModel();
	    modelo.addColumn("CODIGO");
	    modelo.addColumn("TITULO");
	    modelo.addColumn("DESCRIPCION");
	    modelo.addColumn("FECHA FIN");
	    
	    modelo.addColumn("PRIORIDAD");
	    modelo.addColumn("ASIGNADO");
	    modelo.addColumn("FIRMADO"); // Renombrando la columna y moviéndola al final

	    con = conexion.getConexion();
	    String sql = "SELECT tareas.codigo AS CODIGO, tareas.titulo AS TITULO, tareas.descripcion AS DESCRIPCION, tareas.fechaFin AS FECHA_FIN, "
	            +  "tareas.prioridad AS PRIORIDAD, tareas.empleadoAsignado AS EMPLEADO_ASIGNADO, tareas.dniEmpleado AS FIRMADO "
	            + "FROM tareas";
	    try {
	        st = (Statement) con.createStatement();
	        resultado = st.executeQuery(sql);
	        while (resultado.next()) {
	            Object[] fila = {
	                resultado.getString("CODIGO"),
	                resultado.getString("TITULO"),
	                resultado.getString("DESCRIPCION"),
	                resultado.getString("FECHA_FIN"),
	                
	                resultado.getString("PRIORIDAD"),
	                resultado.getString("EMPLEADO_ASIGNADO"),
	                resultado.getString("FIRMADO"), // Cambiando el nombre de la columna
	            };
	            modelo.addRow(fila);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return modelo;
	}
	
	public String buscarEmail() throws SQLException {
	    String emailEncontrado = null; // Inicializamos como null en caso de no encontrar ningún email
	    con = conexion.getConexion();
	    String sql = "SELECT usuarios.email FROM usuarios INNER JOIN empleados ON usuarios.dni = empleados.dni WHERE password IS NULL";
	    try {
	        st = (Statement) con.createStatement();
	        resultado = st.executeQuery(sql);
	        if (resultado.next()) {
	            // Si la consulta devuelve al menos una fila, obtenemos el correo electrónico y lo asignamos a la variable
	            emailEncontrado = resultado.getString("email");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Es una buena práctica cerrar los recursos después de utilizarlos
	        if (resultado != null) {
	            resultado.close();
	        }
	        if (st != null) {
	            st.close();
	        }
	        if (con != null) {
	            con.close();
	        }
	    }
	    return emailEncontrado;
	}
	public boolean eliminarEmpleado(String dni) throws SQLException {
	    int confirmar = 0;
	    con = conexion.getConexion();
	   
	    // Creamos dos consultas separadas para eliminar de las tablas correspondientes
	    String sqlEliminarTablon = "DELETE FROM tablon WHERE dniUsuario = '" + dni + "'";
	    String sqlEliminarAgenda = "DELETE FROM agenda WHERE dniEmpleado = '" + dni + "'";
	    String sqlEliminarTareasAsignadas = "DELETE FROM tareas WHERE empleadoAsignado = '" + dni + "'";
	    String sqlEliminarTareasFirmadas = "DELETE FROM tareas WHERE dniEmpleado = '" + dni + "'";
	    String sqlEmpleados = "DELETE FROM empleados WHERE dni = '" + dni + "'";
	    String sqlUsuarios = "DELETE FROM usuarios WHERE dni = '" + dni + "'";
	   
	    try {
	        st = (Statement) con.createStatement();
	       
	        // Ejecutamos consultas para eliminar el empleado de la tabla agenda,tablon,tareas,empleado y usuario
	        confirmar += st.executeUpdate(sqlEliminarTablon);
	        confirmar += st.executeUpdate(sqlEliminarAgenda);
	        confirmar += st.executeUpdate(sqlEliminarTareasAsignadas);
	        confirmar += st.executeUpdate(sqlEliminarTareasFirmadas);
	        confirmar += st.executeUpdate(sqlEmpleados);
	        confirmar += st.executeUpdate(sqlUsuarios);
	       
	        st.close();
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	   
	    // Devolvemos true si al menos una fila fue eliminada en alguna de las consultas
	    return confirmar > 0;
	}


	public byte[] obtenerImagenEmpleado(String dni) throws SQLException {
	    byte[] imagenBytes = null;
	    String sql = "SELECT foto FROM empleados WHERE dni = ?";
	    try (Connection conn = conexion.getConexion();
	         PreparedStatement statement = conn.prepareStatement(sql)) {
	        statement.setString(1, dni);
	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                imagenBytes = rs.getBytes("foto");
	            }
	        }
	    }
	    return imagenBytes;
	}


	public ResultSet obtenerDatosEmpleado(String dni, JTextField tfNombre, JTextField tfApellido1, JTextField tfApellido2,JTextField tfDni, JTextField tfFechNac,JTextField tfCargo, JTextField tfTlf, JTextField tfEmail, JTextField tfPass) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {
            conn = conexion.getConexion();
            String query = "SELECT u.dni, u.nombre, u.apellido1, u.apellido2, u.fechaNac, u.tlf, u.email, e.cargo, e.password " +
                    "FROM usuarios u " +
                    "INNER JOIN empleados e ON u.dni = e.dni " +
                    "WHERE u.dni = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, dni);
            resultado = statement.executeQuery();

            if (resultado.next()) {
                // Obtener los datos del ResultSet y establecer los valores en los campos de texto
                tfNombre.setText(resultado.getString("nombre"));
                tfApellido1.setText(resultado.getString("apellido1"));
                tfApellido2.setText(resultado.getString("apellido2"));
                tfDni.setText(resultado.getString("dni"));
                tfFechNac.setText(resultado.getString("fechaNac"));
                tfCargo.setText(resultado.getString("cargo"));
                tfTlf.setText(resultado.getString("tlf"));
                tfEmail.setText(resultado.getString("email"));
                tfPass.setText(resultado.getString("password"));
                
                
                
                // Establecer los demás campos de texto si es necesario
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        throw ex;
	    }
		return resultado;
	}
}
