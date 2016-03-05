package fr.unice.polytech.qgl.qda.strategy;

import fr.unice.polytech.qgl.qda.Game.Assignment;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import fr.unice.polytech.qgl.qda.Json.actions.ActionFactory;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

/**
 * IslandProject created on 23/11/2015 by Keynes Timothy - Aloysius_tim
 */
public abstract class Strategy {
    protected IslandMap islandMap;
    protected Assignment assignment;
    protected LinkedList<JSONObject> bufferActions;
    protected LinkedList<Action> actionsHistory;

    protected int remainingBudget;

    protected boolean endOfStrat;

    public Strategy(JSONObject assignment) {
        this.endOfStrat = false;
        this.assignment = new Assignment(assignment);
        this.remainingBudget = this.assignment.getBudget();

        this.islandMap = new IslandMap(this.assignment.getHeading());

        this.bufferActions = new LinkedList<>();
        this.actionsHistory = new LinkedList<>();
    }

    public Strategy(IslandMap islandMap, Assignment assignment, LinkedList<JSONObject> bufferActions, LinkedList<Action> actionsHistory) {
        this.islandMap = islandMap;
        this.assignment = assignment;
        this.bufferActions = bufferActions;
        this.actionsHistory = actionsHistory;
    }

    public void interpretAcknowledgeResult(JSONObject acknowledgeResult) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        this.actionsHistory.addLast(ActionFactory.createAction(bufferActions.removeFirst(), acknowledgeResult, islandMap));
        this.remainingBudget-=this.actionsHistory.getLast().getCost();
    }

    public JSONObject getNextMove() {
        return bufferActions.getFirst();
    }

    public boolean isEndOfStrat() {
        return endOfStrat;
    }

    public abstract Strategy getNextStrategy();
}