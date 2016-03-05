package fr.unice.polytech.qgl.qda.Json.actions.both;

import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import org.json.JSONObject;

/**
 * IslandProject created on 17/12/2015 by Keynes Timothy - Aloysius_tim
 */
public class Stop extends Action {
    public Stop(IslandMap islandMap, JSONObject action, JSONObject acknowledgeResult) {
        super(islandMap, action, acknowledgeResult);
    }

    public JSONObject strategy() {
        return null;
    }

    public static JSONObject buildAction() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "stop");
        return cmd;
    }
}
