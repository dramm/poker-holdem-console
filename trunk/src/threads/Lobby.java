/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threads;

import enums.TableType;
import holdemEngyne.Message;
import holdemEngyne.Player;
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
        tables.add(new Table(TableType.Type.NOLIMIT, 5, 1.00f, 3));
        tables.add(new Table(TableType.Type.NOLIMIT, 5, 1.00f, 4));
        tables.add(new Table(TableType.Type.NOLIMIT, 4, 1.00f, 5));
        tables.add(new Table(TableType.Type.NOLIMIT, 4, 1.00f, 6));
    }
    
    
    @Override
    public void run() {
        while(run){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setCommand(int command) throws JSONException{
        switch(command){
            case 101:{
                getTablesList(command);
                break;
            }
        }
    }
    
    public void SetCommandAndData(int command, JSONObject data){
        switch(command){
            case 121:{
                playerPlant(data);
                break;
            }
        }
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
    
    private void playerPlant(JSONObject data){
        try {
            int userId = data.getInt("userId");
            double stack = data.getDouble("stack");
            int tableId = data.getInt("tableId");
            int plaseId = data.getInt("plaseId");
            Player p = new Player(userId, stack, plaseId, tableId);
            //tables.get(tableId).playerPlant(plaseId, p);
            for (Table table : tables) {
                if(table.getTableId() == tableId){
                    table.playerPlant(plaseId, p);
                }
            }
            
        } catch (JSONException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param run the run to set
     */
    public void setRun(boolean run) {
        this.run = run;
    }
    
}
