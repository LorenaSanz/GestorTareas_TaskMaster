package Interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mysql.jdbc.Statement;

import BaseDatos.Conexion;
import BaseDatos.GestionBD;
import Clases.Metodos;
import Clases.correo;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class VentanaGestionEmpleados extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnGenerarPass;
	private JButton btnVerEmpleado;
	private JButton btnEliminarEmpleado;
	private JButton btnAtras;
	private VentanaVerEmpleado ve = new VentanaVerEmpleado();
	private VentanaAdmin a1 = new VentanaAdmin();
	private correo c = new correo();
	private Metodos m = new Metodos();
	private Conexion conexion = new Conexion();
	private Connection con;
	private Statement st;
	private ResultSet resultado;
	private Conexion cn;
	private GestionBD gb;
	private JButton btnExportarPDF;
	private JButton btnRefrescar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaGestionEmpleados frame = new VentanaGestionEmpleados();
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
	public VentanaGestionEmpleados() {
		setResizable(false);

		setTitle("Gestión de Empleados");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaGestionEmpleados.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 989, 651);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel contentPane_1 = new JPanel();
		contentPane_1.setLayout(null);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane_1.setBackground(Color.WHITE);
		contentPane_1.setBounds(0, 0, 975, 614);
		contentPane.add(contentPane_1);

		btnAtras = new JButton("");
		btnAtras.setIcon(new ImageIcon(VentanaGestionEmpleados.class.getResource("/Imagenes/BOTON_ATRAS.png")));
		btnAtras.addActionListener(this);
		btnAtras.setBounds(913, 552, 52, 52);
		btnAtras.setFocusPainted(false);
		contentPane_1.add(btnAtras);
		btnAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setToolTipText("");
		scrollPane.setBounds(59, 81, 855, 378);
		contentPane_1.add(scrollPane);

		table = new JTable();
		table.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 12)); // Establecer fuente personalizada
																					// para los encabezados de columna
		table.setFont(new Font("Century Gothic", Font.PLAIN, 12)); // Establecer fuente para el contenido de la tabla
		scrollPane.setViewportView(table);

		btnGenerarPass = new JButton("");
		btnGenerarPass.setToolTipText("Generar contraseña");
		btnGenerarPass.addActionListener(this);
		btnGenerarPass
				.setIcon(new ImageIcon(VentanaGestionEmpleados.class.getResource("/Imagenes/contrasena (1).png")));
		btnGenerarPass.setBounds(59, 469, 85, 35);
		btnGenerarPass.setFocusPainted(false);
		contentPane_1.add(btnGenerarPass);
		btnGenerarPass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnEliminarEmpleado = new JButton("Borrar");
		btnEliminarEmpleado.setToolTipText("Borrar empleado");
		btnEliminarEmpleado.addActionListener(this);
		btnEliminarEmpleado.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnEliminarEmpleado
				.setIcon(new ImageIcon(VentanaGestionEmpleados.class.getResource("/Imagenes/BASURA_XS.png")));
		btnEliminarEmpleado.setBounds(154, 469, 92, 35);
		btnEliminarEmpleado.setFocusPainted(false);
		contentPane_1.add(btnEliminarEmpleado);
		btnEliminarEmpleado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnVerEmpleado = new JButton("");
		btnVerEmpleado.setToolTipText("Ver ficha empleado");
		btnVerEmpleado.addActionListener(this);
		btnVerEmpleado.setIcon(new ImageIcon(VentanaGestionEmpleados.class.getResource("/Imagenes/ojo4.png")));
		btnVerEmpleado.setBounds(829, 42, 85, 29);
		btnVerEmpleado.setFocusPainted(false);
		contentPane_1.add(btnVerEmpleado);
		btnVerEmpleado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnExportarPDF = new JButton("Exportar");
		btnExportarPDF.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnExportarPDF.setToolTipText("Exportar tabla a PDF");
		btnExportarPDF.addActionListener(this);
		btnExportarPDF.setIcon(new ImageIcon(VentanaGestionEmpleados.class.getResource("/Imagenes/PDF_XS.png")));
		btnExportarPDF.setBounds(256, 469, 112, 35); // Ajusta las coordenadas según tu diseño
		btnExportarPDF.setFocusPainted(false);
		contentPane_1.add(btnExportarPDF);
		btnExportarPDF.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnRefrescar = new JButton("");
		btnRefrescar.setIcon(new ImageIcon(VentanaGestionEmpleados.class.getResource("/Imagenes/REFRESH.png")));
		btnRefrescar.addActionListener(this);
		btnRefrescar.setBounds(59, 42, 34, 29);
		btnRefrescar.setFocusPainted(false);
		contentPane_1.add(btnRefrescar);
		btnRefrescar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		gb = new GestionBD();
		cn = new Conexion();

		// Llamada para cargar los usuarios al abrir la ventana
		cargarUsuariosEnTabla();
	}

	// Método para cargar los usuarios en la tabla al abrir la ventana
	private void cargarUsuariosEnTabla() {
		try {
			DefaultTableModel modeloTabla = gb.mostrarUsuarios();
			table.setModel(modeloTabla);
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al mostrar usuarios: " + ex.getMessage());
		}
	}

	@Override

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnRefrescar)) {
			VentanaGestionEmpleados nueva = new VentanaGestionEmpleados();
			nueva.setVisible(true);
			dispose();
		}
		if (e.getSource().equals(btnGenerarPass)) {
			int filaSeleccionada = table.getSelectedRow();
			if (filaSeleccionada != -1) {
				String email = (String) table.getValueAt(filaSeleccionada, 6);
				try {
					String contrasennaGenerada = Metodos.generarPassword();
					correo.enviarCorreo(email, contrasennaGenerada);
					boolean insertado = gb.insertarContrasenna(email, contrasennaGenerada);
					if (insertado) {
						JOptionPane.showMessageDialog(null, "Contraseña generada y enviada correctamente.");
					} else {
						JOptionPane.showMessageDialog(null, "Error al insertar la contraseña en la base de datos.");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al buscar usuarios sin contraseña: " + ex.getMessage());
				}
			} else {
				JOptionPane.showMessageDialog(null, "Por favor, seleccione un empleado de la tabla.");
			}

		} else if (e.getSource().equals(btnVerEmpleado)) {
			int filaSeleccionada = table.getSelectedRow();
			if (filaSeleccionada != -1) {
				String dni = (String) table.getValueAt(filaSeleccionada, 0);
				String nombre = (String) table.getValueAt(filaSeleccionada, 1);
				String apellido1 = (String) table.getValueAt(filaSeleccionada, 2);
				String apellido2 = (String) table.getValueAt(filaSeleccionada, 3);
				String fechaNac = (String) table.getValueAt(filaSeleccionada, 4);
				String tlf = (String) table.getValueAt(filaSeleccionada, 5);
				String email = (String) table.getValueAt(filaSeleccionada, 6);
				String cargo = (String) table.getValueAt(filaSeleccionada, 7);
				ve.mostrarDatosEmpleado(dni, nombre, apellido1, apellido2, fechaNac, cargo, tlf, email);
				ve.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "Por favor, seleccione un empleado de la tabla.");
			}
		} else if (e.getSource().equals(btnAtras)) {
			a1.setVisible(true);
			dispose();
		} else if (e.getSource().equals(btnEliminarEmpleado)) {
			int filaSeleccionada = table.getSelectedRow();
			if (filaSeleccionada != -1) {
				String dni = (String) table.getValueAt(filaSeleccionada, 0);
				// Crear una instancia de VentanaBorrarEmpleado y mostrarla
				VentanaBorrarEmpleado be = new VentanaBorrarEmpleado(dni);
				be.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "Por favor, seleccione un empleado de la tabla.");
			}
		} else if (e.getSource().equals(btnExportarPDF)) {
			// Código para exportar la tabla a PDF
			try {
				// Crear un objeto Document
				Document documento = new Document(PageSize.A4);

				// Definir la ruta del archivo PDF
				String rutaPDF = "DocEmpleados.pdf";

				// Crear un objeto PdfWriter para escribir en el documento PDF
				PdfWriter.getInstance(documento, new FileOutputStream(rutaPDF));

				// Abrir el documento para comenzar a escribir
				documento.open();

				// Añadir título al documento
				Paragraph titulo = new Paragraph("Informe de Empleados\n\n",
						FontFactory.getFont(FontFactory.HELVETICA, 18));
				titulo.setAlignment(Element.ALIGN_CENTER);
				documento.add(titulo);

				// Crear un objeto PdfPTable para representar la tabl
				PdfPTable tablaPDF = new PdfPTable(table.getColumnCount() - 1); // Excluye la última columna

				// Establecer el ancho de las columnas para ajustarse automáticamente
				tablaPDF.setWidthPercentage(100);

				// Agregar encabezados de columna a la tabla PDF (excluyendo la última columna)
				for (int i = 0; i < table.getColumnCount() - 1; i++) {
					PdfPCell cell = new PdfPCell(
							new Phrase(table.getColumnName(i), FontFactory.getFont(FontFactory.HELVETICA, 10)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tablaPDF.addCell(cell);
				}

				// Agregar filas y celdas de datos a la tabla PDF (excluyendo la última columna)
				for (int fila = 0; fila < table.getRowCount(); fila++) {
					for (int columna = 0; columna < table.getColumnCount() - 1; columna++) {
						Object valorCelda = table.getValueAt(fila, columna);
						// Verificar si el valor de la celda es null antes de llamar a toString()
						if (valorCelda != null) {
							PdfPCell cell = new PdfPCell(
									new Phrase(valorCelda.toString(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tablaPDF.addCell(cell);
						} else {
							tablaPDF.addCell("");
						}
					}
				}

				documento.add(tablaPDF);

				documento.close();

				// Abrir el archivo PDF con el programa predeterminado
				File pdfFile = new File(rutaPDF);
				if (pdfFile.exists()) {
					try {
						if (Desktop.isDesktopSupported()) {
							Desktop.getDesktop().open(pdfFile);
						} else {
							JOptionPane.showMessageDialog(null,
									"No se puede abrir el PDF automáticamente en este sistema.");
						}
					} catch (IOException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error al abrir el archivo PDF: " + ex.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "El archivo PDF no se ha creado correctamente.");
				}

				JOptionPane.showMessageDialog(null, "Tabla exportada correctamente a PDF.");

			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al exportar tabla a PDF: " + ex.getMessage());
			}
		}
	}

}
