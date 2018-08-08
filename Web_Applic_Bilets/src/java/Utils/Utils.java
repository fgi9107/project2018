/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.net.Socket;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BJ
 */
public class Utils {
//    private RequeteTICKMAP req;
//    private ReponseTICKMAP rep;
    private Socket sock;
    private JTable tabVol;
    
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Utils() {
    }

    public Utils(Socket sock, JTable tabVol) {
        this.sock = sock;
        this.tabVol = tabVol;
    }
    
    public void envoieMail(String mail) throws AddressException, MessagingException {
        System.out.println("Je suis dnas le mail");
//        String from = "inpresbenjamin@gmail.com";
//        String password ="inpres1234";
        String from = "inpresmail@gmail.com";
        String password ="inpresmail1";
        String host = "smtp.gmail.com";
        String port = "587";
        String charset = "iso-8859-1";
        //
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("file.encoding", charset);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        // Get the Session object.
        
                Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });
                System.out.println("tableau : " + mail);
                // Create a default MimeMessage object.
                Message message = new MimeMessage(session);
                //MimeMessage message = new MimeMessage(session);
                //ton adresse mail
                message.setFrom(new InternetAddress(from));

                // Destinataire
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));

                // sujet
                String sujetMail = "Places reservees";
                message.setSubject(sujetMail);

                message.setText("Vos places sont reservees");
                //sinon 
                
                Transport.send(message);
                //test C:\Users\Benjamin\Documents\Info de gestion\3eme\Anglais\avantages.jpg
                System.out.println("Message envoye");
            
    }
    
//    public void setJtable() throws SQLException, IOException, ClassNotFoundException
//    {
//        DefaultTableModel model = (DefaultTableModel)tabVol.getModel();
//        
//        suppressionLigne(model);
//        
//        req = new RequeteTICKMAP(RequeteTICKMAP.REQ_DATAVOL, "dataVol");
//        oos = new ObjectOutputStream(sock.getOutputStream());
//        oos.writeObject(req); oos.flush();
//
//        rep = null;
//        ois = new ObjectInputStream(sock.getInputStream());
//        rep = (ReponseTICKMAP)ois.readObject();
//        
//        Vector vData;
//        Vol vol;
//        for(Object v : rep.getListVol())
//        {
//            vol = (Vol)v;
//            vData = new Vector();
//            vData.add(vol.getId());
//            vData.add(vol.getDatedepart());
//            vData.add(vol.getNbbillets());
//            vData.add(vol.getPrixbillet());
//            vData.add(vol.getDestination());
//
//            model.addRow(vData);
//        }    
//    }
//    private void suppressionLigne(DefaultTableModel model) {
//        if(model.getRowCount()> 0)
//        {
//            System.out.println("Suppression de lignes");
//            while(model.getRowCount() != 0)
//            {
//                model.removeRow(0);
//            }                
//        }    
//    }
//
//    public RequeteTICKMAP getReq() {
//        return req;
//    }
//
//    public void setReq(RequeteTICKMAP req) {
//        this.req = req;
//    }
//
//    public ReponseTICKMAP getRep() {
//        return rep;
//    }
//
//    public void setRep(ReponseTICKMAP rep) {
//        this.rep = rep;
//    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public JTable getTabVol() {
        return tabVol;
    }

    public void setTabVol(JTable tabVol) {
        this.tabVol = tabVol;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }
    
}
