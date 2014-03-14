/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrey
 */
public class Transmitter extends Thread { //передатчик

    private OutputStream output = null;
    private boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            if (Lobby.messager.isFlag()) {
                int command = Lobby.messager.getCommand();
                switch (command) {
                    case 101: 
                    case 121:
                    case 131:
                    case 141:
                    case 151://starting stage
                    case 161://preflop
                    {
                        writeMessage();
                        break;
                    }
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Transmitter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setOutput(OutputStream output) {
        this.output = new BufferedOutputStream(output);
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    private void writeMessage() {
        try {
            output.write(Functions.intToByteArray(Lobby.messager.getCommand()));
            output.write(Functions.intToByteArray(Lobby.messager.getMessage().toString().length()));
            output.write(Xor.encode(Lobby.messager.getMessage().toString().getBytes()));
            System.out.println("Write message");
            System.out.println(Lobby.messager.getMessage().toString());
            output.flush();
            Lobby.messager.setFlag(false);
        } catch (IOException ex) {
            Logger.getLogger(Transmitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
