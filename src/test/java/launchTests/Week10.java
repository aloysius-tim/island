package launchTests;

import fr.unice.polytech.qgl.qda.Explorer;

import java.io.File;

import static eu.ace_design.island.runner.Runner.run;

/**
 * IslandProject created on 19/11/2015 by Keynes Timothy - Aloysius_tim
 */
public class Week10 {
    public static void main(String[] args) throws Exception {
        run(Explorer.class)
                .exploring(new File("src/test/java/launchTests/maps/week10.json"))
                .withSeed(0L)
                .startingAt(158,158, "NORTH")
                .backBefore(20000)
                .withCrew(15)
                .collecting(10000, "WOOD")
                .collecting(500,   "QUARTZ")
                .collecting(300,   "LEATHER")
                .collecting(50,    "RUM")
                .storingInto("src/test/java/launchTests/outputs/week10")
                .silentMode()
                .fire();
    }
}