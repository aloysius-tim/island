package fr.unice.polytech.qgl.qda.strategy.GroundPhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

/**
 * Created by justin on 08/12/15.
 */

public class FindStrategy extends Strategy {
    public FindStrategy(JSONObject context) {
        super(context);
    }

    @Override
    public Strategy getNextStrategy() {
        return null;
    }
}
