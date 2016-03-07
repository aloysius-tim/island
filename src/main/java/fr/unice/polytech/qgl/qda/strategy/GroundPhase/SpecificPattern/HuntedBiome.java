package fr.unice.polytech.qgl.qda.strategy.GroundPhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Game.Assignment;
import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Island.Tile;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import fr.unice.polytech.qgl.qda.strategy.GroundPhase.GroundStrategy;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

import java.util.LinkedList;

public class HuntedBiome extends GroundStrategy {

    private Tile currentTile;
    private Ressource targetedResource;
    private Biome biomeOfResource;



    /**
     *
     * Default constructor
     *
     * @param islandMap The current map running
     * @param assignment The assigments of the map
     * @param bufferActions The actions waiting to be sent
     * @param actionsHistory The previous actions
     * @param currentTile The first Tile we want to reach
     * @param targetedResource The resource we want to harvest
     * @param biomeOfResource The biome of the target Tile & Resource
     */
    public HuntedBiome(IslandMap islandMap, Assignment assignment, LinkedList<JSONObject> bufferActions, LinkedList<Action> actionsHistory, Tile currentTile, Ressource targetedResource, Biome biomeOfResource, int remainingBudget) {
        super(islandMap, assignment, bufferActions, actionsHistory, remainingBudget);
        this.currentTile = currentTile; // Dans Island Map ! #responsabiltiés !
        this.targetedResource = targetedResource;
        this.biomeOfResource = biomeOfResource; //Voir et utiliser l'enum Ressource qui contient toutes les associations entre biome et ressources !
    }

    @Override
    public Strategy getNextStrategy() {
        return null;
    }
}
