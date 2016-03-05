package fr.unice.polytech.qgl.qda.Strategizer.DronePhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Strategizer.DronePhase.DroneStrategy;
import fr.unice.polytech.qgl.qda.Strategizer.IStrategy;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

/**
 * IslandProject created on 23/11/2015 by Keynes Timothy - Aloysius_tim
 */
public class EdgeStrategy extends DroneStrategy {
    private boolean up = false;
    private boolean down = false;
    private boolean init = true;
    private boolean onLeft;
    private boolean clock;

    /*
    * Cette stratégie permet de faire le contoure de l'île jusqu'à trouver une creek
    *
    */


    // Si on instancie la stratégie à partir du contrat
    public EdgeStrategy(JSONObject context) {
        super(context);
    }

    // si on instancie la stratégie à partir d'une autre stratégie

    public EdgeStrategy(IStrategy strategy) {
        super(strategy);
        // si on a déjà trouvé une creek lors d'une autre stratégie, on passe à la stratégie suivante, celle-ci ne sert pas
        if(creekIds.size()>0){
            position = strategy.getPosition();
            this.getIslandMap().clear();
            fly();
            end();
        }
        else {
            nextAction = strategy.getBufferActions();
            currentHeading = nextHeading;
            position = strategy.getPosition();
            this.getIslandMap().clear();
            if(currentHeading == Direction.N || currentHeading == Direction.S) {
                echo(Direction.E);
            }
            else{
                echo(currentHeading);
            }

            scan();
        }
    }


    public void setPosition(Position set){this.position = set; }
    public boolean getClock(){return this.clock;}
    public void setClock(boolean set){this.clock = set; }

    public void setOnLeft(boolean set){this.onLeft = set; }
    public void setDirection(Direction set){this.currentHeading = set;}

    // methode qui permet, en fonction de sa position de départ (trouver dans LocationStrategy) de savoir si on
    // parcours le contour de l'île dans le sens horaire ou anti-horaire
    public void init(){
        switch(position){
            case BOTTOM_LEFT:
                if(onLeft){
                    clock = false;
                }
                else{
                    clock = true;
                }
                break;
            case BOTTOM_RIGHT:
                if(onLeft){
                    clock = false;
                }
                else{
                    clock = true;
                }
                break;
            case UPPER_LEFT:
                if(onLeft){
                    clock = true;
                }
                else{
                    clock = false;
                }
                break;
            case UPPER_RIGHT:
                if(onLeft) {
                    clock = true;
                }
                else{
                    clock = false;
                }
                break;
            default:
                clock = false;
        }
    }

    ///////////// METHODES DE PLACEMENT ////////////////

    /*
    elles permettent de se placer sur le tile supérieur ou inférieur à sa position tout en conservant le même heading
     */

    public void up(){
        if(clock) {
            turnRight();
            turnRight();
            turnRight();
            fly();
            turnRight();
        }
        else{
            turnRight();
            fly();
            turnRight();
            turnRight();
            turnRight();
        }

    }

    public void down(){
        if(clock){
            turnRight();
            fly();
            turnRight();
            turnRight();
            turnRight();
        }
        else{
            turnRight();
            turnRight();
            turnRight();
            fly();
            turnRight();
        }

    }


    //////////////////////// METHODES DE CHANGEMENT DE COTE DE L'ILE////////////////////////

    /*
    Sous certaines conditions, le bot se rend compte qu'il doit avoir comme référenciel un nouveau côté  (ex: si l'île est un carré, il devra faire une rotation de 90°
    lorsque il arrivera au bout d'un côté, comme l'île n'est pas forcément carré, le headingPrevious permet de revenir au référenciel précédent
     */
    public void headingNext(){

        if(clock) {
            turnLeft();
            turnLeft();
            turnLeft();
            fly();
        }
        else{
            turnRight();
            turnRight();
            turnRight();
            fly();
        }

    }
    public void headingPrevious(){
        if(clock) {
            turnRight();
            turnRight();
            turnRight();
            fly();
        }
        else{
            turnLeft();
            turnLeft();
            turnLeft();
            fly();
        }

    }

    /*
    méthode lié aux résultats des échos, le premier écho fait sert à déterminer si le drone est sur le côté gauche ou droit de l'île, les suivants serviront à changer de référenciel
    ou à continuer sur le même
     */
    private void echoStrat(JSONObject result){

        JSONObject extras = result.getJSONObject("extras");
        int range = extras.getInt("range");
        boolean ground;
        ground = extras.getString("found").equals("GROUND");
        if(init){
            init = false;
            if(ground){
                if(echoDir == Direction.E)
                    onLeft = true;
                else
                    onLeft = false;
            }
            else{
                if(echoDir == Direction.E)
                    onLeft = false;
                else
                    onLeft = true;
            }
            init();
        }
        else {
            if (ground) fly();
            else headingNext();
            scan();
        }
    }
    @Override
    protected void findCreeks(JSONArray creeks){
        if (creeks.length() > 0) {
            for (Object x : creeks) {

                nextAction.clear();
                creekIds.add((String) x);
                end();
            }
        }
    }
    private void scanStrat(JSONObject result) {

        JSONObject extras = result.getJSONObject("extras");
        JSONArray creeks = extras.getJSONArray("creeks");
        JSONArray biome = extras.getJSONArray("biomes");
        boolean ocean = false;
        boolean ground = false;
        findCreeks(creeks);
        for (Object x : biome) {

            if (Biome.valueOf(((String) x)) == Biome.OCEAN)
                ocean = true;
            else
                ground = true;
        }
        EdgeStrat(ocean,ground);

    }
    /*
    méthode la plus importante de cette classe, elle contient l'algorythme pour faire le contours de l'île

    les variable up/down : si le drone monte la variable up passe à true et si il descend down passe true
    permet d'éviter les boucles
     */
    private void EdgeStrat(boolean ocean, boolean ground){
        if (ocean && !ground){ // cas où le scan ne trouve que de l'ocean
            if(up){
                fly();
                up = false;
                down = false;
            }
            else {

                if(down){
                    headingNext();
                    scan();
                    up = false;
                    down = false;
                    return;
                }
                down();
                down = true;
            }

        }
        else if (!ocean && ground) { // cas où le scan ne trouve que de la terre
            if(down){
                fly();
                down = false;
                up = false;
            }
            else{
                if(up){
                    headingPrevious();
                    scan();
                    up = false;
                    down = false;
                    return;
                }
                up();
                up = true;
            }
        }
        else{ // cas où le scan trouve de la terre et de l'ocean
            if(clock)
                echo(currentHeading.getRightDirection());
            else
                echo(currentHeading.getLeftDirection());
            up = false;
            down = false;
            return;
        }
        scan();
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
