package projecttwo;

/**
 * The main method will call the run() method in the GymManager class.
 *
 * @author Kangwei Zhu, Michael Israel
 */
public class RunProject1 {
    /**
     * Default constructor.
     */
    public RunProject1() {
    }

    /**
     * Read incoming command and run the GymManager class
     *
     * @param args commandLine inputs
     */
    public static void main(String[] args) {
        new GymManager().run();
    }
}
