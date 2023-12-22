import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.concurrent.Callable;

public class Mapper implements Callable<List<Map<String, Integer>>> {
    private String text;
    private int nbrReduce;
    private ShuffleStrategy shuffleStrategy = new TourniquetStrategy();
    public Mapper(String text, int nbrReduce) {
        this.text = text;
        this.nbrReduce = nbrReduce;
    }

    public List<Map<String, Integer>> countWord(String texte, int nbReduce) {
        // Utilisation d'une carte (Map) pour stocker les mots et leur nombre d'occurrences
        List<Map<String, Integer>> compteur = new ArrayList<>();
        for (int i = 0;i<nbReduce;i++){
            compteur.add(new HashMap<>());
        }

        // Diviser le texte en mots en utilisant l'espace comme délimiteur
        String[] mots = texte.split("\\s+");

        // Parcourir chaque mot et mettre à jour le compteur
        for (String mot : mots) {
            // Ignorer la casse en convertissant tous les mots en minuscules
            String motEnMinuscules = mot.replaceAll("[,:;?.]","").toLowerCase();

            // Mettre à jour le compteur pour le mot actuel
            int destReducer = this.shuffleStrategy.shuffle(motEnMinuscules, nbReduce);
            compteur.get(destReducer).put(motEnMinuscules, compteur.get(destReducer).getOrDefault(motEnMinuscules, 0) + 1);
        }

        return compteur;
    }

    public void setShuffleStrategy(ShuffleStrategy strategy) {
        this.shuffleStrategy = strategy;
    }

    @Override
    public List<Map<String, Integer>> call() throws Exception {
        return countWord(text,nbrReduce);
    }
}
