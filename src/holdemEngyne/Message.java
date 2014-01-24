/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package holdemEngyne;

import org.json.JSONObject;

/**
 *
 * @author andrey
 */
public class Message {
    private JSONObject message;
    private boolean flag;
    private int command;

    public Message() {
        flag = false;
        message = new JSONObject();
    }

    public Message(JSONObject message, boolean flag, int command) {
        this.message = message;
        this.flag = flag;
        this.command = command;
    }
    
    
    
    public synchronized JSONObject getMessage() {
        return message;
    }

    public synchronized void setMessage(JSONObject message) {
        this.message = message;
    }

    public synchronized boolean isFlag() {
        return flag;
    }

    public synchronized void setFlag(boolean flag) {
        this.flag = flag;
    }

    public synchronized int getCommand() {
        return command;
    }

    public synchronized void setCommand(int command) {
        this.command = command;
    }
    
}
