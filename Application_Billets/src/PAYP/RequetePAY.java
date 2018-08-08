/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PAYP;

import BeanDataBase.BeanConnection;
import BeanDataBase.BeanMySql;
import PackageSecurite.Securite;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.bouncycastle.asn1.ocsp.Signature;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import requete.Requete;
import serveur_Billets.KeyClient;

/**
 *
 * @author Benjamin
 */
public class RequetePAY implements Requete,Serializable{
    public static final int REQ_DEFAUT = 0;
    public static final int REQ_PAIEMENT = 200;
    public static final int REQ_HANDSHAKE = 201;
    
    private int type;
    
    private BeanConnection bc;
    private String chargeUtile;
    private Socket s;
    private String chaine;
    
    private PublicKey pukCli;
    private PublicKey pukSer;
    
    private byte[] signatureCli;
    private byte[] cbCrypt;
    private String msg;
    private long time;
    Vector<Object> v ;
    KeyClient kc;
    public String sep;
    private Object information;
    
    public RequetePAY() {
        this.type = REQ_DEFAUT;
        this.chargeUtile = null;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");
    }

    public RequetePAY(int r, String p, String t) {
        this.type = r;
        this.chargeUtile = p;
        this.chaine = t;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");
    }

    public RequetePAY(int r, String p) {
        this.type = r;
        this.chargeUtile = p;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");
    }

    public RequetePAY(int r, String p, PublicKey k) {
        this.type = r;
        this.chargeUtile = p;
        this.pukCli = k;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");
    }

    public RequetePAY(int r, String p, byte[] sign, String chaineEnClair, byte[] c) {
        this.type = r;
        this.chargeUtile = p;
        this.signatureCli = sign;
        this.msg = chaineEnClair;
        this.cbCrypt = c;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");
    }

    public RequetePAY(int r, String p, byte[] sign, String chaineEnClair, byte[] c, long time) {
        this.type = r;
        this.chargeUtile = p;
        this.signatureCli = sign;
        this.msg = chaineEnClair;
        this.cbCrypt = c;    
        this.time = time;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");
    }

    public RequetePAY(int r, String p, byte[] sign, String chaineEnClair, byte[] c, long time, Object temp) {
        this.type = r;
        this.chargeUtile = p;
        this.signatureCli = sign;
        this.msg = chaineEnClair;
        this.cbCrypt = c;    
        this.time = time;
        this.information = temp;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");    }
    
    @Override
    public Runnable createRunnable(Socket s, Object...Objlist)
    {
        //recuperation cle
        v = new Vector();
        for(Object obj:Objlist)
        {
            v.add(obj);
        }
        kc = (KeyClient)v.get(0);
        Security.addProvider(new BouncyCastleProvider());
        switch (type) {
            case REQ_PAIEMENT:
                return new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try {
                            traitePaiement(s);
                        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException | UnrecoverableKeyException | ClassNotFoundException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | SignatureException | SQLException ex) {
                            Logger.getLogger(RequetePAY.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };    
            case REQ_HANDSHAKE:
                return new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try {
                            traiteHandshake(s);
                        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException ex) {
                            Logger.getLogger(RequetePAY.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };  
            default:
                break;
        }
        return null;
    }
    
    private void traitePaiement(Socket s) throws IOException, FileNotFoundException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException, ClassNotFoundException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, SignatureException, SQLException 
    {
        ReponsePAY rep = null;
        PrivateKey prk = null;
        String[] tab = null;
        setTrame();
        System.out.println("traitement paiement");
        if(chargeUtile.equals("paiement"))
        {
            System.out.println("Je rentre traitement paiement");
            //Carte bancaire
            prk = (PrivateKey)Securite.getPrivateKey("serveurP");
            pukSer = (PublicKey)Securite.getPublicKey("serveurP");
            Object cbTemp = Securite.dechiffrement(cbCrypt, prk, true);
            
            //Le reste en clair
            tab = msg.split("\\$");
            for (String tab1 : tab) {
                System.out.println("test : " + tab1);
            }
            String tempMsg = cbTemp.toString()+sep+msg;
            
            boolean ok = Securite.comparaisonSignature(kc.getPub(), tempMsg, signatureCli);
           
            if(ok)
            {                
                rep = new ReponsePAY(ReponsePAY.REP_PAIEMENT, "paiement_effectue");
            }
            else
            {
                
                rep = new ReponsePAY(ReponsePAY.REP_PAIEMENT, "paiement_refuse");
            }
        }
        else
            rep = new ReponsePAY(ReponsePAY.REP_PAIEMENT, "paiement_refuse");
            
            
        
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(rep); oos.flush(); 
    }

    private void traiteHandshake(Socket s) throws IOException, FileNotFoundException, KeyStoreException, NoSuchAlgorithmException, CertificateException 
    {
        ReponsePAY rep = null;
        pukSer = (PublicKey)Securite.getPublicKey("serveurP");
        
        kc.setPub(pukCli);
        System.out.println("pukSer : " + pukSer);
        rep = new ReponsePAY(ReponsePAY.REP_PAIEMENT, "HANDSHAKE", pukSer);
        
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(rep); oos.flush(); 
    }
    
    public void setTrame() throws IOException 
    {
                Properties prop = new Properties();
                FileInputStream in = null;
                in = new FileInputStream("prop.properties");
                prop.load(in);
                
                sep = prop.getProperty("SEP_TRAME");
    }

    private String[] splitInformation(Object information) {
        String tab[] = information.toString().split("\\$");
        for(Object o:tab)
            System.out.println("information : " + o.toString());
        return information.toString().split("\\$");
    }
}
