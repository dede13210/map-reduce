import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static int nbMap = 231; //231 for block with equal size
    public static int nbReducer = 10; //231

    public static void main(String[] args){
        // Client
        String txt;
        try {
            txt = Coordinateur.read("bible.txt");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return;
        }

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
        // The reducers will merge the dictionaries they received and send it to
        List<Reducer> reducers = new ArrayList<>();
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

        System.out.println(reducedMaps.size());
        System.out.println(reducedMaps);
    }
}
