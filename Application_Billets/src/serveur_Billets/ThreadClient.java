/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur_Billets;

import PackageSecurite.Securite;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import requete.*;
import serveur_Billets.Zone_queue_socket;

/**
 *
 * @author Benjamin
 */
public class ThreadClient extends Thread {
    private SourceTache tachesAExecuter;
    private String nom;
    private Runnable tacheEnCours;
    private LinkedList<Socket> QueueSocket;
    private ConsoleServeur guiApplication;
    Zone_queue_socket zone_queue;
     
    KeyClient kc = null;
    Socket CSocket = null;
    
    public ThreadClient(SourceTache st, String n, Zone_queue_socket zone)
    {
        tachesAExecuter = st;
        nom = n;
        zone_queue = zone;        
    }    
    
    @Override
    public void run()
    {
       while(true){
            
            try 
            {
                CSocket = zone_queue.retire_socket();
            } catch (InterruptedException ex) 
            {
                System.err.println("zone_queue : " + ex.getMessage());
            }
            
           try {
               kc = new KeyClient();
               kc.setCrypt((SecretKey)Securite.createSymmetrique());
               kc.setHMAC((SecretKey)Securite.createSymmetrique());
           } catch (NoSuchAlgorithmException ex) {
               Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
           }
           while(true){
              
                //preparation flux - requete
                ObjectInputStream ois=null;
                Requete req = null;         
                try
                {
                    ois = new ObjectInputStream(CSocket.getInputStream());
                    req = (Requete)ois.readObject(); //en attente d'une requete a lire dans le flux
                    System.out.println("[THREAD CLIENT] Requete lue par le serveur, instance de " +req.getClass().getName());
                }
                catch (ClassNotFoundException e)
                {
                    System.err.println("Erreur de def de classe [" + e.getMessage() + "]");
                }
                catch (IOException e)
                {
                    System.err.println("Erreur (ici) ? [" + e + "]");
                }
                System.err.println("keyclient : " + kc);
                //On cree le travail a faire (donc la requete lugap a effectuer en fonction de la requete envoyee par le client)
                Runnable travail = req.createRunnable(CSocket, kc);
                
                if (travail != null)
                {
                    tachesAExecuter.recordTache(travail); //le travail est mis dans une file pour que le thread l'effectue
                    System.out.println("[THREAD CLIENT]Travail mis dans la file");
                }
                else System.out.println("Pas de mise en file");
                
                try
                {   //Recupere le travail si il y en a un
                    System.out.println("Tread client avant get");
                    tacheEnCours = tachesAExecuter.getTache();                    
                }
                catch (InterruptedException e)
                {
                    System.out.println("Interruption : " + e.getMessage());
                }
                //Execute le travail de la requete recue
                tacheEnCours.run();
                    
                if(CSocket.isClosed()==true)
                {
                    System.out.println("Socket fermée -> Cient deconnecte");
                    break;
                }
            }
        }
    }        
}
        
//    }
