package Interfaz;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Statement;

import BaseDatos.Conexion;
import BaseDatos.GestionBD;
import Clases.SesionIniciada;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentanaFirmar extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private String codigoTarea;
    private String dniEmpleado; // Nuevo campo para almacenar el DNI del empleado
    private GestionBD gb;
	private Conexion conexion = new Conexion();
	private Connection con;
	private Statement st;
	private ResultSet resultado;
	private Conexion cn;

    /**
     * Create the frame.
     * @param codigo 
     */
    public VentanaFirmar() {
    	try {
			con = conexion.getConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setTitle("Firmar Tarea");
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaFirmar.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(500, 300, 437, 178);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(this);
        btnAceptar.setIcon(new ImageIcon(VentanaFirmar.class.getResource("/Imagenes/TIC.png")));
        btnAceptar.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        btnAceptar.setFocusPainted(false);
        btnAceptar.setBounds(79, 66, 122, 37);
        contentPane.add(btnAceptar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setIcon(new ImageIcon(VentanaFirmar.class.getResource("/Imagenes/ASPA.png")));
        btnCancelar.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBounds(219, 66, 122, 37);
        contentPane.add(btnCancelar);
        
        JLabel lblestaSeguroDe = new JLabel("¿Está seguro de que desea marcar la tarea como REALIZADA?");
        lblestaSeguroDe.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblestaSeguroDe.setBounds(27, 11, 369, 44);
        contentPane.add(lblestaSeguroDe);
        gb = new GestionBD();
	     cn = new Conexion();
        
        // Obtener el DNI del empleado que ha iniciado sesión
        dniEmpleado = SesionIniciada.getLogeado();
    }


    public void setCodigoTarea(String codigo) {
        codigoTarea = codigo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnAceptar)) {
            try {
                gb.marcarRealizadaComun(dniEmpleado, codigoTarea); // Llamar al método para marcar la tarea como realizada
                dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if(e.getSource().equals(btnCancelar)) {
            dispose();
        }
    }

    // Método para marcar la tarea como realizada en la base de datos
   
}
