package fr.unice.polytech.qgl.qda.strategy.GroundPhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Island.Tile;
import fr.unice.polytech.qgl.qda.strategy.GroundPhase.GroundStrategy;
import org.json.JSONObject;

public class HuntedBiome extends GroundStrategy {

    private Tile currentTile;
    private Ressource targetedResource;
    private Biome biomeOfResource;

    /**
     *
     * Default constructor
     *
     * @param context The current context in which we create the Strategy
     * @param currentTile The first Tile we want to reach
     * @param targetedResource The resource we want to harvest
     * @param biomeOfResource The biome of the target Tile & Resource
     */
    public HuntedBiome(JSONObject context, Tile currentTile, Ressource targetedResource, Biome biomeOfResource) {
        super(context);
        this.currentTile = currentTile;
        this.targetedResource = targetedResource;
        this.biomeOfResource = biomeOfResource;
    }
}
