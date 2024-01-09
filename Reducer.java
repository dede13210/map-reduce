import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class Reducer implements Callable<Map<String,Integer>> {
    private List<List<Map<String,Integer>>> allMapList;
    private int myId;

    public Reducer(List<List<Map<String, Integer>>> allMapList, int myId) {
        this.allMapList = allMapList;
        this.myId = myId;
    }

    public Map<String,Integer> reduce(){
        List<Map<String,Integer>> mapList = new ArrayList<>();
        for(List<Map<String,Integer>> mapLIst:allMapList){
            mapList.add(mapLIst.get(myId));
        }
        //merge map in entry
        // TODO : Optimize this
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

    @Override
    public Map<String, Integer> call() throws Exception{
        return reduce();
    }


}