package fr.unice.polytech.qgl.qda.Json.actions.ground;

import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * IslandProject created on 17/12/2015 by Keynes Timothy - Aloysius_tim
 */
public class Explore extends Action implements IGroundStrategy{
    private ArrayList<Ressource> ressources = new ArrayList<>();

    public Explore(IslandMap islandMap, JSONObject action, JSONObject acknowledgeResult) {
        super(islandMap, action, acknowledgeResult);
        for (Object ressource : acknowledgeResult.getJSONObject("extras").getJSONArray("resources")) {
            ressources.add(Ressource.valueOf(((JSONObject) ressource).getString("resource")));
        }

        this.islandMap.explore(ressources);
    }

    @Override
    public JSONObject strategy() {
        return null;
    }

    public static JSONObject buildAction() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "explore");
        return cmd;
    }

    public ArrayList<Ressource> getRessources() {
        return ressources;
    }
}
