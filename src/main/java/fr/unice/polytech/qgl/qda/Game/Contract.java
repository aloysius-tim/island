package fr.unice.polytech.qgl.qda.Game;

/**
 * IslandProject created on 18/11/2015 by Keynes Timothy - Aloysius_tim
 */
public class Contract {
    private int initialAmmount;
    private int remainingAmmount;

    public Contract(int initialAmmount) {
        this.initialAmmount = initialAmmount;
        this.remainingAmmount=initialAmmount;
    }
    public void update(int amount){
        this.remainingAmmount -= amount;
    }
    public int getRemainingAmmount(){
        return remainingAmmount;
    }
}
