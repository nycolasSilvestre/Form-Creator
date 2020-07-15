import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class SendEmailTLS {

        public static void main(String[] args) {

            final String username = "30000763@students.ual.pt";
            final String password = "Nsbjj150608!";
//
            Properties prop = new Properties();
//            gmail
//            prop.put("mail.smtp.host", "smtp.gmail.com");
//            prop.put("mail.smtp.port", "587");
//            prop.put("mail.smtp.auth", "true");
//            prop.put("mail.smtp.starttls.enable", "true"); //TLS

//            outlook
//            prop.put("mail.smtp.host", "outlook.office365.com");
//            prop.put("mail.smtp.port", "587");
//            prop.put("mail.smtp.auth", "true");
//            prop.put("mail.smtp.starttls.enable", "true");

//          yahoo
            prop.put("mail.smtp.host", "smtp.mail.yahoo.com");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.port", "25");

            Session session = Session.getInstance(prop,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("30000763@students.ual.pt"));
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse("nycolaszsilvestre@gmail.com, 30000763@students.ual.pt")
                );
                message.setSubject("Testing out TLS");
                message.setText("Dear Mail Crawler,"
                        + "\n\n Please do not spam my email!");
                BodyPart messageBodyPart = new MimeBodyPart();

                // Now set the actual message
                messageBodyPart.setText("This is message body");

                // Create a multipar message
                Multipart multipart = new MimeMultipart();

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                // Part two is attachment
                messageBodyPart = new MimeBodyPart();
                String filename = "D:\\fichaDoAluno.pdf";
                DataSource source = new FileDataSource(filename);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName("filename.pdf");
                multipart.addBodyPart(messageBodyPart);

                // Send the complete message parts
                message.setContent(multipart);

                // Send message
                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

}