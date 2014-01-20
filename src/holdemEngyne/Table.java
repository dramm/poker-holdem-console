/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package holdemEngyne;

import enums.TableType.Type;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author andrey
 */
public class Table {
     private final Type limit;
     private final float blinds;//блайнды
     private final int maxPlayers;
     private float averageBank; //средний банк
     private int flopView; //сколько раз играли на флопе в %
     private int distributionCount; //количество раздач
     private final ArrayList<Player> players;
     private Bank bank;
     public Table(Type limit, int maxPlayers, float blinds){
         this.limit = limit;
         this.maxPlayers = maxPlayers;
         this.blinds = blinds;
         this.flopView = 0;
         this.averageBank = 0;
         this.distributionCount = 0;
         players = new ArrayList<>(maxPlayers);
     }
     public JSONObject getTableInfo() throws JSONException{
         JSONObject result = new JSONObject();
         result.put("blinds", (blinds / 2) + "/" + blinds);
         result.put("type", limit.toString());
         result.put("players", maxPlayers);
         result.put("averageBank", averageBank);
         result.put("flopView", flopView);
         result.put("distributionCount", distributionCount);
         return result;
     }
     
}
