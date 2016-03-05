package fr.unice.polytech.qgl.qda.Json.actions.ground;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Scan;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Scout Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>janv. 5, 2016</pre>
 */
public class ScoutTest {
    IslandMap islandMap=new IslandMap(Direction.E);
    Scout scout;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: strategy()
     */
    @Test
    public void testStrategy() throws Exception {
        scout=new Scout(islandMap, new JSONObject("{ \"action\": \"scout\", \"parameters\": { \"direction\": \"N\" } }"), new JSONObject("{ \"cost\": 5, \"extras\": { \"altitude\": 1, \"resources\": [\"FUR\", \"WOOD\"] }, \"status\": \"OK\" }"));
        assertEquals(scout.getDirection(), Direction.N);
        assertEquals(scout.getRessources(), new ArrayList<Ressource>(){{add(Ressource.FUR); add(Ressource.WOOD);}});
        assertEquals(scout.getAltitude(), 1);
        scout.strategy();
    }

    /**
     * Method: buildAction(Direction currentHeading)
     */
    @Test
    public void testBuildAction() throws Exception {
        JSONObject jsonObject= Scout.buildAction(Direction.E);
        assertEquals(jsonObject.getJSONObject("parameters").getString("direction"), "E");
    }

    /**
     * Method: getAltitude()
     */
    @Test
    public void testGetAltitude() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getDirection()
     */
    @Test
    public void testGetDirection() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getRessources()
     */
    @Test
    public void testGetRessources() throws Exception {
        //TODO: Test goes here...
    }
} 
