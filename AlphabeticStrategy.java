public class AlphabeticStrategy implements ShuffleStrategy{
    @Override
    public int shuffle(String word, int nbReducers) {
        if(nbReducers==1) return 0;
        return word.charAt(0)%(nbReducers-1);
    }
}
