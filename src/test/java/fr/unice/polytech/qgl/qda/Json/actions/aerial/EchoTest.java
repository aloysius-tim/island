package fr.unice.polytech.qgl.qda.Json.actions.aerial;

import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertEquals;

/**
 * Echo Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>janv. 5, 2016</pre>
 */
public class EchoTest {
    public Echo echo;
    public IslandMap islandMap;

    @Before
    public void before() throws Exception {
        islandMap=new IslandMap(Direction.E);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: strategy()
     */
    @Test
    public void testStrategy() throws Exception {
        echo=new Echo(islandMap, new JSONObject("{ \"action\": \"echo\", \"parameters\": { \"direction\": \"E\" } }"), new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));
        assertEquals(echo.getDirection(), Direction.E);
        assertEquals(echo.getRange(), 2);
        assertEquals(echo.getBiome(), Biome.GROUND);

        echo.strategy();
    }

    /**
     * Method: buildAction(Direction currentHeading)
     */
    @Test
    public void testBuildAction() throws Exception {
        JSONObject jsonObject = Echo.buildAction(Direction.E);
        assertEquals(jsonObject.getJSONObject("parameters").getString("direction"), "E");
    }

    /**
     *
     * Method: getDirection()
     *
     */
    @Test
    public void testGetDirection() throws Exception {
        echo=new Echo(islandMap, new JSONObject("{ \"action\": \"echo\", \"parameters\": { \"direction\": \"E\" } }"), new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));
        assertEquals(echo.getDirection(), Direction.E);
    }

    /**
     *
     * Method: getRange()
     *
     */
    @Test
    public void testGetRange() throws Exception {
        echo=new Echo(islandMap, new JSONObject("{ \"action\": \"echo\", \"parameters\": { \"direction\": \"E\" } }"), new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));

        assertEquals(echo.getRange(), 2);

    }

    /**
     *
     * Method: getBiome()
     *
     */
    @Test
    public void testGetBiome() throws Exception {
        echo=new Echo(islandMap, new JSONObject("{ \"action\": \"echo\", \"parameters\": { \"direction\": \"E\" } }"), new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));


        assertEquals(echo.getBiome(), Biome.GROUND);
    }
} 
