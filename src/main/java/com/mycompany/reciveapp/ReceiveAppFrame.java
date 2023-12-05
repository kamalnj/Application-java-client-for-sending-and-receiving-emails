/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reciveapp;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author kamal
 */
public class ReceiveAppFrame extends JFrame {
        private JTable inboxTable;
    private DefaultTableModel inboxTableModel;
    private JTextArea contentTextArea;
    private JTextField TxtTo, TxtFrom, TxtSubject;
    private JTextArea jTextArea1;
        /**
     * Creates new form reciveapp // kamal ennaji
     */
    public ReceiveAppFrame() {
        myinitComponents();
    }
       private void myinitComponents(){
       
inboxTableModel = new DefaultTableModel();
inboxTableModel.addColumn("From");
inboxTableModel.addColumn("Subject");

inboxTable = new JTable(inboxTableModel);
contentTextArea = new JTextArea(10, 40);
contentTextArea.setEditable(false);

JButton refreshButton = new JButton("Refresh Inbox");
refreshButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        refreshInbox();
    }
});

// Add a "Send Email" button
JButton sendEmailButton = new JButton("Send Email");
sendEmailButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        openSendEmailFrame(); // Method to open a new JFrame
    }
});

JPanel buttonPanel = new JPanel();
buttonPanel.add(refreshButton);
buttonPanel.add(sendEmailButton);

JPanel inboxPanel = new JPanel(new BorderLayout());
inboxPanel.add(new JScrollPane(inboxTable), BorderLayout.CENTER);
inboxPanel.add(buttonPanel, BorderLayout.SOUTH);

JPanel contentPanel = new JPanel(new BorderLayout());
contentPanel.add(new JScrollPane(contentTextArea), BorderLayout.CENTER);

JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inboxPanel, contentPanel);
splitPane.setResizeWeight(0.5);

add(splitPane);

setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setSize(800, 400);
setLocationRelativeTo(null);
setVisible(true);
    
}
  
      private void openSendEmailFrame() {
    SendEmailFrame sendEmailFrame = new SendEmailFrame(this); // Pass the reference
}
   
    //SendEmail method       // kamal ennaji
    public void sendEmail(){
        String ToEmail = TxtTo.getText();
        String FromEmail = TxtFrom.getText();
        String FromEmailPassword ="nkww rjhc zzji dskk";
        String Subject = TxtSubject.getText();

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");

        Session session;
        session = Session.getInstance(properties,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FromEmail, FromEmailPassword);
                }
            });

            try{
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(FromEmail));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(ToEmail));
                message.setSubject(Subject);
                message.setText(jTextArea1.getText());
                Transport.send(message);
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame,"MESSAGE ENVOYER");

            }catch(Exception ex){
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame,"ERORR!");}
    

    }
     private void refreshInbox() {
        String username = "wears5222@gmail.com";
        String password = "nkww rjhc zzji dskk";

        Properties properties = System.getProperties();
        properties.put("mail.pop3.ssl.enable", "true");
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.pop3.host", "pop.gmail.com");
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.pop3.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(properties);

        try {
            Store store = session.getStore("pop3");
            store.connect(username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            inboxTableModel.setRowCount(0);

            for (Message message : messages) {
                String from = InternetAddress.toString(message.getFrom());
                String subject = message.getSubject();
                inboxTableModel.addRow(new Object[]{from, subject});
            }
            if (messages.length > 0) {
                displayContent(messages[0]);
            }
            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     //***************DISPLAY MESSAGE********** kamal ennaji
      private void displayContent(Message message) {
        try {
            contentTextArea.setText(processContent(message.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
           //***************DISPLAY MESSAGE********** kamal ennaji

       private String processContent(Object content) throws MessagingException, java.io.IOException {
        StringBuilder contentBuilder = new StringBuilder();

        if (content instanceof String) {
            // Content is plain text
            contentBuilder.append(content);
        } else if (content instanceof Multipart) {
            // Content is multipart (may include text and attachments)
            Multipart multipart = (Multipart) content;
            int count = multipart.getCount();

            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);

                if (bodyPart.isMimeType("text/plain")) {
                    contentBuilder.append(bodyPart.getContent());
                } else if (bodyPart.isMimeType("multipart/*")) {
                    contentBuilder.append(processContent(bodyPart.getContent()));
                } else {
                    // Handle attachments or other content types
                    contentBuilder.append("Attachment: ").append(bodyPart.getFileName()).append("\n");
                }
            }
        }

        return contentBuilder.toString();
    }
     

}
