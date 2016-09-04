package launchTests;

import fr.unice.polytech.qgl.qda.Explorer;

import java.io.File;

import static eu.ace_design.island.runner.Runner.run;

/**
 * IslandProject created on 19/11/2015 by Keynes Timothy - Aloysius_tim
 */
public class Week09 {
    public static void main(String[] args) throws Exception {
        run(Explorer.class)
                .exploring(new File("src/test/java/launchTests/maps/week09.json"))
                .withSeed(0L)
                .startingAt(1,1, "EAST")
                .backBefore(20000)
                .withCrew(15)
                .collecting(10000, "WOOD")
                .collecting(300,   "LEATHER")
                .collecting(50,   "GLASS")
                .storingInto("src/test/java/launchTests/outputs/week09")
                .silentMode()
                .fire();
    }
}