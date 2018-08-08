/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanDataBase;

import java.sql.*;
/**
 *
 * @author Benjamin
 */
public class test {
    
    public static void main(String args[]) throws SQLException
    {
        BeanConnection bc;
        ResultSet rs = null;
        bc = new BeanMySql("localhost", "3306", "root", "", "mysql");
        bc.CreateConnection();
        System.out.println("executeQuery");
//        
//        rs = bc.executeQuery("select *  from jdbc.personnel where ndc = 'benja'");
//
//        int cpt = 0;
//        System.out.println("Test : " + rs.next());
//        while (rs.next())
//        {
//            if (cpt==0) 
//                System.out.println("Parcours du curseur"); 
//            cpt++;
//            String id = rs.getString("ndc");
//            System.out.println("id avion = "+id);
//            
//        }
//        rs = bc.executeQuery("select * from jdbc2.personnel");
//        while (rs.next())
//        {
//            System.out.println("id mes couilles = " + rs.getString("mdp"));
//        }
//for(int i = 1 ; i < 8 ; i++)
//        bc.executeUpdate("update jdbc2.billets set argent = 10000 where idbillet = "+i+"");
        for(int i = 1 ; i < 6 ; i++)
        {
            bc.executeUpdate("update jdbc2.clients set langues = 'francais' where ID = "+i+"");
            bc.executeUpdate("update jdbc2.clients set mail = 'inpresmail@gmail.com' where ID = "+i+"");

        }
    }
    
}
