package logique;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente le laboratoire où l'alchimiste peut fabriquer ou créer des potions.
 * Le laboratoire charge les ingrédients et les recettes depuis des fichiers texte.
 */
public class Laboratoire
{
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Recette> recettes;
    private Alchimiste proprietaire;

    /**
     * Construit un laboratoire pour l'alchimiste donné.
     * Charge automatiquement les listes d'ingrédients et de recettes depuis les fichiers.
     *
     * @param alchimiste l'alchimiste propriétaire du laboratoire
     */
    public Laboratoire(Alchimiste alchimiste)
    {
        this.chargerIngredients();
        this.chargerRecettes();

        this.proprietaire = alchimiste;
    }

    public List<Ingredient> getIngredients()
    {
        return ingredients;
    }
    public List<Recette> getRecettes()
    {
        return recettes;
    }
    public Alchimiste getProprietaire()
    {
        return proprietaire;
    }

    /**
     * Tente de fabriquer une potion à partir des noms des trois ingrédients fournis.
     * Si la recette correspondante existe, l'alchimiste tente de la réaliser.
     *
     * Pré : ing1 != null, ing2 != null, ing3 != null
     *
     * @param ing1 le nom du premier ingrédient
     * @param ing2 le nom du deuxième ingrédient
     * @param ing3 le nom du troisième ingrédient
     * @return le résultat de l'expérience
     * @throws IllegalArgumentException si l'un des noms d'ingrédient est null
     */
    public ResultatExperience fairePotion(String ing1, String ing2, String ing3)
    {
        if (ing1 == null || ing2 == null || ing3 == null)
            throw new IllegalArgumentException("Les noms d'ingrédients ne peuvent pas être null");

        ResultatExperience experience = new ResultatExperience();

        Recette recette = this.trouverRecette(ing1, ing2, ing3);
        if(recette != null)
        {
            boolean success = this.proprietaire.fairePotion(recette);

            experience.setExiste(true);
            experience.setSuccess(success);
        }

        return experience;
    }

    /**
     * Crée et ajoute une nouvelle recette dans le compendium à partir des informations fournies.
     * Si une recette avec ces trois ingrédients existe déjà, l'opération échoue.
     *
     * Pré : ing1 != null, ing2 != null, ing3 != null
     * Pré : nom != null
     * Pré : 1 <= difficulte <= 5
     * Pré : pointExperience > 0
     *
     * @param ing1            le nom du premier ingrédient
     * @param ing2            le nom du deuxième ingrédient
     * @param ing3            le nom du troisième ingrédient
     * @param nom             le nom de la nouvelle recette
     * @param difficulte      la difficulté de la recette (entre 1 et 5)
     * @param pointExperience les points d'expérience de la recette
     * @return le résultat de l'expérience
     * @throws IllegalArgumentException si l'un des noms d'ingrédient est null
     * @throws IllegalArgumentException si le nom est null
     * @throws IllegalArgumentException si la difficulté n'est pas entre 1 et 5
     * @throws IllegalArgumentException si le nombre de points d'expérience est inférieur ou égal à 0
     */
    public ResultatExperience creerNouvellePotion(String ing1, String ing2, String ing3, String nom, int difficulte, int pointExperience)
    {
        if (ing1 == null || ing2 == null || ing3 == null)
            throw new IllegalArgumentException("Les noms d'ingrédients ne peuvent pas être null");
        if (nom == null)
            throw new IllegalArgumentException("Le nom ne peut pas être null");
        if (difficulte < 1 || difficulte > 5)
            throw new IllegalArgumentException("La difficulté doit être entre 1 et 5 inclusivement");
        if (pointExperience <= 0)
            throw new IllegalArgumentException("Le nombre de points d'expérience doit être supérieur à 0");

        ResultatExperience experience = new ResultatExperience();
        experience.setExiste(true);

        Recette recette = this.trouverRecette(ing1, ing2, ing3);

        if(recette == null)
        {
            experience.setExiste(false);
            experience.setSuccess(true);

            Ingredient ingredient1 = this.trouverIngredient(ing1);
            Ingredient ingredient2 = this.trouverIngredient(ing2);
            Ingredient ingredient3 = this.trouverIngredient(ing3);

            recette = new Recette(ingredient1, ingredient2, ingredient3, nom, difficulte, pointExperience);
            ajouterRecette(recette);
        }

        return experience;
    }

    /**
     * Recherche dans le compendium une recette contenant les trois ingrédients donnés.
     *
     * Pré : ing1 != null, ing2 != null, ing3 != null
     *
     * @param ing1 le nom du premier ingrédient
     * @param ing2 le nom du deuxième ingrédient
     * @param ing3 le nom du troisième ingrédient
     * @return la recette trouvée, ou null si elle n'existe pas
     * @throws IllegalArgumentException si l'un des noms d'ingrédient est null
     */
    public Recette trouverRecette(String ing1, String ing2, String ing3)
    {
        if (ing1 == null || ing2 == null || ing3 == null)
            throw new IllegalArgumentException("Les noms d'ingrédients ne peuvent pas être null");

        Recette resultat = null;

        for(Recette element : this.recettes)
        {
            if (element.contientIngredient(ing1) && element.contientIngredient(ing2) && element.contientIngredient(ing3))
            {
                resultat = element;
                break;
            }
        }

        return resultat;
    }

    /**
     * Recherche dans la liste un ingrédient portant le nom donné.
     *
     * Pré : nom != null
     *
     * @param nom le nom de l'ingrédient recherché
     * @return l'ingrédient trouvé, ou null s'il n'existe pas
     * @throws IllegalArgumentException si nom est null
     */
    public Ingredient trouverIngredient(String nom)
    {
        if (nom == null)
            throw new IllegalArgumentException("Le nom ne peut pas être null");

        Ingredient resultat = null;

        for (Ingredient ing : ingredients)
        {
            if (ing.getNom().equals(nom))
            {
                resultat = ing;
                break;
            }
        }

        return resultat;
    }

    private ArrayList<Ingredient> chargerIngredients()
    {
        ingredients = new ArrayList<Ingredient>();
        List<String> lignesFichier = null;

        try
        {
            Path path = Paths.get("src/ingredients.txt");
            lignesFichier = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Fichier non trouvé");
        }

        for (String ligneFichier : lignesFichier)
        {
            String[] valeurs = ligneFichier.split("\\|");
            String nomIngredient = valeurs[0];
            int prixIngredient = Integer.parseInt(valeurs[1]);

            Ingredient ingredient = new Ingredient(nomIngredient, prixIngredient);
            ingredients.add(ingredient);
        }

        return ingredients;
    }

    private ArrayList<Recette> chargerRecettes()
    {
        recettes = new ArrayList<Recette>();
        List<String> lignesFichier = null;

        try
        {
            Path path = Paths.get("src/recettes.txt");
            lignesFichier = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Fichier non trouvé");
        }

        for (String ligneFichier : lignesFichier)
        {
            String[] valeurs = ligneFichier.split("\\|");
            String nomRecette = valeurs[0];
            Ingredient ingredent1 = this.trouverIngredient(valeurs[1]);
            Ingredient ingredent2 = this.trouverIngredient(valeurs[2]);
            Ingredient ingredent3 = this.trouverIngredient(valeurs[3]);
            int difficulte = Integer.parseInt(valeurs[4]);
            int pointExperience = Integer.parseInt(valeurs[5]);

            Recette recette = new Recette(ingredent1, ingredent2, ingredent3, nomRecette, difficulte, pointExperience);
            recettes.add(recette);
        }

        return recettes;
    }

    private void ajouterRecette(Recette recette)
    {
        String nouvelleRecette = recette.toString();
        try(PrintWriter output = new PrintWriter(new FileWriter("src/recettes.txt",true)))
        {
            output.printf("%s\r\n", nouvelleRecette);
        }
        catch (Exception e){}

        this.recettes.add(recette);
    }
}
