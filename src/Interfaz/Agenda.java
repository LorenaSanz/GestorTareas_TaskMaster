package Interfaz;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JCalendar;
import java.awt.Toolkit;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import BaseDatos.GestionBD;
import Clases.SesionIniciada;

public class Agenda extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JCalendar calendario_1;
    private JTextArea textAreaTexto;
    private GestionBD gb;
    private String dniEmpleado;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Agenda frame = new Agenda();
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
    public Agenda() {
    	setResizable(false);
        setTitle("Agenda personal");
        setIconImage(Toolkit.getDefaultToolkit().getImage(Agenda.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(300, 200, 450, 498);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        
        gb = new GestionBD(); // Inicializamos el objeto GestionBD
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setForeground(new Color(128, 128, 255));
        btnGuardar.addActionListener(this);
        btnGuardar.setBounds(0, 430, 436, 31);
        btnGuardar.setFocusPainted(false);
        contentPane.add(btnGuardar);
        
        calendario_1 = new JCalendar();
        calendario_1.getYearChooser().getSpinner().setFont(new Font("Century Gothic", Font.PLAIN, 11));
        calendario_1.getMonthChooser().getComboBox().setFont(new Font("Century Gothic", Font.PLAIN, 11));
        calendario_1.getDayChooser().setSundayForeground(new Color(134, 22, 201));
        calendario_1.getDayChooser().getDayPanel().setBackground(new Color(215, 187, 253));
        calendario_1.getDayChooser().setWeekdayForeground(new Color(255, 255, 255));
        calendario_1.getDayChooser().setDecorationBackgroundColor(new Color(170, 170, 255));
        calendario_1.setBounds(0, 26, 436, 212);
        contentPane.add(calendario_1);
        
        JLabel lblSesionIniciada = new JLabel("Sesión iniciada: ");
        lblSesionIniciada.setBounds(0, 0, 190, 23);
        contentPane.add(lblSesionIniciada);
        
        textAreaTexto = new JTextArea();
        textAreaTexto.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        textAreaTexto.setBackground(new Color(255, 255, 189));
        textAreaTexto.setBounds(10, 277, 416, 152);
        contentPane.add(textAreaTexto);
        
        JLabel lblEscriba = new JLabel("Area de notas:");
        lblEscriba.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        lblEscriba.setBounds(10, 254, 147, 13);
        contentPane.add(lblEscriba);
        dniEmpleado = SesionIniciada.getLogeado();
        if (dniEmpleado != null) {
            lblSesionIniciada.setText("Sesión iniciada: " + dniEmpleado);
        }
        
        // Agregar un PropertyChangeListener al calendario para detectar cambios en la fecha seleccionada
        calendario_1.addPropertyChangeListener("calendar", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                mostrarTareas();
            }
        });
    }

    // Método para mostrar las tareas para la fecha seleccionada
    public void mostrarTareas() {
        // Obtener la fecha seleccionada del calendario
        int year = calendario_1.getCalendar().get(java.util.Calendar.YEAR);
        int month = calendario_1.getCalendar().get(java.util.Calendar.MONTH) + 1; // Sumamos 1 porque los meses van de 0 a 11
        int day = calendario_1.getCalendar().get(java.util.Calendar.DAY_OF_MONTH);
        
        // Formatear la fecha en formato deseado
        String fechaSeleccionada = String.format("%04d-%02d-%02d", year, month, day);
        
        // Obtener el texto de la agenda para la fecha seleccionada
        String textoExistente = obtenerTextoAgenda(fechaSeleccionada);
        
        // Mostrar el texto en el JTextArea
        textAreaTexto.setText(textoExistente);
    }

    // Método para obtener el texto de la agenda para una fecha específica
    public String obtenerTextoAgenda(String fecha) {
        String textoExistente = "";
        try {
            textoExistente = gb.obtenerTextoAgenda(fecha,dniEmpleado);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return textoExistente;
    }

    // Método para guardar una tarea en la base de datos
    private void guardarTarea() {
        // Obtener la fecha seleccionada del calendario
        int year = calendario_1.getCalendar().get(java.util.Calendar.YEAR);
        int month = calendario_1.getCalendar().get(java.util.Calendar.MONTH) + 1; // Sumamos 1 porque los meses van de 0 a 11
        int day = calendario_1.getCalendar().get(java.util.Calendar.DAY_OF_MONTH);
        
        // Formatear la fecha en formato deseado
        String fechaSeleccionada = String.format("%04d-%02d-%02d", year, month, day);
        String tarea = textAreaTexto.getText();
        
        // Actualizar la tarea en la base de datos solo si se ha ingresado algo en el texto
        if (!tarea.isEmpty()) {
            // Realizar un select para obtener la información existente en la base de datos para la fecha seleccionada
            String textoExistente = obtenerTextoAgenda(fechaSeleccionada);
            
            // Si ya existe texto para esa fecha, actualizamos; si no, insertamos
            if (!textoExistente.isEmpty()) {
                // Ya existe texto para esta fecha, realizamos un update
                try {
                    gb.actualizarAgenda(fechaSeleccionada, tarea, dniEmpleado);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                // No hay texto para esta fecha, realizamos un insert
                try {
                    gb.agregarAgenda(fechaSeleccionada, tarea, dniEmpleado);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        // Actualizar las tareas mostradas
        mostrarTareas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton btn = (JButton) e.getSource();
            if (btn.getText().equals("Guardar")) {
                guardarTarea();
                
            }
        }
    }
}
