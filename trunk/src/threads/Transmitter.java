/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import holdemEngyne.Table;
import holdemEngyne.Xor;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                    case 101: {
                        try {
                            writeMessage(command);
                            Lobby.messager.setFlag(false);
                            break;
                        } catch (IOException ex) {
                            Logger.getLogger(Transmitter.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Transmitter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setOutput(OutputStream output) {
        this.output = output;
    }
    
    public boolean isFlag() {
        return flag;
    }
    
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    private void writeMessage(int command) throws IOException {
        output.write(Functions.intToByteArray(command));
        output.write(Functions.intToByteArray(Lobby.messager.getMessage().toString().length()));
        output.write(Xor.encode(Lobby.messager.getMessage().toString().getBytes()));
        System.out.println(Lobby.messager.getMessage().toString());
        output.flush();
    }
}
