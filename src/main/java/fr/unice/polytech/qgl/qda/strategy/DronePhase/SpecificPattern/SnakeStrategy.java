package fr.unice.polytech.qgl.qda.strategy.DronePhase.SpecificPattern;


import fr.unice.polytech.qgl.qda.Game.Assignment;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import fr.unice.polytech.qgl.qda.strategy.DronePhase.DroneStrategy;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

import java.util.LinkedList;

public class SnakeStrategy extends DroneStrategy {
    public SnakeStrategy(IslandMap islandMap, Assignment assignment, LinkedList<JSONObject> bufferActions, LinkedList<Action> actionsHistory, int remainingBudget) {
        super(islandMap, assignment, bufferActions, actionsHistory, remainingBudget);
    }

    @Override
    public Strategy getNextStrategy() {
        return null;
    }
}


