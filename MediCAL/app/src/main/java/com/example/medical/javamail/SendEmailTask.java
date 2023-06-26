package com.example.medical.javamail;
import android.os.AsyncTask;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailTask extends AsyncTask<Void, Void, Void> {

    private String emailAddress;
    private String verificationCode;

    public SendEmailTask(String emailAddress, String verificationCode) {
        this.emailAddress = emailAddress;
        this.verificationCode = verificationCode;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Configurar las propiedades del servidor de correo
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Cambia esto si utilizas otro proveedor de correo
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        // Iniciar sesión con las credenciales del correo
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("noreplymedicalapputn@gmail.com", "lybrzacxkmmqcmmv");
            }
        });

        try {
            // Crear el mensaje de correo
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("noreplymedicalapputn@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            message.setSubject("Código de verificación");
            message.setText("Tu código de verificación es: " + verificationCode);

            // Enviar el mensaje
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }
}

