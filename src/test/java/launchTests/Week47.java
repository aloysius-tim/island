package launchTests;

import fr.unice.polytech.qgl.qda.Explorer;

import java.io.File;
import static eu.ace_design.island.runner.Runner.run;

public class Week47 {
    public static void main(String[] args) throws Exception {
        run(Explorer.class)
                .exploring(new File("src/test/java/launchTests/maps/week47.json"))
                .withSeed(0L)
                .startingAt(1,1, "SOUTH")
                .backBefore(7000)
                .withCrew(15)
                .collecting(1000, "WOOD")
                .collecting(300,  "QUARTZ")
                .collecting(10,   "FLOWER")
                .storingInto("src/test/java/launchTests/outputs/week47")
                .silentMode()
                .fire();
    }
}