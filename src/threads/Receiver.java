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
            transmitter.setOutput(clientSocket.getOutputStream());
            transmitter.start();
            input = new BufferedInputStream(clientSocket.getInputStream());
            int flag = 1;
            while (flag > 0) {
                byte[] command = new byte[4];
                flag = input.read(command, 0, 4);
                int result = Functions.byteArrayToInt(command);
                switch (result) {
                    case 100: {
                        synchronized (transmitter) {
                            transmitter.setEventComand(result);
                            transmitter.wait();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTablesList() {

    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
