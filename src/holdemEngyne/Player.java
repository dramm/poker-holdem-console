/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package holdemEngyne;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author andrey
 */
public class Player {
    private int playerId;
    private double stack;
    private int plaseId;
    private int tableId;

    public Player(int playerId, double stack, int plaseId, int tableId) {
        this.playerId = playerId;
        this.stack = stack;
        this.plaseId = plaseId;
        this.tableId = tableId;
    }
    
    public JSONObject getPlayerInfo(){
        JSONObject result = new JSONObject();
        try { 
            result.put("playerId", playerId);
            result.put("stack", stack);
            result.put("plaseId", plaseId);  
        } catch (JSONException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
        
    }
    
}
