package fr.unice.polytech.qgl.qda.Json.actions.aerial;

import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * IslandProject created on 17/12/2015 by Keynes Timothy - Aloysius_tim
 */
public class Scan extends Action implements IAerialStrategy{
    private ArrayList<String> creeks = new ArrayList<>();
    private ArrayList<Biome> biomes = new ArrayList<>();

    public Scan(IslandMap islandMap, JSONObject action, JSONObject acknowledgeResult) {
        super(islandMap, action, acknowledgeResult);

        for (Object biome : acknowledgeResult.getJSONObject("extras").getJSONArray("biomes")) {
            biomes.add(Biome.valueOf((String) biome));
        }

        for (Object creek : acknowledgeResult.getJSONObject("extras").getJSONArray("creeks")) {
            creeks.add((String) creek);
        }

        this.islandMap.scan(biomes, creeks);
    }

    public static JSONObject buildAction() {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "scan");
        return cmd;
    }

    public ArrayList<String> getCreeks() {
        return creeks;
    }

    public ArrayList<Biome> getBiomes() {
        return biomes;
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
