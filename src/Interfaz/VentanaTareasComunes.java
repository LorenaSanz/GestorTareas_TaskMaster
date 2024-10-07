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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Statement;

import BaseDatos.Conexion;
import BaseDatos.GestionBD;
import Clases.SesionIniciada;

public class VentanaTareasComunes extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private DefaultTableModel modelo = new DefaultTableModel();// Modelado de la tabla
	private String[] datos = new String[2]; // Para introducir datos en la tabla
	private JTable table;
	private VentanaAgregarTarea at = new VentanaAgregarTarea();
	private JLabel lblNewLabel;
	private JButton btnAtras;
	private VentanaEmpleado ve = new VentanaEmpleado();
	private JButton btnFirmar;
	private VentanaFirmar vf = new VentanaFirmar();
	private JLabel lblUsuario; // Etiqueta para mostrar el DNI del empleado
	private GestionBD gb;
	private Conexion conexion = new Conexion();
	private Connection con;
	private Statement st;
	private ResultSet resultado;
	private Conexion cn;
	private JButton btnRefresh;
    private JButton btnVerTarea;
    private VerTareaAdmin vta = new VerTareaAdmin();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaTareasComunes frame = new VentanaTareasComunes();
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
	public VentanaTareasComunes() {
		setResizable(false);
		setTitle("Tareas Comunes");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaTareasComunes.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 989, 651);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);

		btnAtras = new JButton("");
		btnAtras.setBounds(910, 549, 55, 55);
		btnAtras.addActionListener(this);
		contentPane.setLayout(null);
		btnAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

		btnRefresh = new JButton("");
		btnRefresh.addActionListener(this);
		btnRefresh.setIcon(new ImageIcon(VentanaTareasComunes.class.getResource("/Imagenes/REFRESH.png")));
		btnRefresh.setBounds(59, 45, 36, 33);
		btnRefresh.setFocusPainted(false);
		contentPane.add(btnRefresh);
		btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		
		btnAtras.setIcon(new ImageIcon(VentanaTareasComunes.class.getResource("/Imagenes/BOTON_ATRAS.png")));
		btnAtras.setFocusPainted(false);
		contentPane.add(btnAtras);
		
		btnVerTarea = new JButton(""); // Aquí asignamos el botón a la variable de instancia
		btnVerTarea.addActionListener(this);
		btnVerTarea.setIcon(new ImageIcon(VentanaTareasAsignadas.class.getResource("/Imagenes/ojo4.png")));
		btnVerTarea.setToolTipText("Ver ficha empleado");
		btnVerTarea.setFocusPainted(false);
		btnVerTarea.setBounds(829, 479, 85, 29); // Corregimos el nombre de la variable
		contentPane.add(btnVerTarea); // Agregamos el botón al panel
		
		

		scrollPane = new JScrollPane();
		scrollPane.setBounds(59, 89, 855, 380);
		scrollPane.setToolTipText("");
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "CODIGO", "TITULO", "DESCRIPCION", "FECHA", "PRIORIDAD" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(63);
		table.getColumnModel().getColumn(2).setPreferredWidth(178);
		scrollPane.setViewportView(table);

		btnFirmar = new JButton("Marcar como realizada");
		btnFirmar.setIcon(new ImageIcon(VentanaTareasComunes.class.getResource("/Imagenes/TIC.png")));
		btnFirmar.setFont(new Font("Century Gothic", Font.PLAIN, 10));
		btnFirmar.addActionListener(this);
		btnFirmar.setBounds(60, 490, 198, 33);
		btnFirmar.setFocusPainted(false);
		contentPane.add(btnFirmar);
		btnFirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(VentanaTareasComunes.class.getResource("/Imagenes/ICONO_GRUPO_XS.png")));
		lblNewLabel_1.setBounds(862, 30, 62, 60);
		contentPane.add(lblNewLabel_1);

		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 975, 614);
		lblNewLabel.setIcon(new ImageIcon("./Imagenes/Diseño sin título.gif"));
		contentPane.add(lblNewLabel);
		// Crear la etiqueta para mostrar el DNI del empleado
		lblUsuario = new JLabel();
		lblUsuario.setToolTipText("");
		lblUsuario.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblUsuario.setBounds(10, 10, 225, 20); // Posición en la esquina superior izquierda
		contentPane.add(lblUsuario);

		gb = new GestionBD();
		cn = new Conexion();

		// Obtener el DNI del empleado que ha iniciado sesión
		String dniEmpleado = SesionIniciada.getLogeado();
		if (dniEmpleado != null) {
			lblUsuario.setText("Sesión iniciada: " + dniEmpleado); // Mostrar el DNI del empleado
			cargarTareasComunesEnTabla(); // Llamar al método para cargar las tareas comunes en la tabla
		}
	}

	protected JScrollPane getScrollPane() {
		return scrollPane;
	}

	private void cargarTareasComunesEnTabla() {
		try {
			DefaultTableModel modelo = gb.mostrarTareasSinEmpleadoAsignado();
			table.setModel(modelo);
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al mostrar tareas: " + ex.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnRefresh)) {
			dispose(); // Cierra la ventana actual
			VentanaTareasComunes nuevaVentana = new VentanaTareasComunes();
			nuevaVentana.setVisible(true);
		}
		if (e.getSource().equals(btnAtras)) {
			ve.setVisible(true);
			dispose();
		}
		if (e.getSource().equals(btnVerTarea)) {
            int filaSeleccionada = table.getSelectedRow();
            if (filaSeleccionada != -1) {
                String codigo = (String) table.getValueAt(filaSeleccionada, 0);
                String titulo = (String) table.getValueAt(filaSeleccionada, 1);
                String descripcion = (String) table.getValueAt(filaSeleccionada, 2);
                // Mostrar los datos de la tarea seleccionada en VerTareaAdmin
                vta.mostrarDatosTarea(codigo, titulo, descripcion);
                vta.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una tarea de la tabla.");
            }
        }
		if (e.getSource().equals(btnFirmar)) {
			int filaSeleccionada = table.getSelectedRow();
			if (filaSeleccionada != -1) {
				String codigo = (String) table.getValueAt(filaSeleccionada, 0);
				vf.setCodigoTarea(codigo); // Pasar el código de la tarea a la ventana de confirmación
				vf.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "Por favor, seleccione una tarea de la tabla.");
			}
		}
	}
}