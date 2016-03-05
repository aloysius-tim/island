package launchTests;

import fr.unice.polytech.qgl.qda.Explorer;

import java.io.File;
import static eu.ace_design.island.runner.Runner.run;


public class Week48 {

    public static void main(String[] args) throws Exception {
        run(Explorer.class)
                .exploring(new File("src/test/java/launchTests/maps/week48.json"))
                .withSeed(0L)
                .startingAt(1,1,"SOUTH") //152,158,NORTH
                .backBefore(10000)
                .withCrew(3)
                .collecting(1000, "FUR")
                .collecting(30,  "GLASS")
                .collecting(2000,   "WOOD")
                .storingInto("src/test/java/launchTests/outputs/week48")
                .fire();
    }
}