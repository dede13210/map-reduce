import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Coordinateur {
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
}

