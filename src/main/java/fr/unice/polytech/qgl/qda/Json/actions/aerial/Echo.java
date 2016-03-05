package fr.unice.polytech.qgl.qda.Json.actions.aerial;

import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

/**
 * IslandProject created on 17/12/2015 by Keynes Timothy - Aloysius_tim
 */
public class Echo extends Action implements IAerialStrategy{
    private Direction direction;
    private int range;
    private Biome biome;

    public Echo(IslandMap islandMap, JSONObject action, JSONObject acknowledgeResult) {
        super(islandMap, action, acknowledgeResult);
        this.direction = Direction.getHeading(action.getJSONObject("parameters").getString("direction"));
        this.range = acknowledgeResult.getJSONObject("extras").getInt("range");
        this.biome = Biome.valueOf(acknowledgeResult.getJSONObject("extras").getString("found"));

        this.islandMap.echo(direction, range, biome);
    }

    public static JSONObject buildAction(Direction currentHeading) {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "echo");

        JSONObject parametersDirection = new JSONObject();
        parametersDirection.put("direction", currentHeading.getDescription());

        cmd.put("parameters", parametersDirection);
        return cmd;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getRange() {
        return range;
    }

    public Biome getBiome() {
        return biome;
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
