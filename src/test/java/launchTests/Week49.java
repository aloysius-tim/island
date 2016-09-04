package launchTests;

import fr.unice.polytech.qgl.qda.Explorer;

import java.io.File;
import static eu.ace_design.island.runner.Runner.run;


public class Week49 {
    public static void main(String[] args) throws Exception {
        run(Explorer.class)
                .exploring(new File("src/test/java/launchTests/maps/week49.json"))
                .withSeed(0L)
                .startingAt(1,1,"SOUTH") // 152,159,NORTH
                .backBefore(10000)
                .withCrew(3)
                .collecting(1000,  "SUGAR_CANE")
                .collecting(5000, "WOOD")
                .collecting(300,   "GLASS")
                .storingInto("src/test/java/launchTests/outputs/week49")
                .silentMode()
                .fire();
    }
}