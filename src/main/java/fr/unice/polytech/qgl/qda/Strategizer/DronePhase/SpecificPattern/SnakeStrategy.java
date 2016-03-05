package fr.unice.polytech.qgl.qda.Strategizer.DronePhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import fr.unice.polytech.qgl.qda.Strategizer.DronePhase.DroneStrategy;
import fr.unice.polytech.qgl.qda.Strategizer.IStrategy;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;


public class SnakeStrategy extends DroneStrategy {

    /*
    stratégie qui parcours l'île en faisant un quadrillage de scan, le nombre d'occurence de scan par ligne est déterminé
    par la variable FORWARD, le nombre de tile qu'on saute entre 2 lignes est déterminé par la variable STEP

    si un scan nous donne un biome en relation avec un de nos contrats, on l'enregistre dans une liste de tile pour la phase terrestre
     */
    private final static int STEP = 2;
    private final static int FORWARD = 3;
    private final static int TILES_BY_FLY = 3;
    private final static int TILES_BY_RESOURCE = 3;
    private final static int TILE_LIMIT = 14;
    private final static int TILE_LIMIT_ON_SIDE = 9;

    public final int LIMIT_E = (getIslandMap().getWidth()/2)- TILE_LIMIT_ON_SIDE;
    private final int LIMIT_W = -(getIslandMap().getWidth()/2)+ TILE_LIMIT_ON_SIDE;
    private final int LIMIT_N = (getIslandMap().getHeight()/2)- TILE_LIMIT;
    private final int LIMIT_S = -(getIslandMap().getHeight()/2)+ TILE_LIMIT;

    private boolean lastStep = false;
    private boolean fasterEnd = false;
    private boolean bottom;
    private boolean leftToRight;


    private int BUDGET_LIMIT;
    private int noGround = 0;

    public SnakeStrategy(JSONObject context) {
        super(context);
    }

    public SnakeStrategy(IStrategy strategy) {

        super(strategy);
        currentHeading = nextHeading;
        position = strategy.getPosition();
        this.tileList = strategy.getTileList();
        nextAction.clear();

        // on évite dépenser tous nos points sur cette phase

        if(this.getAssignement().getBudget()>9000 && this.getAssignement().getBudget()<=10000){
            BUDGET_LIMIT = 8500;
        }
        else if(this.getAssignement().getBudget()>6000 && this.getAssignement().getBudget()<=7000){
            BUDGET_LIMIT = 5500;
        }
        else{
            BUDGET_LIMIT = 5000;
        }
        init();
        scan();
    }



    public void setDirection(Direction set){this.currentHeading = set;}
    public Direction getDirection(){return this.currentHeading;}
    public void setLeftToRight(boolean set){this.leftToRight = set;}
    public boolean getLeftToRight(){return this.leftToRight;}
    public boolean getBottom(){return this.bottom;}
    public void setBottom(boolean set){this.bottom = set;}

    public void init(){
        /*
        on initialise le sens dans lequelle nous allong quadriller la carte
         */
        int x = getIslandMap().getPositionActuelle().getX();
        int y = getIslandMap().getPositionActuelle().getY();

        if(x>0){
            if(currentHeading == Direction.W){
                leftToRight = false;
            }
            else if(currentHeading == Direction.E){
                leftToRight = true;
            }
            else if(currentHeading == Direction.N || currentHeading == Direction.S){
                setHeading(Direction.W);
                leftToRight = false;
            }
        }
        else{
            if(currentHeading == Direction.W){
                leftToRight = false;
            }
            else  if(currentHeading == Direction.E){
                leftToRight = true;
            }
            else  if(currentHeading == Direction.N || currentHeading == Direction.S){
                setHeading(Direction.E);
                leftToRight = true;
            }
        }
        if(y>0)
            bottom = false;
        else
            bottom = true;


    }
    public void stepByRight(){

        turnRight();
        upAndDownSecurity();
        turnRight();
    }
    public void stepByLeft(){
        turnLeft();
        upAndDownSecurity();
        turnLeft();
    }
    public void nextFloor(){

        if((bottom && !leftToRight) || (!bottom && leftToRight))
            stepByRight();

        else
            stepByLeft();
        leftToRight = !leftToRight;

    }

    /*
    permet d'éviter de sortir de la carte en terme de nombre de fly maximum possible entre 2 scan
     */
    private void forwardSecurity(){
        int currentX = getIslandMap().getPositionActuelle().getX();
        int diff;
        if(currentHeading == Direction.E) {
            diff = LIMIT_E - currentX;
        }
        else{
            diff = Math.abs(LIMIT_W - currentX);
        }
        int i = FORWARD;
        if(diff<TILES_BY_FLY){
            nextFloor();
            return;
        }
        while(i>0 && (diff>0)){
            fly();
            diff = diff - TILES_BY_FLY;
            i--;
        }

    }
    /*
    permet d'éviter de sortir de la carte en terme de coordonnée critique
     */
    private void forwardLimit(){
        int currentX = getIslandMap().getPositionActuelle().getX();
        int diff;
        if(currentHeading == Direction.E) {
            diff = LIMIT_E - currentX;
        }
        else{
            diff = Math.abs(LIMIT_W - currentX);
        }


        while(diff>0){
            fly();
            diff = diff - TILES_BY_FLY;

        }
    }

    /*
    permet d'éviter de sortir de la carte sur l'axe des Y
     */
    private void upAndDownSecurity() {

        int currentY = getIslandMap().getPositionActuelle().getY();
        int diff;
        if(currentHeading == Direction.N){
            diff =  LIMIT_N-currentY;
        }
        else{
            diff = Math.abs(LIMIT_S-currentY);
        }

        int i = STEP;
        while(i>0 && (diff>0)){
            fly();
            diff = diff-TILES_BY_FLY;
            i--;
        }
    }

    private void goToEnd(int x){
        nextAction.clear();
        int tmp_x = x;
        bottom = !bottom;
        if(currentHeading == Direction.E){
            while(tmp_x<LIMIT_E){
                fly();
                tmp_x+=TILES_BY_FLY;
            }
        }
        else{
            while(tmp_x>LIMIT_W){
                fly();
                tmp_x-=TILES_BY_FLY;
            }

        }
        nextFloor();
        echo(currentHeading);
        lastStep = true;
    }

    /*
    méthode qui effectue la position du drone tout au long de la stratégie
     */
    private void snakeStrat(boolean ocean, boolean other, boolean lake){
        int x = getIslandMap().getPositionActuelle().getX();
        if (ocean == true){
            if(fasterEnd){
                goToEnd(x);
            }
            else {
                echo(currentHeading);
            }
        }

        else {

            forwardSecurity();
            scan();

        }

    }

    protected void parseResult(JSONObject acknowledgeResult){
        switch (lastAction.getString("action")) {

            case "echo":
                echoStrat(acknowledgeResult);
                break;
            case "scan":
                scanStrat(acknowledgeResult);
                break;
        }
    }



    // méthode qui détermine le nombre de fly limit avant de sortire de la carte ainsi que le nombre de fly afin d'atteindre la terre
    // c'est cette méthode qui arrête la stratégie pour passer à la suivante
    private void echoStrat(JSONObject result){
        JSONObject extras = result.getJSONObject("extras");
        int range = extras.getInt("range");
        boolean ground;
        ground = extras.getString("found").equals("GROUND");
        if(ground) {
            noGround = 0;
            for (int i = 0; i <= range; i++) {
                fly();
            }
            if(lastStep){
                end();
            }
            else {
                scan();
            }
        }
        else{
            int y = getIslandMap().getPositionActuelle().getY();
            noGround++;

            if(noGround>1){

                if(bottom && y>0) {
                    nextAction.clear();
                    end();
                }
                else if(!bottom && y<0){
                    nextAction.clear();
                    end();
                }

                else{
                    forwardLimit();
                    nextFloor();
                    echo(currentHeading);
                }
                noGround = 0;
            }
            else {



                if((position == Position.UPPER_LEFT || position == Position.UPPER_RIGHT)&& y<LIMIT_S){
                    nextAction.clear();
                    end();
                }
                else if((position == Position.BOTTOM_LEFT || position == Position.BOTTOM_RIGHT) && y>LIMIT_N){
                    nextAction.clear();
                    end();
                }

                else {
                    if(this.assignment.getBudget()<BUDGET_LIMIT){
                        nextAction.clear();
                        bottom = !bottom;
                        forwardLimit();
                        nextFloor();
                        echo(currentHeading);
                        lastStep = true;
                    }

                    else{
                        forwardLimit();
                        nextFloor();
                        echo(currentHeading);
                    }
                }
            }

        }

    }
    @Override
    protected void findCreeks(JSONArray creeks){
        if (creeks.length() > 0) {
            for (Object x : creeks) {
                creekIds.add((String) x);

            }
        }
    }

    //méthode qui enregistre les tiles intéressant dans une liste
    // si chaque contrat possède au moins 3 tiles lié à lui, on arrête la stratégie
    // on ne récupère que les tiles qui n'ont qu'un seul biome lié à eux
    private void scanStrat(JSONObject result) {

        JSONObject extras = result.getJSONObject("extras");
        JSONArray creeks = extras.getJSONArray("creeks");
        JSONArray biome = extras.getJSONArray("biomes");

        boolean multipleBiome = (biome.length()>1?true:false);
        boolean ocean = false;
        boolean ground = false;
        boolean lake = false;
        boolean enough = true;

        findCreeks(creeks);
        for (Object x : biome) {

            if (Biome.valueOf(((String) x)) == Biome.OCEAN)
                ocean = true;
            else if (Biome.valueOf(((String) x)) == Biome.LAKE)
                lake = true;
            else{
                ground = true;

                for (Ressource res : this.tileList.keySet()) {
                    if (res.getTypeRessource() != Ressource.TypeRessource.MANUFACTURED) {

                        for (Biome bio : res.getAssociatedBiomes()) {
                            if (!multipleBiome && bio == Biome.valueOf((String) x)) {
                                tileList.get(res).add(getIslandMap().getPositionActuelle());

                            }
                        }
                    }
                }

            }

        }
        for(Ressource res: tileList.keySet()){
            if (res.getTypeRessource() != Ressource.TypeRessource.MANUFACTURED) {
                if (tileList.get(res).size() < TILES_BY_RESOURCE) {
                    enough = false;
                }
            }
        }
        if(enough){
            fasterEnd = true;
        }
        snakeStrat(ocean,ground,lake);

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
