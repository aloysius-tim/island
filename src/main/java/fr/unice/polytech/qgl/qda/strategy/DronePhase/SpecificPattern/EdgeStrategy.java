package fr.unice.polytech.qgl.qda.strategy.DronePhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.strategy.DronePhase.DroneStrategy;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

/**
 * IslandProject created on 23/11/2015 by Keynes Timothy - Aloysius_tim
 */
public class EdgeStrategy extends DroneStrategy {
    public EdgeStrategy(JSONObject context) {
        super(context);
    }

    @Override
    public Strategy getNextStrategy() {
        return null;
    }
}
