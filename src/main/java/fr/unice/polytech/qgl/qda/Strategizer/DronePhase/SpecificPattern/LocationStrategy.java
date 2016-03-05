package fr.unice.polytech.qgl.qda.Strategizer.DronePhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Strategizer.DronePhase.DroneStrategy;
import fr.unice.polytech.qgl.qda.Strategizer.IStrategy;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by justin on 26/11/15.
 */
public class LocationStrategy extends DroneStrategy {

    /*
        Cette stratégie est la première appellé, elle permet de se localiser sur la carte lors de l'arrivé du drone
    */
    private int nbEcho = 0;
    private int minRange;
    private boolean leftFromStart;
    private boolean left;
    private int correctX = 0;
    private int correctY = 0;

    public LocationStrategy(JSONObject context) {
        super(context);
        nextHeading = currentHeading;
        echo(currentHeading.getLeftDirection());
        echo(currentHeading.getRightDirection());
    }

    public LocationStrategy(IStrategy strategy) {
        super(strategy);
    }

    public boolean getLeftFromStart(){return this.leftFromStart;}
    public boolean getLeft(){return this.left;}
    public void setLeftFromStart(boolean set){this.leftFromStart = set;}
    public void setPosition(Position set){this.position = set; }
    public void setDirection(Direction set){this.currentHeading = set;}
    public int getCorrectX(){return this.correctX;}
    public int getCorrectY(){return this.correctY;}
    public int getNbEcho(){return this.nbEcho;}
    public void setNbEcho(int set){this.nbEcho = set;}

    public int getMinRange(){return this.minRange;}

    /*
    le drône cherche à se placer dans un coin de la carte afin de pouvoir savoir la taille de celle-ci, et se donner un point de repère
    qui est la Position
     */
    public Position findPosition(){
        switch(assignment.getHeading()){
            case N: return (leftFromStart? Position.BOTTOM_LEFT:Position.BOTTOM_RIGHT);
            case E: return (leftFromStart? Position.UPPER_LEFT:Position.BOTTOM_LEFT);
            case S: return (leftFromStart? Position.UPPER_RIGHT:Position.UPPER_LEFT);
            case W: return (leftFromStart? Position.BOTTOM_RIGHT:Position.UPPER_RIGHT);
            default:  return null;
        }
    }

    /*
    méthode qui corrige les problèmes de coordonnées avec la carte de l'île est notre modèle à nous de cette île
     */
    public void getReady(){

        switch(position){
            case BOTTOM_RIGHT:
                if(currentHeading == Direction.E) {
                    turnLeft();
                    correctY+=2;
                    correctX+=2;
                    left = false;
                }
                else if(currentHeading == Direction.N){
                    left = false;
                }
                else if(currentHeading == Direction.W){
                    left = true;
                }
                else {
                    turnRight();
                    correctY+=2;
                    correctX+=2;
                    left = true;
                }

                echo(Direction.N);
                echo(Direction.W);

                break;
            case BOTTOM_LEFT:
                if(currentHeading == Direction.S) {
                    turnLeft();
                    correctY+=2;
                    correctX+=2;
                    left = false;
                }
                else if(currentHeading == Direction.N){
                    left = true;
                }
                else if(currentHeading == Direction.E){
                    left = false;
                }
                else {
                    turnRight();
                    correctY+=2;
                    correctX+=2;
                    left = true;
                }
                echo(Direction.N);
                echo(Direction.E);

                break;


            case UPPER_LEFT:
                if(currentHeading == Direction.W) {
                    turnLeft();
                    correctY+=2;
                    correctX+=2;
                    left = false;
                }
                else if(currentHeading == Direction.S){
                    left = false;
                }
                else if(currentHeading == Direction.E){
                    left = true;
                }
                else{
                    turnRight();
                    correctY+=2;
                    correctX+=2;
                    left = true;
                }
                echo(Direction.S);
                echo(Direction.E);

                break;
            case UPPER_RIGHT:
                if(currentHeading == Direction.E) {
                    turnRight();
                    correctY+=2;
                    correctX+=2;
                    left = true;
                }
                else if(currentHeading == Direction.S){
                    left = true;
                }
                else if(currentHeading == Direction.W){
                    left = false;
                }
                else{
                    turnLeft();
                    correctY+=2;
                    correctX+=2;
                    left = false;
                }
                echo(Direction.S);
                echo(Direction.W);

                break;
        }
    }
    public void correct(Direction dir,int range){
        switch(dir){
            case E:
                correctY += range;
                break;
            case W:
                correctY += range;
                break;
            case N:
                correctX += range;
                break;
            case S:
                correctX += range;
                break;
        }
    }

    // détermine la position du drone, le place dans un coin

    public void locate(int range){
        if(nbEcho == 1){
            minRange = range;
        }
        else if(nbEcho == 2){
            if(minRange>range) {
                minRange = range;
                setLeftFromStart(false);
            }
            else{
                setLeftFromStart(true);
            }
            setPosition(findPosition());
            if(minRange > 3){
                if(leftFromStart)
                    turnLeft();
                else
                    turnRight();

                for(int i=0;i<minRange-4;i++){
                    fly();
                }
            }
            else{
                correct(currentHeading, minRange);
            }
            getReady();
        }
    }

    // si le drone n'est toujours pas au dessus de la terre il continue d'avancer en scannant, sinon la strategie s'arrête pour démarrer la suivante
    public void findEarth(JSONArray biome){
        boolean earth = false;
        for(Object x : biome) {
            if (!(Biome.valueOf(((String) x)) == Biome.OCEAN)) {
                earth = true;
            }
        }
        if(earth){
            end();
        }
        else{
            fly();
            scan();

        }

    }




    private void echoStrat(JSONObject result){

        nbEcho++;
        JSONObject extras = result.getJSONObject("extras");
        int range = extras.getInt("range");
        boolean ground;
        ground = extras.getString("found").equals("GROUND");
        ////////////////////////////////// PHASE DE REPERAGE, TAILLE DE L'ILE //////////////////////////////////////////
        if(nbEcho <3){
            locate(range);
        }
        else if(nbEcho == 3){
            // le drone renvoie un nombre de tile de 3*3 donc on mutliplie la valeur par 3 pour récupérer la taille exacte
            this.getIslandMap().setHeight((range+correctY)*3);
        }

        else{
            if(nbEcho == 4){
                this.getIslandMap().setWidth((range+correctX)*3);
                actualizePosition();
            }
         ///////////////////////////////////////////////////// PHASE DE RECHERCHE DE LA TERRE //////////////////////////
            if(ground){
                // si on trouve la terre, on avance jusque celle-ci
                nextAction.clear();
                if(echoDir != currentHeading) {
                    setHeading(echoDir);
                    range --;
                }
                for(int i = 0;i<range;i++){
                    fly();
                }
                scan();
            }
            else{
                // si on ne trouve pas la terre, on avance en diagonale vers le milieu de la carte
                if(left) turnRight();
                else     turnLeft();

                left = !left;
                echo(echoDir);
            }
        }

    }

    // met à jour la taille de la carte sur notre modèle de l'ile
    private void actualizePosition(){
        switch(position){
            case UPPER_LEFT:
                this.getIslandMap().actualizePosition(-this.getIslandMap().getWidth()/2+correctX,this.getIslandMap().getHeight()/2-correctY);
                break;
            case UPPER_RIGHT:
                this.getIslandMap().actualizePosition(this.getIslandMap().getWidth()/2-correctX,this.getIslandMap().getHeight()/2-correctY);
                break;
            case BOTTOM_LEFT:
                this.getIslandMap().actualizePosition(-this.getIslandMap().getWidth()/2+correctX,-this.getIslandMap().getHeight()/2+correctY);
                break;
            case BOTTOM_RIGHT:
                this.getIslandMap().actualizePosition(this.getIslandMap().getWidth()/2-correctX,-this.getIslandMap().getHeight()/2+correctY);
                break;
        }
    }
    private void scanStrat(JSONObject result){

        JSONObject extras=result.getJSONObject("extras");
        JSONArray creeks = extras.getJSONArray("creeks");
        JSONArray biome = extras.getJSONArray("biomes");
        findCreeks(creeks);
        findEarth(biome);

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
