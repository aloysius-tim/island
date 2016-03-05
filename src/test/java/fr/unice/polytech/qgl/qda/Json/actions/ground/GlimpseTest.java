package fr.unice.polytech.qgl.qda.Json.actions.ground;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertEquals;

/**
 * Glimpse Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>janv. 5, 2016</pre>
 */
public class GlimpseTest {
    Glimpse glimpse;
    IslandMap islandMap=new IslandMap(Direction.E);

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
        glimpse=new Glimpse(islandMap, new JSONObject("{ \"action\": \"glimpse\", \"parameters\": { \"direction\": \"N\", \"range\": 2 } }"), new JSONObject("{ \n" +
                "  \"cost\": 3,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [ [ \"BEACH\", 59.35 ], [ \"OCEAN\", 40.65 ] ],\n" +
                "      [ [ \"OCEAN\", 70.2  ], [ \"BEACH\", 29.8  ] ],\n" +
                "      [ \"OCEAN\", \"BEACH\" ],\n" +
                "      [ \"OCEAN\" ]\n" +
                "    ]\n" +
                "  },\n" +
                "  \"status\": \"OK\"\n" +
                "}  "));
        glimpse.strategy();
    }

    /**
     * Method: buildAction(Direction currentHeading, int range)
     */
    @Test
    public void testBuildAction() throws Exception {
        JSONObject jsonObject=Glimpse.buildAction(Direction.E, 2);
        assertEquals(jsonObject.getJSONObject("parameters").getString("direction"), "E");
        assertEquals(jsonObject.getJSONObject("parameters").getInt("range"), 2);

    }


} 
