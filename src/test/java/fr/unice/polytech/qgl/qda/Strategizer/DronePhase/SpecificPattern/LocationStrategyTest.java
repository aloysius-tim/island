package fr.unice.polytech.qgl.qda.Strategizer.DronePhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Strategizer.Strategy;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * Created by justin on 16/01/16.
 */
public class LocationStrategyTest{
    private JSONObject contrat;
    private LocationStrategy test;

    @Before
    public void setUp(){
        contrat = new JSONObject();
        contrat.put("men", 12);
        contrat.put("budget", 10000);

        JSONObject wood = new JSONObject();
        wood.put("amount", 600);
        wood.put("resource", "WOOD");

        JSONObject glass = new JSONObject();
        glass.put("amount", 200);
        glass.put("resource", "GLASS");

        JSONArray ressources = new JSONArray();
        ressources.put(wood);
        ressources.put(glass);

        contrat.put("contracts", ressources);
        contrat.put("heading", "W");

        test = new LocationStrategy(contrat);
    }

    public ArrayList<String> actionToString(LinkedList<JSONObject> list){
        ArrayList<String> action = new ArrayList<>();
        LinkedList<JSONObject> tmp = (LinkedList<JSONObject>)list.clone();
        while(tmp.size()>0){
            JSONObject json = tmp.pop();
            if(json.get("action").equals("heading")){
                action.add(((JSONObject) json.get("parameters")).getString("direction"));
            }
            else if(json.get("action").equals("echo")){
                action.add(json.getString("action") + "_" + ((JSONObject) json.get("parameters")).getString("direction"));
            }
            else if(json.get("action").equals("fly")){
                action.add(json.getString("action"));
            }
            else if(json.get("action").equals("scan")){
                action.add(json.getString("action"));
            }
        }
        return action;
    }

    @Test
    public void testFindPosition(){

        test.setLeftFromStart(true);
        test.setPosition(test.findPosition());
        assertEquals(Strategy.Position.BOTTOM_RIGHT, test.getPosition());

        test.setLeftFromStart(false);
        test.setPosition(test.findPosition());
        assertEquals(Strategy.Position.UPPER_RIGHT, test.getPosition());

        contrat.remove("heading");
        contrat.put("heading","E");
        test = new LocationStrategy(contrat);

        test.setLeftFromStart(true);
        test.setPosition(test.findPosition());
        assertEquals(Strategy.Position.UPPER_LEFT, test.getPosition());

        test.setLeftFromStart(false);
        test.setPosition(test.findPosition());
        assertEquals(Strategy.Position.BOTTOM_LEFT, test.getPosition());

        contrat.remove("heading");
        contrat.put("heading","N");
        test = new LocationStrategy(contrat);

        test.setLeftFromStart(true);
        test.setPosition(test.findPosition());
        assertEquals(Strategy.Position.BOTTOM_LEFT, test.getPosition());

        test.setLeftFromStart(false);
        test.setPosition(test.findPosition());
        assertEquals(Strategy.Position.BOTTOM_RIGHT, test.getPosition());

        contrat.remove("heading");
        contrat.put("heading","S");
        test = new LocationStrategy(contrat);

        test.setLeftFromStart(true);
        test.setPosition(test.findPosition());
        assertEquals(Strategy.Position.UPPER_RIGHT, test.getPosition());

        test.setLeftFromStart(false);
        test.setPosition(test.findPosition());
        assertEquals(Strategy.Position.UPPER_LEFT, test.getPosition());
    }
    @Test
    public void testLocate(){

        test.setNbEcho(1);

        test.locate(3);
        assertEquals(3,test.getMinRange());

        test.setNbEcho(2);

        test.locate(2);
        assertEquals(2,test.getMinRange());
        assertEquals(false,test.getLeftFromStart());

        test.locate(3);
        assertEquals(2,test.getMinRange());
        assertEquals(true,test.getLeftFromStart());

        test.locate(2);
        assertEquals(2,test.getMinRange());
        assertEquals(true,test.getLeftFromStart());

    }

    @Test
    public void noExceptionWithContext() {
        JSONObject context = new JSONObject();
        context.put("heading", "S");
        context.put("men", 10);
        context.put("budget", 10000);

        JSONObject wood = new JSONObject();
        wood.put("amount", 5000);
        wood.put("resource", "WOOD");

        JSONObject glass = new JSONObject();
        glass.put("amount", 100);
        glass.put("resource", "GLASS");

        JSONObject flower = new JSONObject();
        flower.put("amount", 80);
        flower.put("resource", "FLOWER");

        JSONArray contracts = new JSONArray();
        contracts.put(flower);
        contracts.put(wood);
        contracts.put(glass);

        context.put("contracts", contracts);

        Strategy strategy = new LocationStrategy(context);
        LocationStrategy edgeStrategy = new LocationStrategy(strategy);
    }


    @Test
    public void testGetReady(){
        test.setPosition(Strategy.Position.BOTTOM_RIGHT);

        test.setDirection(Direction.E);
        test.getReady();
        assertEquals(false,test.getLeft());

        test.setDirection(Direction.W);
        test.getReady();
        assertEquals(true,test.getLeft());

        test.setDirection(Direction.N);
        test.getReady();
        assertEquals(false,test.getLeft());

        test.setDirection(Direction.S);
        test.getReady();
        assertEquals(true,test.getLeft());

        test.setPosition(Strategy.Position.BOTTOM_LEFT);

        test.setDirection(Direction.E);
        test.getReady();
        assertEquals(false,test.getLeft());

        test.setDirection(Direction.W);
        test.getReady();
        assertEquals(true,test.getLeft());

        test.setDirection(Direction.N);
        test.getReady();
        assertEquals(true, test.getLeft());

        test.setDirection(Direction.S);
        test.getReady();
        assertEquals(false, test.getLeft());

        test.setPosition(Strategy.Position.UPPER_LEFT);

        test.setDirection(Direction.E);
        test.getReady();
        assertEquals(true,test.getLeft());

        test.setDirection(Direction.W);
        test.getReady();
        assertEquals(false,test.getLeft());

        test.setDirection(Direction.N);
        test.getReady();
        assertEquals(true, test.getLeft());

        test.setDirection(Direction.S);
        test.getReady();
        assertEquals(false, test.getLeft());

        test.setPosition(Strategy.Position.UPPER_RIGHT);

        test.setDirection(Direction.E);
        test.getReady();
        assertEquals(true,test.getLeft());

        test.setDirection(Direction.W);
        test.getReady();
        assertEquals(false,test.getLeft());

        test.setDirection(Direction.N);
        test.getReady();
        assertEquals(false, test.getLeft());

        test.setDirection(Direction.S);
        test.getReady();
        assertEquals(true, test.getLeft());
    }


    @Test
    public void testFindEarth(){
        JSONArray biome = new JSONArray();
        String x = "OCEAN";
        String y = "MANGROVE";
        biome.put(x);
        test.findEarth(biome);
        assertEquals(false,test.isEnd());
        biome.put(y);
        test.findEarth(biome);
        assertEquals(true,test.isEnd());

    }

    @Test
    public void testCorrect(){
        test.correct(Direction.E,1);
        assertEquals(1,test.getCorrectY());

        test.correct(Direction.N,1);
        assertEquals(1,test.getCorrectX());

        test.correct(Direction.S,1);
        assertEquals(2,test.getCorrectX());

        test.correct(Direction.W,1);
        assertEquals(2,test.getCorrectY());
    }

    @Test
    public void testFindNextMove() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        ArrayList<String> action = new ArrayList<>();
        JSONObject tmp;

        JSONObject context = new JSONObject();
        context.put("heading", "S");
        context.put("men", 10);
        context.put("budget", 10000);

        JSONObject wood = new JSONObject();
        wood.put("amount", 5000);
        wood.put("resource", "WOOD");

        JSONObject glass = new JSONObject();
        glass.put("amount", 100);
        glass.put("resource", "GLASS");

        JSONObject flower = new JSONObject();
        flower.put("amount", 80);
        flower.put("resource", "FLOWER");

        JSONArray contracts = new JSONArray();
        contracts.put(flower);
        contracts.put(wood);
        contracts.put(glass);

        context.put("contracts", contracts);

        LocationStrategy locationStrategy = new LocationStrategy(context);
        assertEquals(2,locationStrategy.getBufferActions().size());


        tmp=locationStrategy.getNextMove();

        JSONObject echoResult1 = new JSONObject();

        echoResult1.put("cost", 4);

        JSONObject echoExtras1 = new JSONObject();
        echoExtras1.put("found", "OUT_OF_RANGE");
        echoExtras1.put("range", 12);

        echoResult1.put("extras", echoExtras1);
        echoResult1.put("status", "ok");

        locationStrategy.interpretAcknowledgeResult(echoResult1);
        locationStrategy.getNextMove();

        assertEquals(0,locationStrategy.getBufferActions().size());


        JSONObject echoResult2 = new JSONObject();

        echoResult2.put("cost", 4);

        JSONObject echoExtras2 = new JSONObject();
        echoExtras2.put("found", "OUT_OF_RANGE");
        echoExtras2.put("range", 8);

        echoResult2.put("extras", echoExtras2);
        echoResult2.put("status", "ok");

        locationStrategy.interpretAcknowledgeResult(echoResult2);
        assertEquals(false,locationStrategy.getLeftFromStart());
        assertEquals(8,locationStrategy.getMinRange());

        action = actionToString(locationStrategy.getBufferActions());
        assertEquals("W",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("S",action.remove(0));
        assertEquals("echo_S",action.remove(0));
        assertEquals("echo_E",action.remove(0));

        locationStrategy.getNextMove();
        locationStrategy.getNextMove();
        locationStrategy.getNextMove();
        locationStrategy.getNextMove();
        locationStrategy.getNextMove();
        locationStrategy.getNextMove();
        locationStrategy.getNextMove();


        JSONObject echoResult3 = new JSONObject();

        echoResult3.put("cost", 4);

        JSONObject echoExtras3 = new JSONObject();
        echoExtras3.put("found", "OUT_OF_RANGE");
        echoExtras3.put("range", 20);

        echoResult3.put("extras", echoExtras2);
        echoResult3.put("status", "ok");

        locationStrategy.interpretAcknowledgeResult(echoResult3);
        locationStrategy.getNextMove();

        JSONObject echoResult4 = new JSONObject();

        echoResult4.put("cost", 4);

        JSONObject echoExtras4 = new JSONObject();
        echoExtras4.put("found", "OUT_OF_RANGE");
        echoExtras4.put("range", 20);

        echoResult4.put("extras", echoExtras4);
        echoResult4.put("status", "ok");
        locationStrategy.interpretAcknowledgeResult(echoResult4);

        JSONObject echoResult5 = new JSONObject();
        echoResult5.put("cost", 4);

        JSONObject echoExtras5 = new JSONObject();
        echoExtras5.put("found", "GROUND");
        echoExtras5.put("range", 5);

        echoResult5.put("extras", echoExtras5);
        echoResult5.put("status", "ok");
        locationStrategy.interpretAcknowledgeResult(echoResult5);

        action = actionToString(locationStrategy.getBufferActions());
        assertEquals("fly",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("scan",action.remove(0));

        locationStrategy.getNextMove();
        locationStrategy.getNextMove();
        locationStrategy.getNextMove();
        locationStrategy.getNextMove();
        locationStrategy.getNextMove();
        locationStrategy.getNextMove();
        assertEquals(0,locationStrategy.getBufferActions().size());




    }

}