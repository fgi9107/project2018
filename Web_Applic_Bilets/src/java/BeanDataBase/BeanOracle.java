/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanDataBase;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Fanny Gillet
 */
public class BeanOracle extends BeanConnection{
    
    public BeanOracle(String host, String port, String login, String password, String schema) 
    {
        super( host, port, login, password, schema);
        setDriver("oracle.jdbc.driver.OracleDriver");
    }
    
    @Override
    public void CreateConnection()
    {
        System.out.println("Fonction CreateConnection");
        try
        {
            String infos = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + schema;
            Class.forName(driver);
            connection = DriverManager.getConnection(infos, login, password);
            System.out.println("Connexion à la BDD Oracle réalisée");
            
            statement = getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Création d'une instance d'instruction pour cette connexion");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("erreur : "+e);
        }
    }
    
}
