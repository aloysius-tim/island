package fr.unice.polytech.qgl.qda;

import fr.unice.polytech.qgl.qda.Strategizer.DronePhase.SpecificPattern.EdgeStrategy;
import fr.unice.polytech.qgl.qda.Strategizer.DronePhase.SpecificPattern.LocationStrategy;
import fr.unice.polytech.qgl.qda.Strategizer.DronePhase.SpecificPattern.SnakeStrategy;
import fr.unice.polytech.qgl.qda.Strategizer.GroundPhase.SpecificPattern.FindStrategy;
import fr.unice.polytech.qgl.qda.Strategizer.IStrategy;
import fr.unice.polytech.qgl.qda.Strategizer.Strategy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Maxime
 */
public class ExplorerOLDTest {
    Explorer_OLD explorerOLD =new Explorer_OLD();

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private IStrategy iStrategy;

    /**
     *
     * Method: initialize(String s)
     *
     */
    @Test
    public void testInitialize() throws Exception {
        explorerOLD.initialize("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}");
    }

    /**
     *
     * Method: takeDecision()
     *
     */
    @Test
    public void testTakeDecision() throws Exception {
        explorerOLD.initialize("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}");
        explorerOLD.takeDecision();

        IStrategy iStrategy= explorerOLD.setStrategy(new LocationStrategy(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}")));
        explorerOLD.takeDecision();
        iStrategy= explorerOLD.setStrategy(new SnakeStrategy(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}")));
        explorerOLD.takeDecision();
        iStrategy= explorerOLD.setStrategy(new EdgeStrategy(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}")));
        explorerOLD.takeDecision();
        iStrategy= explorerOLD.setStrategy(new FindStrategy(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}")));
        explorerOLD.takeDecision();
    }

    /**
     *
     * Method: acknowledgeResults(String s)
     *
     */
    @Test(expected = Exception.class)
    public void testAcknowledgeResults() {
        explorerOLD.acknowledgeResults("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
    }


    /**
     *
     * Method: setStrategy(IStrategy strategy)
     *
     */
    @Test
    public void testSetStrategy() throws Exception {
        iStrategy= explorerOLD.setStrategy(new FindStrategy(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}")));

        try {
           Method method = explorerOLD.getClass().getMethod("setStrategy", IStrategy.class);
           method.setAccessible(true);
           method.invoke(explorerOLD, iStrategy);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }
    }

    @Test
    public void noExceptionWeek46Initialize() throws Exception {
        JSONObject contextOfWeek46 = new JSONObject();
        contextOfWeek46.put("heading", "S");
        contextOfWeek46.put("men", 15);
        contextOfWeek46.put("budget", 7000);


        JSONObject woodOfWeek46 = new JSONObject();
        woodOfWeek46.put("amount", 1000);
        woodOfWeek46.put("resource", "WOOD");

        JSONObject flowerOfWeek46 = new JSONObject();
        flowerOfWeek46.put("amount", 10);
        flowerOfWeek46.put("resource", "FLOWER");

        JSONObject quartzOfWeek46 = new JSONObject();
        quartzOfWeek46.put("amount", 300);
        quartzOfWeek46.put("resource", "QUARTZ");


        JSONArray contractsOfWeek46 = new JSONArray();
        contractsOfWeek46.put(woodOfWeek46);
        contractsOfWeek46.put(flowerOfWeek46);
        contractsOfWeek46.put(quartzOfWeek46);

        contextOfWeek46.put("contracts", contractsOfWeek46);
        Strategy strategy = new LocationStrategy(contextOfWeek46);
    }

    @Test
    public void noExceptionWeek47Initialize() throws Exception {
        JSONObject contextOfWeek47 = new JSONObject();
        contextOfWeek47.put("heading", "S");
        contextOfWeek47.put("men", 15);
        contextOfWeek47.put("budget", 7000);


        JSONObject woodOfWeek47 = new JSONObject();
        woodOfWeek47.put("amount", 1000);
        woodOfWeek47.put("resource", "WOOD");

        JSONObject flowerOfWeek47 = new JSONObject();
        flowerOfWeek47.put("amount", 10);
        flowerOfWeek47.put("resource", "FLOWER");

        JSONObject quartzOfWeek47 = new JSONObject();
        quartzOfWeek47.put("amount", 300);
        quartzOfWeek47.put("resource", "QUARTZ");


        JSONArray contractsOfWeek47 = new JSONArray();
        contractsOfWeek47.put(woodOfWeek47);
        contractsOfWeek47.put(flowerOfWeek47);
        contractsOfWeek47.put(quartzOfWeek47);

        contextOfWeek47.put("contracts", contractsOfWeek47);
        Strategy strategy = new LocationStrategy(contextOfWeek47);
    }

    @Test
    public void noExceptionWeek48Initialize() throws Exception {
        JSONObject contextOfWeek48 = new JSONObject();
        contextOfWeek48.put("heading", "S");
        contextOfWeek48.put("men", 3);
        contextOfWeek48.put("budget", 10000);


        JSONObject furOfWeek48 = new JSONObject();
        furOfWeek48.put("amount", 1000);
        furOfWeek48.put("resource", "FUR");

        JSONObject woodOfWeek48 = new JSONObject();
        woodOfWeek48.put("amount", 2000);
        woodOfWeek48.put("resource", "WOOD");

        JSONObject glassOfWeek48 = new JSONObject();
        glassOfWeek48.put("amount", 30);
        glassOfWeek48.put("resource", "GLASS");


        JSONArray contractsOfWeek48 = new JSONArray();
        contractsOfWeek48.put(furOfWeek48);
        contractsOfWeek48.put(woodOfWeek48);
        contractsOfWeek48.put(glassOfWeek48);

        contextOfWeek48.put("contracts", contractsOfWeek48);
        Strategy strategy = new LocationStrategy(contextOfWeek48);
    }

    @Test
    public void noExceptionWeek49Initialize() throws Exception {
        JSONObject contextOfWeek49 = new JSONObject();
        contextOfWeek49.put("heading", "S");
        contextOfWeek49.put("men", 3);
        contextOfWeek49.put("budget", 10000);


        JSONObject glassOfWeek49 = new JSONObject();
        glassOfWeek49.put("amount", 300);
        glassOfWeek49.put("resource", "GLASS");

        JSONObject woodOfWeek49 = new JSONObject();
        woodOfWeek49.put("amount", 5000);
        woodOfWeek49.put("resource", "WOOD");

        JSONObject sugarCaneOfWeek49 = new JSONObject();
        sugarCaneOfWeek49.put("amount", 1000);
        sugarCaneOfWeek49.put("resource", "SUGAR_CANE");


        JSONArray contractsOfWeek49 = new JSONArray();
        contractsOfWeek49.put(glassOfWeek49);
        contractsOfWeek49.put(woodOfWeek49);
        contractsOfWeek49.put(sugarCaneOfWeek49);

        contextOfWeek49.put("contracts", contractsOfWeek49);
        Strategy strategy = new LocationStrategy(contextOfWeek49);
    }

    @Test
    public void noExceptionWeek50Initialize() throws Exception {
        JSONObject contextOfWeek50 = new JSONObject();
        contextOfWeek50.put("heading", "E");
        contextOfWeek50.put("men", 3);
        contextOfWeek50.put("budget", 10000);

        JSONObject woodOfWeek50 = new JSONObject();
        woodOfWeek50.put("amount", 1000);
        woodOfWeek50.put("resource", "WOOD");

        JSONObject glassOfWeek50 = new JSONObject();
        glassOfWeek50.put("amount", 300);
        glassOfWeek50.put("resource", "GLASS");

        JSONObject furOfWeek50 = new JSONObject();
        furOfWeek50.put("amount", 5000);
        furOfWeek50.put("resource", "FUR");

        JSONArray contractsOfWeek50 = new JSONArray();
        contractsOfWeek50.put(furOfWeek50);
        contractsOfWeek50.put(woodOfWeek50);
        contractsOfWeek50.put(glassOfWeek50);

        contextOfWeek50.put("contracts", contractsOfWeek50);
        Strategy strategy = new LocationStrategy(contextOfWeek50);
    }

    @Test
    public void noExceptionWeek51Initialize() throws Exception {
        JSONObject contextOfWeek51 = new JSONObject();
        contextOfWeek51.put("heading", "S");
        contextOfWeek51.put("men", 3);
        contextOfWeek51.put("budget", 10000);

        JSONObject furOfWeek51 = new JSONObject();
        furOfWeek51.put("amount", 3000);
        furOfWeek51.put("resource", "FUR");

        JSONObject glassOfWeek51 = new JSONObject();
        glassOfWeek51.put("amount", 100);
        glassOfWeek51.put("resource", "GLASS");

        JSONObject woodOfWeek51 = new JSONObject();
        woodOfWeek51.put("amount", 3000);
        woodOfWeek51.put("resource", "WOOD");

        JSONArray contractsOfWeek51 = new JSONArray();
        contractsOfWeek51.put(woodOfWeek51);
        contractsOfWeek51.put(furOfWeek51);
        contractsOfWeek51.put(glassOfWeek51);

        contextOfWeek51.put("contracts", contractsOfWeek51);
        Strategy strategy = new LocationStrategy(contextOfWeek51);
    }

    @Test
    public void noExceptionWeek52Initialize() throws Exception {
        JSONObject contextOfWeek52 = new JSONObject();
        contextOfWeek52.put("heading", "S");
        contextOfWeek52.put("men", 3);
        contextOfWeek52.put("budget", 10000);

        JSONObject furOfWeek52 = new JSONObject();
        furOfWeek52.put("amount", 1000);
        furOfWeek52.put("resource", "FUR");

        JSONObject woodOfWeek52 = new JSONObject();
        woodOfWeek52.put("amount", 4000);
        woodOfWeek52.put("resource", "WOOD");

        JSONObject glassOfWeek52 = new JSONObject();
        glassOfWeek52.put("amount", 100);
        glassOfWeek52.put("resource", "GLASS");

        JSONArray contractsOfWeek52 = new JSONArray();
        contractsOfWeek52.put(furOfWeek52);
        contractsOfWeek52.put(woodOfWeek52);
        contractsOfWeek52.put(glassOfWeek52);

        contextOfWeek52.put("contracts", contractsOfWeek52);
        Strategy strategy = new LocationStrategy(contextOfWeek52);
    }

    @Test
    public void noExceptionWeek53Initialize() throws Exception {
        JSONObject contextOfWeek53 = new JSONObject();
        contextOfWeek53.put("heading", "S");
        contextOfWeek53.put("men", 3);
        contextOfWeek53.put("budget", 10000);

        JSONObject woodOfWeek53 = new JSONObject();
        woodOfWeek53.put("amount", 5000);
        woodOfWeek53.put("resource", "WOOD");

        JSONObject glassOfWeek53 = new JSONObject();
        glassOfWeek53.put("amount", 100);
        glassOfWeek53.put("resource", "GLASS");

        JSONObject flowerOfWeek53 = new JSONObject();
        flowerOfWeek53.put("amount", 80);
        flowerOfWeek53.put("resource", "FLOWER");

        JSONArray contractsOfWeek53 = new JSONArray();
        contractsOfWeek53.put(flowerOfWeek53);
        contractsOfWeek53.put(woodOfWeek53);
        contractsOfWeek53.put(glassOfWeek53);

        contextOfWeek53.put("contracts", contractsOfWeek53);
        Strategy strategy = new LocationStrategy(contextOfWeek53);
    }

    @Test
    public void ExceptionWithContextMissingBudget() throws Exception {
        JSONObject wrongContextBudget = new JSONObject();
        wrongContextBudget.put("heading", "S");
        wrongContextBudget.put("men", 3);

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

        wrongContextBudget.put("contracts", contracts);

        thrown.expect(JSONException.class);
        thrown.expectMessage("JSONObject[\"budget\"] not found.");
        Strategy strategy = new LocationStrategy(wrongContextBudget);
    }

    @Test
    public void ExceptionWithContextMissingHeading() throws Exception {
        JSONObject wrongContextHeading = new JSONObject();
        wrongContextHeading.put("men", 3);
        wrongContextHeading.put("budget", 10000);

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

        wrongContextHeading.put("contracts", contracts);

        thrown.expect(JSONException.class);
        thrown.expectMessage("JSONObject[\"heading\"] not found.");
        Strategy strategy = new LocationStrategy(wrongContextHeading);
    }

    @Test
    public void ExceptionWithContextMissingMen() throws Exception {
        JSONObject wrongContextMen = new JSONObject();
        wrongContextMen.put("heading", "S");
        wrongContextMen.put("budget", 10000);

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

        wrongContextMen.put("contracts", contracts);

        thrown.expect(JSONException.class);
        thrown.expectMessage("JSONObject[\"men\"] not found.");
        Strategy strategy = new LocationStrategy(wrongContextMen);
    }

    @Test
    public void ExceptionWithContextMissingContracts() throws Exception {
        JSONObject wrongContextContracts = new JSONObject();
        wrongContextContracts.put("heading", "S");
        wrongContextContracts.put("men", 3);
        wrongContextContracts.put("budget", 10000);

        thrown.expect(JSONException.class);
        thrown.expectMessage("JSONObject[\"contracts\"] not found.");
        Strategy strategy = new LocationStrategy(wrongContextContracts);
    }
}