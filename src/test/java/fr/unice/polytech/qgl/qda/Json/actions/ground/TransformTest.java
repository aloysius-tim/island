package fr.unice.polytech.qgl.qda.Json.actions.ground;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertEquals;

/**
 * Transform Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>janv. 5, 2016</pre>
 */
public class TransformTest {
    IslandMap islandMap=new IslandMap(Direction.E);
    Transform transform;

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
        transform=new Transform(islandMap, new JSONObject("{ \"action\": \"transform\", \"parameters\": { \"WOOD\": 6, \"QUARTZ\": 11 }}"), new JSONObject("{ \"cost\": 5, \"extras\": { \"production\": 1, \"kind\": \"GLASS\" },\"status\": \"OK\" }"));
        transform.strategy();
    }

    /**
     * Method: buildAction(Ressource ressource1, int q1, Ressource ressource2, int q2)
     */
    @Test
    public void testBuildAction() throws Exception {
        JSONObject jsonObject=Transform.buildAction(Ressource.FISH, 1, Ressource.FLOWER, 2);
        assertEquals(jsonObject.getJSONObject("parameters").getInt("FISH"), 1);
        assertEquals(jsonObject.getJSONObject("parameters").getInt("FLOWER"), 2);
    }


} 
