package fr.unice.polytech.qgl.qda.Json.actions.aerial;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

/**
 * IslandProject created on 17/12/2015 by Keynes Timothy - Aloysius_tim
 */
public class Heading extends Action implements IAerialStrategy{
    private Direction direction;

    public Heading(IslandMap islandMap, JSONObject action, JSONObject acknowledgeResult) {
        super(islandMap, action, acknowledgeResult);
        this.direction = Direction.getHeading(action.getJSONObject("parameters").getString("direction"));
        this.islandMap.heading(direction);
    }

    public static JSONObject buildAction(Direction heading) {
        JSONObject firstCmdParameters = new JSONObject();
        firstCmdParameters.put("direction", heading.getDescription());
        JSONObject firstCmd = new JSONObject();
        firstCmd.put("action", "heading");
        firstCmd.put("parameters", firstCmdParameters);
        return firstCmd;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public JSONObject DroneStrategy(Strategy strategy) {
        return null;
    }

    @Override
    public JSONObject LocationStrategy(Strategy strategy) {
        return null;
    }

    @Override
    public JSONObject SnakeStrategy(Strategy strategy) {
        return null;
    }
}
