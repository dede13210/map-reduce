import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Coordinateur {
    private int nbMap; //231 for block with equal size
    private int nbReducer;

    public Coordinateur(int nbMap, int nbReducer) {
        this.nbMap = nbMap;
        this.nbReducer = nbReducer;
    }

    public static String read(String filepath) throws FileNotFoundException {
        try {
            return new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException e) {
            throw new FileNotFoundException("Le fichier \"" + filepath + "\" n'existe pas");
        }
    }

    /**
     * Splits a text into nbBlocks blocks of equivalent size
     * Only the last block can be smaller than the previous ones
     * @param txt       the text to split (String)
     * @param nbBlocks  the number of blocks to split into (int)
     * @return          the array of blocks (String[])
     */
    public static String[] split(String txt, int nbBlocks) {
        String[] lines = txt.split("\n");
        int linesPerBlock = (int)Math.ceil((double)lines.length / nbBlocks);
        String[] result = new String[nbBlocks];

        for (int i = 0; i < nbBlocks; i++) {
            int startIdx = i * linesPerBlock;
            int endIdx = (i == nbBlocks - 1) ? lines.length : (i + 1) * linesPerBlock;
            result[i] = String.join("\n", Arrays.copyOfRange(lines, startIdx, endIdx));
        }

        return result;
    }

    /**
     * Returns the mappers results (list of nbReducer map to send to reducers)
     * @param blocks    the blocks to map (String[])
     * @param nbReducer the number of reducers to send (int)
     * @return          a list of the list of dictionaries
     */
    public static List<List<Map<String, Integer>>> maps(String[] blocks, int nbReducer) {
        List<List<Map<String, Integer>>> res = new ArrayList<>();
        for (String block : blocks) {
            List<Map<String, Integer>> maps = new Mapper().countWord(block, nbReducer);
            res.add(maps);
        }
        return res;
    }


    public Map<String, Integer> mapReduce(String filename) throws FileNotFoundException {
        String txt = Coordinateur.read(filename);

        // Splitting
        // By Client, depends on number of maps (eventually, we could use a block size instead)
        // Client will "send" each blocks to mappers
        String[] blocks = Coordinateur.split(txt, nbMap);

        // Mapping
        // By Coordinator, depends on number of reducer
        // The coordinator will send each blocks to mappers
        // The mappers will make a list of dictionaries and "send" them to each reducer
        List<List<Map<String, Integer>>> mapsList = Coordinateur.maps(blocks,nbReducer);


        // Reducing
        // The reducers will merge the dictionaries they received and "send" it to client
        List<Map<String, Integer>> reducedMaps = new ArrayList<>();
        for (int i = 0; i < nbReducer; i++) {
            Reducer reducer = new Reducer();

            // Get dictionaries sent to reducer
            List<Map<String, Integer>> reducerMaps = new ArrayList<>();
            for (List<Map<String, Integer>> mapList : mapsList) {
                reducerMaps.add(mapList.get(i));
            }

            // Reduce dictionaries and add the result to reducedMaps
            reducedMaps.add(reducer.reduce(reducerMaps));
        }

        Map<String, Integer> result = new HashMap<>();
        for (Map<String, Integer> map : reducedMaps) result.putAll(map);
        return result;
    }
}

