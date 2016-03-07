package fr.unice.polytech.qgl.qda.strategy.DronePhase;

import fr.unice.polytech.qgl.qda.Game.Assignment;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * IslandProject created on 23/11/2015 by Keynes Timothy - Aloysius_tim
 */
public abstract class DroneStrategy extends Strategy {
    public DroneStrategy(IslandMap islandMap, Assignment assignment, LinkedList<JSONObject> bufferActions, LinkedList<Action> actionsHistory, int remainingBudget) {
        super(islandMap, assignment, bufferActions, actionsHistory, remainingBudget);
    }

    @Override
    public abstract Strategy getNextStrategy();
}
