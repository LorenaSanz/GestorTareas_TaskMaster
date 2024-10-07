package Interfaz;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import BaseDatos.Conexion;
import BaseDatos.GestionBD;


import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JLabel;


// Importa la clase SessionManager
import Clases.SesionIniciada;


public class VentanaEmpleado extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton btnComunes;
    private JButton btnAsignadas;
    private JButton btnCerrarSesion;
    private JButton btnPerfil;
    private JLabel lblDNI; // Etiqueta para mostrar el DNI del empleado
    private Conexion conexion = new Conexion();
    private GestionBD gb;
    private Conexion cn;
    private JButton btnAgenda;
    private JButton btnTablon;


    public VentanaEmpleado() {
    	setResizable(false);
        setTitle("Menú Empleado");
        setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaEmpleado.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(550, 100, 450, 651);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);


        btnComunes = new JButton("TAREAS COMUNES");
        btnComunes.setIcon(new ImageIcon(VentanaEmpleado.class.getResource("/Imagenes/ICONO_ASIGNADAS.png")));
        btnComunes.addActionListener(this);
        btnComunes.setForeground(Color.BLACK);
        btnComunes.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        btnComunes.setFocusPainted(false);
        btnComunes.setBounds(96, 249, 236, 74);
        contentPane.add(btnComunes);
        btnComunes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 


        btnAsignadas = new JButton("TAREAS ASIGNADAS");
        btnAsignadas.setIcon(new ImageIcon(VentanaEmpleado.class.getResource("/Imagenes/ICONO_PERSONA_XXS.png")));
        btnAsignadas.addActionListener(this);
        btnAsignadas.setForeground(Color.BLACK);
        btnAsignadas.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        btnAsignadas.setFocusPainted(false);
        btnAsignadas.setBounds(95, 352, 237, 74);
        contentPane.add(btnAsignadas);
        btnAsignadas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

        btnCerrarSesion = new JButton("");
        btnCerrarSesion.setIcon(new ImageIcon(VentanaEmpleado.class.getResource("/Imagenes/BOTON_ATRAS.png")));
        btnCerrarSesion.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnCerrarSesion.addActionListener(this);
        btnCerrarSesion.setBounds(185, 456, 49, 44);
        btnCerrarSesion.setFocusPainted(false);
        contentPane.add(btnCerrarSesion);
        btnCerrarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 


        btnPerfil = new JButton("");
        btnPerfil.addActionListener(this);
        btnPerfil.setIcon(new ImageIcon(VentanaEmpleado.class.getResource("/Imagenes/ICONO_PERFIL.png")));
        btnPerfil.setBounds(386, 10, 40, 37);
        btnPerfil.setFocusPainted(false);
        contentPane.add(btnPerfil);
        btnPerfil.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 


        // Crear la etiqueta para mostrar el DNI del empleado
        lblDNI = new JLabel();
        lblDNI.setToolTipText("");
        lblDNI.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        lblDNI.setBounds(10, 10, 202, 20); // Posición en la esquina superior izquierda
        contentPane.add(lblDNI);
        
        btnAgenda = new JButton("");
        btnAgenda.addActionListener(this);
        btnAgenda.setIcon(new ImageIcon(VentanaEmpleado.class.getResource("/Imagenes/agenda.png")));
        btnAgenda.setBounds(96, 131, 90, 90);
        btnAgenda.setFocusPainted(false);
        contentPane.add(btnAgenda);
        btnAgenda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        
        btnTablon = new JButton("");
        btnTablon.addActionListener(this);
        btnTablon.setIcon(new ImageIcon(VentanaEmpleado.class.getResource("/Imagenes/MENSAJE2.png")));
        btnTablon.setBounds(242, 131, 90, 90);
        btnTablon.setFocusPainted(false);
        contentPane.add(btnTablon);
        btnTablon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 


        // Obtener el DNI del empleado que ha iniciado sesión
        String dniEmpleado = SesionIniciada.getLogeado();
        if (dniEmpleado != null) {
            lblDNI.setText("Sesión iniciada: " + dniEmpleado); // Mostrar el DNI del empleado
        }
        
        
        gb = new GestionBD();
        cn = new Conexion();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnComunes)) {
            VentanaTareasComunes tc = new VentanaTareasComunes();
            tc.setVisible(true);
            dispose();
        }
        if (e.getSource().equals(btnAsignadas)) {


            VentanaTareasAsignadas ta = new VentanaTareasAsignadas();
            ta.setVisible(true);
            dispose();
        }
        if (e.getSource().equals(btnCerrarSesion)) {
            VentanaLogin vl = new VentanaLogin(); // Abre la ventana login
            vl.setVisible(true); // Cierra la ventana anterior
            dispose();
        }
        if (e.getSource().equals(btnPerfil)) {
            VentanaPerfilEmpleado p = new VentanaPerfilEmpleado();
            p.setVisible(true);
        }
        if(e.getSource().equals(btnAgenda)) {
        	Agenda a = new Agenda();
        	a.setVisible(true);
        	}
        if(e.getSource().equals(btnTablon)) {
        	Tablon ca = new Tablon();
        	ca.setVisible(true);
        }


    }

}

