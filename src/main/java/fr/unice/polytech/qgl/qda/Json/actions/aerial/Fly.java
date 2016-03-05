package fr.unice.polytech.qgl.qda.Json.actions.aerial;

import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

/**
 * IslandProject created on 17/12/2015 by Keynes Timothy - Aloysius_tim
 */
public class Fly extends Action implements IAerialStrategy{
    public Fly(IslandMap islandMap, JSONObject action, JSONObject acknowledgeResult) {
        super(islandMap, action, acknowledgeResult);
        this.islandMap.fly();
    }

    public static JSONObject buildAction() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "fly");
        return cmd;
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
