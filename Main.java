import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class Main {
    public static int nbMap = 231; //231
    public static int nbReducer = 10; //231

    public static void main(String[] args){
        String txt;
        try {
            txt = Coordinateur.read("bible.txt");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        String[] blocks = Coordinateur.split(txt, nbMap);

        List<List<Map<String, Integer>>> maps = Coordinateur.maps(blocks,nbReducer);
        for(List<Map<String,Integer>> listmap:maps){

        }

    }
}
