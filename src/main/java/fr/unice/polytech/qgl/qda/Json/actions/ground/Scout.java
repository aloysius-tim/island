package fr.unice.polytech.qgl.qda.Json.actions.ground;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * IslandProject created on 17/12/2015 by Keynes Timothy - Aloysius_tim
 */
public class Scout extends Action implements IGroundStrategy{
    private final Direction direction;
    private final ArrayList<Ressource> ressources;
    private final int altitude;

    public Scout(IslandMap islandMap, JSONObject action, JSONObject acknowledgeResult) {
        super(islandMap, action, acknowledgeResult);

        this.direction = Direction.getHeading(action.getJSONObject("parameters").getString("direction"));
        this.altitude = acknowledgeResult.getJSONObject("extras").getInt("altitude");
        this.ressources = new ArrayList<>();

        for (Object ressource : acknowledgeResult.getJSONObject("extras").getJSONArray("resources")) {
            ressources.add(Ressource.valueOf((String) ressource));
        }

        this.islandMap.scout(direction, altitude, ressources);
    }

    @Override
    public JSONObject strategy() {
        return null;
    }

    public static JSONObject buildAction(Direction currentHeading) {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "scout");

        JSONObject parametersDirection = new JSONObject();
        parametersDirection.put("direction", currentHeading.getDescription());

        cmd.put("parameters", parametersDirection);
        return cmd;
    }

    public Direction getDirection() {
        return direction;
    }

    public ArrayList<Ressource> getRessources() {
        return ressources;
    }

    public int getAltitude() {
        return altitude;
    }
}
