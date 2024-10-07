package Interfaz;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;
import BaseDatos.Conexion;
import BaseDatos.GestionBD;



public class VentanaBorrarTarea extends JFrame implements ActionListener {


    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private String codigo;
    private Conexion conexion = new Conexion();
    private Connection con;
    private Statement st;
    
	private ResultSet resultado;
	private GestionBD gb;
	private Conexion cn;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaBorrarTarea frame = new VentanaBorrarTarea();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public VentanaBorrarTarea() {
        setTitle("Eliminar Tarea");
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaBorrarTarea.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(500, 300, 437, 178);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        JLabel lblNewLabel = new JLabel("¿Está seguro de que desea ELIMINAR la tarea seleccionada?");
        lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblNewLabel.setBounds(34, 11, 362, 44);
        contentPane.add(lblNewLabel);


        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(this);
        btnAceptar.setIcon(new ImageIcon(VentanaBorrarTarea.class.getResource("/Imagenes/TIC.png")));
        btnAceptar.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        btnAceptar.setBounds(79, 66, 122, 37);
        btnAceptar.setFocusPainted(false);
        contentPane.add(btnAceptar);
        btnAceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 


        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setIcon(new ImageIcon(VentanaBorrarTarea.class.getResource("/Imagenes/ASPA.png")));
        btnCancelar.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBounds(219, 66, 122, 37);
        contentPane.add(btnCancelar);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        
   
        gb = new GestionBD();
		cn = new Conexion();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnAceptar)) {
            try {
                if (gb.eliminarTarea(codigo)) {
                    // Si la tarea se elimina correctamente, cierra la ventana
                    dispose();
                    
                } else {
                    // Si hay un problema al eliminar la tarea, muestra un mensaje de error
                    System.out.println("Error al eliminar la tarea.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Error al eliminar la tarea: " + ex.getMessage());
            }
        } else if (e.getSource().equals(btnCancelar)) {
            // Si se presiona el botón Cancelar, simplemente cierra la ventana
            dispose();
        }
    }


    public void setCodigoTarea(String codigo) {
        this.codigo = codigo;
    }


    
}


