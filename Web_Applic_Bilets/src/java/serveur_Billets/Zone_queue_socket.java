/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur_Billets;

import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * @author Benjamin
 */
public class Zone_queue_socket {
    private Socket socket_use;
    private boolean occupé = false;
    private LinkedList queue = new LinkedList();
    
    public synchronized Socket retire_socket() throws InterruptedException
    {
        while(queue.size() == 0) wait();
        occupé = false;
        
        socket_use = (Socket)queue.getFirst();
        Socket sauvContenu = socket_use;
        queue.remove(0);
        notify();
        return sauvContenu;
    }
    
    public synchronized void depose_socket(Socket s) throws InterruptedException
    {
        while (queue.size() > 3) wait();
            socket_use = s;
            queue.add(s);
            occupé = true;
            notify();
    }
    
    public Socket getSocket_use() {
        return socket_use;
    }

    public void setSocket_use(Socket socket_use) {
        this.socket_use = socket_use;
    }

    public boolean isOccupé() {
        return occupé;
    }

    public void setOccupé(boolean occupé) {
        this.occupé = occupé;
    }
}
