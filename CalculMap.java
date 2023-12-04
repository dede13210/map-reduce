import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CalculMap {
    public static List<Map<String, Integer>> countWord(String texte, int nbrReduce) {


        // Utilisation d'une carte (Map) pour stocker les mots et leur nombre d'occurrences

        List<Map<String, Integer>> compteur = new ArrayList<Map<String,Integer>>();
        for (int i = 0;i<nbrReduce;i++){
            compteur.add(new HashMap<String,Integer>());
        }

        // Diviser le texte en mots en utilisant l'espace comme délimiteur
        String[] mots = texte.split("\\s+");

        // Parcourir chaque mot et mettre à jour le compteur
        for (String mot : mots) {
            // Ignorer la casse en convertissant tous les mots en minuscules
            String motEnMinuscules = mot.replace(",","").replace(":","").replace(".","").replace(";","").replace("?","").toLowerCase();

            // Mettre à jour le compteur pour le mot actuel
            compteur.get(shuffling(motEnMinuscules,nbrReduce)).put(motEnMinuscules, compteur.get(shuffling(motEnMinuscules,nbrReduce)).getOrDefault(motEnMinuscules, 0) + 1);
        }

        return compteur;
    }

    public static int shuffling(String mot,int nbrReduce){
        return mot.length()%nbrReduce;

    }
}
