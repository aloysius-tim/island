package launchTests;

import fr.unice.polytech.qgl.qda.Explorer;

import java.io.File;

import static eu.ace_design.island.runner.Runner.run;

/**
 * Created by justin on 01/02/16.
 */
public class Week05 {
    public static void main(String[] args) throws Exception {
        run(Explorer.class)
                .exploring(new File("src/test/java/launchTests/maps/week05.json"))
                .withSeed(0L)
                .startingAt(1,1,"SOUTH")
                .backBefore(20000)
                .withCrew(25)
                .collecting(2000,  "FUR")
                .collecting(7000, "WOOD")
                .collecting(100,   "GLASS")
                .collecting(5000,   "PLANK")
                .storingInto("src/test/java/launchTests/outputs/week05")
                .silentMode()
                .fire();
    }
}
