/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threads;

import java.io.OutputStream;

/**
 *
 * @author andrey
 */
public class Transmitter extends Thread{ //передатчик
    private OutputStream output = null;
    private boolean flag = true;

    @Override
    public void run(){
        while(flag){
            
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
    
    
}
