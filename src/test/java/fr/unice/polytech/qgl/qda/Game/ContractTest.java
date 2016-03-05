package fr.unice.polytech.qgl.qda.Game;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertEquals;

/**
 * Contract Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>janv. 5, 2016</pre>
 */
public class ContractTest {
    private Contract contract;

    @Before
    public void before() throws Exception {
        contract = new Contract(1000);
    }

    @After
    public void after() throws Exception {
        contract = new Contract(1000);
    }

    /**
     * Method: update(int amount)
     */
    @Test
    public void testUpdate() throws Exception {
        contract.update(100);
        assertEquals(900, contract.getRemainingAmmount());
    }

    /**
     * Method: getRemainingAmmount()
     */
    @Test
    public void testGetRemainingAmmount() throws Exception {
        contract.update(1000);
        assertEquals(0, contract.getRemainingAmmount());
    }


} 
