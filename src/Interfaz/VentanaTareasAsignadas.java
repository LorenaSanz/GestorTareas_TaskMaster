package Interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
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
import BaseDatos.Conexion;
import BaseDatos.GestionBD;
import Clases.SesionIniciada;

public class VentanaTareasAsignadas extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private DefaultTableModel modelo = new DefaultTableModel(); // Modelado de la tabla
	private String[] datos = new String[2]; // Para introducir datos en la tabla
	private JTable table;
	private VentanaAgregarTarea at = new VentanaAgregarTarea();
	private JLabel lblNewLabel;
	private JButton btnAtras;
	private VentanaEmpleado ve = new VentanaEmpleado();
	private JButton btnFirmar;
	private JLabel lblNewLabel_1;
	private VentanaFirmar vf = new VentanaFirmar();
	private JLabel lblUsuario; // Etiqueta para mostrar el DNI del empleado
	private Conexion conexion = new Conexion();
	private Connection con;
	private GestionBD gb;
	private JButton btnRefrescar;
    private JButton btnVerTarea;
    private VerTareaAdmin vta = new VerTareaAdmin();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaTareasAsignadas frame = new VentanaTareasAsignadas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaTareasAsignadas() {
		setResizable(false);
		setTitle("Tareas Asignadas");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaTareasAsignadas.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 989, 651);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnAtras = new JButton("");
		btnAtras.addActionListener(this);
		btnAtras.setFocusPainted(false);

		btnRefrescar = new JButton("");
		btnRefrescar.setIcon(new ImageIcon(VentanaTareasAsignadas.class.getResource("/Imagenes/REFRESH.png")));
		btnRefrescar.addActionListener(this);
		
		btnVerTarea = new JButton(""); // Aquí asignamos el botón a la variable de instancia
		btnVerTarea.addActionListener(this);
		btnVerTarea.setIcon(new ImageIcon(VentanaTareasAsignadas.class.getResource("/Imagenes/ojo4.png")));
		btnVerTarea.setToolTipText("Ver ficha empleado");
		btnVerTarea.setFocusPainted(false);
		btnVerTarea.setBounds(829, 479, 85, 29); // Corregimos el nombre de la variable
		contentPane.add(btnVerTarea); // Agregamos el botón al panel
		btnRefrescar.setBounds(59, 49, 34, 29);
		btnRefrescar.setFocusPainted(false);
		contentPane.add(btnRefrescar);
		
		btnAtras.setIcon(new ImageIcon(VentanaTareasAsignadas.class.getResource("/Imagenes/BOTON_ATRAS.png")));
		btnAtras.setBounds(910, 549, 55, 55);
		contentPane.add(btnAtras);
		btnAtras.setFocusPainted(false);
		btnAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		btnRefrescar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

		scrollPane = new JScrollPane();
		scrollPane.setToolTipText("");
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(59, 89, 855, 380);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "CODIGO", "TITULO", "DESCRIPCION", "FECHA", "PRIORIDAD" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(63);
		table.getColumnModel().getColumn(2).setPreferredWidth(178);
		scrollPane.setViewportView(table);

		btnFirmar = new JButton("Marcar como realizada");
		btnFirmar.setIcon(new ImageIcon(VentanaTareasAsignadas.class.getResource("/Imagenes/TIC.png")));
		btnFirmar.setFont(new Font("Century Gothic", Font.PLAIN, 10));
		btnFirmar.addActionListener(this);
		btnFirmar.setBounds(59, 479, 198, 34);
		btnFirmar.setFocusPainted(false);
		contentPane.add(btnFirmar);
		btnFirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1
				.setIcon(new ImageIcon(VentanaTareasAsignadas.class.getResource("/Imagenes/ICONO_PERSONA_XS.png")));
		lblNewLabel_1.setBounds(865, 23, 82, 68);
		contentPane.add(lblNewLabel_1);

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("./Imagenes/Diseño sin título.gif"));
		lblNewLabel.setBounds(0, 0, 975, 614);
		contentPane.add(lblNewLabel);
		// Crear la etiqueta para mostrar el DNI del empleado
		lblUsuario = new JLabel();
		lblUsuario.setToolTipText("");
		lblUsuario.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblUsuario.setBounds(10, 10, 186, 20); // Posición en la esquina superior izquierda
		contentPane.add(lblUsuario);

		gb = new GestionBD();

		// Obtener el DNI del empleado que ha iniciado sesió
		String dniEmpleado = SesionIniciada.getLogeado();
		if (dniEmpleado != null) {
			lblUsuario.setText("Sesión iniciada: " + dniEmpleado); // Mostrar el DNI del empleado
			cargarTareasEnTabla(dniEmpleado);
		}
	}

	protected JScrollPane getScrollPane() {
		return scrollPane;
	}

	private void cargarTareasEnTabla(String dniEmpleado) {
		try {
			DefaultTableModel modelo = gb.mostrarTareasAsignadas(dniEmpleado);
			table.setModel(modelo);
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al mostrar tareas: " + ex.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnRefrescar)) {
			dispose(); // Cierra la ventana actual
			VentanaTareasAsignadas nuevaVentana = new VentanaTareasAsignadas();
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
