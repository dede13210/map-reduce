public class TourniquetStrategy implements ShuffleStrategy {

    @Override
    public int shuffle(String word, int nbReducers) {
        if (nbReducers==1)
            return 0;
        return word.length()%(nbReducers-1);
    }
}
