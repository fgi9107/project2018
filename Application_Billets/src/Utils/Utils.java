/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import TICKMAP.ReponseTICKMAP;
import TICKMAP.RequeteTICKMAP;
import classObj.Vol;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BJ
 */
public class Utils {
    private RequeteTICKMAP req;
    private ReponseTICKMAP rep;
    private Socket sock;
    private JTable tabVol;
    
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Utils() {
    }

    public Utils(Socket sock, JTable tabVol) {
        this.sock = sock;
        this.tabVol = tabVol;
    }
    
    public void setJtable() throws SQLException, IOException, ClassNotFoundException
    {
        DefaultTableModel model = (DefaultTableModel)tabVol.getModel();
        
        suppressionLigne(model);
        
        req = new RequeteTICKMAP(RequeteTICKMAP.REQ_DATAVOL, "dataVol");
        oos = new ObjectOutputStream(sock.getOutputStream());
        oos.writeObject(req); oos.flush();

        rep = null;
        ois = new ObjectInputStream(sock.getInputStream());
        rep = (ReponseTICKMAP)ois.readObject();
        
        Vector vData;
        Vol vol;
        for(Object v : rep.getListVol())
        {
            vol = (Vol)v;
            vData = new Vector();
            vData.add(vol.getId());
            vData.add(vol.getDatedepart());
            vData.add(vol.getNbbillets());
            vData.add(vol.getPrixbillet());
            vData.add(vol.getDestination());

            model.addRow(vData);
        }    
    }
    private void suppressionLigne(DefaultTableModel model) {
        if(model.getRowCount()> 0)
        {
            System.out.println("Suppression de lignes");
            while(model.getRowCount() != 0)
            {
                model.removeRow(0);
            }                
        }    
    }

    public RequeteTICKMAP getReq() {
        return req;
    }

    public void setReq(RequeteTICKMAP req) {
        this.req = req;
    }

    public ReponseTICKMAP getRep() {
        return rep;
    }

    public void setRep(ReponseTICKMAP rep) {
        this.rep = rep;
    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public JTable getTabVol() {
        return tabVol;
    }

    public void setTabVol(JTable tabVol) {
        this.tabVol = tabVol;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }
    
}
