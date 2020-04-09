package domain;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Alert {
    private Set<Subscriber> mailAlertList;
    private Set<Subscriber> inSystemAlertList;

/////////// Constructor ///////////
    public Alert() {
        this.mailAlertList = new HashSet<>();
        this.inSystemAlertList = new HashSet<>();
    }


/////////// Functionality ///////////
    public void addToMailSet(Subscriber user){
        this.mailAlertList.add(user);
    }

    public void addToSystemSet(Subscriber user){
        this.inSystemAlertList.add(user);
    }

    public void removeFromMailSet(Subscriber user){
        this.mailAlertList.remove(user);
    }

    public void removeFromSystemSet(Subscriber user){
        this.inSystemAlertList.remove(user);
    }

    /**
     * public function to send alert to user. Chooses from list where to send the alert: mail or system
     * @param alertMessage
     */
    public void sendAlert(String alertMessage){
        for(Subscriber user : this.mailAlertList){
            sendMailAlert(user.getMail(), alertMessage);
        }

        for(Subscriber user : this.inSystemAlertList){
            sendInSystemAlert(user, alertMessage);
        }
    }

    // Taken from https://www.tutorialspoint.com/java/java_sending_email.htm
    /**
     * send alert to mail
     * @param to
     * @param alertMessage
     * @return
     */
    private boolean sendMailAlert(String to, String alertMessage){
        // Recipient's email ID needs to be mentioned.
        //String to = mail;

        // Sender's email ID needs to be mentioned
        String from = "euguman@gmail.com";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        // if problem: https://stackoverflow.com/questions/40650229/getting-syntax-error-in-property-setpropertymail-smtp-host-host
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Football System Alert!");

            // Now set the actual message
            message.setText(alertMessage);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Send alert to user in system
     * @param user
     * @param alertMessage
     * @return
     */
    private boolean sendInSystemAlert(Subscriber user, String alertMessage){
        user.addAlertMessage(alertMessage);
        return true;
    }


/////////// Getters and Setters ///////////
    public Set<Subscriber> getMailAlertList() {
        return mailAlertList;
    }

    public Set<Subscriber> getInSystemAlertList() {
        return inSystemAlertList;
    }
}
