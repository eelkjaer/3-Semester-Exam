package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.Persistence;
import org.apache.commons.io.IOUtils;

public class JavaXEmailService {

  private final String subject = "Du har modtaget svar i TheQ";
  private final String smtpHost = "smtp.eu.mailgun.org";
  private final int smtpPort = 587;
  private final boolean smtpAuth = true;
  private final boolean smtpTls = true;
  private final String smtpUser = "postmaster@mail.codergram.me";
  private final String smtpSentfrom = "theq@mail.codergram.me";
  private final String smtpSentfromName = "TheQ";

  private final Properties props;

  public JavaXEmailService() {
    Properties props = new Properties();
    props.setProperty("mail.smtp.host", smtpHost);
    props.setProperty("mail.smtp.port", String.valueOf(smtpPort));
    props.setProperty("mail.smtp.auth", String.valueOf(smtpAuth));
    props.setProperty("mail.smtp.starttls.enable", String.valueOf(smtpTls));
    props.setProperty("USERNAME", smtpUser);
    props.setProperty("SENT_FROM", smtpSentfrom);
    props.setProperty("SENT_FROM_NAME", smtpSentfromName);

    boolean isDeployed = (System.getenv("DEPLOYED") != null);
    if (isDeployed) {
      System.out.println("USING ENVIRONMENT VARIABLES");
      System.out.println("PW             -->" + System.getenv("MAILGUN_PW"));
      String pw = System.getenv("MAILGUN_PW");
      props.setProperty("PASSWORD", pw);
    } else {
      try (InputStream input =
          JavaXEmailService.class.getClassLoader().getResourceAsStream("mail/config.properties")) {
        //props.load(input);
        props.setProperty("PASSWORD", "deployment");
      } catch (IOException ex) {
        System.out.println(ex.getMessage());
      }
    }
    this.props = props;
  }

  private static String fileToString() {
    try (InputStream input = JavaXEmailService.class.getClassLoader().getResourceAsStream(
        "mail/template.html")) {
      if (input == null) return null;
      return IOUtils.toString(input);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public String createMessage(String message){
      return fileToString()
          .replace("$$OVERSKRIFT$$", subject)
          .replace("$$TEKST$$", message);
  }

  public void sendEmail(String toAddress, String message)
      throws Exception {
    Authenticator auth =
        new Authenticator() {
          @Override
          public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(
                props.getProperty("USERNAME"), props.getProperty("PASSWORD"));
          }
        };

    Session session = Session.getInstance(props, auth);

    Message msg = new MimeMessage(session);
    Multipart multipart = new MimeMultipart();

    try {
      msg.setFrom(
          new InternetAddress(
              props.getProperty("SENT_FROM"), props.getProperty("SENT_FROM_NAME")));
      InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
      msg.setRecipients(Message.RecipientType.TO, toAddresses);
      msg.setSubject(subject);
      msg.setSentDate(new Date());

      // Bodypart for text
      MimeBodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setContent(message, "text/html; charset=utf-8");


      multipart.addBodyPart(messageBodyPart);

      // Sets content of message
      msg.setContent(multipart);

      // Sends mail
      Transport.send(msg);

    } catch (UnsupportedEncodingException | MessagingException e) {
      throw new Exception(e);
    }
  }
}