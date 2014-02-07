/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package holdemEngyne;

import enums.TableType.Type;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import threads.Lobby;


public class Table {
    private int tableId;
     private final Type limit;
     private final float blinds;//блайнды
     private final int maxPlayers;
     private float averageBank; //средний банк
     private int flopView; //сколько раз играли на флопе в %
     private int distributionCount; //количество раздач
     private final ArrayList<Player> players;
     private Bank bank;
     public Table(Type limit, int maxPlayers, float blinds, int tableId){
         this.tableId = tableId;
         this.limit = limit;
         this.maxPlayers = maxPlayers;
         this.blinds = blinds;
         this.flopView = 0;
         this.averageBank = 0;
         this.distributionCount = 0;
         players = new ArrayList<>(maxPlayers);
         for (int i = 0; i < maxPlayers; i++) {
             players.add(null);
         }
     }
     public JSONObject getTableInfo() throws JSONException{
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
     
     public void playerPlant(int possition, Player player) throws JSONException{
         if(players.get(possition) == null){
            players.set(possition, player);
         }
         Lobby.messager.setCommand(121);
         Lobby.messager.setMessage(sayTableStat());
         Lobby.messager.setFlag(true);
     }
     
     private int getPlayerCount(){
         int i = 0;
         for (Player player : players) {
             if(player != null){
                 i++;
             }
         }
        return i;
         
     }
     
     private JSONObject sayTableStat() throws JSONException{
         JSONObject result = new JSONObject();
         JSONArray userlist = new JSONArray();
         for (Player player : players) {
             if(player != null){
                userlist.put(player.getPlayerInfo());
             }else{
                 userlist.put(new JSONObject());
             }
         }
         result.put("request", 120);
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
     
}
