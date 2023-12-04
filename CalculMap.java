import java.util.HashMap;
import java.util.Map;

public class CalculMap {
    public static Map<String, Integer> countWord(String texte) {
        // Utilisation d'une carte (Map) pour stocker les mots et leur nombre d'occurrences
        Map<String, Integer> compteur = new HashMap<>();

        // Diviser le texte en mots en utilisant l'espace comme délimiteur
        String[] mots = texte.split("\\s+");

        // Parcourir chaque mot et mettre à jour le compteur
        for (String mot : mots) {
            // Ignorer la casse en convertissant tous les mots en minuscules
            String motEnMinuscules = mot.replace(",","").replace(":","").replace(".","").replace(";","").toLowerCase();

            // Mettre à jour le compteur pour le mot actuel
            compteur.put(motEnMinuscules, compteur.getOrDefault(motEnMinuscules, 0) + 1);
        }

        return compteur;
    }
}
