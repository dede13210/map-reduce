import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Print common stats
        Map<String, Integer> result = new Coordinateur(3, 3).mapReduce("bible.txt");
        int nbWords = result.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println(result.size() +" tokens, " + nbWords + " words");

        int[] nbMapChoices     = {1, 2, 3, 4, 5, 6};
        int[] nbReducerChoices = {1}; // TODO {1, 2, 3, 4, 5, 6}
        long avgDuration;

        // Check performances for each combination
        for (int nbMap: nbMapChoices) {
            for (int nbReducer: nbReducerChoices) {
                Coordinateur coordinateur = new Coordinateur(nbMap, nbReducer);

                // Check performance (duration) n times
                long[] durations = new long[1];
                for (int i = 0; i < 1; i++) {
                    // Start timer
                    long startTime = System.currentTimeMillis();
                    try {
                        result = coordinateur.mapReduce("bible.txt");
                    } catch (FileNotFoundException ex) {
                        System.out.println(ex.getMessage());
                    }
                    durations[i] = System.currentTimeMillis() - startTime;
                }

                // Compute average duration
                long sum = 0;
                for (long value : durations) sum += value;
                avgDuration = sum / durations.length;

                // Print result
                nbWords = result.values().stream().mapToInt(Integer::intValue).sum();
                // System.out.println("("+nbMap+","+nbReducer+") " + avgDuration + " ms (" + result.size() +" tokens, " + nbWords + " words)");
                System.out.println(result.get("serviteur") + " serviteurs");
            }
        }
    }
}
