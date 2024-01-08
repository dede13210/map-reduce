import java.util.Random;

public class RandomStrategy implements ShuffleStrategy {
    Random random = new Random();
    @Override
    public int shuffle(String word, int nbReducers) {
        if (nbReducers==1)  return 0;
        return random.nextInt(nbReducers);
    }
}
