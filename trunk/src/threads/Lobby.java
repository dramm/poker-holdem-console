/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threads;

import enums.TableType;
import holdemEngyne.Message;
import holdemEngyne.Table;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author andrey
 */
public class Lobby extends Thread {
    
    public static Message messager = new Message();
    
    private ArrayList<Table> tables = new ArrayList<>();
    private boolean run;

    public Lobby() {
        run = true;
        tables.add(new Table(TableType.Type.LIMIT, 9, 0.5f, 1));
        tables.add(new Table(TableType.Type.NOLIMIT, 9, 0.5f, 2));
        tables.add(new Table(TableType.Type.NOLIMIT, 6, 1.00f, 3));
    }
    
    
    @Override
    public void run() {
        while(run){
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Message setCommand(int command) throws JSONException{
        switch(command){
            case 101:{
                getTablesList(command);
                break;
            }
        }
        
        return null;
    }

    private void getTablesList(int command) throws JSONException {
        JSONArray tablesList = new JSONArray();
        JSONObject result = new JSONObject();
        for (Table table : tables) {
            tablesList.put(table.getTableInfo());
        }
        result.put("data", tablesList);
        result.put("request", command - 1);
        messager.setMessage(result);
        messager.setCommand(command);
        messager.setFlag(run);
    }
    
}
