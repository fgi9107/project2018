/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackageSecurite;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import serveur_Billets.Serveur_Billets;

/**
 *
 * @author Benjamin
 */
public class Securite implements Serializable{
    
    public static byte[] setDigest(String _pwd, long d) throws NoSuchAlgorithmException
    {
        java.security.Security.addProvider(new BouncyCastleProvider());

        String plainString = _pwd+d;

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");        
        byte[] hashedString = messageDigest.digest(plainString.getBytes());
        return hashedString;
    }
    public static Key createTest() throws NoSuchAlgorithmException
    {
        SecretKey cle1;
        KeyGenerator cleGen1 = KeyGenerator.getInstance("DES");
        cleGen1.init(new SecureRandom());
        
        return cle1 = cleGen1.generateKey();
    }
    public static byte[] doHMAC(Key pub, String msg) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, IOException
    {
        byte[] temp;
        Mac hmac = Mac.getInstance("HMAC-MD5", "BC");
        hmac.init(pub);
        
        temp = Securite.objectToByte(msg);
        
        hmac.update(temp);
        temp = hmac.doFinal();
        return temp;
    }
    public static Key createSymmetrique() throws NoSuchAlgorithmException
    {
        //singletonServeur sServ = singletonServeur.getInstance();
        
        KeyGenerator cleGen1 = KeyGenerator.getInstance("DES");
        cleGen1.init(new SecureRandom());
        
        SecretKey cle1 = cleGen1.generateKey();
        
        return cle1;
        //sServ.setHmacKey(cle1);
//        Serveur_Billets.serveurSymmetric = cle1;
//        
//        KeyGenerator cleGen2 = KeyGenerator.getInstance("DES");
//        cleGen2.init(new SecureRandom());
//        
//        SecretKey cle2 = cleGen2.generateKey();
//        
//        Serveur_Billets.hmacSymmetric = cle2;
//        //sServ.setSymmKey(cle2);
//        System.err.println("Test : " + Serveur_Billets.serveurSymmetric);
//        System.err.println("Test : " + Serveur_Billets.hmacSymmetric);
    }
    public static Key getPublicKey(String file) throws FileNotFoundException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException
    {
        Key PublicKey;
        System.out.println("File : " + file);
            FileInputStream inStream = new FileInputStream("C:\\Users\\BJ\\Documents\\Cours\\secure\\"+file+".jks");
            //FileInputStream inStream = new FileInputStream("C:\\Users\\Benjamin\\Documents\\Info de gestion\\Secure\\test\\"+file+".jks");
        
        //accède au keystore
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        String pwd = "azerty";
        
        keystore.load(inStream, pwd.toCharArray());
        
        java.security.cert.Certificate cert = keystore.getCertificate(file);
        
        return PublicKey = cert.getPublicKey();        
    }
    public static Key getPrivateKey(String file) throws FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException
    {
        System.out.println("file : " + file);
        Key PrivateKey;
        String pwd = "azerty";
        //FileInputStream inStream = new FileInputStream("C:\\Users\\Benjamin\\Documents\\Info de gestion\\Secure\\test\\"+file+".jks");
        FileInputStream inStream = new FileInputStream("C:\\Users\\BJ\\Documents\\Cours\\secure\\"+file+".jks");
        
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(inStream, pwd.toCharArray());
                
        return PrivateKey = keystore.getKey(file, pwd.toCharArray());
    }
    public static byte[] chiffrement(Object tab, Key publicKey, Boolean a) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException
    {
        Cipher chiffrement;
        if(a)
        {
            chiffrement = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "BC");  
        }
        else
        {
            chiffrement = Cipher.getInstance("DES/ECB/PKCS5PADDING");
        }        
        
        byte[] test ;
        test = Securite.objectToByte(tab);
        
        chiffrement.init(Cipher.ENCRYPT_MODE, publicKey);            
            
        return test = chiffrement.doFinal(test);
    }
    public static Object dechiffrement(byte[] tab, Key privee, Boolean a) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        Object obj;
        Cipher deciph;
        if(a)
        {
            deciph = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "BC");
        }
        else
        {
            deciph = Cipher.getInstance("DES/ECB/PKCS5PADDING");
        }
        
        deciph.init(Cipher.DECRYPT_MODE, privee);
        tab = deciph.doFinal(tab);     
        
        return obj = Securite.byteToObject(tab);        
    }
    
    public static byte[] signature(PrivateKey pk, String msg) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException, IOException
    {
        System.out.println("test : " + pk);
        System.out.println("test : " + msg.toString());
        byte[] retour, temp;
        Signature s = Signature.getInstance("SHA1withRSA", "BC");
        
        s.initSign(pk);
        
        temp = Securite.objectToByte(msg);
        
        s.update(temp);
        
        retour = s.sign();
        
        return retour;
    }
    public static boolean comparaisonSignature(PublicKey pk, String msgACree, byte[] msgACompare) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException, IOException
    {
        boolean ok;
        byte[] temp;
        Signature s = Signature.getInstance("SHA1withRSA", "BC");
        s.initVerify(pk);
        
        temp = Securite.objectToByte(msgACree);
        
        s.update(temp);
        
        return ok = s.verify(msgACompare);        
    }

    
    public static Object byteToObject(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
    return is.readObject();
    }
    
     public static byte[]objectToByte(Object o) throws IOException
    {
        //déclare ByteArrayOutputStream
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] yourBytesReturn = null;

            //Utilisation d'un objectoutputstream sur un bos
            out = new ObjectOutputStream(bos);
            //on écrit l'objet dans le flux
            out.writeObject(o);
            //on vide le buffer
            out.flush();
            //on récupère le tableau de byte associer au flux
            yourBytesReturn = bos.toByteArray();

        return yourBytesReturn;
    }
   
    
//    public static Object byteToSecretKey(byte[] data)
//    {
//        SecretKey originalKey = new SecretKeySpec(data, 0, data.length, "DES");
//        return originalKey;
//    }
}
