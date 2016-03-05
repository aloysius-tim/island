package fr.unice.polytech.qgl.qda.Json.actions;

import fr.unice.polytech.qgl.qda.Island.IslandMap;
import org.json.JSONObject;

/**
 * IslandProject created on 17/12/2015 by Keynes Timothy - Aloysius_tim
 */
public abstract class Action {
    protected int cost;
    protected boolean status;
    protected static IslandMap islandMap;

    public Action(IslandMap islandMap, JSONObject action, JSONObject acknowledgeResult) {
        this.islandMap = islandMap;

        this.cost = acknowledgeResult.getInt("cost");

        this.status = acknowledgeResult.getString("status").equals("OK");
    }

    public int getCost() {
        return cost;
    }

    public IslandMap getIslandMap() {
        return islandMap;
    }

    public boolean isStatus() {
        return status;
    }
}
