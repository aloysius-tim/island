package fr.unice.polytech.qgl.qda.Json.actions.ground;

import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import org.json.JSONObject;

/**
 * IslandProject created on 17/12/2015 by Keynes Timothy - Aloysius_tim
 */
public class Transform extends Action implements IGroundStrategy{
    public Transform(IslandMap islandMap, JSONObject action, JSONObject acknowledgeResult) {
        super(islandMap, action, acknowledgeResult);
    }

    @Override
    public JSONObject strategy() {
        return null;
    }

    public static JSONObject buildAction(Ressource ressource1, int q1, Ressource ressource2, int q2) {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "transform");
        JSONObject parameters = new JSONObject();

        parameters.put(ressource1.name(), q1);
        parameters.put(ressource2.name(), q2);

        cmd.put("parameters", parameters);

        return cmd;
    }
}
