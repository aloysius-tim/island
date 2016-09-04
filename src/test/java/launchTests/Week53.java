package launchTests;

import fr.unice.polytech.qgl.qda.Explorer;

import java.io.File;

import static eu.ace_design.island.runner.Runner.run;


public class Week53 {
    public static void main(String[] args) throws Exception {
        run(Explorer.class)
                .exploring(new File("src/test/java/launchTests/maps/week53.json"))
                .withSeed(0L)
                .startingAt(1,1,"SOUTH")
                .backBefore(10000)
                .withCrew(3)
                .collecting(80,  "FLOWER")
                .collecting(5000, "WOOD")
                .collecting(100,   "GLASS")
                .storingInto("src/test/java/launchTests/outputs/week53")
                .silentMode()
                .fire();
    }
}