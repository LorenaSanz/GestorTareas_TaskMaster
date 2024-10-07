package Interfaz;
import javax.swing.*;

import com.mysql.jdbc.Statement;

import BaseDatos.Conexion;
import BaseDatos.GestionBD;
import Clases.SesionIniciada;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Tablon extends JFrame {
    private JTextArea areaNotas;
    private JTextField mensaje;
    private JButton BtnEnviar;
    private JButton emoji; // Botón para emojis
    private JPopupMenu emojiMenu; // Menú emergente para emojis
    private GestionBD gb;

    public Tablon() {
    	setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(Tablon.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
        setTitle("Tablón");
        setSize(475, 506);
        setBounds(1169, 200, 475, 395);
        

        // Panel para la entrada de mensajes
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        // Área para mostrar las notas
        areaNotas = new JTextArea();
        areaNotas.setBackground(new Color(225, 225, 255));
        areaNotas.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        areaNotas.setEditable(false);
        areaNotas.setLineWrap(true); // Hacer saltos de línea automáticos
        areaNotas.setWrapStyleWord(true); // Romper líneas solo en espacios entre palabras

        JScrollPane scrollPane = new JScrollPane(areaNotas);

        // Panel para los botones de emojis y enviar
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Alinear los botones a la izquierda

        mensaje = new JTextField();
        BtnEnviar = new JButton("Enviar");
        BtnEnviar.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        BtnEnviar.setFocusPainted(false);
        BtnEnviar.addActionListener(e -> postMessage());

        // Crear el menú emergente para emojis
        emojiMenu = new JPopupMenu();
        String[] emojis = {"😊", "😄","❤️", "😆", "😂", "😍","💩", "😎", "😜", "👍", "👏", "😡"}; // Ejemplos de emojis, puedes agregar más
        for (String BtnEmoji : emojis) {
            JMenuItem item = new JMenuItem(BtnEmoji);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Insertar emoji seleccionado en el campo de mensaje
                    String currentMessage = mensaje.getText();
                    mensaje.setText(currentMessage + " " + BtnEmoji);
                }
            });
            emojiMenu.add(item);
        }
        buttonPanel.add(BtnEnviar);

        // Añadir el campo de texto al panel de entrada de mensajes
        inputPanel.add(mensaje, BorderLayout.CENTER);

        // Añadir el panel de botones al panel de entrada de mensajes
        inputPanel.add(buttonPanel, BorderLayout.EAST);

        // Agregar el área de texto y el panel de entrada de mensajes al JFrame
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);

        // Botón para emojis
        JButton BtnEmoji = new JButton("😊"); // Emoji predeterminado
        inputPanel.add(BtnEmoji, BorderLayout.WEST);
        BtnEmoji.setFocusPainted(false);
        BtnEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14)); // Fuente para emojis

        // Agregar el evento de clic al botón para mostrar el menú emergente
        BtnEmoji.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = 0; // Posición x
                int y = -emojiMenu.getHeight(); // Posición y para que aparezca hacia arriba
                emojiMenu.show(BtnEmoji, x, y);
            }
        });

        // Inicializar la clase de gestión de base de datos
        gb = new GestionBD();

        // Mostrar todos los mensajes existentes al iniciar la aplicación
        showAllMessages();
    }

    private void postMessage() {
        String mensajeTexto = mensaje.getText();
        String dniEmpleado = SesionIniciada.getLogeado(); // Obtener el DNI del empleado

        // Guardar el mensaje en la base de datos
        gb.saveMessageToDatabase(dniEmpleado, mensajeTexto);

        // Mostrar el mensaje en el JTextArea con el nombre del empleado en lugar del DNI
        String nombreEmpleado = gb.getUserName(dniEmpleado);
        areaNotas.append("■" +nombreEmpleado + ": " + mensajeTexto + "\n\n");
        mensaje.setText(""); // Limpiar el campo de mensaje
    }

    public void showAllMessages() {
        try {
            ResultSet rs = gb.getAllMessages();
            while (rs.next()) {
                String nombreUsuario = rs.getString("nombre");
                String mensaje = rs.getString("mensaje");
                areaNotas.append("■"+nombreUsuario + ": " + mensaje + "\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tablon app = new Tablon();
            app.setVisible(true);
        });
    }
}

