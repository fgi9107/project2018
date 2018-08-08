/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TICKMAP;

import static PackageSecurite.Securite.setDigest;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Benjamin
 */
public class TICKMAP implements Serializable{
    private long date;
    private String ndc;
    private byte [] mdp;

    public TICKMAP() {
        this.date = 0;
        this.ndc = null;
        this.mdp = null;
    }

    public TICKMAP(long date, String ndc, String mdp) throws NoSuchAlgorithmException {
        this.date = date;
        this.ndc = ndc;
        this.mdp = setDigest(mdp, date);
    }
    
    
    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getNdc() {
        return ndc;
    }

    public void setNdc(String ndc) {
        this.ndc = ndc;
    }

    public byte[] getMdp() {
        return mdp;
    }

    public void setMdp(byte[] mdp) {
        this.mdp = mdp;
    }
    
    
}
