package fr.unice.polytech.qgl.qda.Json.actions;

import fr.unice.polytech.qgl.qda.Game.AvailableActions;
import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

/**
 * ActionFactory Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>janv. 5, 2016</pre>
 */
public class ActionFactoryTest {
    ActionFactory actionFactory=new ActionFactory() {
        @Override
        public String toString() {
            return super.toString();
        }
    };

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: createAction(JSONObject action, JSONObject acknowledgeResult, IslandMap islandMap)
     */
    @Test
    public void testCreateAction() {
        try {
            IslandMap islandMap=new IslandMap(Direction.E);
            String action = "{ \"action\": \"land\", \"parameters\": { \"creek\": \"id\", \"people\": 42 }}";
            String ack = "{ \"cost\": 15, \"extras\": { }, \"status\": \"OK\" }";

            Action action1=ActionFactory.createAction(new JSONObject(action), new JSONObject(ack), islandMap);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


} 
