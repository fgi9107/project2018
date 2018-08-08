/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TICKMAP;

import serveur_Billets.*;
import BeanDataBase.BeanConnection;
import BeanDataBase.BeanMySql;
import PackageSecurite.Securite;
import classObj.Vol;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import requete.*;




/**
 *
 * @author Benjamin
 */
public class RequeteTICKMAP implements Requete,Serializable{
    public static final int REQ_DEFAUT = 0;
    public static final int REQ_CONNEXION = 100;
    public static final int REQ_HANDSHAKE = 101;
    public static final int REQ_BILLETS = 102;
    public static final int REQ_CONFIRMATION = 103;
    public static final int REQ_VALIDATION = 104;
    public static final int REQ_MAJ = 105;
    public static final int REQ_DATAVOL = 106;
    public static final int REQ_PAIEMENT_WEB = 107;
    
    private int type;
    private BeanConnection bc;
    private String chargeUtile;
    private long date;
    private TICKMAP sec;
    private String mdp;
    private String ndc;
    private String chaine;
    private Float montantTotal;
    private Object information;
    private byte[] chaineByte;
    private PublicKey publicKey;
    public static byte[] cryptKey;
    public static byte[] cryptHMAC;
    public static SecretKey serveurSymmetric;
    public static SecretKey hmacSymmetric;
    public String sep;
    public String fin;
    public ResultSet rs;
    Vector<Object> v ;
    KeyClient kc;
    
    private ReponseTICKMAP rep;
    
    public RequeteTICKMAP()   {
        this.type = REQ_DEFAUT;
        this.chargeUtile = null;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");
    }

    public RequeteTICKMAP(int type, String chargeUtile, long date, TICKMAP sec) {
        this.type = type;
        this.chargeUtile = chargeUtile;
        this.date = date;
        this.sec = sec;        
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");
    }    

    public RequeteTICKMAP(int REQ_CONNEXION, String requete, long time, String mdp, String ndc) {
        this.type = REQ_CONNEXION;
        this.chargeUtile = requete;
        this.date = time;
        this.mdp = mdp;
        this.ndc = ndc;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");
    }

    public RequeteTICKMAP(int rq, String requete, PublicKey cle) {
        this.type = rq;
        this.chargeUtile = requete;
        this.publicKey = cle;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");
    }

    public RequeteTICKMAP(int REQ_BILLETS, String req, String ch) {
        this.type = REQ_BILLETS;
        this.chargeUtile = req;
        this.chaine = ch;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");
    }

    public RequeteTICKMAP(int REQ_BILLETS, String req, byte[] envoie) {
        this.type = REQ_BILLETS;
        this.chargeUtile = req;
        this.chaineByte = envoie;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");        
    }

    public RequeteTICKMAP(int r, String req, float t) {
        this.type = r;
        this.chargeUtile = req;
        this.montantTotal = t;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");   
    }

    public RequeteTICKMAP(int r, String req) {
        this.type = r;
        this.chargeUtile = req;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");   
    }

    public RequeteTICKMAP(int r, String ok, Object information) {
        this.type = r;
        this.chargeUtile = ok;
        this.information = information;
        this.bc = new BeanMySql("localhost", "3306", "root", "", "mysql");   
    }
    
    @Override
    public Runnable createRunnable(Socket s, Object...Objlist)
    {
        v = new Vector();
        for(Object obj:Objlist)
        {
            v.add(obj);
        }
        kc = (KeyClient)v.get(0);
        
        switch (type) {
            case REQ_CONNEXION:
                return new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try {
                            traiteConnexion(s);
                        } catch (SQLException | NoSuchAlgorithmException | IOException ex) {
                            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
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
                            //traiteHandshake(s);
                            traiteHandshake(s);
                        } catch (IOException | NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchProviderException | NoSuchPaddingException | BadPaddingException | InvalidKeyException ex) {
                            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
            case REQ_BILLETS:
                return new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try {
                            traiteBillets(s);
                        } catch (IOException | SQLException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
                            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
            case REQ_CONFIRMATION :
                return new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try {
                            traiteConfirmation(s);
                        } catch (IOException ex) {
                            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
            case REQ_VALIDATION :
                return new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try {
                            traiteValidation(s);
                        } catch (IOException ex) {
                            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
            case REQ_MAJ :
                return new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try {
                            traiteMAJ(s);
                        } catch (SQLException ex) {
                            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
            case REQ_DATAVOL :
                return new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try {
                            dataFly(s);
                        } catch (SQLException ex) {
                            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
            case REQ_PAIEMENT_WEB :
                return new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try {
                            paiementWeb(s);
                        } catch (IOException ex) {
                            Logger.getLogger(RequeteTICKMAP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
            default:
                break;
        }
        return null;
    }
    
    private void traiteConnexion(Socket s) throws SQLException, NoSuchAlgorithmException, IOException 
    {
        ResultSet rs;
        bc.CreateConnection();
        
        rs = bc.executeQuery("select mdp from jdbc2.personnel where ndc = '"+sec.getNdc()+"'");
        
        if(rs.next())
        {
            byte[] test = Securite.setDigest(rs.getString("mdp"), sec.getDate());
            
            if(Arrays.equals(test, sec.getMdp()))
            {
                rep = new ReponseTICKMAP(ReponseTICKMAP.REP_CONNEXION, "CONNEXION_OK");
            }
            else
                rep = new ReponseTICKMAP(ReponseTICKMAP.REP_CONNEXION, "CONNEXION_NOK");
        }
        else
        {
            rep = new ReponseTICKMAP(ReponseTICKMAP.REP_CONNEXION, "CONNEXION_NOK");
        }
        
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(rep); oos.flush(); 
    }    

    private void traiteHandshake(Socket s) throws IOException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchProviderException, NoSuchPaddingException, BadPaddingException, InvalidKeyException 
    {
        System.out.println("TraiteHandshake");
        
        cryptKey = Securite.chiffrement(kc.getCrypt(), publicKey, true);
        cryptHMAC = Securite.chiffrement(kc.getHMAC(), publicKey, true);
        
        rep = new ReponseTICKMAP(ReponseTICKMAP.REP_HANDSHAKE, "HANDSHAKE", cryptKey, cryptHMAC);
        
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(rep); oos.flush(); 
    }

        private void traiteBillets(Socket s) throws IOException, SQLException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException 
    {
        String renvoie;
        String[] tab;
        Object o;
        setTrame();
        SecretKey serveurSymm, HMACSymm;
        serveurSymm = kc.getCrypt();
        HMACSymm = kc.getHMAC();
        
        o = Securite.dechiffrement(this.chaineByte, serveurSymm, false);
        
        tab = o.toString().split("\\$");
        
        bc.CreateConnection();
        
        if(verifPlaces(Integer.parseInt(tab[4]), tab[5]))
        {
            int argent = (int) (Math.random() * (70000 - 1));
            bc.executeUpdate("insert into jdbc2.billets(nom,prenom,age,sexe, nbreAccompagnant,FKidvols, argent) values('"+tab[0]+"','"+tab[1]+"',"+tab[2]+",'"+tab[3]+"',"+tab[4]+",'"+tab[5]+"', "+argent+")");
            bc.executeUpdate("update jdbc2.vols set nbbillets = nbbillets - " +tab[4]+ " where idvols = '" + tab[5]+"'");
            rs = bc.executeQuery("select * from jdbc2.billets order by idbillet DESC limit 1");
            rs.next();
            //byte [] temp = Securite.chiffrement(setReponse(tab, rs.getString("idbillet")), HMACSymm, Boolean.FALSE);
            String msgClair = setReponse(tab, rs.getString("idbillet"));
            byte[] temp = Securite.doHMAC(HMACSymm, msgClair);
            
            System.out.println("requeteTickmap : " + temp);
            System.out.println("requeteTickmap : " + msgClair);
            
            rep = new ReponseTICKMAP(ReponseTICKMAP.REP_BILLETS, "BILLETS_OK", temp, msgClair);
        }
        else
        {
            rep = new ReponseTICKMAP(ReponseTICKMAP.REP_BILLETS, "BILLETS_NOK");
        }
        
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(rep); oos.flush();
    }    

    private void traiteConfirmation(Socket s) throws IOException
    {        
        rep = new ReponseTICKMAP(ReponseTICKMAP.REP_CONFIRMATION, "CONFIRMATION_OK", chaineByte);
                
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(rep); oos.flush();
    }    

    private void traiteValidation(Socket s) throws IOException 
    {
        if(this.chargeUtile.equals("validation_ok"))
            rep = new ReponseTICKMAP(ReponseTICKMAP.REP_VALIDATION, "VALIDATION_OK");
        else
            rep = new ReponseTICKMAP(ReponseTICKMAP.REP_VALIDATION, "VALIDATION_NOK");
        
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(rep); oos.flush();    
    }    

    private void traiteMAJ(Socket s) throws SQLException, IOException {
        System.out.println("TRAITE MAJ");
        bc.CreateConnection();
        String[] tabI = splitInformation(information);
        if(this.chargeUtile.equals("OK"))
        {          
            bc.executeUpdate("insert into jdbc2.logachat values('"+tabI[1]+"', "+tabI[0]+",'"+tabI[4]+"','Y')");
            bc.executeUpdate("update jdbc2.billets set achat = 'Y' where idbillet = '"+tabI[4]+"'");
            envoiEmail(tabI);
        }
        else
        {
            System.out.println("NOK");
            bc.executeUpdate("insert into jdbc2.logachat values('"+tabI[1]+"', "+tabI[0]+",'"+tabI[4]+"','N')");
            bc.executeUpdate("update jdbc2.vols set nbbillets = nbbillets + " +tabI[0]+ " where idvols = '" + tabI[1]+"'");
            bc.executeUpdate("update jdbc2.billets set achat = 'N' where idbillet = '"+tabI[4]+"'");
        }
        rep = new ReponseTICKMAP(ReponseTICKMAP.REP_MAJ, "action_effectue");
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(rep); oos.flush();   
    }
    private void paiementWeb(Socket s) throws IOException
    {
        rep = new ReponseTICKMAP(ReponseTICKMAP.REP_MAJ, "action_effectue");
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(rep); oos.flush(); 
    }
    private void dataFly(Socket s) throws SQLException, IOException
    {
        //List dataVol = new ArrayList();
        List listVol = new ArrayList();
        this.bc.CreateConnection();
        
        rs = this.bc.executeQuery("select idvols, datedepart, PrixBillet, nbbillets, destination from jdbc2.vols");;
        
        while(rs.next())
        {
            Vol v = new Vol(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5));
            listVol.add(v);
        }
        
        rep = new ReponseTICKMAP(ReponseTICKMAP.REP_MAJ, "action_effectue", listVol);
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
                fin = prop.getProperty("FIN_TRAME");
    }
    
    public boolean verifPlaces(int place, String idvols) throws SQLException
    {
        bc.CreateConnection();
        
        rs = bc.executeQuery("select nbbillets from jdbc2.vols where idvols = '" + idvols+"'");
        rs.next();
        if(place > rs.getInt("nbbillets"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
        
    public int getType() 
    {
        return type;
    }

    public void setType(int type) 
    {
        this.type = type;
    }
    
    public PublicKey getPublicKey()
    {
        return publicKey;
    }

    private String setReponse(String[] tab, String s) throws SQLException
    {
        //Envoie du prix, idvols, accompagnant, destination
        String envoie;
            rs = bc.executeQuery("select PrixBillet, destination from jdbc2.vols where idvols = '"+tab[5]+"'");
            
            rs.next();
            
            return envoie = tab[4] + sep + tab[5] + sep + rs.getString("PrixBillet")+sep + rs.getString("destination")+sep+s;
            
    }

    private String[] splitInformation(Object information) {
        return information.toString().split("\\$");
    }

    private void envoiEmail(String[] tabI) {
        
    }
}
