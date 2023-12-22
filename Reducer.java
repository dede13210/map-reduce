import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Reducer {
    public Map<String,Integer> reduce(List<Map<String,Integer>> mapList){
        //merge map in entry
        HashMap<String, Integer> result = new HashMap<>(mapList.get(0));
        for (int i=1;i<mapList.size();i++){
            for (String word:mapList.get(i).keySet()){
                if(result.containsKey(word)){
                    result.put(word,result.get(word)+mapList.get(i).get(word));
                }else {
                    result.put(word,mapList.get(i).get(word));
                }
            }
        }
        return result;

    }

}