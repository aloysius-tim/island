package fr.unice.polytech.qgl.qda.Json.actions.aerial;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Fly Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>janv. 5, 2016</pre>
 */
public class FlyTest {
    private Fly fly;
    private IslandMap islandMap;

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
        islandMap=new IslandMap(Direction.E);
        fly=new Fly(islandMap, new JSONObject("{ \"action\": \"fly\" }"), new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }"));
        assertEquals(fly.getCost(), 2);
        assertEquals(fly.isStatus(), true);

        fly.strategy();
    }

    /**
     * Method: buildAction()
     */
    @Test
    public void testBuildAction() throws Exception {
        JSONObject jsonObject=Fly.buildAction();
        assertEquals(jsonObject.getString("action"), "fly");
    }


} 
