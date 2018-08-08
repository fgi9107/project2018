/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TICKMAP;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;
import javax.crypto.SecretKey;
import requete.Reponse;

/**
 *
 * @author Benjamin
 */
public class ReponseTICKMAP implements Reponse,Serializable{
    public static final int REP_DEFAUT = 0;
    public static final int REP_CONNEXION = 1000;
    public static final int REP_HANDSHAKE = 1001;
    public static final int REP_BILLETS = 1002;
    public static final int REP_CONFIRMATION = 1003;
    public static final int REP_VALIDATION = 1004;
    public static final int REP_MAJ = 1005;
    public static final int REP_WEB = 1006;
        
    private final int code_de_retour;
    private final String chargeUtile;
    private byte[] cleCrypte;
    private byte[] cleHMAC;
    private float montantTotal;
    private String message;
    private List listVol;

    public ReponseTICKMAP() {
        this.code_de_retour = REP_DEFAUT;
        this.chargeUtile = null;
    }

    public ReponseTICKMAP(int code_de_retour, String chargeUtile) {
        this.code_de_retour = code_de_retour;
        this.chargeUtile = chargeUtile;
    }
    
    public ReponseTICKMAP(int code_de_retour, String chargeUtile, float t) {
        this.code_de_retour = code_de_retour;
        this.chargeUtile = chargeUtile;
        this.montantTotal = t;
    }

    ReponseTICKMAP(int code, String chargeUtile, byte[] txt) {
        this.code_de_retour = code;
        this.chargeUtile = chargeUtile;
        this.cleCrypte = txt;
    }

    ReponseTICKMAP(int code, String chargeUtile, byte[] cryptKey, byte[] cryptHMAC) {
        this.code_de_retour = code;
        this.chargeUtile = chargeUtile;
        this.cleCrypte = cryptKey;
        this.cleHMAC = cryptHMAC;
    }
    
    ReponseTICKMAP(int code, String chargeUtile, byte[] cryptKey, String msg) {
        this.code_de_retour = code;
        this.chargeUtile = chargeUtile;
        this.cleCrypte = cryptKey;
        this.message = msg;
    }

    ReponseTICKMAP(int code, String chargeUtile, List dataVol) {
        this.code_de_retour = code;
        this.chargeUtile = chargeUtile;
        this.listVol = dataVol;
    }

    public List getListVol() {
        return listVol;
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
    public float getMontantTotal()
    {
        return this.montantTotal;
    }
    
    public String getMessage()
    {
        return this.message;
    }
    
    @Override
    public int getCode() {
        return 0;
    }
    
    
}
