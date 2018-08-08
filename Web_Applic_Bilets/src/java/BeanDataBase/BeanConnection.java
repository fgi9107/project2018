/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanDataBase;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Fanny Gillet
 */
abstract public class BeanConnection implements Serializable{
    
    protected String driver;
    protected String host;
    protected String port;
    protected String login;
    protected String password;
    protected String schema;
    protected Connection connection;
    protected Statement statement;

    public BeanConnection( String host, String port, String login, String password, String schema/*, Connection connection, Statement statement*/) {
        System.out.println("Constructeur BeanConnection");
        this.host = host;
        this.port = port;
        this.login = login;
        this.password = password;
        this.schema = schema;
    }
    
    public ResultSet executeQuery(String qry) throws SQLException
    {
        try
        {
            return getStatement().executeQuery(qry);
        }
        catch(SQLException e)
        {
            System.out.println("erreur : "+e);
        }
        return null;
    }
    
    public void executeUpdate(String qry) throws SQLException
    {
        try
        {
            getStatement().executeUpdate(qry);
        }
        catch(SQLException e)
        {
            System.out.println("erreur : "+e);
        }
    }

    public void CreateConnection(){} // à redéfinier dans les classes fille
    
    
    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public Statement getStatement() {
        return statement;
    }
    
    
    

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    
    
    
    public String getDriver() {
        return driver;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getSchema() {
        return schema;
    }

    public Connection getConnection() {
        return connection;
    }
    
    
    
    
    
    
    
}
