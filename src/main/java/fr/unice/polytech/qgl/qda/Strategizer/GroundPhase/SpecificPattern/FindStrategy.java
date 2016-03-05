package fr.unice.polytech.qgl.qda.Strategizer.GroundPhase.SpecificPattern;

/**
 * Created by justin on 08/12/15.
 */

import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Island.Tile;
import fr.unice.polytech.qgl.qda.Strategizer.IStrategy;
import fr.unice.polytech.qgl.qda.Strategizer.Strategy;
import org.json.JSONArray;
import org.json.JSONObject;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;

public class FindStrategy extends Strategy implements IStrategy {

    /*
    stratégie qui s'occupe de toute la phase terrestre avec HuntedBiome

     */

    private HuntedBiome huntedBiome;

    protected LinkedList<Ressource> findedRessource;
    private ArrayList<Tile> tileShown = new ArrayList<>();
    private Tile creek;
    private boolean avoid = false;

    private final int RANGE = 0;

    ///////////////////// ORDRE DE PRIORITE DES CONTRATS /////////////////////////////
    private Ressource[] interestRessource = {Ressource.FLOWER,Ressource.SUGAR_CANE,Ressource.WOOD,Ressource.FUR,Ressource.FRUITS,Ressource.FISH,Ressource.ORE,Ressource.QUARTZ};
    /////////////////////////////////////////////////////////////////////////////////

    public FindStrategy(JSONObject context) {
        super(context);
    }

    public FindStrategy(IStrategy strategy) {
        super(strategy);
        currentHeading = nextHeading;
        findedRessource = new LinkedList<>();
        position = strategy.getPosition();
        this.tileList = strategy.getTileList();
        land(creekIds.get(0), 1);
    }
    public HuntedBiome getHuntedBiome(){return this.huntedBiome;}
    public void setCreek(Tile set){this.creek = set;}
    public void setAvoid(boolean set){this.avoid = set;}
    public void initFindedRessource(){this.findedRessource = new LinkedList<>();}
    public void setLastAction(JSONObject set){this.lastAction = set;}

    public int findDistance(Tile a,Tile b){
        // retourne la distance entre 2 tiles
        return (int) Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
    }


    public void findNearestTile(Tile fromThis){
        // cherche le tile intéressant le plus proche entre la creek sur lequel on est arrivé et les tiles enregistrés par la phase aérienne
        Tile min = null;
        int prevDistance = Integer.MAX_VALUE;
        int i = 0;
        while(i<interestRessource.length){ // parcours la liste des priorités pour les ressources du contrats

            for (Ressource res : tileList.keySet()) {
                if(res == interestRessource[i]) {

                    for (Tile tile : tileList.get(res)) {
                        int distance = findDistance(fromThis, tile);
                        if (distance < prevDistance) {
                            min = tile;
                            prevDistance = distance;
                        }
                    }
                    if(min != null){
                        for(Tile t : tileList.get(res)){
                            if(t.getX() == min.getX() && t.getY() == min.getY()){
                                for(Biome biome:t.getRelatedBiomes().keySet()){
                                    if(res.getAssociatedBiomes().contains(biome)){
                                        huntedBiome = new HuntedBiome(min,res,biome); // on a une nouvelle cible
                                        break;
                                    }
                                }
                                tileList.get(res).remove(t);
                                return;
                            }
                        }
                    }
                }

            }
            i++;
        }
    }

    // détermine le nombre de move_to sur l'axe des X à faire, pour rejoindre le huntedBiome
    public void xPath(Tile currentTile,Tile futurTile){
        if (currentTile.getX()< futurTile.getX()) {
            for (int i = currentTile.getX(); i < futurTile.getX(); i++) {
                move_to(Direction.E);
            }
        } else if (currentTile.getX() > futurTile.getX()) {
            for (int i = currentTile.getX(); i > futurTile.getX(); i--) {
                move_to(Direction.W);
            }
        }
    }
    // détermine le nombre de move_to sur l'axe des Y à faire, pour rejoindre le huntedBiome
    public void yPath(Tile currentTile, Tile futurTile){
        if (currentTile.getY()< futurTile.getY()) {
            for (int i = currentTile.getY(); i < futurTile.getY(); i++) {
                move_to(Direction.N);
            }
        } else if (currentTile.getY() > futurTile.getY()) {
            for (int i = currentTile.getY(); i > futurTile.getY(); i--) {
                move_to(Direction.S);
            }
        }
    }


    //détermine si il vaut mieux se déplacer sur l'axe des X en premier ou Y en premier pour rejoindre le HuntedBiome (afin d'éviter la sortie de l'ile
    // et de devoir nager sur une partie du trajet)
    public void findPath(Tile currentTile,Tile futurTile){
        boolean Xfirst;
        if(currentTile.getX() > futurTile.getX()){
            if(currentTile.getX()>0){ Xfirst = true;}
            else { Xfirst = false;}
        }
        else{
            if(currentTile.getX()<0){ Xfirst = true;}
            else { Xfirst = false;}
        }

        if(Xfirst){
            xPath(currentTile,futurTile);
            yPath(currentTile, futurTile);
        }
        else{
            yPath(currentTile,futurTile);
            xPath(currentTile, futurTile);
        }

    }

// on recherche un nouveau Tile (biome) à explorer  et on s'y rend
    public void startMove(){
        nextAction.clear();

        huntedBiome = null;
        avoid = false;


        findNearestTile(creek);

        if (huntedBiome == null) {
            stop();
            return;
        }

        findPath(getIslandMap().getPositionActuelle(), huntedBiome.getTile());
        explore();
    }

    public void moveStrat(){

        if(huntedBiome.getOnBiome()) {
            actualiseList(getIslandMap().getPositionActuelle()); //on actualise nos coordonées sur notre carte
        }

    }

    //permet de supprimer les tiles (dans la liste de la phase aérienne) adjacents à celui qu'on chasse pour éviter de retourner plusieurs fois sur le même
    // biome déjà visité, on ne l'utilise pas (RANGE = 0) car on passe parfois à coté d'un tile qui aurait pu nous intéresser
    public void actualiseList(Tile currentTile){
        for(Ressource res : tileList.keySet()){
            if(currentTile.getAssociatedRessources().contains(res)){
                for(Tile tile: tileList.get(res)){
                    int distance = findDistance(currentTile,tile);
                    if(distance <= RANGE ){
                        tileList.get(res).remove(tile);
                        return;
                    }
                }
            }


        }
    }

    // détermine si la ressource qu'on a scout est intéressante
    public boolean[] gotInterestingResource(JSONArray resources) {
        boolean interest = false;
        boolean water = false;
        boolean[] response = {water,interest};

        for (Object resourceObject : resources) {
            Ressource resource = Ressource.valueOf((String) resourceObject);
            for (Biome ResearchedBiome : resource.getAssociatedBiomes()) {

                if (ResearchedBiome == huntedBiome.getBiome() ||resource == huntedBiome.getResource() ) {
                    response[1] = true;

                }
            }
        }


        return response;
    }

    //détermine si le biome qu'on a glimpse est intéressant
    public boolean[] gotInterestingBiome(JSONArray report,int range){
        boolean interest = false;
        boolean other = false;
        boolean[] response = {other,interest};
        int currentRange = 0;
        for (Object biomeListObject : report) {

            JSONArray biomeList = (JSONArray) biomeListObject;
            for (Object biomeObject : biomeList) {
                Biome biome;
                double pourcent;
                if (biomeObject instanceof String) {
                    biome = Biome.valueOf((String) biomeObject);
                } else {
                    JSONArray biomeArray = (JSONArray) biomeObject;
                    biome = Biome.valueOf(biomeArray.getString(0));
                    pourcent = biomeArray.getDouble(1);
                }

                if(range == 2 && currentRange >0) {
                    if (biome == huntedBiome.getBiome()) {
                        response[1] = true;
                    } else {
                        response[0] = true;
                    }
                }
                else if(range==1){
                    if(biome == huntedBiome.getBiome()) {
                        response[1] = true;
                    }
                    else{
                        response[0] = true;
                    }
                }

            }
            currentRange++;

        }
        return response;
    }

    public void scoutStrat(JSONObject result){

        nextAction.clear();
        JSONObject extras = result.getJSONObject("extras");
        JSONArray resources = extras.getJSONArray("resources");
        int altitude = extras.getInt("altitude");
        boolean[] response = gotInterestingResource(resources);
        boolean other = response[0];
        boolean interest = response[1];


        if(interest){ // le résultat du scout nous intéresse

            nextAction = huntedBiome.run(huntedBiome.getDirection());
        }
        else{ // le résultat n'est pas intéressant
            if(avoid){ // si on arrive sur un biome est qu'on a pas encore explore, avoid est sur false, à partir du moment où l'on a déjà trouvé une ressource, il est sur true
                if(!huntedBiome.getReverse()){ //on parcours le biome dans l'autre sens
                    huntedBiome.setReverse(true);
                    findPath(getIslandMap().getPositionActuelle(), huntedBiome.getTile());
                    scout(Direction.W);
                }
                else{ // on a parcours le biome dans un sens puis dans l'autre, donc on a terminé sur ce biome là, on passe au suivant
                    startMove();
                }
            }
            else { // on a pas encore trouvé de ressource sur ce biome
                nextAction = huntedBiome.change(huntedBiome.getDirection());
                if (nextAction.size() == 0) {

                    if (huntedBiome.getEnd()) {
                        startMove();
                    } else if (huntedBiome.getReverse()) {
                        findPath(getIslandMap().getPositionActuelle(), huntedBiome.getTile());
                        scout(Direction.W);
                    }
                }
            }

        }
    }
    // même principe que scoutStrat
    public void glimpseStrat(JSONObject result) {
        nextAction.clear();
        JSONObject extras = result.getJSONObject("extras");
        JSONArray report = extras.getJSONArray("report");
        int range = extras.getInt("asked_range");
        int currentRange = 0;
        boolean[] response;
        boolean interest;
        boolean other;

        response = gotInterestingBiome(report,range);
        other = response[0];
        interest = response[1];

        if(interest){
            nextAction = huntedBiome.run(huntedBiome.getDirection());
        }
        else{
            nextAction = huntedBiome.change(huntedBiome.getDirection());
            if(nextAction.size() == 0 ){

                if(huntedBiome.getEnd()){
                    startMove();
                }
                else if (huntedBiome.getReverse()){
                    findPath(getIslandMap().getPositionActuelle(), huntedBiome.getTile());
                    scout(Direction.W);
                }
            }

        }
    }

    public ArrayList<Ressource> gotInterestingExplore(JSONArray resources){
        ArrayList<Ressource> interest = new ArrayList<>();
        for(Object resourceObject : resources){
            JSONObject resourceJSON = (JSONObject) resourceObject;
            Ressource resource = Ressource.valueOf(resourceJSON.getString("resource"));
            String amount = resourceJSON.getString("amount");
            String cond = resourceJSON.getString("cond");
            for (Ressource key : this.tileList.keySet()) {
                if (key == resource ) {
                    if((!cond.equals("HARSH"))){
                        interest.add(key);
                    }
                }
            }
        }
        return interest;
    }

    public void exploreStrat(JSONObject result){
        nextAction.clear();
        if(huntedBiome.getOnBiome() && tileShown.contains(getIslandMap().getPositionActuelle())){
            startMove();
            return;
        }
        if(huntedBiome.getOnBiome()) {
            tileShown.add(getIslandMap().getPositionActuelle());
        }
        JSONObject extras = result.getJSONObject("extras");
        JSONArray resources = extras.getJSONArray("resources");
        JSONArray creeks = extras.getJSONArray("pois");

        ArrayList<Ressource> alreadyExploit;
        alreadyExploit = gotInterestingExplore(resources);
        if(!alreadyExploit.isEmpty()){
            for(Ressource resource : alreadyExploit){
                findedRessource.add(resource);
                exploit(resource);
            }
        }
        else {
            if(!avoid) {
                avoid = true;
                nextAction = huntedBiome.change(huntedBiome.getDirection());
            }
            else {
                scout(huntedBiome.getDirection());
            }
        }

    }

    public void exploitStrat(JSONObject result){
        boolean primary = false;

        JSONObject extras = result.getJSONObject("extras");
        int amount = extras.getInt("amount");
        updateList(amount);

        for(Ressource res : tileList.keySet()){
            if(res.getTypeRessource() == Ressource.TypeRessource.PRIMARY){
                primary = true;
                break;
            }
        }
        if(!primary){
            nextAction.clear();
            stop();
        }

        else{
            if(huntedBiome == null){
                nextAction.clear();
                stop();
                return;
            }
            avoid = false;
            scout(huntedBiome.getDirection());

        }

    }
    // on actualise les ressources manquante pour effectuer les contrats
    // si une ressource d'un contrat passe à 0 ou moins, on le supprime des contrats restant

    public void updateList(int amount){
        boolean keep = false;
        if(this.assignment.containsKey(findedRessource.getFirst())) {
            this.assignment.get(findedRessource.getFirst()).update(amount);
            if (this.assignment.get(findedRessource.getFirst()).getRemainingAmmount() <= 0) {
                this.assignment.remove(findedRessource.getFirst());
                this.tileList.remove(findedRessource.getFirst());
                for(Ressource res : tileList.keySet()){
                    for( Biome biome : res.getAssociatedBiomes()){
                        if(biome == huntedBiome.getBiome()){
                            keep = true;
                        }
                    }
                }

                if(!keep) {
                    nextAction.clear();
                    startMove();
                }

            }
            findedRessource.removeFirst();
        }
    }


    protected void parseResult(JSONObject acknowledgeResult){
        switch (lastAction.getString("action")) {

            case "land":
                creek = getIslandMap().getPositionActuelle();
                startMove();
                break;
            case "move_to":
                moveStrat();
                break;
            case "scout":
                scoutStrat(acknowledgeResult);
                break;
            case "glimpse":
                glimpseStrat(acknowledgeResult);
                break;
            case "explore":
                exploreStrat(acknowledgeResult);
                break;
            case "exploit":
                exploitStrat(acknowledgeResult);
                break;
        }
    }

    @Override
    public void interpretAcknowledgeResult(JSONObject acknowledgeResult) throws IllegalAccessException, InvocationTargetException, InstantiationException {

        super.interpretAcknowledgeResult(acknowledgeResult);
        update(acknowledgeResult.getInt("cost"));
        parseResult(acknowledgeResult);
    }

    @Override
    public JSONObject getNextMove() {
        return super.getNextMove();
    }


}
