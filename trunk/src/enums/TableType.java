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
    public enum Type{ LIMIT(0), NOLIMIT(1);
        private int value;

        private Type(int value) {
            this.value = value;
        }
        
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
        public int getInt(){
            return value;
        }
    }
}
