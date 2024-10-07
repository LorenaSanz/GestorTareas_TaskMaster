package Interfaz;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import BaseDatos.GestionBD;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Desktop;
import java.awt.Font;

public class VentanaGestionTareas extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private DefaultTableModel modelo = new DefaultTableModel();// Modelado de la tabla
    private JTable table;
    private JButton btnAgregarTarea;
    private JButton btnEliminarTarea;
    private VentanaAgregarTarea at = new VentanaAgregarTarea();
    private JButton btnAtras;
    private VentanaAdmin a1 = new VentanaAdmin();
    private VentanaBorrarTarea bt = new VentanaBorrarTarea();
    private GestionBD gb;
    private JButton btnVerTarea;
    private VerTareaAdmin vta = new VerTareaAdmin();
    private JButton btnRefrescar;
    private JButton btnFiltrar; // Botón para aplicar el filtro
    private JComboBox<String> comboBoxUrgencia;
    private JTextField txtDNI;
    private JButton btnExportarPDF;
    private JComboBox<String> comboBoxEstado; // Combo box para el estado de la tarea

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaGestionTareas frame = new VentanaGestionTareas();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public VentanaGestionTareas() {
    	setResizable(false);
        setTitle("Gestión de Tareas");
        setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaGestionTareas.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 989, 651);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);

        btnAtras = new JButton("");
        btnAtras.setToolTipText("Atrás");
        btnAtras.addActionListener(this);
        btnAtras.setIcon(new ImageIcon(VentanaGestionTareas.class.getResource("/Imagenes/BOTON_ATRAS.png")));
        btnAtras.setBounds(913, 552, 52, 52);
        btnAtras.setFocusPainted(false);
        contentPane.add(btnAtras);
        btnAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

        scrollPane = new JScrollPane();
        scrollPane.setToolTipText("");
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(59, 89, 855, 380);
        contentPane.add(scrollPane);

        gb = new GestionBD();

        table = new JTable();
        table.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 12)); // Establecer fuente personalizada para los encabezados de columna
        table.setFont(new Font("Century Gothic", Font.PLAIN, 12)); // Establecer fuente para el contenido de la tabla
        cargarTareasEnTabla();
        scrollPane.setViewportView(table);
        

        btnAgregarTarea = new JButton("");
        btnAgregarTarea.setToolTipText("Añadir tarea");
        btnAgregarTarea.setIcon(new ImageIcon(VentanaGestionTareas.class.getResource("/Imagenes/mas.png")));
        btnAgregarTarea.addActionListener(this);
        btnAgregarTarea.setBounds(59, 479, 85, 21);
        btnAgregarTarea.setFocusPainted(false);
        contentPane.add(btnAgregarTarea);
        btnAgregarTarea.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

        btnEliminarTarea = new JButton("");
        btnEliminarTarea.setToolTipText("Eliminar tarea");
        btnEliminarTarea.setIcon(new ImageIcon(VentanaGestionTareas.class.getResource("/Imagenes/menos.png")));
        btnEliminarTarea.addActionListener(this);
        btnEliminarTarea.setBounds(154, 479, 85, 21);
        btnEliminarTarea.setFocusPainted(false);
        contentPane.add(btnEliminarTarea);
        btnEliminarTarea.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        
        btnVerTarea = new JButton("");
        btnVerTarea.addActionListener(this);
        btnVerTarea.setIcon(new ImageIcon(VentanaGestionTareas.class.getResource("/Imagenes/ojo4.png")));
        btnVerTarea.setToolTipText("Ver ficha empleado");
        btnVerTarea.setBounds(829, 50, 85, 29);
        btnVerTarea.setFocusPainted(false);
        contentPane.add(btnVerTarea);
        btnVerTarea.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        
        btnRefrescar = new JButton("");
        btnRefrescar.setIcon(new ImageIcon(VentanaGestionTareas.class.getResource("/Imagenes/REFRESH.png")));
        btnRefrescar.addActionListener(this);
        btnRefrescar.setBounds(59, 50, 34, 29);
        btnRefrescar.setFocusPainted(false);
        contentPane.add(btnRefrescar);
        btnRefrescar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        

        btnExportarPDF = new JButton("Exportar");
        btnExportarPDF.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        btnExportarPDF.setToolTipText("Exportar tabla a PDF");
        btnExportarPDF.addActionListener(this);
        btnExportarPDF.setIcon(new ImageIcon(VentanaGestionEmpleados.class.getResource("/Imagenes/PDF_XS.png")));
        btnExportarPDF.setBounds(787, 479, 127, 36);
        btnExportarPDF.setFocusPainted(false);
        contentPane.add(btnExportarPDF);
        btnExportarPDF.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

        comboBoxUrgencia = new JComboBox<String>();
        comboBoxUrgencia.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        comboBoxUrgencia.addItem("Todas"); // Opción por defecto
        comboBoxUrgencia.addItem("Urgente");
        comboBoxUrgencia.addItem("Importante");
        comboBoxUrgencia.addItem("No urgente");
        comboBoxUrgencia.setBounds(260, 50, 100, 29);
        contentPane.add(comboBoxUrgencia);

        txtDNI = new JTextField();
        txtDNI.setToolTipText("DNI...");
        txtDNI.setBounds(380, 50, 100, 29);
        contentPane.add(txtDNI);
        txtDNI.setColumns(10);

        comboBoxEstado = new JComboBox<String>(); // Combo box para el estado de la tarea
        comboBoxEstado.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        comboBoxEstado.addItem("Todas"); // Opción por defecto
        comboBoxEstado.addItem("Sin Firmar");
        comboBoxEstado.addItem("Firmada");
        comboBoxEstado.setBounds(500, 50, 100, 29);
        contentPane.add(comboBoxEstado);

        btnFiltrar = new JButton(""); // Botón para aplicar el filtro
        btnFiltrar.setIcon(new ImageIcon(VentanaGestionTareas.class.getResource("/Imagenes/FILTRO_XS.png")));
        btnFiltrar.addActionListener(this);
        btnFiltrar.setBounds(620, 50, 34, 29);
        btnFiltrar.setFocusPainted(false);
        contentPane.add(btnFiltrar);
    }

    private void cargarTareasEnTabla() {
        try {
            DefaultTableModel modeloTabla = gb.mostrarTareasFiltradas("Todas", "", "Todas"); // Estado por defecto
            table.setModel(modeloTabla);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar tareas: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnRefrescar)) {
            cargarTareasEnTabla();
        }
        if (e.getSource().equals(btnAgregarTarea)) {
            at.setVisible(true);
           
        }
        if (e.getSource().equals(btnAtras)) {
            a1.setVisible(true);
            dispose();
        }
        if (e.getSource().equals(btnEliminarTarea)) {
            int filaSeleccionada = table.getSelectedRow();
            if (filaSeleccionada != -1) {
                String codigo = (String) table.getValueAt(filaSeleccionada, 0);
                bt.setCodigoTarea(codigo); // Pasar el código de la tarea a la ventana de confirmación
                bt.setVisible(true);
                
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una tarea de la tabla.");
            } 
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
        if (e.getSource().equals(btnFiltrar)) {
            try {
                String prioridad = comboBoxUrgencia.getSelectedItem().toString();
                String dni = txtDNI.getText();
                String estado = comboBoxEstado.getSelectedItem().toString();
              
                DefaultTableModel modeloTabla = gb.mostrarTareasFiltradas(prioridad, dni, estado);
                table.setModel(modeloTabla);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al filtrar tareas: " + ex.getMessage());
            }
        }


        if (e.getSource().equals(btnExportarPDF)) {
            try {
                // Crear un objeto Document
                Document documento = new Document(PageSize.A4);

                // Definir la ruta del archivo PDF
                String rutaPDF = "DocTareas.pdf";

                // Crear un objeto PdfWriter para escribir en el documento PDF
                PdfWriter.getInstance(documento, new FileOutputStream(rutaPDF));

                // Abrir el documento para comenzar a escribir
                documento.open();

                // Añadir título al documento
                Paragraph titulo = new Paragraph("Informe de Tareas\n\n", FontFactory.getFont(FontFactory.HELVETICA, 18));
                titulo.setAlignment(Element.ALIGN_CENTER);
                documento.add(titulo);

                // Crear un objeto PdfPTable para representar la tabla
                PdfPTable tablaPDF = new PdfPTable(table.getColumnCount());

                // Establecer el ancho de las columnas para ajustarse automáticamente
                tablaPDF.setWidthPercentage(100);

                // Agregar encabezados de columna a la tabla PDF
                for (int i = 0; i < table.getColumnCount(); i++) {
                    PdfPCell cell = new PdfPCell(new Phrase(table.getColumnName(i), FontFactory.getFont(FontFactory.HELVETICA, 10)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablaPDF.addCell(cell);
                }

                // Agregar filas y celdas de datos a la tabla PDF
                for (int fila = 0; fila < table.getRowCount(); fila++) {
                    for (int columna = 0; columna < table.getColumnCount(); columna++) {
                        Object valorCelda = table.getValueAt(fila, columna);
                        // Verificar si el valor de la celda es null antes de llamar a toString()
                        if (valorCelda != null) {
                            PdfPCell cell = new PdfPCell(new Phrase(valorCelda.toString(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
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
                            JOptionPane.showMessageDialog(null, "No se puede abrir el PDF automáticamente en este sistema.");
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
