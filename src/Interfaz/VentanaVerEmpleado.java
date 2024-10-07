package Interfaz;


import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


import com.mysql.jdbc.Statement;


import BaseDatos.Conexion;
import BaseDatos.GestionBD;


import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Font;


public class VentanaVerEmpleado extends JFrame implements ActionListener {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel nameLabel;
    private JLabel lbFechaNac;
    private JLabel imageLabel;
    private ImageIcon imageIcon;
    private JTextField tfNombre;
    private JTextField tfApellido1;
    private JTextField tfApellido2;
    private JTextField tfDni;
    private JTextField tfFechaNac;
    private JTextField tfTlf;
    private JTextField tfEmail;
    private JTextField tfCargo;
    ImageIcon icono = new ImageIcon();
	private Conexion conexion = new Conexion();
	private Connection con;
	private Statement st;
	private ResultSet resultado;
	private GestionBD gb;
	private Conexion cn;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaVerEmpleado frame = new VentanaVerEmpleado();
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
	public VentanaVerEmpleado() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaVerEmpleado.class.getResource("/Imagenes/ICONO_PERSONA_XS.png")));
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Perfil del empleado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(380, 270, 400, 300);


        nameLabel = new JLabel("Nombre: ");
        nameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 11));
        nameLabel.setBounds(22, 23, 54, 21);
        lbFechaNac = new JLabel("DNI:");
        lbFechaNac.setFont(new Font("Century Gothic", Font.PLAIN, 11));
        lbFechaNac.setBounds(22, 86, 66, 21);
        imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(VentanaVerEmpleado.class.getResource("/Imagenes/avatar.png")));
        imageLabel.setBackground(new Color(128, 128, 128));
        imageLabel.setBounds(247, 107, 113, 129);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().setLayout(null);


        getContentPane().add(nameLabel);
        getContentPane().add(lbFechaNac);
        getContentPane().add(imageLabel);
        
        JLabel lbApellidos = new JLabel("Apellidos:");
        lbApellidos.setFont(new Font("Century Gothic", Font.PLAIN, 11));
        lbApellidos.setBounds(22, 54, 54, 21);
        getContentPane().add(lbApellidos);
        
        JLabel lbTlf = new JLabel("Teléfono:");
        lbTlf.setFont(new Font("Century Gothic", Font.PLAIN, 11));
        lbTlf.setBounds(22, 150, 54, 21);
        getContentPane().add(lbTlf);
        
        JLabel lbEmail = new JLabel("Email:");
        lbEmail.setFont(new Font("Century Gothic", Font.PLAIN, 11));
        lbEmail.setBounds(22, 182, 44, 21);
        getContentPane().add(lbEmail);
        
        JLabel lbCargo = new JLabel("FechaNac:");
        lbCargo.setFont(new Font("Century Gothic", Font.PLAIN, 11));
        lbCargo.setBounds(22, 118, 66, 21);
        getContentPane().add(lbCargo);
        
        JLabel lbPass = new JLabel("Cargo:");
        lbPass.setFont(new Font("Century Gothic", Font.PLAIN, 11));
        lbPass.setBounds(22, 214, 80, 21);
        getContentPane().add(lbPass);
        
        tfNombre = new JTextField();
        tfNombre.addActionListener(this);
        tfNombre.setEditable(false);
        tfNombre.setBounds(99, 26, 188, 20);
        getContentPane().add(tfNombre);
        tfNombre.setColumns(10);
        
        tfApellido1 = new JTextField();
        tfApellido1.addActionListener(this);
        tfApellido1.setEditable(false);
        tfApellido1.setColumns(10);
        tfApellido1.setBounds(97, 56, 91, 20);
        getContentPane().add(tfApellido1);
        
        tfApellido2 = new JTextField();
        tfApellido2.addActionListener(this);
        tfApellido2.setEditable(false);
        tfApellido2.setColumns(10);
        tfApellido2.setBounds(191, 56, 96, 20);
        getContentPane().add(tfApellido2);
        
        tfDni = new JTextField();
        tfDni.addActionListener(this);
        tfDni.setEditable(false);
        tfDni.setColumns(10);
        tfDni.setBounds(98, 89, 127, 20);
        getContentPane().add(tfDni);
        
        tfFechaNac = new JTextField();
        tfFechaNac.addActionListener(this);
        tfFechaNac.setEditable(false);
        tfFechaNac.setColumns(10);
        tfFechaNac.setBounds(99, 121, 126, 20);
        getContentPane().add(tfFechaNac);
        
        tfTlf = new JTextField();
        tfTlf.addActionListener(this);
        tfTlf.setEditable(false);
        tfTlf.setColumns(10);
        tfTlf.setBounds(99, 152, 126, 20);
        getContentPane().add(tfTlf);
        
        tfEmail = new JTextField();
        tfEmail.setHorizontalAlignment(SwingConstants.LEFT);
        tfEmail.addActionListener(this);
        tfEmail.setEditable(false);
        tfEmail.setColumns(10);
        tfEmail.setBounds(99, 185, 126, 20);
        getContentPane().add(tfEmail);
        
        tfCargo = new JTextField();
        tfCargo.addActionListener(this);
        tfCargo.setEditable(false);
        tfCargo.setColumns(10);
        tfCargo.setBounds(99, 216, 126, 20);
        getContentPane().add(tfCargo);


        gb = new GestionBD();
		cn = new Conexion();
        
    }



//Esta parte la coge de la extracción de datos de la ventana anterior, no lo coge de la base de 
//datos directamente, si no de lo que se ha cargado en la tabla.
	
//lo único que coge de la base de datos es la foto porque no se almacena en la tabla.
	public void mostrarDatosEmpleado(String dni, String nombre, String apellido1, String apellido2, String fechaNac, String cargo, String tlf, String email) {
	    tfNombre.setText(nombre);
	    tfApellido1.setText(apellido1);
	    tfApellido2.setText(apellido2);
	    tfDni.setText(dni);
	    tfFechaNac.setText(fechaNac);
	    tfCargo.setText(cargo);
	    tfTlf.setText(tlf);
	    tfEmail.setText(email);
	    
	    // Cargar y mostrar la imagen
	    try {
	        byte[] imagenBytes = gb.obtenerImagenEmpleado(dni);
	        if (imagenBytes != null) {
	            ImageIcon imagenIcono = new ImageIcon(imagenBytes);
	            // Escalar la imagen para que se ajuste al tamaño del JLabel
	            Image imagenEscalada = imagenIcono.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
	            imagenIcono = new ImageIcon(imagenEscalada);
	            imageLabel.setIcon(imagenIcono);
	        } else {
	            // Si no hay imagen en la base de datos, mostrar una imagen predeterminada
	        	ImageIcon imagenPredeterminada = new ImageIcon(getClass().getResource("/Imagenes/avatar.png"));


	            Image imagenEscalada = imagenPredeterminada.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
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
		// TODO Auto-generated method stub
		
	}

}


