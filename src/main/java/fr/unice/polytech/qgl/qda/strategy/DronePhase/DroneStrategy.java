package fr.unice.polytech.qgl.qda.strategy.DronePhase;

import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

/**
 * IslandProject created on 23/11/2015 by Keynes Timothy - Aloysius_tim
 */
public class DroneStrategy extends Strategy {
    public DroneStrategy(JSONObject context) {
        super(context);
    }

    @Override
    public Strategy getNextStrategy() {
        return null;
    }
}
