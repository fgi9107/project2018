/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classObj;

/**
 *
 * @author BJ
 */
public class Personne {
    private String login;
    private String password;
    private String langue;
    private String email;

    public Personne() {
    }

    public Personne(String login, String password, String langue, String email) {
        this.login = login;
        this.password = password;
        this.langue = langue;
        this.email = email;
    }

    public Personne(String login, String password, String langue) {
        this.login = login;
        this.password = password;
        this.langue = langue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }
    
    
}
