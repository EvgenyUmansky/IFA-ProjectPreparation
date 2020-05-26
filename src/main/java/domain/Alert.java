package domain;

import org.apache.log4j.Logger;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * This class represents a notifications system.
 */
public class Alert {
    static Logger logger = Logger.getLogger(Alert.class.getName());

    private Set<Subscriber> mailAlertList;
    private Set<Subscriber> inSystemAlertList;


    /**
     * Constructor
     */
    public Alert() {
        this.mailAlertList = new HashSet<>();
        this.inSystemAlertList = new HashSet<>();
    }


/////////// Functionality ///////////

    /**
     * Adds a given Subscriber to a Subscribers list according to his preference in the way of receiving notifications
     * @param user the given Subscriber
     */
    public void addSubscriber(Subscriber user) {
        if(user.isMail()){
            this.mailAlertList.add(user);
        }
        else {
            this.inSystemAlertList.add(user);
        }
    }

    /**
     * Adds a Subscriber to the list of in-mail notifications recipients
     * @param user the given Subscriber
     */
    public void addToMailSet(Subscriber user){
        this.mailAlertList.add(user);
    }

    /**
     * Adds a Subscriber to the list of in-system notifications recipients
     * @param user the given Subscriber
     */
    public void addToSystemSet(Subscriber user){
        this.inSystemAlertList.add(user);
    }

    /**
     * Removes a given Subscriber from the Subscribers list he is in
     * @param user the given Subscriber
     */
    public void removeSubscriber(Subscriber user){
        if(user.isMail()){
            this.mailAlertList.remove(user);
        }
        else {
            this.inSystemAlertList.remove(user);
        }
    }

    /**
     * Removes a given Subscriber from the list of in-mail notifications recipients
     * @param user the given Subscriber
     */
    public void removeFromMailSet(Subscriber user){
        this.mailAlertList.remove(user);
    }

    /**
     * Removes a given Subscriber from the list of in-system notifications recipients
     * @param user the given Subscriber
     */
    public void removeFromSystemSet(Subscriber user){
        this.inSystemAlertList.remove(user);
    }


    /**
     * Removes all the Subscribers in both lists
     */
    public void clearSubscribers(){
        this.mailAlertList = new HashSet<>();
        this.inSystemAlertList = new HashSet<>();
    }

    /**
     * Removes all the Subscribers from the in-mail notification recipients list
     */
    public void clearMailList(){
        this.mailAlertList = new HashSet<>();
    }

    /**
     * Removes all the Subscribers from the in-system notification recipients list
     */
    public void clearInSystemList(){
        this.inSystemAlertList = new HashSet<>();
    }

    /**
     * Sends a notification to all of the Subscribers in each list. Each Subscriber would receive the notification in a platform according to the list he's in.
     * @param notification the notification sent to each Subscriber
     * @return a Map that holds a Subscriber's username as key and a boolean value of true whether he has received the message or false otherwise
     */
    public Map<String, Boolean> sendAlert(Notification notification)  {
        // TODO: save this map in DB
        Map isSentMap = new HashMap<>();

        for(Subscriber user : this.mailAlertList){
            boolean isSent = sendMailAlert(user.getMail(), notification);
            isSentMap.put(user.getUserName(), isSent);

        }

        for(Subscriber user : this.inSystemAlertList){
            sendInSystemAlert(user, notification);
            isSentMap.put(user.getUserName(), true);
        }

        return isSentMap;
    }

    /**
     * Sends a notification by mail
     * @param to the mail address of the recipient
     * @param notification the notification
     * @return true if the recipient has received the mail, false otherwise
     */
    private boolean sendMailAlert(String to, Notification notification) {

        // Get a Properties object
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "465");                 //TLS Port 465
        props.put("mail.smtp.auth", "true");                //enable authentication
        props.put("mail.smtp.starttls.enable", "true");     //enable STARTTLS
        props.put("mail.smtp.ssl.enable", "true");          // enable ssl
        props.put("mail.smtp.auth", "true");

        final String username = "fantasticfiveifa@gmail.com";
        final String password = "naorevgenymohsenguyroy";
        try {
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to, false));
            msg.setSubject(notification.getTitle());
            msg.setText(notification.getSubject());
            Transport.send(msg);

            System.out.println("The mail sent successfully");
            return true;
        }
        catch (Exception e){
            logger.error(e.getMessage());

            e.printStackTrace();
            return false;
        }

    }

    /**
     * Sends a notification by the system's notifications system
     * @param user the recipient
     * @param notification the notification
     */
    private void sendInSystemAlert(Subscriber user, Notification notification){
        user.addNotifications(notification);
    }


/////////// Getters and Setters ///////////

    /**
     * Returns the list of mail notification recipients
     * @return the list of mail notification recipients
     */
    public Set<Subscriber> getMailAlertList() {
        return mailAlertList;
    }

    /**
     * Returns the list of in-system notification recipients
     * @return the list of in-system notification recipients
     */
    public Set<Subscriber> getInSystemAlertList() {
        return inSystemAlertList;
    }
}
