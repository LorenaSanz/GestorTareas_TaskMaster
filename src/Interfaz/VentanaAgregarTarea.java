package Interfaz;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


import com.mysql.jdbc.Statement;
import com.toedter.calendar.JCalendar;


import BaseDatos.Conexion;
import BaseDatos.GestionBD;
import Clases.Metodos;


public class VentanaAgregarTarea extends JFrame implements ActionListener {


    private static final long serialVersionUID = 1L;
	public static String prioridad;
    private JPanel contentPane;
    private JTextField tfCodigo;
    private JTextField tfTitulo;
    private JTextField tfDescripcion;
    private JComboBox<String> comboBox;
    private JCalendar calendar;
    private JButton btnConfirmar;
    private JButton btnLimpiar;
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
                    VentanaAgregarTarea frame = new VentanaAgregarTarea();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public VentanaAgregarTarea() {
    	setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaAgregarTarea.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
        setTitle("Agregar Tarea");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(360, 200, 450, 450);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);


        setContentPane(contentPane);
        contentPane.setLayout(null);


        btnConfirmar = new JButton("");
        btnConfirmar.setToolTipText("Agregar Tarea");
        btnConfirmar.addActionListener(this);
        btnConfirmar.setIcon(new ImageIcon(VentanaAgregarTarea.class.getResource("/Imagenes/TIC.png")));
        btnConfirmar.setBounds(150, 357, 36, 36);
        btnConfirmar.setFocusPainted(false);
        contentPane.add(btnConfirmar);


        btnLimpiar = new JButton("");
        btnLimpiar.setToolTipText("LImpiar");
        btnLimpiar.addActionListener(this);
        btnLimpiar.setIcon(new ImageIcon(VentanaAgregarTarea.class.getResource("/Imagenes/BASURA.png")));
        btnLimpiar.setBounds(232, 357, 36, 36);
        btnLimpiar.setFocusPainted(false);
        contentPane.add(btnLimpiar);


        JLabel lblCodigo = new JLabel("Código");
        lblCodigo.setFont(new Font("Century Gothic", Font.BOLD, 11));
        lblCodigo.setBounds(21, 26, 45, 13);
        contentPane.add(lblCodigo);


        tfCodigo = new JTextField();
        tfCodigo.addActionListener(this);
        tfCodigo.setBounds(21, 49, 45, 19);
        contentPane.add(tfCodigo);
        tfCodigo.setColumns(10);


        JLabel lblTitulo = new JLabel("Titulo");
        lblTitulo.setFont(new Font("Century Gothic", Font.BOLD, 11));
        lblTitulo.setBounds(21, 102, 45, 13);
        contentPane.add(lblTitulo);


        tfTitulo = new JTextField();
        tfTitulo.addActionListener(this);
        tfTitulo.setColumns(10);
        tfTitulo.setBounds(21, 125, 151, 19);
        contentPane.add(tfTitulo);


        JLabel lblFecha = new JLabel("Fecha");
        lblFecha.setFont(new Font("Century Gothic", Font.BOLD, 11));
        lblFecha.setBounds(195, 26, 45, 13);
        contentPane.add(lblFecha);


        calendar = new JCalendar();
        calendar.getYearChooser().getSpinner().setFont(new Font("Century Gothic", Font.PLAIN, 10));
        calendar.getMonthChooser().getComboBox().setFont(new Font("Century Gothic", Font.PLAIN, 10));
        calendar.setDecorationBackgroundColor(new Color(204, 255, 204));
        calendar.getDayChooser().setSundayForeground(new Color(255, 255, 255));
        calendar.getDayChooser().setWeekdayForeground(new Color(255, 255, 255));
        calendar.getDayChooser().setDecorationBackgroundColor(new Color(187, 174, 225));
        calendar.setBounds(195, 49, 216, 152);
        contentPane.add(calendar);


        JLabel lblDescipcion = new JLabel("Descripción");
        lblDescipcion.setFont(new Font("Century Gothic", Font.BOLD, 11));
        lblDescipcion.setBounds(21, 205, 70, 13);
        contentPane.add(lblDescipcion);


        comboBox = new JComboBox<>();
        comboBox.addActionListener(this);
        comboBox.setFont(new Font("Century Gothic", Font.PLAIN, 10));
        comboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Sin Asignar"}));
        comboBox.setBounds(21, 297, 151, 21);
        contentPane.add(comboBox);


        JLabel lblSeleccion = new JLabel("Seleccione si desea asignar un empleado");
        lblSeleccion.setFont(new Font("Century Gothic", Font.BOLD, 11));
        lblSeleccion.setBounds(21, 275, 360, 13);
        contentPane.add(lblSeleccion);


        tfDescripcion = new JTextField();
        tfDescripcion.setBounds(21, 228, 390, 21);
        contentPane.add(tfDescripcion);
        tfDescripcion.setColumns(10);


        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(VentanaAgregarTarea.class.getResource("/Imagenes/WALLPAPER_LOGOS_M.png")));
        lblNewLabel.setBounds(-135, -45, 745, 589);
        contentPane.add(lblNewLabel);


        gb = new GestionBD();
        cn = new Conexion();


        try {
            mostrarUsuariosEnComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void mostrarUsuariosEnComboBox() throws SQLException {
        con = conexion.getConexion();
        String sql = "SELECT usuarios.dni, usuarios.nombre FROM usuarios INNER JOIN empleados ON usuarios.dni = empleados.dni";
        try {
            st = (Statement) con.createStatement();
            resultado = st.executeQuery(sql);
            while (resultado.next()) {
                String dni = resultado.getString("dni");
                String nombreUsuario = resultado.getString("nombre");
                // Agregar el nombre y el DNI del empleado al JComboBox
                comboBox.addItem(nombreUsuario + " - " + dni);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Siempre cierra los recursos de la base de datos después de usarlos
            if (resultado != null) {
                resultado.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnConfirmar)) {
            String codigo = tfCodigo.getText();
            String titulo = tfTitulo.getText();
            String descripcion = tfDescripcion.getText();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaSeleccionada = calendar.getDate();
            Date fechaActual = new Date(); // Obtener la fecha actual del sistema
            
            // Comparar las fechas
            if (fechaSeleccionada.before(fechaActual)) {
                // Mostrar mensaje de error si la fecha seleccionada es anterior a la fecha actual
                JOptionPane.showMessageDialog(null, "La fecha seleccionada es anterior a la fecha actual.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método actionPerformed si la fecha seleccionada es anterior
            }
            
            String fechaFin = dateFormat.format(fechaSeleccionada);
            String empleadoAsignado = null;
            
            // Llamar al método fijarPrioridad para calcular la prioridad
            Metodos.fijarPrioridad(fechaFin);
            
            try {
                // Verificar si el código de tarea ya existe
                boolean existeTarea = gb.existeTarea(codigo);
                
                if (existeTarea) {
                    // Mostrar mensaje de error
                    JOptionPane.showMessageDialog(null, "Ya existe una tarea con el código especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (comboBox.getSelectedIndex() > 0) {
                        // Obtener el valor seleccionado en el JComboBox
                        String seleccionado = comboBox.getSelectedItem().toString();
                        // Extraer el DNI del valor seleccionado
                        empleadoAsignado = seleccionado.substring(seleccionado.lastIndexOf(" ") + 1);
                    }
                    
                    if (empleadoAsignado != null) {
                        boolean insertado = gb.insertarTareaAsignada(codigo, titulo, descripcion, fechaFin, prioridad, empleadoAsignado);
                        if (insertado) {
                            System.out.println("Tarea asignada correctamente.");
                        } else {
                            System.out.println("Error al asignar la tarea.");
                        }
                    } else {
                        boolean insertado = gb.insertarTareaComun(codigo, titulo, descripcion, fechaFin, prioridad);
                        if (insertado) {
                            System.out.println("Tarea insertada correctamente.");
                        } else {
                            System.out.println("Error al insertar la tarea.");
                        }
                    }
                    dispose();
                    tfCodigo.setText("");
                    tfTitulo.setText("");
                    tfDescripcion.setText("");
                    comboBox.setSelectedIndex(0);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Error al insertar la tarea: " + ex.getMessage());
            }
        }
    
    

        if (e.getSource().equals(btnLimpiar)) {
            // Establecer el texto de cada JTextField en una cadena vacía
            tfCodigo.setText("");
            tfTitulo.setText("");
            tfDescripcion.setText("");
            comboBox.setSelectedIndex(0);
        }
    }

}
	
