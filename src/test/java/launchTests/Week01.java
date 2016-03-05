package launchTests;

import fr.unice.polytech.qgl.qda.Explorer;

import java.io.File;

import static eu.ace_design.island.runner.Runner.run;

/**
 * Created by justin on 01/02/16.
 */
public class Week01 {
    public static void main(String[] args) throws Exception {
        run(Explorer.class)
                .exploring(new File("src/test/java/launchTests/maps/week01.json"))
                .withSeed(0L)
                .startingAt(1, 1, "EAST")
                .backBefore(15000)
                .withCrew(3)
                .collecting(80, "FLOWER")
                .collecting(3000, "WOOD")
                .collecting(800, "QUARTZ")
                .collecting(1000,   "PLANK")
                .storingInto("src/test/java/launchTests/outputs/week01")
                .fire();
    }
}
