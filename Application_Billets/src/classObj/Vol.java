package classObj;


import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author BJ
 */
public class Vol implements Serializable{
    private String id;
    private String destination;
    private int nbbillets;
    private int prixbillet ;
    private String datedepart;
    private String fk;

    public Vol() {
    }

    public Vol(String id, String destination, int nbbillets, int prixbillet) {
        this.id = id;
        this.destination = destination;
        this.nbbillets = nbbillets;
        this.prixbillet = prixbillet;
    }
    
    public Vol(String id, String destination, String datedepart) {
        this.id = id;
        this.destination = destination;
        this.datedepart = datedepart;
        this.fk = datedepart;
    }

    public Vol(String id, String datedepart, int nbbillets, int prixbillet, String destination) {
        this.id = id;
        this.destination = destination;
        this.nbbillets = nbbillets;
        this.prixbillet = prixbillet;
        this.datedepart = datedepart;
    }
    
    public String getId() {
        return id;
    }

    public String getDatedepart() {
        return datedepart;
    }
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNbbillets() {
        return nbbillets;
    }

    public void setNbbillets(int nbbillets) {
        this.nbbillets = nbbillets;
    }

    public int getPrixbillet() {
        return prixbillet;
    }

    public void setPrixbillet(int prixbillet) {
        this.prixbillet = prixbillet;
    }

    public String getFk() {
        return fk;
    }
    
}
