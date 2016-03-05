package fr.unice.polytech.qgl.qda.strategy.DronePhase.SpecificPattern;

import fr.unice.polytech.qgl.qda.Game.Assignment;
import fr.unice.polytech.qgl.qda.Game.AvailableActions;
import fr.unice.polytech.qgl.qda.Island.Biome;
import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.IslandMap;
import fr.unice.polytech.qgl.qda.Json.actions.Action;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Echo;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Fly;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Heading;
import fr.unice.polytech.qgl.qda.Json.actions.aerial.Scan;
import fr.unice.polytech.qgl.qda.strategy.DronePhase.DroneStrategy;
import fr.unice.polytech.qgl.qda.strategy.Strategy;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

/**
 * IslandProject created on 23/11/2015 by Keynes Timothy - Aloysius_tim
 */
public class EdgeStrategy extends DroneStrategy {
    private boolean up = false;
    private boolean down = false;
    private boolean init = true;
    private boolean onLeft;
    private boolean clock;

    public EdgeStrategy(IslandMap islandMap, Assignment assignment, LinkedList<JSONObject> bufferActions, LinkedList<Action> actionsHistory) {
        super(islandMap, assignment, bufferActions, actionsHistory);

        if(this.islandMap.getDirectionActuelle() == Direction.N || this.islandMap.getDirectionActuelle() == Direction.S)
            this.bufferActions.add(Echo.buildAction(Direction.E));
        else
            this.bufferActions.add(Echo.buildAction(this.islandMap.getDirectionActuelle()));

        this.bufferActions.add(Scan.buildAction());
    }

    public void init(){
        this.islandMap.getWidth();
        this.islandMap.getPositionActuelle();
        switch(position){
            case BOTTOM_LEFT || BOTTOM_RIGHT:
                if(onLeft){
                    clock = false;
                }
                else{
                    clock = true;
                }
                break;
            case UPPER_LEFT || UPPER-RIGHT:
                if(onLeft){
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
        this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
        if (!clock)this.bufferActions.add(Fly.buildAction());
        this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
        this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
        if (clock)this.bufferActions.add(Fly.buildAction());
        this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
    }

    public void down(){
        this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
        if (clock)this.bufferActions.add(Fly.buildAction());
        this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
        this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
        if (!clock)this.bufferActions.add(Fly.buildAction());
        this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
    }


    //////////////////////// METHODES DE CHANGEMENT DE COTE DE L'ILE////////////////////////
    /*
    Sous certaines conditions, le bot se rend compte qu'il doit avoir comme référenciel un nouveau côté  (ex: si l'île est un carré, il devra faire une rotation de 90°
    lorsque il arrivera au bout d'un côté, comme l'île n'est pas forcément carré, le headingPrevious permet de revenir au référenciel précédent
     */
    public void headingNext(){
        if(clock) {
            this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getLeftDirection()));
            this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getLeftDirection()));
            this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getLeftDirection()));
        }
        else {
            this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
            this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
            this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
        }
        this.bufferActions.add(Fly.buildAction());
    }

    public void headingPrevious(){
        if(clock) {
            this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
            this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
            this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
        }
        else{
            this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getLeftDirection()));
            this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getLeftDirection()));
            this.bufferActions.add(Heading.buildAction(this.islandMap.getDirectionActuelle().getLeftDirection()));
        }
        this.bufferActions.add(Fly.buildAction());
    }

    /*
    méthode lié aux résultats des échos, le premier écho fait sert à déterminer si le drone est sur le côté gauche ou droit de l'île, les suivants serviront à changer de référenciel
    ou à continuer sur le même
     */
    private void echoStrat(Echo echo){
        boolean ground = (echo.getBiome() == Biome.GROUND);

        if(init){
            init = false;
            if(ground){
                if(echo.getDirection() == Direction.E)
                    onLeft = true;
                else
                    onLeft = false;
            }
            else{
                if(echo.getDirection() == Direction.E)
                    onLeft = false;
                else
                    onLeft = true;
            }
            init();
        }
        else {
            if (ground) this.bufferActions.add(Fly.buildAction());
            else headingNext();
            this.bufferActions.add(Scan.buildAction());
        }
    }

    /** méthode la plus importante de cette classe, elle contient l'algorythme pour faire le contours de l'île
    les variable up/down : si le drone monte la variable up passe à true et si il descend down passe true
    permet d'éviter les boucles**/
    private void edgeStrat(Scan scan){
        boolean ocean = (scan.getBiomes().contains(Biome.OCEAN));
        boolean ground = (scan.getBiomes().contains(Biome.GROUND));

        if (ocean && !ground){ // cas où le scan ne trouve que de l'ocean
            if(up){
                this.bufferActions.add(Fly.buildAction());
                up = false;
                down = false;
            }
            else {
                if(down){
                    headingNext();
                    this.bufferActions.add(Scan.buildAction());
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
                this.bufferActions.add(Fly.buildAction());
                down = false;
                up = false;
            }
            else{
                if(up){
                    headingPrevious();
                    this.bufferActions.add(Scan.buildAction());
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
                this.bufferActions.add(Echo.buildAction(this.islandMap.getDirectionActuelle().getRightDirection()));
            else
                this.bufferActions.add(Echo.buildAction(this.islandMap.getDirectionActuelle().getLeftDirection()));
            up = false;
            down = false;
            return;
        }
        this.bufferActions.add(Scan.buildAction());
    }

    @Override
    public void interpretAcknowledgeResult(JSONObject acknowledgeResult) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        super.interpretAcknowledgeResult(acknowledgeResult);

        Action last = this.actionsHistory.getLast();

        if (last.getActionType() == AvailableActions.ECHO)
            echoStrat((Echo) last);
        else
            edgeStrat((Scan) last);
    }

    @Override
    public Strategy getNextStrategy() {
        return super.getNextStrategy();
    }
}
