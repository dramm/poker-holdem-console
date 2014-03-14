/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package holdemEngyne;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Coder
 */
public class Deck {
    private ArrayList<Card> cardsArray;//основная колода
    public Deck(){
        cardsArray = new ArrayList<>(generateCards());
        //cardsArray = DBTools.getCards();
        Collections.shuffle(cardsArray);      
    }
    public Deck(Deck newDeck){
        cardsArray = new ArrayList<>(newDeck.getCardsArray());
    }
    public Deck(ArrayList<Card> CardsArray){
        cardsArray = CardsArray;
        Collections.shuffle(cardsArray);
    }
    public Card issueCard(){
        return cardsArray.remove(0);        
    }
    
    public ArrayList<Card> getCardsArray() {
        return cardsArray;
    }

    public void setCardsArray(ArrayList<Card> cardsArray) {
        this.cardsArray = cardsArray;
    }
    public void shuffleDeck(){
        Collections.shuffle(cardsArray);
    }
    
    private ArrayList<Card> generateCards(){
        ArrayList<Card> result = new ArrayList<>();
        int suits = 0;
        int dignitys = 2;
        for (int i = 1; i <= 52; i++) {
            result.add(new Card(i, suits, dignitys));
            if((suits%4 + 1) == 4){
                dignitys++;
            }
            suits++;
        }
        return result;
    }
}
