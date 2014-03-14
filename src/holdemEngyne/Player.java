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
    private boolean dealer;
    private boolean smallBlind;
    private boolean bigBlind;
    private boolean utg;
    private Card[] pocketCards;
    
    public Player(int playerId, double stack, int plaseId, int tableId) {
        this.playerId = playerId;
        this.stack = stack;
        this.plaseId = plaseId;
        this.tableId = tableId;
        dealer = false;
        smallBlind = false;
        bigBlind = false;
        utg = false;
        pocketCards = new Card[2];
    }
    
    public JSONObject getPlayerInfo(){
        JSONObject result = new JSONObject();
        try { 
            result.put("playerId", getPlayerId());
            result.put("stack", stack);
            result.put("plaseId", plaseId);  
        } catch (JSONException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
        
    }

    /**
     * @return the playerId
     */
    public int getPlayerId() {
        return playerId;
    }
    public Card[] getPocketCards() {
        return pocketCards;
    }

    public boolean isDealer() {
        return dealer;
    }

    public void setDealer(boolean dealer) {
        this.dealer = dealer;
    }

    public boolean isSmallBlind() {
        return smallBlind;
    }

    public void setSmallBlind(boolean smallBlind) {
        this.smallBlind = smallBlind;
    }

    public boolean isBigBlind() {
        return bigBlind;
    }

    public void setBigBlind(boolean bigBlind) {
        this.bigBlind = bigBlind;
    }

    public int getPlaseId() {
        return plaseId;
    }

    public boolean isUtg() {
        return utg;
    }

    public void setUtg(boolean utg) {
        this.utg = utg;
    }
    
    
    
    public void takeOffBlind(float blind){
        if((stack - blind) >= 0){
            stack -= blind;
        }else{
            stack = 0;
        }
    }
    
    public void setPocketCard(Card first, Card second){
        pocketCards = new Card[2];
        pocketCards[0] = first;
        pocketCards[1] = second;
    }
    
    public JSONObject getPocketCard() throws JSONException{
        JSONObject result = new JSONObject();
        result.put("userId", playerId);
        result.put("plaseId", plaseId);
        result.put("firstCard", pocketCards[0].getId());
        result.put("secondCard", pocketCards[1].getId());
        return result;
    }
    
}
