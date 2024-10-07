package Interfaz;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.mysql.jdbc.Statement;
import BaseDatos.Conexion;
import BaseDatos.GestionBD;
import Clases.SesionIniciada;


public class VentanaPerfilEmpleado extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JLabel nameLabel;
	private JLabel lbFechaNac;
	private JLabel imageLabel;
	private JButton loadImageBtn;
	private JTextField tfApellido2;
	private JTextField tfApellido1;
	private JTextField tfNombre;
	private JTextField tfFechNac;
	private JTextField tfCargo;
	private JTextField tfTlf;
	private JTextField tfEmail;
	private JTextField tfPass;
	private JButton btnEditarPass;
	private ImageIcon icono = new ImageIcon();
	private JButton btnEditarEmail;
	private JButton btnEditarTlf;
	private JButton btnEditarCargo;
	private JTextField tfDni;
	private JButton btnConfirmarEmail;
	private JButton btnConfirmarCargo;
	private JButton btnConfirmarPass;
	private JButton btnConfirmarTlf;
	private JLabel lblUsuario; // Etiqueta para mostrar el DNI del empleado
	private GestionBD gb;
	private Conexion conexion = new Conexion();
	private Connection con;
	private Statement st;
	private ResultSet resultado;
	private Conexion cn;
	private String dniEmpleado;
	private JButton btnGuardar;


	public static void main(String[] args) {
		VentanaPerfilEmpleado ventana = new VentanaPerfilEmpleado();
		ventana.setVisible(true);
	}


	public VentanaPerfilEmpleado() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPerfilEmpleado.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Perfil de Usuario");
		setBounds(575, 200, 475, 395);
		setLocationRelativeTo(null);
		
		nameLabel = new JLabel("Nombre: ");
		nameLabel.setFont(new Font("Century Gothic", Font.BOLD, 12));
		nameLabel.setBounds(20, 41, 54, 21);
		
		
		lbFechaNac = new JLabel("FechaNac:");
		lbFechaNac.setFont(new Font("Century Gothic", Font.BOLD, 12));
		lbFechaNac.setBounds(20, 163, 66, 21);
		
		imageLabel = new JLabel();
		imageLabel.setBackground(new Color(128, 128, 128));
		imageLabel.setBounds(316, 41, 123, 143);
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		loadImageBtn = new JButton("Cargar Imagen");
		loadImageBtn.addActionListener(this);
		loadImageBtn.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		loadImageBtn.setBounds(316, 192, 123, 21);
		loadImageBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		
		btnEditarCargo = new JButton("");
		btnEditarCargo.addActionListener(this);
		btnEditarCargo.setBounds(239, 162, 22, 19);
		btnEditarCargo.setFocusPainted(false);
		
		getContentPane().setLayout(null);
		getContentPane().add(nameLabel);
		getContentPane().add(lbFechaNac);
		getContentPane().add(imageLabel);
		getContentPane().add(loadImageBtn);
		
		JLabel lbApellidos = new JLabel("Apellidos:");
		lbApellidos.setFont(new Font("Century Gothic", Font.BOLD, 12));
		lbApellidos.setBounds(20, 71, 60, 21);
		getContentPane().add(lbApellidos);
		
		JLabel lbTlf = new JLabel("Teléfono:");
		lbTlf.setFont(new Font("Century Gothic", Font.BOLD, 12));
		lbTlf.setBounds(20, 223, 54, 21);
		getContentPane().add(lbTlf);
		
		JLabel lbEmail = new JLabel("Email:");
		lbEmail.setFont(new Font("Century Gothic", Font.BOLD, 12));
		lbEmail.setBounds(20, 254, 44, 21);
		getContentPane().add(lbEmail);
		
		JLabel lbCargo = new JLabel("Cargo:");
		lbCargo.setFont(new Font("Century Gothic", Font.BOLD, 12));
		lbCargo.setBounds(20, 192, 44, 21);
		getContentPane().add(lbCargo);
		
		JLabel lbPass = new JLabel("Contraseña:");
		lbPass.setFont(new Font("Century Gothic", Font.BOLD, 12));
		lbPass.setBounds(20, 283, 78, 21);
		getContentPane().add(lbPass);
		
		tfApellido2 = new JTextField();
		tfApellido2.addActionListener(this);
		tfApellido2.setEditable(false);
		tfApellido2.setBounds(96, 105, 133, 20);
		getContentPane().add(tfApellido2);
		
		tfApellido2.setColumns(10);
		tfApellido1 = new JTextField();
		tfApellido1.addActionListener(this);
		tfApellido1.setEditable(false);
		tfApellido1.setColumns(10);
		tfApellido1.setBounds(96, 73, 133, 20);
		getContentPane().add(tfApellido1);
		
		tfNombre = new JTextField();
		tfNombre.addActionListener(this);
		tfNombre.setEditable(false);
		tfNombre.setColumns(10);
		tfNombre.setBounds(96, 43, 133, 20);
		getContentPane().add(tfNombre);
		
		tfFechNac = new JTextField();
		tfFechNac.addActionListener(this);
		tfFechNac.setEditable(false);
		tfFechNac.setColumns(10);
		tfFechNac.setBounds(96, 165, 133, 20);
		getContentPane().add(tfFechNac);
		
		tfCargo = new JTextField();
		tfCargo.addActionListener(this);
		tfCargo.setEditable(false);
		tfCargo.setColumns(10);
		tfCargo.setBounds(96, 195, 133, 20);
		getContentPane().add(tfCargo);
		
		tfTlf = new JTextField();
		tfTlf.addActionListener(this);
		tfTlf.setEditable(false);
		tfTlf.setColumns(10);
		tfTlf.setBounds(96, 225, 133, 20);
		getContentPane().add(tfTlf);
		
		tfEmail = new JTextField();
		tfEmail.addActionListener(this);
		tfEmail.setEditable(false);
		tfEmail.setColumns(10);
		tfEmail.setBounds(96, 255, 133, 20);
		getContentPane().add(tfEmail);
		
		tfPass = new JTextField();
		tfPass.addActionListener(this);
		tfPass.setEditable(false);
		tfPass.setColumns(10);
		tfPass.setBounds(96, 285, 133, 20);
		getContentPane().add(tfPass);
		
		btnEditarPass = new JButton("");
		btnEditarPass.addActionListener(this);
		btnEditarPass.setBounds(239, 285, 22, 19);
		btnEditarPass.setFocusPainted(false);
		getContentPane().add(btnEditarPass);
		
		btnEditarEmail = new JButton("");
		btnEditarEmail.addActionListener(this);
		btnEditarEmail.setBounds(239, 256, 22, 19);
		btnEditarEmail.setFocusPainted(false);
		getContentPane().add(btnEditarEmail);

		btnEditarCargo = new JButton("");
		btnEditarCargo.addActionListener(this);
		btnEditarCargo.setBounds(239, 194, 22, 19);
		btnEditarCargo.setFocusPainted(false);
		getContentPane().add(btnEditarCargo);
		
		btnEditarTlf = new JButton("");
		btnEditarTlf.addActionListener(this);
		btnEditarTlf.setBounds(239, 225, 22, 19);
		btnEditarTlf.setFocusPainted(false);
		getContentPane().add(btnEditarTlf);
		
		ImageIcon icono = new ImageIcon(VentanaPerfilEmpleado.class.getResource("/Imagenes/editar.png"));
		Image img = icono.getImage();
		Image nuevaImagen = img.getScaledInstance(btnEditarPass.getWidth(), btnEditarPass.getHeight(),
				Image.SCALE_SMOOTH);
		icono = new ImageIcon(nuevaImagen);
		btnEditarPass.setIcon(icono);
		Image nuevaImagen1 = img.getScaledInstance(btnEditarEmail.getWidth(), btnEditarEmail.getHeight(),
				Image.SCALE_SMOOTH);
		icono = new ImageIcon(nuevaImagen1);
		btnEditarEmail.setIcon(icono);
		Image nuevaImagen2 = img.getScaledInstance(btnEditarTlf.getWidth(), btnEditarTlf.getHeight(),
				Image.SCALE_SMOOTH);
		icono = new ImageIcon(nuevaImagen2);
		btnEditarTlf.setIcon(icono);
		Image nuevaImagen3 = img.getScaledInstance(btnEditarCargo.getWidth(), btnEditarCargo.getHeight(),
				Image.SCALE_SMOOTH);
		icono = new ImageIcon(nuevaImagen3);
		btnEditarCargo.setIcon(icono);
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setFont(new Font("Century Gothic", Font.BOLD, 12));
		lblDni.setBounds(20, 133, 54, 21);
		getContentPane().add(lblDni);
		tfDni = new JTextField();
		tfDni.addActionListener(this);
		tfDni.setEditable(false);
		tfDni.setColumns(10);
		tfDni.setBounds(96, 135, 133, 20);
		getContentPane().add(tfDni);
		
		btnConfirmarCargo = new JButton("");
		btnConfirmarCargo.addActionListener(this);
		btnConfirmarCargo.setIcon(new ImageIcon(VentanaPerfilEmpleado.class.getResource("/Imagenes/TIC.png")));
		btnConfirmarCargo.setBounds(271, 194, 22, 19);
		btnConfirmarCargo.setFocusPainted(false);
		getContentPane().add(btnConfirmarCargo);
		
		btnConfirmarTlf = new JButton("");
		btnConfirmarTlf.addActionListener(this);
		btnConfirmarTlf.setIcon(new ImageIcon(VentanaPerfilEmpleado.class.getResource("/Imagenes/TIC.png")));
		btnConfirmarTlf.setBounds(271, 225, 22, 19);
		btnConfirmarTlf.setFocusPainted(false);
		getContentPane().add(btnConfirmarTlf);
		
		btnConfirmarEmail = new JButton("");
		btnConfirmarEmail.addActionListener(this);
		btnConfirmarEmail.setIcon(new ImageIcon(VentanaPerfilEmpleado.class.getResource("/Imagenes/TIC.png")));
		btnConfirmarEmail.setBounds(271, 256, 22, 19);
		btnConfirmarEmail.setFocusPainted(false);
		getContentPane().add(btnConfirmarEmail);
		
		btnConfirmarPass = new JButton("");
		btnConfirmarPass.addActionListener(this);
		btnConfirmarPass.setIcon(new ImageIcon(VentanaPerfilEmpleado.class.getResource("/Imagenes/TIC.png")));
		btnConfirmarPass.setBounds(271, 285, 22, 19);
		btnConfirmarPass.setFocusPainted(false);
		getContentPane().add(btnConfirmarPass);


		// Crear la etiqueta para mostrar el DNI del empleado
		lblUsuario = new JLabel();
		lblUsuario.setToolTipText("");
		lblUsuario.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblUsuario.setBounds(10, 10, 209, 21); // Posición en la esquina superior izquierda
		getContentPane().add(lblUsuario);


		btnGuardar = new JButton("");
		btnGuardar.addActionListener(this);
		btnGuardar.setIcon(new ImageIcon(VentanaPerfilEmpleado.class.getResource("/Imagenes/disquete.png")));
		btnGuardar.setBounds(385, 294, 54, 54);
		btnGuardar.setFocusPainted(false);
		getContentPane().add(btnGuardar);
		btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 


		gb = new GestionBD();
		cn = new Conexion();


		// Obtener el DNI del empleado que ha iniciado sesión
		dniEmpleado = SesionIniciada.getLogeado();
		if (dniEmpleado != null) {
			lblUsuario.setText("Sesión iniciada: " + dniEmpleado); // Mostrar el DNI del empleado
			ResultSet rs = null;
			try {
				rs = gb.obtenerDatosEmpleado(dniEmpleado, tfNombre, tfApellido1, tfApellido2, tfDni, tfFechNac, tfCargo,
						tfTlf, tfEmail, tfPass);
				if (rs.next()) {
					tfNombre.setText(rs.getString("nombre"));
					tfApellido1.setText(rs.getString("apellido1"));
					tfApellido2.setText(rs.getString("apellido2"));
					tfDni.setText(rs.getString("dni"));
					tfFechNac.setText(rs.getString("fechaNac"));
					tfCargo.setText(rs.getString("cargo"));
					tfTlf.setText(rs.getString("tlf"));
					tfEmail.setText(rs.getString("email"));
					tfPass.setText(rs.getString("password"));
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				// Cierra el ResultSet aquí, después de haber accedido a los datos necesarios
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		// Lógica para cargar la imagen desde la base de datos
		try {
			byte[] imagenBytes = gb.obtenerImagenEmpleado(dniEmpleado);
			if (imagenBytes != null) {
				ImageIcon imagenIcono = new ImageIcon(imagenBytes);
				// Escalar la imagen para que se ajuste al tamaño del JLabel
				Image imagenEscalada = imagenIcono.getImage().getScaledInstance(imageLabel.getWidth(),
						imageLabel.getHeight(), Image.SCALE_SMOOTH);
				imagenIcono = new ImageIcon(imagenEscalada);
				imageLabel.setIcon(imagenIcono);
			} else {
				// Si no hay imagen en la base de datos, mostrar una imagen predeterminada
				ImageIcon imagenPredeterminada = new ImageIcon(getClass().getResource("/Imagenes/avatar.png"));
				Image imagenEscalada = imagenPredeterminada.getImage().getScaledInstance(imageLabel.getWidth(),
						imageLabel.getHeight(), Image.SCALE_SMOOTH);
				imagenPredeterminada = new ImageIcon(imagenEscalada);
				imageLabel.setIcon(imagenPredeterminada);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Manejar errores al obtener la imagen
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
	    boolean inicio = false;
	    if (e.getSource().equals(btnGuardar)) {
	        dispose();
	    }
	    if (e.getSource().equals(btnEditarCargo)) {
	        tfCargo.setEditable(true); // Hacer editable el campo de cargo
	    }


	    if (e.getSource().equals(btnConfirmarCargo)) {
	        String cargo = tfCargo.getText();
	        try {
	            inicio = gb.editarCargo(dniEmpleado, cargo);
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        tfCargo.setEditable(false); // Después de enviar, deshabilitar la edición del campo de cargo
	    }


	    if (e.getSource().equals(btnEditarTlf)) {
	        tfTlf.setEditable(true); // Hacer editable el campo de teléfono
	    }


	    if (e.getSource().equals(btnConfirmarTlf)) {
	        String tlf = tfTlf.getText();
	        if (tlf.length() != 9 || !tlf.matches("\\d{9}")) {
	            JOptionPane.showMessageDialog(null, "El número de teléfono debe tener exactamente 9 dígitos");
	            return; // Salir del método si el teléfono no tiene 9 dígitos
	        }
	        try {
	            inicio = gb.editarTlf(dniEmpleado, tlf);
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        tfTlf.setEditable(false); // Después de enviar, deshabilitar la edición del campo de teléfono
	    }


	    if (e.getSource().equals(btnEditarEmail)) {
	        tfEmail.setEditable(true); // Hacer editable el campo de email
	    }


	    if (e.getSource().equals(btnConfirmarEmail)) {
	        String email = tfEmail.getText();
	        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
	            JOptionPane.showMessageDialog(null, "El formato del email no es válido");
	            return; // Salir del método si el email no tiene el formato adecuado
	        }
	        try {
	            inicio = gb.editarEmail(dniEmpleado, email);
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        tfEmail.setEditable(false); // Después de enviar, deshabilitar la edición del campo de email
	    }


	    if (e.getSource().equals(btnEditarPass)) {
	        tfPass.setEditable(true); // Hacer editable el campo de contraseña
	    }


	    if (e.getSource().equals(btnConfirmarPass)) {
	        String password = tfPass.getText(); // Obtener la nueva contraseña ingresada por el usuario
	        try {
	            inicio = gb.editarContrasenna(dniEmpleado, password); // Enviar la nueva contraseña a la base de datos
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        tfPass.setEditable(false); // Después de enviar, deshabilitar la edición del campo de contraseña
	    }


	    if (e.getSource().equals(loadImageBtn)) {
	        JFileChooser fileChooser = new JFileChooser();
	        int result = fileChooser.showOpenDialog(VentanaPerfilEmpleado.this);
	        if (result == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            try {
	                byte[] imageData = Files.readAllBytes(selectedFile.toPath());
	                ImageIcon imageIcon = new ImageIcon(imageData);
	                // Redimensionar la imagen para que se ajuste al tamaño del JLabel
	                Image image = imageIcon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),
	                        Image.SCALE_SMOOTH);
	                imageIcon = new ImageIcon(image);
	                imageLabel.setIcon(imageIcon);
	                if (gb.insertarFoto(dniEmpleado,imageData)) {
	                    System.out.println("Error al insertar en la base de datos.");
	                } else {
	                    System.out.println("Imagen insertada en la base de datos.");
	                }
	            } catch (IOException | SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	}
}


