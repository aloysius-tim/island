package fr.unice.polytech.qgl.qda.Strategizer.GroundPhase;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Json.actions.ground.*;
import fr.unice.polytech.qgl.qda.Strategizer.IStrategy;
import fr.unice.polytech.qgl.qda.Strategizer.Strategy;
import org.json.JSONObject;

public class GroundStrategy extends Strategy implements IStrategy {
    public GroundStrategy(JSONObject context) {
        super(context);
    }

    public GroundStrategy(IStrategy strategy) {
        super(strategy);
    }

    public void move_to(Direction dir) {
        nextAction.add(Move_to.buildAction(dir));
        moveDir = dir;
    }

    public void scout(Direction dir) {
        nextAction.add(Scout.buildAction(dir));
        scoutDir = dir;
    }

    public void glimpse(Direction dir, int range) {
        nextAction.add(Glimpse.buildAction(dir,range));
        glimpseDir = dir;
    }

    public void explore() {
        nextAction.add(Explore.buildAction());
    }

    public void exploit(Ressource ressource) {
        nextAction.add(Exploit.buildAction(ressource));
    }

    public void transform(Ressource r1, int q1, Ressource r2, int q2) {
        nextAction.add(Transform.buildAction(r1, q1, r2, q2));
    }

}
