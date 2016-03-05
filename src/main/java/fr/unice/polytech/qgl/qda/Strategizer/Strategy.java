package fr.unice.polytech.qgl.qda.Strategizer;

import fr.unice.polytech.qgl.qda.Game.Assignment;
import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Island.Tile;
import fr.unice.polytech.qgl.qda.Json.actions.ActionFactory;
import fr.unice.polytech.qgl.qda.Json.actions.both.Land;
import fr.unice.polytech.qgl.qda.Json.actions.both.Stop;
import fr.unice.polytech.qgl.qda.Json.actions.ground.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * IslandProject created on 23/11/2015 by Keynes Timothy - Aloysius_tim
 */
@SuppressWarnings("ALL")
public class Strategy implements IStrategy {
    protected IslandMap islandMap;
    protected Assignment assignment;
    protected LinkedList<JSONObject> nextAction;
    public ArrayList<String> creekIds = new ArrayList<String>();
    protected HashMap<Ressource,ArrayList<Tile>> tileList;
    protected Direction currentHeading;
    public static Direction nextHeading;
    protected static final int BUDGET_LIMIT = 200;
    protected JSONObject lastAction;
    protected boolean end = false;
    protected Position position;
    protected Direction echoDir;
    protected Direction glimpseDir;
    protected Direction scoutDir;
    protected Direction moveDir;

    public Strategy(JSONObject context) {
        this.assignment = new Assignment(context);
        currentHeading = assignment.getHeading();
        nextHeading=currentHeading;
        this.islandMap = new IslandMap(assignment.getHeading());
        this.nextAction = new LinkedList<>();
        this.tileList = new HashMap<>();
        for(Ressource res : assignment.keySet()){
            if(res.getTypeRessource() != Ressource.TypeRessource.MANUFACTURED) {
                this.tileList.put(res, new ArrayList<Tile>());
            }
        }
    }

    public Strategy(IStrategy strategy) {
        this.assignment = strategy.getAssignement();
        this.islandMap = strategy.getIslandMap();
        this.nextAction = new LinkedList<>();
        this.creekIds = strategy.getCreekIds();
        this.tileList = strategy.getTileList();

    }

    @Override
    public void interpretAcknowledgeResult(JSONObject acknowledgeResult) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        ActionFactory.createAction(lastAction, acknowledgeResult, islandMap);
    }

    @Override
    public JSONObject getNextMove() {
        if (nextAction.size() == 0) {
            lastAction = Stop.buildAction();
            return lastAction;
        }
        lastAction = this.nextAction.removeFirst();
        return lastAction;
    }

    protected void stop(){nextAction.add(Stop.buildAction());}

     /*
     * modifie le heading et le budget à chaque resultat obtenue
     */
    protected void update(int cost){
        this.assignment.updateBudget(cost);
        if(this.assignment.getBudget() < BUDGET_LIMIT){

            nextAction.clear();
            nextAction.add(Stop.buildAction());
        }
    }

    protected void findCreeks(JSONArray creeks){
        if (creeks.length() > 0) {
            for (Object x : creeks) {
                //Action.println("CREEK FOUND :"+(String) x);
                nextAction.clear();
                creekIds.add((String) x);
                end();
            }
        }
    }
    protected void move_to(Direction dir){
        nextAction.add(Move_to.buildAction(dir));
        moveDir = dir;
    }

    protected void glimpse(Direction dir, int range){
        nextAction.add(Glimpse.buildAction(dir,range));
        glimpseDir = dir;
    }

    protected void scout(Direction dir){
        nextAction.add(Scout.buildAction(dir));
        scoutDir = dir;
    }

    protected void explore(){
        nextAction.add(Explore.buildAction());
    }
    protected void exploit(Ressource res){
        nextAction.add(Exploit.buildAction(res));
    }

    protected void land(String creekId, int person){
        nextAction.add(Land.buildAction(creekId,person));
    }

    public enum Position {
        UPPER_RIGHT,
        UPPER_LEFT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT
    }

    public boolean isEnd(){
        return this.end;
    }
    protected void end(){
        end = true;
    }
    public Position getPosition(){
        return position;
    }
    public HashMap<Ressource,ArrayList<Tile>> getTileList(){
        return this.tileList;
    }
    public Assignment getAssignement() {
        return this.assignment;
    }
    public IslandMap getIslandMap() {
        return this.islandMap;
    }
    public ArrayList<String> getCreekIds(){
        return this.creekIds;
    }
    public LinkedList<JSONObject> getBufferActions(){
        return nextAction;
    }
}
