package fr.unice.polytech.qgl.qda.Json.actions.aerial;

import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Scan Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>janv. 5, 2016</pre>
 */
public class ScanTest {
    IslandMap islandMap=new IslandMap(Direction.E);
    Scan scan;

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
        scan=new Scan(islandMap, new JSONObject("{ \"action\": \"scan\" }"), new JSONObject("{\"cost\": 2, \"extras\": { \"biomes\": [\"GLACIER\", \"ALPINE\"], \"creeks\": []}, \"status\": \"OK\"}"));

        assertEquals(scan.getBiomes(), new ArrayList<Biome>(){{add(Biome.GLACIER); add(Biome.ALPINE);}});
        assertEquals(scan.getCreeks(), new ArrayList<String>());

        scan.strategy();
    }

    /**
     * Method: buildAction()
     */
    @Test
    public void testBuildAction() throws Exception {
        JSONObject jsonObject=Scan.buildAction();
        assertEquals(jsonObject.getString("action"), "scan");
    }

    /**
     * Method: getBiomes()
     */
    @Test
    public void testGetBiomes() throws Exception {
        scan=new Scan(islandMap, new JSONObject("{ \"action\": \"scan\" }"), new JSONObject("{\"cost\": 2, \"extras\": { \"biomes\": [\"GLACIER\", \"ALPINE\"], \"creeks\": []}, \"status\": \"OK\"}"));

        assertEquals(scan.getBiomes(), new ArrayList<Biome>(){{add(Biome.GLACIER); add(Biome.ALPINE);}});
    }

    /**
     * Method: getCreeks()
     */
    @Test
    public void testGetCreeks() throws Exception {
        scan=new Scan(islandMap, new JSONObject("{ \"action\": \"scan\" }"), new JSONObject("{\"cost\": 2, \"extras\": { \"biomes\": [\"BEACH\"], \"creeks\": [\"id\"]}, \"status\": \"OK\"}\n"));

        assertEquals(scan.getCreeks(), new ArrayList<String>(){{add("id");}});
    }


} 
