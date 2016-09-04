package launchTests;

import fr.unice.polytech.qgl.qda.Explorer;

import java.io.File;

import static eu.ace_design.island.runner.Runner.run;

/**
 * IslandProject created on 19/11/2015 by Keynes Timothy - Aloysius_tim
 */
public class Week11 {
    public static void main(String[] args) throws Exception {
        run(Explorer.class)
                .exploring(new File("src/test/java/launchTests/maps/week11.json"))
                .withSeed(0L)
                .startingAt(1,1, "SOUTH")
                .backBefore(20000)
                .withCrew(15)
                .collecting(10000, "WOOD")
                .collecting(100,   "QUARTZ")
                .collecting(300,   "LEATHER")
                .collecting(50,    "ORE")
                .storingInto("src/test/java/launchTests/outputs/week11")
                .silentMode()
                .fire();
    }
}