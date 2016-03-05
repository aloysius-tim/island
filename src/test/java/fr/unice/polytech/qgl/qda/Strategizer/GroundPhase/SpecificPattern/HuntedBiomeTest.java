package fr.unice.polytech.qgl.qda.Strategizer.GroundPhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Island.Tile;
import fr.unice.polytech.qgl.qda.Json.actions.ground.Explore;
import fr.unice.polytech.qgl.qda.Json.actions.ground.Glimpse;
import fr.unice.polytech.qgl.qda.Json.actions.ground.Move_to;
import fr.unice.polytech.qgl.qda.Json.actions.ground.Scout;
import fr.unice.polytech.qgl.qda.Strategizer.GroundPhase.SpecificPattern.HuntedBiome;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by justin on 15/01/16.
 */
public class HuntedBiomeTest {
    private HuntedBiome test;
    @Before
    public void setUp(){
        HuntedBiome test = new HuntedBiome(new Tile(0,0), Ressource.FLOWER, Biome.TEMPERATE_RAIN_FOREST);
    }

    public ArrayList<String> actionToString(LinkedList<JSONObject> list){
        ArrayList<String> action = new ArrayList<>();
        while(list.size()>0){
            JSONObject json = list.pop();
            if(json.get("action").equals("heading")){
                action.add(((JSONObject) json.get("parameters")).getString("direction"));
            }
            else if(json.get("action").equals("glimpse")){
                action.add(json.getString("action")+"_"+((JSONObject)json.get("parameters")).get("direction"));
            }
            else if(json.get("action").equals("scout")){
                action.add(json.getString("action")+"_"+((JSONObject)json.get("parameters")).get("direction"));
            }
            else if(json.get("action").equals("move_to")){
                action.add(json.getString("action")+"_"+((JSONObject)json.get("parameters")).get("direction"));
            }
            else if(json.get("action").equals("explore")){
                action.add(json.getString("action"));
            }

        }
        return action;
    }

    @Test
    public void testGlimpse(){
        ArrayList<String> action = new ArrayList<>();
        HuntedBiome test = new HuntedBiome(new Tile(0,0), Ressource.FLOWER, Biome.TEMPERATE_RAIN_FOREST);
        test.glimpse(Direction.W, 1);
        action = actionToString(test.getNextAction());
        assertEquals("glimpse_W",action.remove(0));
        assertEquals(Direction.W,test.getDirection());
    }

    @Test
    public void testNextStep() {
        ArrayList<String> action = new ArrayList<>();
        HuntedBiome test = new HuntedBiome(new Tile(0,0), Ressource.FLOWER, Biome.TEMPERATE_RAIN_FOREST);

        test.setReverse(false);
        test.nextStep();
        action = actionToString(test.getNextAction());
        assertEquals("move_to_N",action.remove(0));


        test.getNextAction().clear();
        test.setReverse(true);
        test.nextStep();
        action = actionToString(test.getNextAction());
        assertEquals("move_to_S",action.remove(0));


    }

    @Test
    public void testRun(){
        HuntedBiome test = new HuntedBiome(new Tile(0,0), Ressource.FLOWER, Biome.TEMPERATE_RAIN_FOREST);
        ArrayList<String> action = new ArrayList<>();
        test.run(Direction.N);
        action = actionToString(test.getNextAction());
        assertEquals("scout_E",action.remove(0));

        test.run(Direction.W);
        action = actionToString(test.getNextAction());
        assertEquals("move_to_W",action.remove(0));
        assertEquals("explore",action.remove(0));
    }

    @Test
    public void testChange(){
        ArrayList<String> action = new ArrayList<>();
        HuntedBiome test = new HuntedBiome(new Tile(0,0), Ressource.FLOWER, Biome.TEMPERATE_RAIN_FOREST);

        test.change(Direction.N);
        assertEquals(test.getEnd(),true);


        test = new HuntedBiome(new Tile(0,0), Ressource.FLOWER, Biome.TEMPERATE_RAIN_FOREST);

        test.change(Direction.E);
        action = actionToString(test.getNextAction());
        assertEquals("scout_W",action.remove(action.size()-1));


        while(test.getOutOfBiomeCounter()<test.getOUT_OF_INTEREST())
            test.increment();
        test.getReverse().equals(false);

        test.change(Direction.E);

        assertEquals(test.getEnd(),false);
        assertEquals(test.getReverse(),true);
        assertEquals(0,test.getOutOfBiomeCounter());


        test.increment();
        test.change(Direction.E);
        action = actionToString(test.getNextAction());
        assertEquals(2,test.getOutOfBiomeCounter());
        assertEquals("scout_E",action.remove(action.size()-1));

    }

    @Test
    public void testIncrement(){
        test = new HuntedBiome(new Tile(0,0), Ressource.FLOWER, Biome.TEMPERATE_RAIN_FOREST);
        test.increment();
        assertEquals(1,test.getOutOfBiomeCounter());
        for(int i = 0;i<10;i++){
            test.increment();
        }
        assertEquals(11,test.getOutOfBiomeCounter());
    }

    @Test
    public void testReset(){
        test = new HuntedBiome(new Tile(0,0), Ressource.FLOWER, Biome.TEMPERATE_RAIN_FOREST);
        test.increment();
        assertEquals(1,test.getOutOfBiomeCounter());
        test.reset();
        assertEquals(0,test.getOutOfBiomeCounter());
    }

    @Test
    public void testSetReverse(){
        test = new HuntedBiome(new Tile(0,0), Ressource.FLOWER, Biome.TEMPERATE_RAIN_FOREST);
        test.getReverse();
        assertEquals(false, test.getReverse());
        test.setReverse(true);
        assertEquals(true, test.getReverse());

    }

}
