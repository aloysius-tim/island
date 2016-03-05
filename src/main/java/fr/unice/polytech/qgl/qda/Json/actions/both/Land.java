package fr.unice.polytech.qgl.qda.Json.actions.both;

import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import org.json.JSONObject;

/**
 * IslandProject created on 17/12/2015 by Keynes Timothy - Aloysius_tim
 */
public class Land extends Action {
    private String creek;
    private int peoples;

    public Land(IslandMap islandMap, JSONObject action, JSONObject acknowledgeResult) {
        super(islandMap, action, acknowledgeResult);

        this.creek = action.getJSONObject("parameters").getString("creek");
        this.peoples = action.getJSONObject("parameters").getInt("people");

        this.islandMap.land(creek, peoples);
    }

    public JSONObject strategy() {
        return null;
    }

    public static JSONObject buildAction(String creekID, int people) {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "land");

        JSONObject parameters = new JSONObject();
        parameters.put("creek", creekID);
        parameters.put("people", people);
        cmd.put("parameters", parameters);
        return cmd;
    }

    public String getCreek() {
        return creek;
    }

    public int getPeoples() {
        return peoples;
    }
}
