/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application_Billets;

import BeanDataBase.BeanConnection;
import BeanDataBase.BeanMySql;
import PackageSecurite.Securite;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Benjamin
 */
public class Application_ConnexionIT {
    
    public Application_ConnexionIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Application_Connexion.
     * @throws java.sql.SQLException
     */
//    @Test
//    public void testMain() {
//        System.out.println("main");
//        String[] args = null;
//        Application_Connexion.main(args);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    @Test
//    public void testCrypt() throws IOException, FileNotFoundException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, ClassNotFoundException
//    {
//        Key pub, priv, cle1;
//        String t = "coucou";
//        byte[] test;
//        Object o;
//        SecretKey sk;
//        Security.addProvider(new BouncyCastleProvider());
//        
//        System.out.println("testCrypt");
//        priv = Securite.getPrivateKey("client");
//        pub = Securite.getPublicKey("client");
//        cle1 = Securite.createTest();
//        
//        test = Securite.chiffrement(cle1, pub);
//        System.out.println("test");
//        
//        sk = (SecretKey)Securite.dechiffrement(test, priv);
//        System.out.println("Dechiffrement");
//        
//    }
    @Test
    public void test() throws SQLException, IOException, FileNotFoundException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, ClassNotFoundException 
    {
        Object a = "123";
        
        PrivateKey prk = (PrivateKey)Securite.getPrivateKey("client");
        
        PublicKey puk = (PublicKey)Securite.getPublicKey("client");
        
        Security.addProvider(new BouncyCastleProvider());
        
        byte[] temp = Securite.chiffrement(a, prk, Boolean.TRUE);
        
        Object test = Securite.dechiffrement(temp, puk, Boolean.TRUE);
        
        System.out.println("test : " + test.toString());
    }
//    @Test
//    public void test() throws IOException, ClassNotFoundException
//    {
//        String test = "test";
//        
//        byte[] b = Securite.objectToByte(test);
//        String test2 = (String) Securite.byteToObject(b);
//        
//        assertEquals(test, test2);
//    }
}
