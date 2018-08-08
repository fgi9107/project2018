/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requete;

import java.net.Socket;

/**
 *
 * @author Benjamin
 */
public interface Requete {
        public Runnable createRunnable (Socket s, Object...ListObj);
}
