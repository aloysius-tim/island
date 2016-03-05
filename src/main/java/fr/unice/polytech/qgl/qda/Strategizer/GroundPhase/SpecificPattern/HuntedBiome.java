package fr.unice.polytech.qgl.qda.Strategizer.GroundPhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Island.Tile;
import fr.unice.polytech.qgl.qda.Json.actions.ground.Explore;
import fr.unice.polytech.qgl.qda.Json.actions.ground.Glimpse;
import fr.unice.polytech.qgl.qda.Json.actions.ground.Move_to;
import fr.unice.polytech.qgl.qda.Json.actions.ground.Scout;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by justin on 13/12/15.
 */
public class HuntedBiome {
    /*
    Classe associé à FindStrategy qui enregistre les coordonnées d'un tile ainsi que la ressource associé et son biome
    afin de pouvoir mieux contrôler la phase d'exploration d'un biome pour récupérer des matières premières

     */

    private Biome biome;
    private Ressource resource;
    private Tile tile;
    private int outOfBiomeCounter;
    private final int OUT_OF_INTEREST = 5; // on estime qu'après 5 déplacement sans retrouver le Biome  (après un changement de palié) , on  est soit au dessus , soit en dessous du biome
    private boolean end;
    private boolean onBiome;
    private boolean reverse;
    private Direction direction;
    private LinkedList<JSONObject> nextAction;

    public HuntedBiome(Tile tile, Ressource resource, Biome biome){
        nextAction = new LinkedList<>();

        direction = Direction.E;

        this.tile = tile;

        this.resource = resource;
        this.biome = biome;

        this.onBiome = false;
        this.reverse = false;
        this.end = false;

        outOfBiomeCounter = 0;
    }

    public Tile getTile(){
        return this.tile;
    }
    public Biome getBiome(){
        return this.biome;
    }
    public Ressource getResource(){
        return this.resource;
    }
    public Boolean getOnBiome(){ return this.onBiome;}
    public Boolean getReverse(){ return this.reverse;}
    public Boolean getEnd(){ return this.end;}
    public void setOnBiome(boolean set){ this.onBiome = set;}
    public Direction getDirection(){ return this.direction;}
    public LinkedList<JSONObject> getNextAction(){ return this.nextAction;}
    public int getOutOfBiomeCounter(){ return this.outOfBiomeCounter;}
    public int getOUT_OF_INTEREST(){ return this.OUT_OF_INTEREST;}

    public void update(Direction direction){ this.direction = direction;}

    public void glimpse(Direction dir,int range){
       this.nextAction.add(Glimpse.buildAction(dir, range));
        this.direction = dir;
    }

    public void nextStep() {
        if(reverse){
            nextAction.add(Move_to.buildAction(Direction.S));
        }
        else{
            nextAction.add(Move_to.buildAction(Direction.N));
        }

    }
    //  si on trouve une ressource interessante, on continue dans la même direction
    public LinkedList<JSONObject> run(Direction direction){
        this.onBiome = true;
        this.nextAction.clear();
        reset();
        if(direction == Direction.N){
            nextAction.add(Scout.buildAction(Direction.E));
            update(Direction.E);
        }
        else{
            nextAction.add(Move_to.buildAction(direction));
            nextAction.add(Explore.buildAction());
            update(direction);
        }
        return this.nextAction;
    }
    // si on ne trouve plus de ressource interessante on passe au pallié suivant et on repart dans le sens opposé,
    // si après qu'on ait changé de palié on a toujours pas de ressource, on repart au point de départ du biome et on fait la même chose
    // dans le sens inverse (on commence l'exploration vers l'Est et on monte à chaque changement de palié au début donc à cette étape, on explore vers l'Ouest
    // on descend à chaque changement de palié)

    public LinkedList<JSONObject> change(Direction direction){
        this.nextAction.clear();

        if(direction == Direction.N){
            end = true;
        }

        else{
            if(outOfBiomeCounter == 0){
                increment();

                nextStep();
                nextAction.add(Scout.buildAction(direction.getReverse()));
                update(direction.getReverse());



            }
            else if(outOfBiomeCounter<OUT_OF_INTEREST){
                increment();

                nextAction.add(Move_to.buildAction(direction));
                nextAction.add(Scout.buildAction(direction));
                update(direction);

            }
            else{
                this.onBiome = false;
                reset();
                if(reverse) {
                    end = true;
                }
                else{
                    reverse = true;
                    update(Direction.W);
                }
            }

        }
        return this.nextAction;
    }


    public void increment(){outOfBiomeCounter++;}
    public void reset(){outOfBiomeCounter = 0;}
    public void setReverse(boolean set){
        reverse = set;
    }

}
