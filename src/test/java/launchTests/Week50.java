package launchTests;

import fr.unice.polytech.qgl.qda.Explorer;

import java.io.File;

import static eu.ace_design.island.runner.Runner.run;

/**
 * Created by justin on 14/12/15.
 */
public class Week50 {
    public static void main(String[] args) throws Exception {
        run(Explorer.class)
                .exploring(new File("src/test/java/launchTests/maps/week50.json"))
                .withSeed(0L)
                .startingAt(1,1,"EAST") // 152,159,NORTH
                .backBefore(10000)
                .withCrew(3)
                .collecting(5000,  "FUR")
                .collecting(1000, "WOOD")
                .collecting(300,   "GLASS")
                .storingInto("src/test/java/launchTests/outputs/week50")
                .fire();
    }
}
