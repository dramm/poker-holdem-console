/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package enums;

/**
 *
 * @author andrey
 */
public class TableType {
    public enum Type{ LIMIT, NOLIMIT;
        @Override
        public String toString(){
            switch(this){
                case LIMIT:{
                    return "Limit";
                }
                case NOLIMIT:{
                    return "No limit";
                }
            }
            return null;
        }
    }
}
