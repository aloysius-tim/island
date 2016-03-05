package fr.unice.polytech.qgl.qda.Json.actions;

import fr.unice.polytech.qgl.qda.Game.AvailableActions;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Fly;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

/**
 * IslandProject created on 17/12/2015 by Keynes Timothy - Aloysius_tim
 */
public abstract class ActionFactory {
    public static Action createAction(JSONObject action, JSONObject acknowledgeResult, IslandMap islandMap) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return (Action) AvailableActions.valueOf(action.getString("action").toUpperCase()).getAssociatedClass().getConstructors()[0].newInstance(islandMap, action, acknowledgeResult);
    }
}