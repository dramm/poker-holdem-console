/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package holdemEngyne;

/**
 *
 * @author Coder
 */
public class Card implements Comparable{
    private int id;
    private int suitsId;
    private int dignitysId;
    public Card(){
        this.id = 0;
        this.suitsId = 0;
        this.dignitysId = 0;
    }
    public Card(int id, int suitsId, int dignitysId){
        this.id = id;
        this.suitsId = suitsId;
        this.dignitysId = dignitysId;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setSuitsId(int suitsId){
        this.suitsId = suitsId;
    }
    public int getSuitsId(){
        return this.suitsId;
    }
    public void setDignitysId(int dignitysId){
        this.dignitysId = dignitysId;
    }
    public int getDignitysId(){
        return this.dignitysId;
    }
    @Override
    public int compareTo(Object obj){
        Card tmp = (Card)obj;
        if(this.dignitysId < tmp.dignitysId){
            return -1;
        }
        else if(this.dignitysId > tmp.dignitysId){
            return 1;
        }
        return 0;
    }
    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Card)) return false;
        Card card = (Card)obj;
        return card.getId() == this.id;
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
    }
    public boolean isMore(Card card){
        if(this.dignitysId > card.getDignitysId()){
            return true;
        }
        return false;
    }
    public boolean isLess(Card card){
        return !isMore(card);
    }
}

