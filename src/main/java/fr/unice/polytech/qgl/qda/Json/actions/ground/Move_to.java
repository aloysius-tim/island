package fr.unice.polytech.qgl.qda.Json.actions.ground;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import org.json.JSONObject;

/**
 * IslandProject created on 17/12/2015 by Keynes Timothy - Aloysius_tim
 */
public class Move_to extends Action implements IGroundStrategy{
    private final Direction direction;

    public Move_to(IslandMap islandMap, JSONObject action, JSONObject acknowledgeResult) {
        super(islandMap, action, acknowledgeResult);
        this.direction = Direction.getHeading(action.getJSONObject("parameters").getString("direction"));

        this.islandMap.move_to(direction);
    }

    @Override
    public JSONObject strategy() {
        return null;
    }

    public static JSONObject buildAction(Direction currentHeading) {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "move_to");
        JSONObject parametersDirection = new JSONObject();
        parametersDirection.put("direction", currentHeading.getDescription());

        cmd.put("parameters", parametersDirection);
        return cmd;
    }

    public Direction getDirection() {
        return direction;
    }
}
