package fr.unice.polytech.qgl.qda.Strategizer.DronePhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Echo;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Scan;
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
public class SnakeStrategyTest {

    private SnakeStrategy test;
    private JSONObject contrat;


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

        test = new SnakeStrategy(contrat);
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

        test.getIslandMap().actualizePosition(1,1);

        test.setDirection(Direction.W);
        test.init();
        assertEquals(false, test.getLeftToRight());

        test.setDirection(Direction.E);
        test.init();
        assertEquals(true, test.getLeftToRight());

        test.setDirection(Direction.N);
        test.init();
        assertEquals(Direction.W, test.getDirection());

        assertEquals(false,test.getBottom());

        test.getIslandMap().actualizePosition(0,0);

        test.setDirection(Direction.W);
        test.init();
        assertEquals(false, test.getLeftToRight());

        test.setDirection(Direction.E);
        test.init();
        assertEquals(true, test.getLeftToRight());

        test.setDirection(Direction.N);
        test.init();
        assertEquals(Direction.E, test.getDirection());

        assertEquals(true,test.getBottom());

    }

    @Test
    public void testStepByRight(){

        ArrayList<String> action;

        LocationStrategy tmp = new LocationStrategy(contrat);

        tmp.getIslandMap().setHeight(100);
        tmp.getIslandMap().setWidth(100);

        test = new SnakeStrategy(tmp);

        test.setDirection(Direction.W);

        test.stepByRight();
        action = actionToString(test.getBufferActions());

        assertEquals("N",action.remove(0));
        assertEquals("E",action.remove(action.size()-1));

        test.setDirection(Direction.E);

        test.stepByRight();
        action = actionToString(test.getBufferActions());

        assertEquals("S",action.remove(0));
        assertEquals("W",action.remove(action.size()-1));


    }

    @Test
    public void testStepByLeft(){

        ArrayList<String> action;

        LocationStrategy tmp = new LocationStrategy(contrat);

        tmp.getIslandMap().setHeight(100);
        tmp.getIslandMap().setWidth(100);

        test = new SnakeStrategy(tmp);

        test.setDirection(Direction.W);

        test.stepByLeft();
        action = actionToString(test.getBufferActions());

        assertEquals("S",action.remove(0));
        assertEquals("E",action.remove(action.size()-1));

        test.setDirection(Direction.E);

        test.stepByLeft();
        action = actionToString(test.getBufferActions());

        assertEquals("N",action.remove(0));
        assertEquals("W",action.remove(action.size()-1));
    }

    @Test
    public void testNextFloor(){

        ArrayList<String> action;

        LocationStrategy tmp = new LocationStrategy(contrat);

        tmp.getIslandMap().setHeight(100);
        tmp.getIslandMap().setWidth(100);

        test = new SnakeStrategy(tmp);

        test.setDirection(Direction.W);

        test.setBottom(true);

        test.setLeftToRight(true);
        test.nextFloor();
        action = actionToString(test.getBufferActions());
        assertEquals("S",action.remove(0));
        assertEquals("E",action.remove(action.size()-1));

        test.setDirection(Direction.W);

        test.setLeftToRight(false);
        test.nextFloor();
        action = actionToString(test.getBufferActions());
        assertEquals("N",action.remove(0));
        assertEquals("E",action.remove(action.size()-1));


        test.setBottom(false);


        test.setDirection(Direction.W);


        test.setLeftToRight(true);
        test.nextFloor();
        action = actionToString(test.getBufferActions());
        assertEquals("N",action.remove(0));
        assertEquals("E",action.remove(action.size()-1));


        test.setDirection(Direction.W);

        test.setLeftToRight(false);
        test.nextFloor();
        action = actionToString(test.getBufferActions());
        assertEquals("S",action.remove(0));
        assertEquals("E",action.remove(action.size()-1));

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

        SnakeStrategy snakeStrategy = new SnakeStrategy(context);

        snakeStrategy.getBufferActions().add(0, Echo.buildAction(Direction.E));

        snakeStrategy.getNextMove();

        JSONObject echoResult1 = new JSONObject();
        echoResult1.put("cost", 4);

        JSONObject echoExtras1 = new JSONObject();
        echoExtras1.put("found", "OUT_OF_RANGE");
        echoExtras1.put("range", 30);

        echoResult1.put("extras", echoExtras1);
        echoResult1.put("status", "ok");

        snakeStrategy.interpretAcknowledgeResult(echoResult1);

        tmp=snakeStrategy.getNextMove();

        assertEquals("fly", tmp.get("action"));

        JSONObject flyResult1 = new JSONObject();
        flyResult1.put("cost", 7);
        JSONArray flyExtras1 = new JSONArray();
        flyResult1.put("extras", flyExtras1);
        flyResult1.put("status", "ok");

        snakeStrategy.interpretAcknowledgeResult(flyResult1);

        tmp = snakeStrategy.getNextMove();

        assertEquals("fly", tmp.get("action"));

        JSONObject flyResult2 = new JSONObject();
        flyResult2.put("cost", 7);
        JSONArray flyExtras2 = new JSONArray();
        flyResult2.put("extras", flyExtras2);
        flyResult2.put("status", "ok");

        snakeStrategy.interpretAcknowledgeResult(flyResult2);

        snakeStrategy.getBufferActions().clear();

        snakeStrategy.getBufferActions().add(0, Scan.buildAction());

        snakeStrategy.getNextMove();

        JSONObject scanResult1 = new JSONObject();
        scanResult1.put("cost", 4);
        JSONObject scanExtras1 = new JSONObject();
        JSONArray scanExtrasCreeks = new JSONArray();
        JSONArray scanExtrasBiomes = new JSONArray();
        scanExtrasBiomes.put("MANGROVE");
        scanExtras1.put("creeks", scanExtrasCreeks);
        scanExtras1.put("biomes", scanExtrasBiomes);
        scanResult1.put("extras", scanExtras1);
        scanResult1.put("status", "ok");

        snakeStrategy.interpretAcknowledgeResult(scanResult1);

        JSONObject flyResult3 = new JSONObject();
        flyResult3.put("cost", 7);
        JSONArray flyExtras3 = new JSONArray();
        flyResult3.put("extras", flyExtras3);
        flyResult3.put("status", "ok");

        tmp = snakeStrategy.getNextMove();
        assertEquals("fly", tmp.get("action"));
        snakeStrategy.interpretAcknowledgeResult(flyResult3);

        JSONObject flyResult4 = new JSONObject();
        flyResult4.put("cost", 7);
        JSONArray flyExtras4 = new JSONArray();
        flyResult4.put("extras", flyExtras4);
        flyResult4.put("status", "ok");

        tmp = snakeStrategy.getNextMove();
        assertEquals("fly", tmp.get("action"));
        snakeStrategy.interpretAcknowledgeResult(flyResult4);

        JSONObject flyResult5 = new JSONObject();
        flyResult5.put("cost", 7);
        JSONArray flyExtras5 = new JSONArray();
        flyResult5.put("extras", flyExtras5);
        flyResult5.put("status", "ok");

        tmp = snakeStrategy.getNextMove();
        assertEquals("fly", tmp.get("action"));
        snakeStrategy.interpretAcknowledgeResult(flyResult5);

        tmp = snakeStrategy.getNextMove();
        assertEquals("scan", tmp.get("action"));

        JSONObject scanResult2 = new JSONObject();
        scanResult2.put("cost", 4);
        JSONObject scanExtras2 = new JSONObject();
        JSONArray scanExtrasCreeks2 = new JSONArray();
        JSONArray scanExtrasBiomes2 = new JSONArray();
        scanExtrasBiomes2.put("OCEAN");
        scanExtras2.put("creeks", scanExtrasCreeks2);
        scanExtras2.put("biomes", scanExtrasBiomes2);
        scanResult2.put("extras", scanExtras2);
        scanResult2.put("status", "ok");

        snakeStrategy.interpretAcknowledgeResult(scanResult2);

        snakeStrategy.getBufferActions().add(0, Echo.buildAction(snakeStrategy.getDirection()));
        snakeStrategy.getNextMove();

        JSONObject echoResult2 = new JSONObject();
        echoResult2.put("cost", 4);
        JSONObject echoExtras2 = new JSONObject();
        echoExtras2.put("found", "OUT_OF_RANGE");
        echoExtras2.put("range", 10);
        echoResult2.put("extras", echoExtras2);
        echoResult2.put("status", "ok");

        snakeStrategy.interpretAcknowledgeResult(echoResult2);

        // Now we test if the
    }

}