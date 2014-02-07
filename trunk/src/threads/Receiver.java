/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.Socket;
import org.json.JSONObject;

/**
 *
 * @author andrey
 */
public class Receiver extends Thread { //приемник

    private Socket clientSocket = null;
    private InputStream input = null;

    @Override
    public void run(){
        Transmitter transmitter = new Transmitter();
        Lobby lobby = new Lobby();
        try {
            input = new BufferedInputStream(clientSocket.getInputStream());
            int flag = 1;
            System.out.println("Start flag " + flag);
            transmitter.setOutput(clientSocket.getOutputStream());
            transmitter.start();
            lobby.start();
            while (flag > 0) {
                System.out.println("New iteration");
                byte[] command = new byte[4];
                flag = input.read(command, 0, 4);
                System.out.println("Read command " + flag);
                int result = Functions.byteArrayToInt(command);
                switch (result) {
                    case 100: { // передать список столов
                        lobby.setCommand(result + 1);
                        System.out.println("request tableList");
                        break;
                    }
                    case 120: { // пользователь нажал на крестик на столе.{userId, tableId, plaseId, stack}
                        byte[] len = new byte[4];
                        flag = input.read(len, 0, 4);
                        byte[] message = new byte[Functions.byteArrayToInt(len)];
                        flag = input.read(message, 0, Functions.byteArrayToInt(len));
                        JSONObject data = new JSONObject(new String(Xor.encode(message)));
                        lobby.SetCommandAndData(result + 1, data);
                        System.out.println("Plant user");
                        System.out.println(data.toString());
                        break;
                    }
                }
                Thread.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            transmitter.setFlag(false);
            lobby.setRun(false);
        }
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
