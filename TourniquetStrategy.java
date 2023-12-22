public class TourniquetStrategy implements ShuffleStrategy {

    @Override
    public int shuffle(String word, int nbReducers) {
        return word.length()%nbReducers;
    }
}
