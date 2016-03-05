package fr.unice.polytech.qgl.qda.Json.actions.aerial;

import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

/**
 * IslandProject created on 05/01/2016 by Keynes Timothy - Aloysius_tim
 */
public interface IAerialStrategy {
    JSONObject DroneStrategy(Strategy strategy);
    JSONObject LocationStrategy(Strategy strategy);
    JSONObject SnakeStrategy(Strategy strategy);
}