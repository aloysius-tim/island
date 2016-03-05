package fr.unice.polytech.qgl.qda.Strategizer.GroundPhase;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Strategizer.GroundPhase.GroundStrategy;
import fr.unice.polytech.qgl.qda.Strategizer.GroundPhase.SpecificPattern.FindStrategy;
import fr.unice.polytech.qgl.qda.Strategizer.IStrategy;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;import static org.junit.Assert.assertEquals;


/**
 * GroundStrategy Tester.
 *
 * @author <Authors name>
 * @since <pre>janv. 17, 2016</pre>
 * @version 1.0
 */
public class GroundStrategyTest {
    GroundStrategy groundStrategy=new GroundStrategy(new JSONObject("{ \n" +
            "  \"men\": 12,\n" +
            "  \"budget\": 10000,\n" +
            "  \"contracts\": [\n" +
            "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
            "    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
            "  ],\n" +
            "  \"heading\": \"W\"\n" +
            "}"));

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: move_to(Direction dir)
     *
     */
    @Test
    public void testMove_to() throws Exception {
        groundStrategy.move_to(Direction.E);
        assertEquals(groundStrategy.getNextMove().getString("action"), "move_to");
    }

    /**
     *
     * Method: scout(Direction dir)
     *
     */
    @Test
    public void testScout() throws Exception {
        groundStrategy.scout(Direction.E);
        assertEquals(groundStrategy.getNextMove().getString("action"), "scout");
    }

    /**
     *
     * Method: glimpse(Direction dir, int range)
     *
     */
    @Test
    public void testGlimpse() throws Exception {
        groundStrategy.glimpse(Direction.E, 2);
        assertEquals(groundStrategy.getNextMove().getString("action"), "glimpse");
    }

    /**
     *
     * Method: explore()
     *
     */
    @Test
    public void testExplore() throws Exception {
        groundStrategy.explore();
        assertEquals(groundStrategy.getNextMove().getString("action"), "explore");
    }

    /**
     *
     * Method: exploit(Ressource ressource)
     *
     */
    @Test
    public void testExploit() throws Exception {
        groundStrategy.exploit(Ressource.FISH);
        assertEquals(groundStrategy.getNextMove().getString("action"), "exploit");
    }

    /**
     *
     * Method: transform(Ressource r1, int q1, Ressource r2, int q2)
     *
     */
    @Test
    public void testTransform() throws Exception {
        groundStrategy.transform(Ressource.FISH, 2, Ressource.FLOWER, 4);
        assertEquals(groundStrategy.getNextMove().getString("action"), "transform");

        groundStrategy=new GroundStrategy(new FindStrategy(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}")));
    }


}
