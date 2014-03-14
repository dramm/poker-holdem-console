/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holdemEngyne;

import enums.TableType.Type;
import holdemEngyne.GameStages.Stage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import threads.Lobby;

public class Table extends Thread {

    private boolean run;
    private int tableId;
    private final Type limit;
    private final float blinds;//блайнды
    private final int maxPlayers;
    private float averageBank; //средний банк
    private int flopView; //сколько раз играли на флопе в %
    private int distributionCount; //количество раздач
    private final ArrayList<Player> players;
    private int currentPlayerCount;
    private Bank bank;
    private Deck deck;
    private Card[] board;
    private Stage gameStage;
    private boolean nextStage;

    public Table(Type limit, int maxPlayers, float blinds, int tableId) {
        this.tableId = tableId;
        this.limit = limit;
        this.maxPlayers = maxPlayers;
        this.blinds = blinds;
        this.flopView = 0;
        this.averageBank = 0;
        this.distributionCount = 0;
        gameStage = Stage.STARTING;
        players = new ArrayList<>(maxPlayers);
        currentPlayerCount = 0;
        deck = new Deck();
        board = new Card[5];
        for (int i = 0; i < maxPlayers; i++) {
            players.add(null);
        }
    }

    @Override
    public void run() {
        nextStage = false;
        run = true;
        while(run){
            try {
                if(currentPlayerCount >= 2){
                    if(nextStage){
                        switch(gameStage){
                            case STARTING:{
                                starting();
                                gameStage = Stage.PREFLOP;
                                break;
                            }
                            case PREFLOP:{
                                preflop();
                                break;
                            }
                            case FLOP:{
                                flop();
                                break;
                            }
                            case TURN:{
                                turn();
                                break;
                            }
                            case RIVER:{
                                river();
                                break;
                            }
                            case SHOWDOWN:{
                                showDown();
                                break;
                            }
                        }
                        //nextStage = false;
                    }
                }
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public JSONObject getTableInfo() throws JSONException {
        JSONObject result = new JSONObject();
        JSONArray blindsJs = new JSONArray();
        blindsJs.put((blinds / 2));
        blindsJs.put(blinds);
        result.put("tableId", getTableId());
        result.put("blinds", blindsJs);
        result.put("type", limit.getInt());
        result.put("players", maxPlayers);
        result.put("playersSitting", getPlayerCount());
        result.put("averageBank", averageBank);
        result.put("flopView", flopView);
        result.put("distributionCount", distributionCount);
        return result;
    }

    public void playerPlant(int possition, Player player) throws JSONException {
        System.out.println("playerPlant");
        if (players.get(possition) == null) {
            players.set(possition, player);
            currentPlayerCount++;
            Lobby.messager.set(121, sayTableStat(121), true);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Complite");
            if(currentPlayerCount >= 2){
                nextStage = true;
            }
        }
        
    }

    private int getPlayerCount() {
        return currentPlayerCount;

    }

    private JSONObject sayTableStat(int command) throws JSONException {
        JSONObject result = new JSONObject();
        JSONArray userlist = new JSONArray();
        for (Player player : getPlayers()) {
            if (player != null) {
                userlist.put(player.getPlayerInfo());
            } else {
                userlist.put(new JSONObject());
            }
        }
        result.put("request", command - 1);
        result.put("data", userlist);
        result.put("tableId", getTableId());
        return result;

    }

    /**
     * @return the tableId
     */
    public int getTableId() {
        return tableId;
    }

    /**
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void removePlayer(int userId) throws JSONException {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) != null) {
                if (players.get(i).getPlayerId() == userId) {
                    players.set(i, null);
                    currentPlayerCount--;
                    break;
                }
            }
        }
        Lobby.messager.set(141, sayTableStat(141), true);
        if(currentPlayerCount < 2){
            gameStage = Stage.STARTING;
            nextStage = false;
        }
    }

    public boolean isNextStage() {
        return nextStage;
    }
    
    private Player getLastPlayer(){
        Player result = null;
        int plaseId = -1;
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i) != null){
                if(players.get(i).getPlaseId() >= plaseId){
                    plaseId = players.get(i).getPlaseId();
                }
            }
        }
        if(plaseId != -1){
            return players.get(plaseId);
        }
        return result;
    }
    
    private Player getFirstPlayer(){
        int plaseId = getLastPlayer().getPlaseId();
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i) != null){
                if(players.get(i).getPlaseId() <= plaseId){
                    plaseId = players.get(i).getPlaseId();
                }
            }
        }
        return players.get(plaseId);
    }

    private Player getNextPlayer(Player currentPlayer){
        if(currentPlayer.getPlaseId() == getLastPlayer().getPlaseId()){
            return getFirstPlayer();
        }else{
            for (int i = currentPlayer.getPlaseId() + 1; i < players.size(); i++) {
                if(players.get(i) != null){
                    return players.get(i);
                }
            }
        }
        return null;
    }
    
    private Player getSmalBlind(){
        for (Player player : players) {
            if(player != null){
                if(player.isSmallBlind()){
                    return player;
                }
            }
        }
        return null;
    }
    
    private  Player getBigBlind(){
        for (Player player : players) {
            if(player != null){
                if(player.isBigBlind()){
                    return player;
                }
            }
        }
        return null;
    }
    
    private Player getDealer(){
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i) != null){
                if(players.get(i).isDealer()){
                    return players.get(i);
                }
            }
        }
        return null;
    }
    
    private void setNewDealer(){
        if(getDealer() == null){
            getFirstPlayer().setDealer(true);
        }else if(getDealer().getPlaseId() == getLastPlayer().getPlaseId()){
            getDealer().setDealer(false);
            getFirstPlayer().setDealer(true);
        }else{
            for (int i = getDealer().getPlaseId() + 1; i < players.size(); i++) {
                if(players.get(i) != null){
                    getDealer().setDealer(false);
                    players.get(i).setDealer(true);
                    break;
                }
            }
        }
    }
    
    private void removeUtg(){
        for (Player player : players) {
            if(player != null){
                if(player.isUtg()){
                    player.setUtg(false);
                }
            }
        }
    }
    
    private Player getUtg(){
        for (Player player : players) {
            if(player != null){
                if(player.isUtg()){
                    return player;
                }
            }
        }
        return null;
    }
    
    public void setNextStage(boolean nextStage) {
        this.nextStage = nextStage;
    }
    
    private void starting() throws JSONException{
        setNewDealer();
        Player bigBlind;
        Player smallBlind;
        Player utg;
        if(currentPlayerCount == 2){
            smallBlind = getDealer();
            smallBlind.setSmallBlind(true);
            smallBlind.setUtg(true);
            bigBlind = getNextPlayer(smallBlind);
            bigBlind.setBigBlind(true);
            bigBlind.takeOffBlind(blinds);
            smallBlind.takeOffBlind(blinds / 2);          
        }else{
            
        }
        JSONObject data = new JSONObject();
        data.put("dealer", getDealer().getPlayerInfo());
        data.put("smalBlind", getSmalBlind().getPlayerInfo());
        data.put("bigBlind", getBigBlind().getPlayerInfo());
        JSONObject result = new JSONObject();
        result.put("request", 150);
        result.put("data", data);
        result.put("tableId", getTableId());
        Lobby.messager.set(151, result, true);
    }

    private void preflop() throws JSONException{
        for (Player player : players) {
            if(player != null){
                player.setPocketCard(deck.issueCard(), deck.issueCard());
            }
        }
        JSONObject data = new JSONObject();
        JSONArray body = new JSONArray();
        for (Player player : players) {
            if(player != null){
                body.put(player.getPocketCard());
            }
        }
        data.put("request", 160);
        data.put("data", body);
        data.put("tableId", tableId);
        Lobby.messager.set(161, data, true);
        nextStage = false;
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
        askPlayer();
    }
    
    private void flop(){
        
    }
    
    private void turn(){
        
    }
    
    private void river(){
        
    }
    
    private void showDown(){
        
    }
    
    public void askPlayer() throws JSONException{
        Player player = getUtg();
        JSONObject buttons = new JSONObject();
        buttons.put("call", getCallButton());
        buttons.put("raise", getRaiseButton());
        buttons.put("fold", getFoldButton());
        buttons.put("check", getCheckButton());
        JSONObject body = new JSONObject();
        body.put("playerId", player.getPlayerId());
        body.put("plaseId", player.getPlaseId());
        body.put("buttons", buttons);
        JSONObject data = new JSONObject();
        data.put("request", 200);
        data.put("tableId", tableId);
        data.put("data", body);
        Lobby.messager.set(201, data, true);
    }
    
    private JSONObject getCallButton() throws JSONException{
        JSONObject result = new JSONObject();
        result.put("visible", true);
        result.put("value", 99);
        return result;
    }
    private JSONObject getRaiseButton() throws JSONException{
        JSONObject result = new JSONObject();
        result.put("visible", true);
        result.put("value", 99);
        result.put("min", 0);
        result.put("max", 99);
        return result;
    }
    
    private JSONObject getFoldButton() throws JSONException{
        JSONObject result = new JSONObject();
        result.put("visible", true);
        return result;
    }
    
    private JSONObject getCheckButton() throws JSONException{
        JSONObject result = new JSONObject();
        result.put("visible", true);
        return result;
    }
}
