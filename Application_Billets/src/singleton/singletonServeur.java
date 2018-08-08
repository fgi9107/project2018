/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton;

import java.security.PublicKey;
import javax.crypto.SecretKey;

/**
 *
 * @author Benjamin
 */
public class singletonServeur {
    private static final singletonServeur instance = new singletonServeur();
    private PublicKey publicKey;
    private SecretKey hmacKey;
    private SecretKey symmKey;
    
    
    private singletonServeur() {
    }
    public static singletonServeur getInstance()
    {
        return instance;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public SecretKey getHmacKey() {
        return hmacKey;
    }

    public void setHmacKey(SecretKey hmacKey) {
        this.hmacKey = hmacKey;
    }

    public SecretKey getSymmKey() {
        return symmKey;
    }

    public void setSymmKey(SecretKey symmKey) {
        this.symmKey = symmKey;
    }
    
}
