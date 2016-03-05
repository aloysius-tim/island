package fr.unice.polytech.qgl.qda;

import eu.ace_design.island.bot.IExplorerRaid;
import fr.unice.polytech.qgl.qda.strategy.DronePhase.SpecificPattern.LocationStrategy;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

/**
 * IslandProject created on 23/11/2015 by Keynes Timothy - Aloysius_tim
 */
public class Explorer implements IExplorerRaid {
    private fr.unice.polytech.qgl.qda.strategy.Strategy strategy;
    private boolean stop;

    public Explorer() {
        this.stop=false;
    }

    @Override
    public void initialize(String s) {
        this.strategy = new LocationStrategy(new JSONObject(s));
    }

    @Override
    public String takeDecision() {
        if (this.strategy.isEndOfStrat()){
            this.strategy=strategy.getNextStrategy();
        }

        return strategy.getNextMove().toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        if (!stop)
            try {
                strategy.interpretAcknowledgeResult(new JSONObject(s));
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
    }
}
