package fr.unice.polytech.qgl.qda.strategy.DronePhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Echo;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Fly;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Heading;
import fr.unice.polytech.qgl.qda.strategy.DronePhase.DroneStrategy;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

/**
 * Created by justin on 08/02/16.
 */
public class LocationStrategy extends DroneStrategy {
    private Integer top;
    private int rangeToCorner;
    private boolean gotCloserEdge = false;
    private boolean isInit = false;
    private boolean inCorner = false;

    public LocationStrategy(JSONObject context) { super(context); }

    public void init(){
        this.bufferActions.add(Echo.buildAction(this.islandMap.getDirectionActuelle().getLeftDirection()));
        this.bufferActions.add(Echo.buildAction((this.islandMap.getDirectionActuelle().getRightDirection())));
        isInit = true;
    }

    public void lastStep(){
        int BORDERCONTROL = 3;
        if(rangeToCorner<BORDERCONTROL){
            this.bufferActions.add(Heading.buildAction(this.assignment.getHeading()));
        }
        this.bufferActions.add(Echo.buildAction(this.assignment.getHeading()));
    }

    public void goToCloserEdge(){
        int BORDERCONTROL = 3;
        inCorner = true;
        if(rangeToCorner<BORDERCONTROL)return;

        if(rangeToCorner == top){this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getLeftDirection())); }
        else {
            this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection())); }

        for(int i = 0;i<top-1;i++){
            this.bufferActions.add(Fly.buildAction());
        }
    }

    public int getCloserEdge(int range){
        if(top==null){
            top = range;
            return top;
        }
        else{
            gotCloserEdge = true;

            if(this.assignment.getHeading() == Direction.E || this.assignment.getHeading() == Direction.W){
                this.islandMap.setHeight(top+range);
            }
            else{
                this.islandMap.setWidth(top+range);
            }

            if(top>=range){return top;}
            else{return range;}
        }
    }

    public void completeMapSize(int range){
        int BORDERCONTROL = 3;
        if(rangeToCorner>=BORDERCONTROL)range += BORDERCONTROL-1;
        if(this.assignment.getHeading() == Direction.E || this.assignment.getHeading() == Direction.W){
            this.islandMap.setWidth(range);
        }
        else{
            this.islandMap.setHeight(range);
        }
    }

    @Override
    public JSONObject getNextMove() {
        if(!isInit){
            init();
        }
        else if(!gotCloserEdge) {
            rangeToCorner = getCloserEdge(((Echo) this.actionsHistory.getLast()).getRange());
        }

        else if(!inCorner){
            goToCloserEdge();
            lastStep();
        }
        else if(!endOfStrat){
            if(this.bufferActions.size()<=0){
                completeMapSize(((Echo) this.actionsHistory.getLast()).getRange());
                endOfStrat = true;
            }
        }
        return super.getNextMove();
    }

    @Override
    public Strategy getNextStrategy() {
        this.islandMap.clear();

        if (this.islandMap.getCreeks().size()==0) {
            //@TODO TO SET TO GOOD STRAYT
            this.bufferActions.add(Fly.buildAction());
            return new SnakeStrategy(new JSONObject());
        }

        return new EdgeStrategy(islandMap, assignment, bufferActions, actionsHistory);
    }
}