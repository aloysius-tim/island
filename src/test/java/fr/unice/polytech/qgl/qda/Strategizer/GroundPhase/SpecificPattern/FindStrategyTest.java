package fr.unice.polytech.qgl.qda.Strategizer.GroundPhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Island.Tile;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * Created by justin on 15/01/16.
 */

public class FindStrategyTest {

    private FindStrategy test;
    private JSONObject contrat;
    private Tile a;
    private Tile b;
    private Tile c;

    @Before
    public void setUp(){
        contrat = new JSONObject();
        contrat.put("men", 12);
        contrat.put("budget", 10000);

        JSONObject wood = new JSONObject();
        wood.put("amount", 600);
        wood.put("resource", "WOOD");

        JSONObject fur = new JSONObject();
        fur.put("amount", 200);
        fur.put("resource", "FUR");

        JSONArray ressources = new JSONArray();
        ressources.put(wood);
        ressources.put(fur);

        contrat.put("contracts", ressources);
        contrat.put("heading", "W");


        a = new Tile(10,1);
        a.addAssociatedRessource(Ressource.WOOD);
        a.addRelatedBiomes(Biome.TEMPERATE_RAIN_FOREST);

        b = new Tile(-3,150);
        b.addAssociatedRessource(Ressource.WOOD);
        b.addRelatedBiomes(Biome.TEMPERATE_RAIN_FOREST);

        c = new Tile(50,50);
        c.addAssociatedRessource(Ressource.FUR);
        c.addRelatedBiomes(Biome.GRASSLAND);

        test = new FindStrategy(contrat);
        test.creekIds.add("9274a8c9-afcc-480c-8cd4-9766630e3a49");

    }

    public ArrayList<String> actionToString(LinkedList<JSONObject> list){
        ArrayList<String> action = new ArrayList<>();
        while(list.size()>0){
            JSONObject json = list.pop();
            if(json.get("action").equals("heading")){
                action.add(((JSONObject) json.get("parameters")).getString("direction"));
            }
            else if(json.get("action").equals("fly")){
                action.add(json.getString("action"));
            }
            else if(json.get("action").equals("move_to")){
                action.add(((JSONObject) json.get("parameters")).getString("direction"));
            }

        }
        return action;
    }

    @Test
    public void testFindStrategyWithContext(){

        FindStrategy test2 = new FindStrategy(test);
    }

    @Test
    public void testFindDistance(){

        assertEquals(1, test.findDistance(new Tile(1, 0), new Tile(0, 0)));
        assertEquals(0, test.findDistance(new Tile(0, 0), new Tile(0, 0)));

    }

    @Test
    public void testFindNearestTile(){


        test.getTileList().get(Ressource.WOOD).add(0,a);
        test.getTileList().get(Ressource.WOOD).add(1,b);
        test.findNearestTile(new Tile(0,0));


        assertEquals(a, test.getHuntedBiome().getTile());
    }

    @Test
    public void testXPath(){

        test.xPath(a,b);
        assertEquals(13,test.getBufferActions().size());
        test.xPath(b,a);
        assertEquals(26,test.getBufferActions().size());

    }

    @Test
    public void testYPath(){
        test.yPath(a, b);
        assertEquals(149,test.getBufferActions().size());
        test.yPath(b, a);
        assertEquals(298,test.getBufferActions().size());
    }

    @Test
    public void testFindPath(){
        test.findPath(a,b);
        ArrayList<String> action = actionToString(test.getBufferActions());
        assertEquals("W",action.remove(0));
        assertEquals("N",action.remove(action.size()-1));
    }




    @Test
    public void testStartMove(){
        test.startMove();
        test.getTileList().get(Ressource.WOOD).add(0,a);
        test.setCreek(a);
        test.startMove();
    }

    @Test
    public void testMoveStrat(){

        test.getTileList().get(Ressource.WOOD).add(0,a);
        test.getTileList().get(Ressource.WOOD).add(1,b);
        test.findNearestTile(a);
        test.getHuntedBiome().setOnBiome(true);
        test.moveStrat();
        test.getHuntedBiome().setOnBiome(false);
        test.moveStrat();
    }

    @Test
    public void testActualiseList(){

        test.getTileList().get(Ressource.WOOD).add(0,a);
        test.getTileList().get(Ressource.WOOD).add(1,b);


        assertEquals(2, test.getTileList().get(Ressource.WOOD).size());
        test.actualiseList(a);
        assertEquals(1,test.getTileList().get(Ressource.WOOD).size());
        test.actualiseList(b);
        assertEquals(0,test.getTileList().get(Ressource.WOOD).size());
    }



    @Test
    public void testGotInterestingRessource(){
        JSONArray resources = new JSONArray();
        resources.put("WOOD");

        test.getTileList().get(Ressource.WOOD).add(0,a);
        test.getTileList().get(Ressource.FUR).add(c);

        test.findNearestTile(a);
        test.gotInterestingResource(resources);
        test.findNearestTile(c);
        test.gotInterestingResource(resources);


    }


    @Test
    public void testGotInterestingBiome(){

        JSONArray report = new JSONArray();
        JSONArray biome = new JSONArray();
        String value = "TEMPERATE_RAIN_FOREST";


        biome.put(value);
        report.put(biome);

        test.getTileList().get(Ressource.WOOD).add(0,a);
        test.findNearestTile(a);
        test.gotInterestingBiome(report,2);

        test.gotInterestingBiome(report,1);


        value = "MANGROVE";
        biome = new JSONArray();
        report = new JSONArray();
        biome.put(value);
        report.put(biome);

        test.gotInterestingBiome(report,2);

        test.gotInterestingBiome(report,1);


       JSONArray v = new JSONArray();
        value = "TEMPERATE_RAIN_FOREST";
        v.put(value);
        v.put(34);
        biome = new JSONArray();
        report = new JSONArray();
        biome.put(v);
        report.put(biome);

        test.gotInterestingBiome(report,2);

    }

    @Test
    public void testScoutStrat(){

        test.getTileList().get(Ressource.WOOD).add(a);
        test.findNearestTile(a);

        JSONObject result = new JSONObject();
        result.put("cost",5);
        JSONObject extras = new JSONObject();
        extras.put("altitude",1);
        JSONArray resources = new JSONArray();
        resources.put("FLOWER");
        //resources.put("WOOD");
        extras.put("resources",resources);
        result.put("extras",extras);
        result.put("status", "OK");

        test.setAvoid(true);
        test.getHuntedBiome().setReverse(false);
        test.scoutStrat(result);

        test.getTileList().get(Ressource.WOOD).add(a);
        test.findNearestTile(a);
        test.getHuntedBiome().setReverse(true);
        test.scoutStrat(result);

        test.setAvoid(false);
        test.getTileList().get(Ressource.WOOD).add(a);
        test.findNearestTile(a);
        test.getHuntedBiome().setReverse(true);
        test.scoutStrat(result);

        result = new JSONObject();
        result.put("cost",5);
        extras = new JSONObject();
        extras.put("altitude",1);
        resources = new JSONArray();
        //resources.put("FUR");
        resources.put("WOOD");
        extras.put("resources",resources);
        result.put("extras",extras);
        result.put("status", "OK");

        test.scoutStrat(result);

    }

    @Test
    public void testGlimpseStrat(){

        test.getTileList().get(Ressource.WOOD).add(a);
        test.findNearestTile(a);

        JSONObject result = new JSONObject();
        result.put("cost",5);
        JSONObject extras = new JSONObject();
        extras.put("asked_range",2);
        JSONArray report = new JSONArray();
        report.put(new JSONArray().put("MANGROVE"));
        report.put(new JSONArray().put("TEMPERATE_RAIN_FOREST"));
        extras.put("report",report);
        result.put("extras",extras);
        result.put("status", "OK");

        test.glimpseStrat(result);


        test.getTileList().get(Ressource.WOOD).add(a);
        test.findNearestTile(a);

        result = new JSONObject();
        result.put("cost",5);
        extras = new JSONObject();
        extras.put("asked_range",2);
        report = new JSONArray();
        report.put(new JSONArray().put("GLACIER"));
        extras.put("report",report);
        result.put("extras",extras);
        result.put("status", "OK");

        test.glimpseStrat(result);

    }

    @Test
    public void testGotInterestingExplore(){



        JSONArray resource = new JSONArray();
        JSONObject res = new JSONObject();
        res.put("amount","HIGH");
        res.put("resource","FUR");
        res.put("cond","FAIR");
        resource.put(res);
        test.gotInterestingExplore(resource);

    }

    @Test
    public void testExploreStrat(){
        test.getTileList().get(Ressource.FUR).add(c);
        test.findNearestTile(c);

        JSONObject result = new JSONObject();
        JSONObject extras = new JSONObject();
        result.put("cost",5);

        JSONArray resource = new JSONArray();
        JSONObject res = new JSONObject();
        res.put("amount","HIGH");
        res.put("resource","FUR");
        res.put("cond","FAIR");
        resource.put(res);
        extras.put("resources",resource);
        extras.put("pois",new JSONArray());
        result.put("extras",extras);
        result.put("status","OK");
        test.initFindedRessource();
        test.exploreStrat(result);

        test.getTileList().get(Ressource.WOOD).add(a);
        test.findNearestTile(a);

        result = new JSONObject();
        extras = new JSONObject();
        result.put("cost",5);

        resource = new JSONArray();
        res = new JSONObject();
        res.put("amount","HIGH");
        res.put("resource","QUARTZ");
        res.put("cond","FAIR");
        resource.put(res);
        extras.put("resources",resource);
        extras.put("pois",new JSONArray());
        result.put("extras",extras);
        result.put("status","OK");
        test.initFindedRessource();
        test.exploreStrat(result);


    }

    @Test
    public void testExploitStrat(){
        test.getTileList().get(Ressource.WOOD).add(a);
        test.findNearestTile(a);

        JSONObject result = new JSONObject();
        JSONObject extras = new JSONObject();
        result.put("cost",5);

        JSONArray resource = new JSONArray();
        JSONObject res = new JSONObject();
        res.put("amount","HIGH");
        res.put("resource","WOOD");
        res.put("cond","FAIR");
        resource.put(res);
        extras.put("resources",resource);
        extras.put("pois",new JSONArray());
        result.put("extras",extras);
        result.put("status","OK");
        test.initFindedRessource();
        test.exploreStrat(result);




        test.getTileList().get(Ressource.WOOD).add(a);
        test.findNearestTile(a);

        result = new JSONObject();
         extras = new JSONObject();

        result.put("cost",3);
        extras.put("amount",9);
        result.put("extras",extras);
        result.put("status","OK");

        test.exploitStrat(result);


    }

    @Test
    public void testUpdateList(){

        test.getTileList().get(Ressource.WOOD).add(a);
        test.findNearestTile(a);

        JSONObject result = new JSONObject();
        JSONObject extras = new JSONObject();
        result.put("cost",5);

        JSONArray resource = new JSONArray();
        JSONObject res = new JSONObject();
        res.put("amount","HIGH");
        res.put("resource","WOOD");
        res.put("cond","FAIR");
        resource.put(res);
        extras.put("resources",resource);
        extras.put("pois",new JSONArray());
        result.put("extras",extras);
        result.put("status","OK");
        test.initFindedRessource();
        test.exploreStrat(result);
        test.updateList(5000);

    }

}
