package launchTests;

import fr.unice.polytech.qgl.qda.Explorer;

import java.io.File;

import static eu.ace_design.island.runner.Runner.run;

/**
 * Created by justin on 01/02/16.
 */
public class Week02 {
    public static void main(String[] args) throws Exception {
        run(Explorer.class)
                .exploring(new File("src/test/java/launchTests/maps/week02.json"))
                .withSeed(0L)
                .startingAt(1,1,"EAST")
                .backBefore(15000)
                .withCrew(3)
                .collecting(40,  "FLOWER")
                .collecting(5000, "WOOD")
                .collecting(200, "QUARTZ")
                .collecting(50,   "GLASS")
                .storingInto("src/test/java/launchTests/outputs/week02")
                .silentMode()
                .fire();
    }
}
