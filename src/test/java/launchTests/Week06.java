package launchTests;

import fr.unice.polytech.qgl.qda.Explorer;

import java.io.File;

import static eu.ace_design.island.runner.Runner.run;

/**
 * Created by justin on 01/02/16.
 */
public class Week06 {
    public static void main(String[] args) throws Exception {
        run(Explorer.class)
                .exploring(new File("src/test/java/launchTests/maps/week06.json"))
                .withSeed(0L)
                .startingAt(1,159,"NORTH")
                .backBefore(20000)
                .withCrew(25)
                .collecting(15000, "WOOD")
                .collecting(100,   "GLASS")
                .storingInto("src/test/java/launchTests/outputs/week06")
                .silentMode()
                .fire();
    }
}
