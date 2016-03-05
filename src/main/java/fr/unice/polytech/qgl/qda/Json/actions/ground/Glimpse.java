package fr.unice.polytech.qgl.qda.Json.actions.ground;

import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * IslandProject created on 17/12/2015 by Keynes Timothy - Aloysius_tim
 */
public class Glimpse extends Action implements IGroundStrategy{
    public Glimpse(IslandMap islandMap, JSONObject action, JSONObject acknowledgeResult) {
        super(islandMap, action, acknowledgeResult);

        //@TODO Redoit because different format for range > 2
        Direction direction = Direction.getHeading(action.getJSONObject("parameters").getString("direction"));
        int range = action.getJSONObject("parameters").getInt("range");

        ArrayList<HashMap<Biome, Double>> myReport = new ArrayList<>();

        JSONArray reports = acknowledgeResult.getJSONObject("extras").getJSONArray("report");

        if (range<=2){
            for (int i = 0; i < range; i++) {
                // if(reports.get(i).getClass().equals(String.class))return;

                JSONArray report = reports.getJSONArray(i);

                HashMap<Biome, Double> hashMap = new HashMap<Biome, Double>();

                for (Object info : report) {
                    if (((JSONArray) info).length() == 2) {
                        double pourcentage = ((JSONArray) info).getDouble(1);
                        Biome biome = Biome.valueOf(((JSONArray) info).getString(0));

                        hashMap.put(biome, pourcentage);
                    } else {
                        Biome biome = Biome.valueOf(((JSONArray) info).getString(0));

                        hashMap.put(biome, 0.0);
                    }
                }

                myReport.add(hashMap);
            }

            this.islandMap.glimpse(direction, range, myReport);
        }
    }

    @Override
    public JSONObject strategy() {
        return null;
    }

    public static JSONObject buildAction(Direction currentHeading, int range) {
        JSONObject cmd = new JSONObject();
        cmd.put("action", "glimpse");

        JSONObject parameters = new JSONObject();

        parameters.put("direction", currentHeading.getDescription());
        parameters.put("range", range);
        cmd.put("parameters", parameters);
        return cmd;
    }
}
