package fr.unice.polytech.qgl.qda.strategy.GroundPhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Game.Assignment;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by justin on 08/12/15.
 */

public class FindStrategy extends Strategy {
    public FindStrategy(IslandMap islandMap, Assignment assignment, LinkedList<JSONObject> bufferActions, LinkedList<Action> actionsHistory, int remainingBudget) {
        super(islandMap, assignment, bufferActions, actionsHistory, remainingBudget);
    }

    @Override
    public Strategy getNextStrategy() {
        return null;
    }
}
