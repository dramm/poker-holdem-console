/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 *
 * @author andrey
 */
public class Receiver extends Thread { //приемник

    private Socket clientSocket = null;
    private InputStream input = null;

    @Override
    public void run() {

        try {
            Transmitter transmitter = new Transmitter();
            input = new BufferedInputStream(clientSocket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
