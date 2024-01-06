import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.concurrent.Callable;

public class Mapper implements Callable<List<Map<String, Integer>>> {
    private final String text;
    private final int nbReduce;
    private ShuffleStrategy shuffleStrategy = new TourniquetStrategy();
    public Mapper(String text, int nbReduce) {
        this.text = text;
        this.nbReduce = nbReduce;
    }

    public List<Map<String, Integer>> countWord() {

        // Création de la liste des map attribuées à chaque reducer
        List<Map<String, Integer>> reducers = new ArrayList<>();
        for (int i = 0; i < nbReduce; i++) reducers.add(new HashMap<>());

        // Diviser le texte en mots en utilisant l'espace comme délimiteur
        String[] mots = text.split("\\s+");

        // Parcourir chaque mot et mettre à jour le compteur
        for (String mot : mots) {
            // Ignorer la casse en convertissant tous les mots en minuscules
            String motEnMinuscules = mot.replaceAll("[,:;?.]","").toLowerCase();

            // Récupérer l'adresse du reducer destination grâce au mot
            int destReducerIndex = this.shuffleStrategy.shuffle(motEnMinuscules, nbReduce);
            Map<String, Integer> destReducer = reducers.get(destReducerIndex);

            // Mettre à jour le compteur pour le mot actuel
            destReducer.put(motEnMinuscules, destReducer.getOrDefault(motEnMinuscules, 0) + 1);
        }

        return reducers;
    }

    public void setShuffleStrategy(ShuffleStrategy strategy) {
        this.shuffleStrategy = strategy;
    }

    @Override
    public List<Map<String, Integer>> call() {
        return countWord();
    }
}
