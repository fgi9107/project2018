/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PAYP;

import java.io.Serializable;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import requete.Reponse;

/**
 *
 * @author Benjamin
 */
public class ReponsePAY implements Reponse,Serializable{
    public static final int REP_DEFAUT = 0;
    public static final int REP_PAIEMENT = 2000;
    
    private final int code_de_retour;
    private final String chargeUtile;
    private byte[] cleCrypte;
    private byte[] cleHMAC;
    private PublicKey pukSer;

    public ReponsePAY() {
        this.code_de_retour = REP_DEFAUT;
        this.chargeUtile = null;
    }

    public ReponsePAY(int code_de_retour, String chargeUtile) {
        this.code_de_retour = code_de_retour;
        this.chargeUtile = chargeUtile;
    }

    ReponsePAY(int REP_HANDSHAKE, String handshake, byte[] txt) {
        this.code_de_retour = REP_HANDSHAKE;
        this.chargeUtile = handshake;
        this.cleCrypte = txt;
    }

    ReponsePAY(int REP_HANDSHAKE, String handshake, byte[] cryptKey, byte[] cryptHMAC) {
        this.code_de_retour = REP_HANDSHAKE;
        this.chargeUtile = handshake;
        this.cleCrypte = cryptKey;
        this.cleHMAC = cryptHMAC;
    }

    ReponsePAY(int REP_PAIEMENT, String handshake, PublicKey pk) {
        this.code_de_retour = REP_PAIEMENT;
        this.chargeUtile = handshake;
        this.pukSer = pk;
    }
    
    

    public int getCode_de_retour() {
        return code_de_retour;
    }

    public String getChargeUtile() {
        return chargeUtile;
    }
    
    public byte[] getCleCrypte(){
        return cleCrypte;
    }
    
    public byte[] getCleHMAC()
    {
        return cleHMAC;
    }
    
    public PublicKey getPub()
    {
        return pukSer;
    }
    
    @Override
    public int getCode() {
        return 0;
    }
    
    
}
