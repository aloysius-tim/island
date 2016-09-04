package launchTests;

import fr.unice.polytech.qgl.qda.Explorer;

import java.io.File;

import static eu.ace_design.island.runner.Runner.run;

/**
 * IslandProject created on 19/11/2015 by Keynes Timothy - Aloysius_tim
 */
public class Week07 {
    public static void main(String[] args) throws Exception {
        run(Explorer.class)
                .exploring(new File("src/test/java/launchTests/maps/week07.json"))
                .withSeed(0L)
                .startingAt(1,159, "NORTH")
                .backBefore(20000)
                .withCrew(25)
                .collecting(15000, "WOOD")
                .collecting(30,   "LEATHER")
                .collecting(100,   "GLASS")
                .storingInto("src/test/java/launchTests/outputs/week07")
                .silentMode()
                .fire();
    }
}