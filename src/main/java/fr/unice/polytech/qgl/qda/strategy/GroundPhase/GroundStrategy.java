package fr.unice.polytech.qgl.qda.strategy.GroundPhase;

import fr.unice.polytech.qgl.qda.Game.Assignment;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

import java.util.LinkedList;

public class GroundStrategy extends Strategy {
    public GroundStrategy(JSONObject context) {
        super(context);
    }

    public GroundStrategy(IslandMap islandMap, Assignment assignment, LinkedList<JSONObject> bufferActions, LinkedList<Action> actionsHistory) {
        super(islandMap, assignment, bufferActions, actionsHistory);
    }

    @Override
    public Strategy getNextStrategy() {
        return null;
    }
}
