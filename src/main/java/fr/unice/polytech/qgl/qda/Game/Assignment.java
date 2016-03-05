package fr.unice.polytech.qgl.qda.Game;

import fr.unice.polytech.qgl.qda.Island.Direction;
import fr.unice.polytech.qgl.qda.Island.Ressource;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * IslandProject created on 17/11/2015 by Keynes Timothy - Aloysius_tim
 */
public class Assignment extends HashMap<Ressource, Contract> {
    private int nbMan;
    private int budget;
    private Direction heading;

    public Assignment(JSONObject contracts) {
        this.budget = contracts.getInt("budget");
        this.heading = Direction.getHeading(contracts.getString("heading"));

        this.nbMan = contracts.getInt("men");

        for(Object json : contracts.getJSONArray("contracts")){
            JSONObject tmp = (JSONObject) json;
            this.put(Ressource.valueOf(tmp.getString("resource")),new Contract(tmp.getInt("amount")));

        }
    }

    public int getBudget() {
        return budget;
    }
    public void updateBudget(int cost ){budget -= cost;}

    public Direction getHeading() {
        return heading;
    }

    public int getNbMan() {
        return nbMan;
    }
}
