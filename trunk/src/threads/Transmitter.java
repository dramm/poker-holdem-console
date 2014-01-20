/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threads;

import holdemEngyne.Table;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author andrey
 */
public class Transmitter extends Thread{ //передатчик
    private OutputStream output = null;
    private int eventComand = 0;
    private boolean flag = true;

    @Override
    public void run(){
            while(flag){
                switch(eventComand){
                    case 100:{
                        JSONArray arr = new JSONArray();
                        for (Table table : holdem.Holdem.tables) {
                            try {
                                arr.put(table.getTableInfo());
                            } catch (JSONException ex) {
                                Logger.getLogger(Transmitter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
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

    public int getEventComand() {
        return eventComand;
    }

    public void setEventComand(int eventComand) {
        this.eventComand = eventComand;
    }
    
    
    
}
