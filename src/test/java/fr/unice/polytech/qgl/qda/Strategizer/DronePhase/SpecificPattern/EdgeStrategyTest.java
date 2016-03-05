package fr.unice.polytech.qgl.qda.Strategizer.DronePhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Echo;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Scan;
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
public class EdgeStrategyTest{

    private JSONObject contrat;
    private EdgeStrategy test;

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

        test = new EdgeStrategy(contrat);


    }
    public ArrayList<String> actionToString(LinkedList<JSONObject> list){
        ArrayList<String> action = new ArrayList<>();
        while(list.size()>0){
            JSONObject json = list.pop();
            if(json.get("action").equals("heading")){
                action.add(((JSONObject) json.get("parameters")).getString("direction"));
            }
            else if(json.get("action").equals("fly")){
                action.add(json.getString("action"));
            }
        }
        return action;
    }

    @Test
    public void testInit(){

        test.setPosition(Strategy.Position.BOTTOM_LEFT);

        test.setOnLeft(true);

        test.init();
        assertEquals(false, test.getClock());

        test.setOnLeft(false);

        test.init();
        assertEquals(true,test.getClock());

        test.setPosition(Strategy.Position.BOTTOM_RIGHT);

        test.setOnLeft(true);

        test.init();
        assertEquals(false,test.getClock());

        test.setOnLeft(false);

        test.init();
        assertEquals(true,test.getClock());

        test.setPosition(Strategy.Position.UPPER_LEFT);

        test.setOnLeft(true);

        test.init();
        assertEquals(true,test.getClock());

        test.setOnLeft(false);

        test.init();
        assertEquals(false,test.getClock());

        test.setPosition(Strategy.Position.UPPER_RIGHT);

        test.setOnLeft(true);

        test.init();
        assertEquals(true,test.getClock());

        test.setOnLeft(false);

        test.init();
        assertEquals(false,test.getClock());
    }

    @Test
    public void testUp(){

        ArrayList<String> action;

        test.setDirection(Direction.W);

        test.setClock(true);
        test.up();

        action = actionToString(test.getBufferActions());

        assertEquals("N",action.remove(0));
        assertEquals("E",action.remove(0));
        assertEquals("S",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("W",action.remove(0));

        test.setClock(false);
        test.up();

        action = actionToString(test.getBufferActions());

        assertEquals("N",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("E",action.remove(0));
        assertEquals("S",action.remove(0));
        assertEquals("W",action.remove(0));

        test.setClock(true);

        test.setDirection(Direction.E);
        test.up();
        action = actionToString(test.getBufferActions());

        assertEquals("S",action.remove(0));
        assertEquals("W",action.remove(0));
        assertEquals("N",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("E",action.remove(0));

        test.setDirection(Direction.N);
        test.up();
        action = actionToString(test.getBufferActions());

        assertEquals("E",action.remove(0));
        assertEquals("S",action.remove(0));
        assertEquals("W",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("N",action.remove(0));

        test.setDirection(Direction.S);
        test.up();
        action = actionToString(test.getBufferActions());

        assertEquals("W",action.remove(0));
        assertEquals("N",action.remove(0));
        assertEquals("E",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("S",action.remove(0));

    }

    @Test
    public void testDown(){
        ArrayList<String> action;

        test.setDirection(Direction.W);

        test.setClock(false);
        test.down();

        action = actionToString(test.getBufferActions());

        assertEquals("N",action.remove(0));
        assertEquals("E",action.remove(0));
        assertEquals("S",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("W",action.remove(0));

        test.setClock(true);
        test.down();

        action = actionToString(test.getBufferActions());

        assertEquals("N",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("E",action.remove(0));
        assertEquals("S",action.remove(0));
        assertEquals("W",action.remove(0));

        test.setClock(false);

        test.setDirection(Direction.E);
        test.down();
        action = actionToString(test.getBufferActions());

        assertEquals("S",action.remove(0));
        assertEquals("W",action.remove(0));
        assertEquals("N",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("E",action.remove(0));

        test.setDirection(Direction.N);
        test.down();
        action = actionToString(test.getBufferActions());

        assertEquals("E",action.remove(0));
        assertEquals("S",action.remove(0));
        assertEquals("W",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("N",action.remove(0));

        test.setDirection(Direction.S);
        test.down();
        action = actionToString(test.getBufferActions());

        assertEquals("W",action.remove(0));
        assertEquals("N",action.remove(0));
        assertEquals("E",action.remove(0));
        assertEquals("fly",action.remove(0));
        assertEquals("S",action.remove(0));
    }

    @Test
    public void testHeadingNext(){
        ArrayList<String> action;

        test.setDirection(Direction.W);

        test.setClock(true);
        test.headingNext();

        action = actionToString(test.getBufferActions());

        assertEquals("S",action.remove(0));
        assertEquals("E",action.remove(0));
        assertEquals("N",action.remove(0));
        assertEquals("fly",action.remove(0));

        test.setClock(false);

        test.setDirection(Direction.W);
        test.headingNext();

        action = actionToString(test.getBufferActions());

        assertEquals("N",action.remove(0));
        assertEquals("E",action.remove(0));
        assertEquals("S",action.remove(0));
        assertEquals("fly",action.remove(0));
    }

    @Test
    public void testHeadingPrevious(){
        ArrayList<String> action;

        test.setDirection(Direction.W);

        test.setClock(false);
        test.headingPrevious();

        action = actionToString(test.getBufferActions());

        assertEquals("S",action.remove(0));
        assertEquals("E",action.remove(0));
        assertEquals("N",action.remove(0));
        assertEquals("fly",action.remove(0));

        test.setClock(true);

        test.setDirection(Direction.W);
        test.headingPrevious();

        action = actionToString(test.getBufferActions());

        assertEquals("N",action.remove(0));
        assertEquals("E",action.remove(0));
        assertEquals("S",action.remove(0));
        assertEquals("fly",action.remove(0));
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

        Strategy strategy = new EdgeStrategy(context);
        EdgeStrategy edgeStrategy = new EdgeStrategy(strategy);
    }

    @Test
    public void noExceptionWithAndCreekAlreadyFound() {
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

        Strategy strategy = new EdgeStrategy(context);
        strategy.creekIds.add("9274a8c9-afcc-480c-8cd4-9766630e3a49");
        EdgeStrategy edgeStrategy = new EdgeStrategy(strategy);
    }

    @Test
    public void findNextMoveTest() throws IllegalAccessException, InstantiationException, InvocationTargetException {

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

        EdgeStrategy edgeStrategy = new EdgeStrategy(context);

        edgeStrategy.getBufferActions().add(0, Echo.buildAction(Direction.S));
        tmp=edgeStrategy.getNextMove();

        JSONObject echoResult1 = new JSONObject();
        echoResult1.put("cost", 4);

        JSONObject echoExtras1 = new JSONObject();
        echoExtras1.put("found", "GROUND");
        echoExtras1.put("range", 0);

        echoResult1.put("extras", echoExtras1);
        echoResult1.put("status", "ok");

        edgeStrategy.setPosition(Strategy.Position.UPPER_RIGHT);
        edgeStrategy.interpretAcknowledgeResult(echoResult1);

        edgeStrategy.getBufferActions().add(0, Scan.buildAction());

        edgeStrategy.getNextMove();

        JSONObject scanResult1 = new JSONObject();
        scanResult1.put("cost", 4);
        JSONObject scanExtras1 = new JSONObject();
        JSONArray scanExtrasCreeks = new JSONArray();
        JSONArray scanExtrasBiomes = new JSONArray();
        scanExtrasBiomes.put("OCEAN");
        scanExtras1.put("creeks", scanExtrasCreeks);
        scanExtras1.put("biomes", scanExtrasBiomes);
        scanResult1.put("extras", scanExtras1);
        scanResult1.put("status", "ok");

        edgeStrategy.interpretAcknowledgeResult(scanResult1);

        edgeStrategy.getNextMove();

        JSONObject headingResult1 = new JSONObject();
        headingResult1.put("cost", 7);
        JSONArray headingExtras1 = new JSONArray();
        headingResult1.put("extras", headingExtras1);
        headingResult1.put("status", "ok");

        edgeStrategy.interpretAcknowledgeResult(headingResult1);

        edgeStrategy.getNextMove();

        JSONObject headingResult2 = new JSONObject();
        headingResult2.put("cost", 7);
        JSONArray headingExtras2 = new JSONArray();
        headingResult2.put("extras", headingExtras2);
        headingResult2.put("status", "ok");

        edgeStrategy.interpretAcknowledgeResult(headingResult2);

        edgeStrategy.getNextMove();

        JSONObject flyResult1 = new JSONObject();
        flyResult1.put("cost", 7);
        JSONArray flyExtras1 = new JSONArray();
        flyResult1.put("extras", flyExtras1);
        flyResult1.put("status", "ok");

        edgeStrategy.interpretAcknowledgeResult(flyResult1);

        edgeStrategy.getNextMove();

        JSONObject echoResult2 = new JSONObject();
        echoResult2.put("cost", 4);
        JSONObject echoExtras2 = new JSONObject();
        echoExtras2.put("found", "OUT_OF_RANGE");
        echoExtras2.put("range", 10);
        echoResult2.put("extras", echoExtras2);
        echoResult2.put("status", "ok");

        edgeStrategy.getBufferActions().add(0, Echo.buildAction(Direction.E));

        edgeStrategy.getNextMove();

        edgeStrategy.interpretAcknowledgeResult(echoResult2);

        JSONObject scanResult2 = new JSONObject();
        scanResult2.put("cost", 4);
        JSONObject scanExtras2 = new JSONObject();
        JSONArray scanExtrasCreeks2 = new JSONArray();
        JSONArray scanExtrasBiomes2 = new JSONArray();
        scanExtrasBiomes2.put("BEACH");
        scanExtras2.put("creeks", scanExtrasCreeks2);
        scanExtras2.put("biomes", scanExtrasBiomes2);
        scanResult2.put("extras", scanExtras2);
        scanResult2.put("status", "ok");

        edgeStrategy.getBufferActions().add(0,Scan.buildAction());

        edgeStrategy.getNextMove();

        edgeStrategy.interpretAcknowledgeResult(scanResult2);

        JSONObject scanResult3 = new JSONObject();
        scanResult3.put("cost", 4);
        JSONObject scanExtras3 = new JSONObject();
        JSONArray scanExtrasCreeks3 = new JSONArray();
        JSONArray scanExtrasBiomes3 = new JSONArray();
        scanExtrasBiomes3.put("OCEAN");
        scanExtrasBiomes3.put("BEACH");
        scanExtras3.put("creeks", scanExtrasCreeks3);
        scanExtras3.put("biomes", scanExtrasBiomes3);
        scanResult3.put("extras", scanExtras3);
        scanResult3.put("status", "ok");

        edgeStrategy.getBufferActions().add(0,Scan.buildAction());

        edgeStrategy.getNextMove();

        edgeStrategy.interpretAcknowledgeResult(scanResult3);
    }

}