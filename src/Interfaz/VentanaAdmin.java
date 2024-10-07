package Interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import com.k33ptoo.components.KButton;

public class VentanaAdmin extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnGestionT;
	private JButton btnGestionE;
	private JButton btnCerrarSesion;
	private JLabel lblProblemas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAdmin frame = new VentanaAdmin();
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
	public VentanaAdmin() {
		setResizable(false);
		setTitle("Menú Administrador");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaAdmin.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550, 100, 450, 651);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnGestionT = new JButton(" GESTIÓN TAREAS   ");
		btnGestionT.setIcon(new ImageIcon(VentanaAdmin.class.getResource("/Imagenes/TAREA_ICONO_XXS.png")));
		btnGestionT.addActionListener(this);
		btnGestionT.setForeground(Color.BLACK);
		btnGestionT.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnGestionT.setFocusPainted(false);
		btnGestionT.setBounds(109, 179, 236, 74);
		contentPane.add(btnGestionT);
		 btnGestionT.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		
		btnGestionE = new JButton("GESTIÓN EMPLEADOS");
		btnGestionE.setIcon(new ImageIcon(VentanaAdmin.class.getResource("/Imagenes/ICONO_PERSONA_XXS.png")));
		btnGestionE.addActionListener(this);
		btnGestionE.setForeground(Color.BLACK);
		btnGestionE.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnGestionT.setFocusPainted(false);
		btnGestionE.setBounds(108, 291, 237, 74);
		contentPane.add(btnGestionE);
		btnGestionE.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		
		btnCerrarSesion = new JButton("");
		btnCerrarSesion.setIcon(new ImageIcon(VentanaAdmin.class.getResource("/Imagenes/BOTON_ATRAS.png")));
		btnCerrarSesion.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnCerrarSesion.addActionListener(this);
		btnCerrarSesion.setBounds(192, 395, 46, 44);
		btnCerrarSesion.setFocusPainted(false);
		contentPane.add(btnCerrarSesion);
		btnCerrarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		
		lblProblemas = new JLabel("¿Problemas con la aplicación?");
		lblProblemas.setToolTipText("Accede a nuestra web");
		//SI QUEREMOS UN COLOR MAS SUAVE: Color lightBlue = new Color(173, 216, 230); lblProblemas.setForeground(lightBlue); // Establece el color del texto como azul claro personalizado
        lblProblemas.setForeground(Color.LIGHT_GRAY);
        lblProblemas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

        // Agregar un MouseListener para manejar el clic en el hipervínculo
        lblProblemas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Abrir la página web en el navegador predeterminado al hacer clic en el enlace
                try {
                    Desktop.getDesktop().browse(new java.net.URI("http://localhost/web/contacto.html"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        // Agregar el JLabel al contentPane
        lblProblemas.setBounds(129, 524, 203, 14); // Ajusta la posición y el tamaño según tus necesidades
        getContentPane().add(lblProblemas);
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnGestionT)) {
			VentanaGestionTareas gt = new VentanaGestionTareas();
			gt.setVisible(true);
			dispose();
		}
		if(e.getSource().equals(btnGestionE)){
			
			VentanaGestionEmpleados ge = new VentanaGestionEmpleados();
			ge.setVisible(true);
			dispose();
		}
		if(e.getSource().equals(btnCerrarSesion)) {
			VentanaLogin vl = new VentanaLogin(); //Abre la ventana login
			vl.setVisible(true); // Cierra la ventana anterior
	        dispose();
		}
		
	}
}