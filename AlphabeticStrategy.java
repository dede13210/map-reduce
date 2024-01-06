import java.util.ArrayList;
import java.util.Arrays;

public class AlphabeticStrategy implements ShuffleStrategy{
    private final ArrayList<String> aphabeticList = new ArrayList<>(Arrays.asList("a","à", "b", "c", "d", "e","é", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
    @Override
    public int shuffle(String word, int nbReducers) {
        if(nbReducers==1)
            return 0;
        System.out.println( word);
        String firstChar = String.valueOf(word.charAt(0));
        if(!aphabeticList.contains(firstChar)){
            return 15%(nbReducers-1);
        }
        return aphabeticList.indexOf(firstChar)%(nbReducers-1);
    }
}
