/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton;

/**
 *
 * @author Benjamin
 */
public class singletonClient {
    
    private static final singletonClient instance = new singletonClient();
    //private PublicKey publicKey;
    
    private singletonClient() {
    }
    public static singletonClient getInstance()
    {
        return instance;
    }
}
