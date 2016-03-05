package fr.unice.polytech.qgl.qda.Island;

import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import org.json.JSONObject;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * IslandProject created on 17/11/2015 by Keynes Timothy - Aloysius_tim
 */
public class Tile {
    private HashMap<Biome, Double> relatedBiomes;
    private HashSet<Ressource> associatedRessources;
    private int altitude;

    private int x;
    private int y;

    //flags
    private boolean unreachable;
    private boolean visited;
    private boolean scouted;
    private boolean glimpsed;

    public Tile(int x, int y) {
        this.relatedBiomes = new HashMap<>();
        this.associatedRessources = new HashSet<>();
        this.x=x;
        this.y=y;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    public void setScouted(boolean scouted) {
        this.scouted = scouted;
    }

    public HashMap<Biome, Double> getRelatedBiomes() {
        return relatedBiomes;
    }

    public Biome getOnlyBiome() {
        Biome dominantBiome=null;
        if (relatedBiomes.size() == 1) {
            for (Map.Entry<Biome, Double> entry : relatedBiomes.entrySet()) {
                dominantBiome = entry.getKey();
            }
            return dominantBiome;
        }
        return null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getNorth() {
        return new Point(x + Direction.N.getMovX(), y + Direction.N.getMovY());
    }

    public Point getSouth() {
        return new Point(x + Direction.S.getMovX(), y + Direction.S.getMovY());
    }

    public Point getEst() {
        return new Point(x + Direction.E.getMovX(), y + Direction.E.getMovY());
    }

    public Point getWest() {
        return new Point(x + Direction.W.getMovX(), y + Direction.W.getMovY());
    }

    public Point getAdjacent(Direction direction) {
        switch (direction) {
            case N:
                return getNorth();
            case W:
                return getWest();
            case S:
                return getSouth();
            case E:
                return getEst();
            default:
                return getEst();
        }
    }

    public void addRelatedBiomes(ArrayList<Biome> relatedBiomes) {
        for (Biome biome : relatedBiomes) {
            this.relatedBiomes.put(biome, null);
        }
    }

    public void addRelatedBiomes(Biome biome) {
        this.relatedBiomes.put(biome, null);
    }

    public void addAssociatedRessource(ArrayList<Ressource> ressources) {
        this.associatedRessources.addAll(ressources);
    }

    public void addAssociatedRessource(Ressource ressource) {
        this.associatedRessources.add(ressource);
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public HashSet<Ressource> getAssociatedRessources() {
        return associatedRessources;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "altitude=" + altitude +
                ", relatedBiomes=" + relatedBiomes +
                ", associatedRessources=" + associatedRessources +
                ", x=" + x +
                ", y=" + y +
                ", unreachable=" + unreachable +
                ", visited=" + visited +
                ", scouted=" + scouted +
                ", glimpsed=" + glimpsed +
                '}';
    }
}
