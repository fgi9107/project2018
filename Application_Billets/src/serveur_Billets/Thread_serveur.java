/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur_Billets;

import requete.SourceTache;
import requete.ConsoleServeur;
import PackageSecurite.Securite;
import TICKMAP.RequeteTICKMAP;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author Benjamin
 */
public class Thread_serveur extends Thread{
    private int port;
    private SourceTache tachesAExecuter;
    private ConsoleServeur guiApplication;
    private ServerSocket SSocket = null;
    public LinkedList<Socket> lSocket = new LinkedList<Socket>();
    Zone_queue_socket zone ;
    
    public Thread_serveur(int p, SourceTache st, ConsoleServeur fs, Zone_queue_socket zq) throws NoSuchAlgorithmException
    {
        port = p; tachesAExecuter = st; guiApplication = fs;
        zone = zq;
    }
    
    @Override
    public void run()
    {
        try {
            SSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.err.println("Erreur socket : " + ex.getMessage());
        }
        
        for(int i = 0 ; i < 3 ; i++)
        {
            ThreadClient tc = new ThreadClient(tachesAExecuter, "Thread du pool n°" + String.valueOf(i), zone);
            tc.start();
        }
        
        Socket CSocket = null;
        while(!isInterrupted())
        {
            try {
                Securite.createSymmetrique();
                CSocket = SSocket.accept();
                guiApplication.TraceEvenements(CSocket.getRemoteSocketAddress().toString()+"#accept#thread serveur"); 
                zone.depose_socket(CSocket);
            } catch (IOException | InterruptedException | NoSuchAlgorithmException ex) {
                Logger.getLogger(Thread_serveur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
}
