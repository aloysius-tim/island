package fr.unice.polytech.qgl.qda.Strategizer;

import fr.unice.polytech.qgl.qda.Game.Assignment;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Island.Tile;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * IslandProject created on 23/11/2015 by Keynes Timothy - Aloysius_tim
 */
public interface IStrategy {
    void interpretAcknowledgeResult(JSONObject acknowledgeResult) throws IllegalAccessException, InstantiationException, InvocationTargetException;

    JSONObject getNextMove();

    Assignment getAssignement();

    IslandMap getIslandMap();

    Strategy.Position getPosition();

    LinkedList<JSONObject> getBufferActions();

    ArrayList<String> getCreekIds();
    HashMap<Ressource,ArrayList<Tile>>getTileList();

    boolean isEnd();
}
