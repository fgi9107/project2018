/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur_Billets;

import java.io.Serializable;
import java.security.PublicKey;
import javax.crypto.SecretKey;

/**
 *
 * @author Benjamin
 */
public class KeyClient implements Serializable{
    private SecretKey HMAC;
    private SecretKey crypt;
    private PublicKey pub;

    public KeyClient() {
    }    
    
    public SecretKey getHMAC() {
        return HMAC;
    }

    public void setHMAC(SecretKey HMAC) {
        this.HMAC = HMAC;
    }

    public SecretKey getCrypt() {
        return crypt;
    }

    public void setCrypt(SecretKey crypt) {
        this.crypt = crypt;
    }

    public PublicKey getPub() {
        return pub;
    }

    public void setPub(PublicKey pub) {
        this.pub = pub;
    }
    
    
}
