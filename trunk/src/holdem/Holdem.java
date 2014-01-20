/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package holdem;

import holdemEngyne.Table;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import threads.Receiver;


/**
 *
 * @author andrey
 */
public class Holdem {

    public static ArrayList<Table> tables = new ArrayList<>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        try {
            ServerSocket server = new ServerSocket(7778);
            Receiver rec = new Receiver();
            rec.setClientSocket(server.accept());
            rec.start();
            rec.join();
        } catch (Exception ex) {
            Logger.getLogger(Holdem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
