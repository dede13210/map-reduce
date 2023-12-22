import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Reducer {
    public Map<String,Integer> reduce(List<Map<String,Integer>> mapList){
        Map<String,Integer> result = new HashMap<>();
        for (Map<String,Integer> countMap:mapList){
            result.putAll(countMap);
        }
        return result;

    }

}
