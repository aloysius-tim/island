package fr.unice.polytech.qgl.qda.strategy.DronePhase.SpecificPattern;


import fr.unice.polytech.qgl.qda.strategy.DronePhase.DroneStrategy;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

public class SnakeStrategy extends DroneStrategy {
    public SnakeStrategy(JSONObject context) {
        super(context);
    }

    @Override
    public Strategy getNextStrategy() {
        return null;
    }
}


