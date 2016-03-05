package fr.unice.polytech.qgl.qda.strategy.GroundPhase;

import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

public class GroundStrategy extends Strategy {
    public GroundStrategy(JSONObject context) {
        super(context);
    }

    @Override
    public Strategy getNextStrategy() {
        return null;
    }
}
