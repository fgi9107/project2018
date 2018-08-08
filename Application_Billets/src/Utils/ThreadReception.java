/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import Utils.Utils;
import java.sql.SQLException;

/**
 *
 * @author BJ
 */
public class ThreadReception extends Thread{
    private MulticastSocket multicast;
    private String[] tabString;
    
    private JTable tabVol;
    private Socket sock;
    private Utils utilsMethode;
    
    public ThreadReception() {
    }

    public ThreadReception(JTable tabVol) {
        this.tabVol = tabVol;
    }

    public ThreadReception(Socket sock, JTable tabVol) {
        this.sock = sock;
        this.tabVol = tabVol;
    }

    public ThreadReception(Socket sock, JTable tabVol, Utils utilsMethode) {
        this.sock = sock;
        this.tabVol = tabVol;
        this.utilsMethode = utilsMethode;
    }
    
    public void run()
    {
        boolean enMarche = true;
        while(enMarche)
        {
            try {
                utilsMethode.setJtable();
                Thread.sleep(10000);
            } catch (SQLException | IOException | ClassNotFoundException | InterruptedException ex) {
                Logger.getLogger(ThreadReception.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
