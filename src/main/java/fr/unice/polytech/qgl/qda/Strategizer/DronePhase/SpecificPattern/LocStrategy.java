package fr.unice.polytech.qgl.qda.Strategizer.DronePhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Strategizer.DronePhase.DroneStrategy;
import fr.unice.polytech.qgl.qda.Strategizer.IStrategy;
import org.json.JSONObject;

/**
 * Created by justin on 08/02/16.
 */
public class LocStrategy extends DroneStrategy {

    private Integer top;
    private Integer bottom;
    private boolean gotCloserEdge = false;
    private boolean isInit = false;

    public enum Position{
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TOP_lEFT,
        TOP_RIGHT
    }

    public LocStrategy(JSONObject context) {
        super(context);
    }

    public void init(){
        echo(this.islandMap.getDirectionActuelle().getLeftDirection());
        echo(this.islandMap.getDirectionActuelle().getRightDirection());
        isInit = true;
    }

    public void goToCloserEdge(){
        if(top==null){

        }
        else{

        }
    }
    public void getCloserEdge(){

    }

    public void getDistance(int range){


    }
    public void getNext(){
        if(isInit==false){
            init();
        }
        else if(gotCloserEdge == false){
            getCloserEdge();
        }
    }

    public LocStrategy(IStrategy strategy) {
        super(strategy);
    }
}
