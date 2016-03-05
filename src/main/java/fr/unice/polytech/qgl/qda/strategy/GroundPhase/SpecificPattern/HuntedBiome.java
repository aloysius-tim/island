package fr.unice.polytech.qgl.qda.strategy.GroundPhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Game.Assignment;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by justin on 13/12/15.
 */
public class HuntedBiome extends Strategy {

    public HuntedBiome(JSONObject assignment) {
        super(assignment);
    }

    public HuntedBiome(IslandMap islandMap, Assignment assignment, LinkedList<JSONObject> bufferActions, LinkedList<Action> actionsHistory) {
        super(islandMap, assignment, bufferActions, actionsHistory);
    }

    @Override
    public Strategy getNextStrategy() {
        return null;
    }
}
