package Interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.mysql.jdbc.Statement;
import BaseDatos.Conexion;
import BaseDatos.GestionBD;

public class VentanaBorrarEmpleado extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnCancelar;
	private JButton btnAceptar;
	private String dni;
	private Conexion conexion = new Conexion();
	private Connection con;
	private Statement st;
	private ResultSet resultado;
	private GestionBD gb;
	private Conexion cn;

	/**
	 * Create the frame.
	 */
	public VentanaBorrarEmpleado(String dni) {
		this.dni = dni; // Almacena el DNI recibido como parámetro
		setTitle("Eliminar Empleado");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaBorrarEmpleado.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 300, 437, 178);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("¿Está seguro de que desea ELIMINAR el empleado seleccionado?");
		lblNewLabel.setBounds(19, 11, 401, 44);
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 12));
		contentPane.add(lblNewLabel);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this);
		btnAceptar.setBounds(79, 66, 122, 37);
		btnAceptar.setIcon(new ImageIcon(VentanaBorrarEmpleado.class.getResource("/Imagenes/TIC.png")));
		btnAceptar.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnAceptar.setFocusPainted(false);
		contentPane.add(btnAceptar);
		btnAceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		btnCancelar.setBounds(219, 66, 122, 37);
		btnCancelar.setIcon(new ImageIcon(VentanaBorrarEmpleado.class.getResource("/Imagenes/ASPA.png")));
		btnCancelar.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnCancelar.setFocusPainted(false);
		contentPane.add(btnCancelar);
		btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		
		gb = new GestionBD();
		cn = new Conexion();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnAceptar)) {
			try {
				// Llama al método eliminarEmpleado del objeto gb, pasando el DNI del empleado
				boolean eliminado = gb.eliminarEmpleado(dni);
				if (eliminado) {
					JOptionPane.showMessageDialog(null, "Empleado eliminado correctamente.");
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Error al eliminar el empleado.");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al eliminar el empleado: " + ex.getMessage());
			}
		} else if (e.getSource().equals(btnCancelar)) {
			// Si se presiona el botón Cancelar, simplemente cierra la ventana
			dispose();
		}
	}
}
