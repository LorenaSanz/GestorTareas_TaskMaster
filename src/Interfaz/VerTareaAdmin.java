package Interfaz;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VerTareaAdmin extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfCodigo;
	private JTextField tfTitulo;
	private JTextArea taDescripcion;
	private JButton btnBotonModerno;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerTareaAdmin frame = new VerTareaAdmin();
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
	public VerTareaAdmin() {
		setResizable(false);
		setTitle("Ver Tarea");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VerTareaAdmin.class.getResource("/Imagenes/LOGO TASKMASTER XS.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 312);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE); // Establecemos el color de fondo del panel
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCodigo = new JLabel("Código:");
		lblCodigo.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblCodigo.setBounds(25, 22, 61, 16);
		contentPane.add(lblCodigo);

		tfCodigo = new JTextField();
		tfCodigo.setEditable(false); // Hacemos que el campo no sea editable
		tfCodigo.setBounds(105, 20, 42, 22);
		contentPane.add(tfCodigo);
		tfCodigo.setColumns(10);

		JLabel lblTitulo = new JLabel("Título:");
		lblTitulo.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblTitulo.setBounds(25, 61, 61, 16);
		contentPane.add(lblTitulo);

		tfTitulo = new JTextField();
		tfTitulo.setEditable(false); // Hacemos que el campo no sea editable
		tfTitulo.setColumns(10);
		tfTitulo.setBounds(105, 59, 309, 22);
		contentPane.add(tfTitulo);

		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblDescripcion.setBounds(25, 100, 82, 16);
		contentPane.add(lblDescripcion);

		// Usamos JTextArea para la descripción
		taDescripcion = new JTextArea();
		taDescripcion.setEditable(false); // Hacemos que el campo no sea editable
		taDescripcion.setLineWrap(true); // Permitimos que el texto se ajuste automáticamente al tamaño del área
		taDescripcion.setWrapStyleWord(true); // Evitamos que las palabras se corten
		JScrollPane scrollPane = new JScrollPane(taDescripcion);
		scrollPane.setBounds(105, 100, 309, 155); // Ajustamos el tamaño del JScrollPane
		contentPane.add(scrollPane);

		// Crear y configurar el botón con relieve
		btnBotonModerno = new JButton("OK");
		btnBotonModerno.addActionListener(this);
		btnBotonModerno.setFont(new Font("Century Gothic", Font.BOLD, 12));
		btnBotonModerno.setBounds(25, 215, 61, 40);
		btnBotonModerno.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBotonModerno.setBackground(new Color(230, 204, 255)); // Establecemos el color lila claro
		btnBotonModerno.setFocusPainted(false);
		contentPane.add(btnBotonModerno);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon(VerTareaAdmin.class.getResource("/Imagenes/WALLPAPER_LOGOS_M.png")));
		lblFondo.setBounds(0, -73, 409, 275);
		contentPane.add(lblFondo);
	}

	public void mostrarDatosTarea(String codigo, String titulo, String descripcion) {
		tfCodigo.setText(codigo);
		tfTitulo.setText(titulo);
		taDescripcion.setText(descripcion); // Establecemos el texto en JTextArea
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnBotonModerno)) {
			dispose();

		}

	}
}
