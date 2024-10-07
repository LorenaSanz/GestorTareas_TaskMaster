package Interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Statement;

import BaseDatos.Conexion;
import BaseDatos.GestionBD;
import Clases.SesionIniciada;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JToggleButton;
import java.awt.Toolkit;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class VentanaLogin extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUsuario;
	private JPasswordField PFContrasenna;
	private JLabel jLabelUsuario;
	private JButton btnLimpiar;
	private JButton btnEntrar;
	private JButton btnSalir;
	private JLabel jLabelContrasenna;
	private VentanaAdmin va;
	private VentanaEmpleado ve;
	private VentanaRegistro vr;
	private JRadioButton RadioButtonAdmin;
	private JLabel TextoInicioSesion;
	private JButton btnRegistro;
	private JLabel lblNewLabel;
	private Conexion conexion = new Conexion();
	private Connection con;
	private Statement st;
	private ResultSet resultado;
	private GestionBD gb;
	private Conexion cn;
	private JRadioButton radioButtonEmpleado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin frame = new VentanaLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaLogin() {
		setResizable(false);
		setTitle("TaskMaster");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaLogin.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 989, 651);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnSalir = new JButton("SALIR");
		btnSalir.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnSalir.addActionListener(this);
		btnSalir.setFocusPainted(false);
		btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

		RadioButtonAdmin = new JRadioButton("Administrador");
		RadioButtonAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRegistro = new JButton("REGISTRATE"); // Asignación del botón creado a btnRegistro
		btnRegistro.setForeground(new Color(153, 153, 255));
		btnRegistro.addActionListener(this);
		btnRegistro.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnRegistro.setBounds(838, 10, 125, 57);
		btnRegistro.setFocusPainted(false);
		contentPane.add(btnRegistro);
		btnRegistro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

		JLabel Pregunta = new JLabel("¿Nuevo en TaskMaster?");
		Pregunta.setFont(new Font("Century Gothic", Font.BOLD, 14));
		Pregunta.setBounds(664, 10, 169, 57);
		contentPane.add(Pregunta);
		btnRegistro.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnRegistro.setFocusPainted(false);
		btnRegistro.setBounds(838, 10, 125, 57);
		contentPane.add(btnRegistro);

		TextoInicioSesion = new JLabel("Inicio de sesión");
		TextoInicioSesion.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		TextoInicioSesion.setBounds(667, 208, 166, 77);
		contentPane.add(TextoInicioSesion);
		
		RadioButtonAdmin.setForeground(new Color(153, 153, 255));
		RadioButtonAdmin.setFont(new Font("Century Gothic", Font.BOLD, 16));
		RadioButtonAdmin.setBounds(584, 370, 141, 41);
		contentPane.add(RadioButtonAdmin);
		
		btnSalir.setBounds(726, 493, 141, 41);
		btnSalir.setFocusPainted(false);
		contentPane.add(btnSalir);
		btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

		jLabelContrasenna = new JLabel("CONTRASEÑA");
		jLabelContrasenna.setFont(new Font("Century Gothic", Font.BOLD, 16));
		jLabelContrasenna.setBounds(543, 311, 141, 30);
		contentPane.add(jLabelContrasenna);

		jLabelUsuario = new JLabel("USUARIO");
		jLabelUsuario.setFont(new Font("Century Gothic", Font.BOLD, 16));
		jLabelUsuario.setBounds(581, 271, 88, 30);
		contentPane.add(jLabelUsuario);

		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(667, 272, 213, 35);
		contentPane.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);

		PFContrasenna = new JPasswordField();
		PFContrasenna.setBounds(667, 317, 213, 35);
		contentPane.add(PFContrasenna);

		btnEntrar = new JButton("ENTRAR");
		btnEntrar.addActionListener(this);
		btnEntrar.setForeground(new Color(0, 0, 0));
		btnEntrar.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnEntrar.setBounds(577, 493, 125, 41);
		btnEntrar.setFocusPainted(false);
		contentPane.add(btnEntrar);
		btnEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

		btnLimpiar = new JButton("LIMPIAR");
		btnLimpiar.addActionListener(this);
		btnLimpiar.setForeground(new Color(153, 153, 255));
		btnLimpiar.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnLimpiar.setBounds(667, 423, 124, 35);
		btnLimpiar.setFocusPainted(false);
		contentPane.add(btnLimpiar);
		btnLimpiar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VentanaLogin.class.getResource("/Imagenes/LOGO TASKMASTER XL.png")));
		lblNewLabel.setBounds(101, 134, 320, 363);
		contentPane.add(lblNewLabel);

		lblNewLabel.addComponentListener(new ComponentAdapter() { // esto es lo nuevo------------------------
			@Override
			public void componentShown(ComponentEvent e) {
				Image img = ((ImageIcon) lblNewLabel.getIcon()).getImage();
				Image nuevaImagen = img.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(),
						Image.SCALE_SMOOTH);
				lblNewLabel.setIcon(new ImageIcon(nuevaImagen));
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1
				.setIcon(new ImageIcon(VentanaLogin.class.getResource("/Imagenes/TASKMASTER_PALABRA_RELIEVE_XS.png")));
		lblNewLabel_1.setBounds(543, 152, 354, 46);
		contentPane.add(lblNewLabel_1);
		
		radioButtonEmpleado = new JRadioButton("Empleado");
		radioButtonEmpleado.addActionListener(this);
		radioButtonEmpleado.setForeground(new Color(153, 153, 255));
		radioButtonEmpleado.setFont(new Font("Century Gothic", Font.BOLD, 16));
		radioButtonEmpleado.setBounds(739, 370, 141, 41);
		contentPane.add(radioButtonEmpleado);
		gb = new GestionBD();
		cn = new Conexion();
		
		ButtonGroup group = new ButtonGroup();
		group.add(RadioButtonAdmin);
		group.add(radioButtonEmpleado);
	}

	  @Override
	    public void actionPerformed(ActionEvent e) {
	        boolean inicio = false;
	        String dni = textFieldUsuario.getText();
	        String password = PFContrasenna.getText();
	        
	        if (e.getSource().equals(btnSalir)) {
	            System.exit(EXIT_ON_CLOSE);
	        }

	        if (e.getSource().equals(btnRegistro)) {
	            vr = new VentanaRegistro();
	            vr.setVisible(true);
	            dispose();
	        }

	        if (e.getSource().equals(btnEntrar)) {
	            // Validar inicio de sesión
	            if (textFieldUsuario.getText().equals(dni) && PFContrasenna.getText().equals(password)) {
	                if (RadioButtonAdmin.isSelected()) {
	                    // Validar inicio de sesión del administrador
	                    try {
	                        inicio = gb.inicioSesionAdmin(dni, password);
	                        if (inicio) {
	                            // Inicio de sesión exitoso, almacenar el DNI del usuario
	                            SesionIniciada.logeadoDni(dni);
	                            JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
	                            va = new VentanaAdmin();
	                            va.setVisible(true);
	                            dispose();
	                        } else {
	                            JOptionPane.showMessageDialog(null, "Nombre o contraseña incorrectos");
	                        }
	                    } catch (SQLException ex) {
	                        ex.printStackTrace();
	                    }
	                } else if (radioButtonEmpleado.isSelected()) {
	                    // Validar inicio de sesión del empleado
	                    try {
	                        inicio = gb.inicioSesionUsuario(dni, password);
	                        if (inicio) {
	                            // Inicio de sesión exitoso, almacenar el DNI del usuario
	                            SesionIniciada.logeadoDni(dni);
	                            JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
	                            ve = new VentanaEmpleado();
	                            ve.setVisible(true);
	                            dispose();
	                        } else {
	                            JOptionPane.showMessageDialog(null, "Nombre o contraseña incorrectos");
	                        }
	                    } catch (SQLException ex) {
	                        ex.printStackTrace();
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "Por favor, recuerde seleccionar si es Administrador o Empleado");
	                }
	            } else {
	                JOptionPane.showMessageDialog(null, "Nombre o contraseña incorrectos");
	            }
	        }

	        if (e.getSource().equals(btnLimpiar)) {
	            textFieldUsuario.setText("");
	            PFContrasenna.setText("");
	        }
	    }
	
}
