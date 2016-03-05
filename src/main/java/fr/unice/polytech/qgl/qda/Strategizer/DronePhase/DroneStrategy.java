package fr.unice.polytech.qgl.qda.Strategizer.DronePhase;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Echo;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Fly;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Heading;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Scan;
import fr.unice.polytech.qgl.qda.Json.actions.both.Land;
import fr.unice.polytech.qgl.qda.Strategizer.IStrategy;
import fr.unice.polytech.qgl.qda.Strategizer.Strategy;
import org.json.JSONObject;

/**
 * IslandProject created on 23/11/2015 by Keynes Timothy - Aloysius_tim
 */
public class DroneStrategy extends Strategy implements IStrategy {
    public DroneStrategy(JSONObject context) {
        super(context);
    }

    public DroneStrategy(IStrategy strategy) {
        super(strategy);
    }

    protected void land(String creekId, int people) {
        nextAction.add(Land.buildAction(creekId, people));
    }

    protected void scan() {
        nextAction.add(Scan.buildAction());
    }

    protected void fly() {
        nextAction.add(Fly.buildAction());
    }

    protected void echo(Direction dir) {
        nextAction.add(Echo.buildAction(dir));
        echoDir = dir;
    }

    protected void turnLeft() {
        currentHeading = currentHeading.getLeftDirection();
        nextAction.add(Heading.buildAction(currentHeading));
    }

    protected void turnRight() {
        currentHeading = currentHeading.getRightDirection();
        nextAction.add(Heading.buildAction(currentHeading));
    }

    protected void setHeading(Direction dir) {
        currentHeading = dir;
        nextAction.add(Heading.buildAction(dir));
    }
}
