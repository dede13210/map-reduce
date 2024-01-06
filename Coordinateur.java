import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Coordinateur {
    private final int nbMap;
    private final int nbReducer;

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
     * Splits a text into blocks of equivalent size
     * Only the last block can be smaller than the previous ones
     * Also replaces single quotes by spaces
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
     * @return          a list of the list of dictionaries
     */
    public List<List<Map<String, Integer>>> maps(String[] blocks) {
        List<List<Map<String, Integer>>> res = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(nbMap);

        ArrayList<Future<List<Map<String, Integer>>>> listThread = new ArrayList<> ();
        for (String block : blocks) {
            Future<List<Map<String, Integer>>> result = executor.submit(new Mapper(block, nbReducer));
            listThread.add(result);
        }

        try {
            for (Future<List<Map<String, Integer>>> resultMapper : listThread){
                res.add(resultMapper.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return res;
    }

    private List<Map<String, Integer>> reduces(List<List<Map<String, Integer>>> mapsList) {
        List<Map<String, Integer>> result = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(nbReducer);

        ArrayList<Future<Map<String, Integer>>> threads = new ArrayList<> ();
        for (int i = 0;i<nbReducer;i++) {
            List<Map<String, Integer>> reducemap = new ArrayList<>();
            for(List<Map<String,Integer>> listmap :mapsList){
                reducemap.add(listmap.get(i));
            }
            Future<Map<String, Integer>> reducerFuture = executor.submit(new Reducer(reducemap));
            threads.add(reducerFuture);
        }

        try {
            for (Future<Map<String, Integer>> resultReducer : threads){
                result.add(resultReducer.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return result;
    }

    public Map<String, Integer> mapReduce(String filename) throws FileNotFoundException {
        String txt = Coordinateur.read(filename);
        // TODO : Separate reducing from coordinator

        // Splitting
        // Client sends each blocks to mappers (depends on number of maps)
        String[] blocks = Coordinateur.split(txt, nbMap);

        // Mapping
        // The coordinator sends each blocks to mappers (depends on number of reducer)
        // The mappers make a list of dictionaries and "send" them to each reducer
        List<List<Map<String, Integer>>> mapsList = maps(blocks);

        // Reducing
        // The reducers merge the dictionaries they received and "send" it to client
        List<Map<String, Integer>> reducedMaps = reduces(mapsList);

        // Final aggregation
        Map<String, Integer> result = new HashMap<>();
        for (Map<String, Integer> map : reducedMaps)
            result.putAll(map);
        return result;
    }
}

