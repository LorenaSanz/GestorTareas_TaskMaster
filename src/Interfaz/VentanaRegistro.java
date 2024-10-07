package Interfaz;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import BaseDatos.Conexion;
import BaseDatos.GestionBD;
import Clases.Metodos;

import com.mysql.jdbc.Statement;

public class VentanaRegistro extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtApellido1;
	private JTextField txtNombre;
	private JTextField txtApellido2;
	private JTextField txtTlfmvil;
	private JTextField txtEmail;
	private JTextField txtDni;
	private JButton btnEnviarRegis;
	private JButton btnLimpiarRegis;
	private JButton btnSalirRegistro;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_1;
	private VentanaLogin vl = new VentanaLogin();
	private JDateChooser dateChooser;
	private Conexion conexion = new Conexion();
	private Connection con;
	private Statement st;
	private ResultSet resultado;
	private GestionBD gb;
	private Conexion cn;
	private Metodos m1 = new Metodos();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegistro frame = new VentanaRegistro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaRegistro() {
		setResizable(false);
		setTitle("Registro TaskMaster");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaRegistro.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 651);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnSalirRegistro = new JButton("");
		btnSalirRegistro.addActionListener(this);
		btnSalirRegistro.setForeground(new Color(0, 128, 128));
		btnSalirRegistro.setIcon(new ImageIcon(VentanaRegistro.class.getResource("/Imagenes/BOTON_SALIR_XS.png")));
		btnSalirRegistro.setBounds(490, 551, 52, 49);
		btnSalirRegistro.setFocusPainted(false);
		contentPane.add(btnSalirRegistro);

		txtNombre = new JTextField();
		txtNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtNombre.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		txtNombre.setBounds(166, 62, 255, 33);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);

		txtApellido1 = new JTextField();
		txtApellido1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtApellido1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		txtApellido1.setBounds(166, 123, 255, 33);
		contentPane.add(txtApellido1);
		txtApellido1.setColumns(10);

		txtApellido2 = new JTextField();
		txtApellido2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtApellido2.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		txtApellido2.setBounds(166, 184, 255, 33);
		contentPane.add(txtApellido2);
		txtApellido2.setColumns(10);

		txtTlfmvil = new JTextField();
		txtTlfmvil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtTlfmvil.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		txtTlfmvil.setBounds(166, 359, 255, 33);
		contentPane.add(txtTlfmvil);
		txtTlfmvil.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtEmail.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		txtEmail.setBounds(166, 417, 255, 33);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		txtDni = new JTextField();
		txtDni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtDni.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		txtDni.setBounds(166, 241, 255, 33);
		contentPane.add(txtDni);
		txtDni.setColumns(10);

		btnEnviarRegis = new JButton("ENVIAR");
		btnEnviarRegis.addActionListener(this);
		btnEnviarRegis.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnEnviarRegis.setBounds(311, 477, 110, 44);
		btnEnviarRegis.setFocusPainted(false);
		contentPane.add(btnEnviarRegis);

		btnLimpiarRegis = new JButton("LIMPIAR");
		btnLimpiarRegis.setForeground(new Color(153, 153, 255));
		btnLimpiarRegis.addActionListener(this);
		btnLimpiarRegis.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnLimpiarRegis.setFocusPainted(false);
		btnLimpiarRegis.setBounds(166, 477, 110, 44);
		contentPane.add(btnLimpiarRegis);

		lblNewLabel_2 = new JLabel("Nombre");
		lblNewLabel_2.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_2.setBounds(82, 74, 74, 21);
		contentPane.add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("1º Apellido");
		lblNewLabel_3.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_3.setBounds(59, 137, 120, 21);
		contentPane.add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel("2º Apellido");
		lblNewLabel_4.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_4.setBounds(59, 196, 120, 21);
		contentPane.add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel("DNI");
		lblNewLabel_5.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_5.setBounds(121, 253, 120, 21);
		contentPane.add(lblNewLabel_5);

		lblNewLabel_6 = new JLabel("Tlf_móvil");
		lblNewLabel_6.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_6.setBounds(77, 371, 120, 21);
		contentPane.add(lblNewLabel_6);

		lblNewLabel_7 = new JLabel("Email");
		lblNewLabel_7.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_7.setBounds(106, 429, 120, 21);
		contentPane.add(lblNewLabel_7);

		dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setFont(new Font("Century Gothic", Font.BOLD, 16));
		dateChooser.setBounds(166, 299, 255, 33);
		contentPane.add(dateChooser);

		JLabel lblFechaNac = new JLabel("Fecha Nac");
		lblFechaNac.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblFechaNac.setBounds(59, 311, 120, 21);
		contentPane.add(lblFechaNac);

		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(VentanaRegistro.class.getResource("/Imagenes/WALLPAPER_LOGOS_M.png")));
		lblNewLabel_1.setBounds(-333, -97, 1189, 850);
		contentPane.add(lblNewLabel_1);
		
		
		gb = new GestionBD();
		cn = new Conexion();

	}

	public void actionPerformed(ActionEvent e) {
	    boolean registro = false;
	    boolean encontrado = false;
	    String dni = txtDni.getText().toUpperCase();
	    String nombre = txtNombre.getText().toUpperCase();
	    String apellido1 = txtApellido1.getText().toUpperCase();
	    String apellido2 = txtApellido2.getText().toUpperCase();
	    String tlf = txtTlfmvil.getText();
	    String email = txtEmail.getText();

	    if (e.getSource().equals(btnSalirRegistro)) {
	        VentanaLogin vl = new VentanaLogin(); // Abre la ventana login
	        vl.setVisible(true); // Cierra la ventana anterior
	        dispose();
	    } else if (!Metodos.esDNIValido(dni)) {
	        JOptionPane.showMessageDialog(null, "El DNI introducido no es válido");
	        return; // Salir del método si el DNI no es válido
	    } else if (tlf.length() != 9 || !tlf.matches("\\d{9}")) {
	        JOptionPane.showMessageDialog(null, "El número de teléfono debe tener exactamente 9 dígitos");
	        return; // Salir del método si el teléfono no tiene 9 dígitos
	    } else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
	        JOptionPane.showMessageDialog(null, "El formato del email no es válido");
	        return; // Salir del método si el email no tiene el formato adecuado
	    } else {
	        // Obtener la fecha seleccionada del JDateChooser
	        java.util.Date fechaSeleccionada = dateChooser.getDate();

	        // Verificar si se ha seleccionado una fecha
	        if (fechaSeleccionada != null) {
	            // Convertir la fecha seleccionada a un formato deseado (por ejemplo, String)
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            String fechaNac = sdf.format(fechaSeleccionada);
	            
	            // Calcular la edad del usuario
	            LocalDate fechaNacimiento = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	            LocalDate fechaActual = LocalDate.now();
	            int edad = Period.between(fechaNacimiento, fechaActual).getYears();

	            // Verificar si el usuario tiene al menos 16 años
	            if (edad < 16) {
	                JOptionPane.showMessageDialog(null, "Debes tener al menos 16 años para registrarte en este sistema.");
	                return; // Salir del método si el usuario tiene menos de 16 años
	            }

	            // Enviar datos a la base de datos
	            try {
	                encontrado = gb.buscarUsuario(dni);
	                if (encontrado) {
	                    JOptionPane.showMessageDialog(null, "Usuario ya existente");
	                } else {
	                    registro = gb.registrarUsuario(dni, nombre, apellido1, apellido2, fechaNac, tlf, email);
	                    registro = gb.generarEmpleado(dni);
	                    if (registro) {
	                        JOptionPane.showMessageDialog(null,
	                                "Registro completado, espere a recibir su contraseña para iniciar sesión");
	                        VentanaLogin vl = new VentanaLogin();
	                        vl.setVisible(true);
	                        dispose();
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Error al registrar usuario");
	                    }
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }

	        } else {
	            // Mostrar mensaje de error si no se ha seleccionado una fecha
	            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fecha de nacimiento");
	        }
	    }

	    if (e.getSource().equals(btnLimpiarRegis)) {
	        txtNombre.setText("");
	        txtApellido1.setText("");
	        txtApellido2.setText("");
	        txtDni.setText("");
	        txtTlfmvil.setText("");
	        txtEmail.setText("");
	    }
	}
}