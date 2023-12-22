import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static int nbMap = 231; //231
    public static int nbReducer = 2; //231

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

        List<Map<String,Integer>> list1 = new ArrayList<>();
        List<Map<String,Integer>> list2 = new ArrayList<>();

        for (List<Map<String,Integer>> mapList:maps){
            list1.add(mapList.get(0));
            list2.add(mapList.get(1));
        }
        System.out.println(Reduce.reduce(list1));
        System.out.println(Reduce.reduce(list2));

    }
}
