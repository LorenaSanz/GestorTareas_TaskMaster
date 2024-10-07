package Clases;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class correo {

	public static void enviarCorreo(String destinatario, String contrasenna) {
        // Datos del remitente
        String remitente = "holataskmaster@gmail.com"; // Cambia esto por tu dirección de correo electrónico
        String password = "pzve ewvb drku kxwi"; // Cambia esto por tu contraseña de aplicación de Gmail

        // Configuración de propiedades para Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Crear una sesión de correo
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });

        try {
            // Crear un objeto MimeMessage
            Message message = new MimeMessage(session);

            // Configurar el remitente
            message.setFrom(new InternetAddress(remitente));

            // Configurar el destinatario
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));

            // Configurar el asunto
            message.setSubject("Hola usuari@");

            // Configurar el contenido del correo
            message.setText("Hola,\n\nEsta es la contraseña para su inicio de sesión en TaskMaster: " + contrasenna);

            // Enviar el mensaje
            Transport.send(message);

            System.out.println("¡El correo electrónico ha sido enviado correctamente!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}