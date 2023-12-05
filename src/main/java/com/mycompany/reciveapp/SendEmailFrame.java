/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reciveapp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author kamal
 */
public class SendEmailFrame extends JFrame {
        private ReceiveAppFrame receiveAppFrame;
    public SendEmailFrame(ReceiveAppFrame receiveAppFrame) {
        this.receiveAppFrame = receiveAppFrame;
        initComponents();
    }
        
    private JTextField TxtTo, TxtFrom, TxtSubject;
    private JTextArea jTextArea1;
       public SendEmailFrame() {
        initComponents();
    }
       private void initComponents() {
           JFrame sendEmailFrame = new JFrame("Send Email");
    sendEmailFrame.setSize(800, 400);

    JPanel sendEmailPanel = new JPanel(new GridLayout(8, 2));  // Adjust layout as needed
       
    TxtTo = new JTextField();
    TxtFrom = new JTextField();
    TxtSubject = new JTextField();
    jTextArea1 = new JTextArea();

    sendEmailPanel.add(new JLabel("To:"));
    sendEmailPanel.add(TxtTo);
    
    sendEmailPanel.add(new JLabel("From:"));
    sendEmailPanel.add(TxtFrom);
    
    sendEmailPanel.add(new JLabel("Subject:"));
    sendEmailPanel.add(TxtSubject);
    
    sendEmailPanel.add(new JLabel("Message:"));
    sendEmailPanel.add(new JScrollPane(jTextArea1));
    
    JButton sendButton = new JButton("SEND");
    sendButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
    receiveAppFrame.sendEmail();
        }
    });

    sendEmailPanel.add(sendButton);

    sendEmailFrame.add(sendEmailPanel);
    sendEmailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    sendEmailFrame.setVisible(true);

}
    
}
