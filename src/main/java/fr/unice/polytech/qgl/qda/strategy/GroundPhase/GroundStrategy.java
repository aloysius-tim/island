package fr.unice.polytech.qgl.qda.strategy.GroundPhase;

import fr.unice.polytech.qgl.qda.Game.Assignment;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

import java.util.LinkedList;

public abstract class GroundStrategy extends Strategy {
    public GroundStrategy(IslandMap islandMap, Assignment assignment, LinkedList<JSONObject> bufferActions, LinkedList<Action> actionsHistory, int remainingBudget) {
        super(islandMap, assignment, bufferActions, actionsHistory, remainingBudget);
    }

    @Override
    public abstract Strategy getNextStrategy();
}