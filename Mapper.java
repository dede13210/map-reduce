import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Mapper {
    public ShuffleStrategy shuffleStrategy = new TourniquetStrategy();
    public List<Map<String, Integer>> countWord(String texte, int nbrReduce) {
        // Utilisation d'une carte (Map) pour stocker les mots et leur nombre d'occurrences
        List<Map<String, Integer>> compteur = new ArrayList<>();
        for (int i = 0;i<nbrReduce;i++){
            compteur.add(new HashMap<>());
        }

        // Diviser le texte en mots en utilisant l'espace comme délimiteur
        String[] mots = texte.split("\\s+");

        // Parcourir chaque mot et mettre à jour le compteur
        for (String mot : mots) {
            // Ignorer la casse en convertissant tous les mots en minuscules
            String motEnMinuscules = mot.replace("[,:;?.]","").toLowerCase();

            // Mettre à jour le compteur pour le mot actuel
            int destReducer = this.shuffleStrategy.shuffle(motEnMinuscules, nbrReduce);
            compteur.get(destReducer).put(motEnMinuscules, compteur.get(destReducer).getOrDefault(motEnMinuscules, 0) + 1);
        }

        return compteur;
    }

    public void setShuffleStrategy(ShuffleStrategy strategy) {
        this.shuffleStrategy = strategy;
    }
}
