package logique;

import java.util.ArrayList;

/**
 * Représente une recette de potion contenant trois ingrédients distincts.
 * Une recette a un nom, un niveau de difficulté et un nombre de points d'expérience.
 *
 * Author : Mathieu Bourgoin
 * Ordre de conception : 3e
 */
public class Recette
{
    private ArrayList<Ingredient> ingredients;
    private String nom;
    private int difficulte;
    private int pointExperience;

    /**
     * Construit une recette avec trois ingrédients distincts et ses caractéristiques.
     *
     * Pré : ing1 != null, ing2 != null, ing3 != null
     * Pré : ing1, ing2 et ing3 sont tous différents (par nom)
     * Pré : nom != null
     * Pré : nom.length() >= 10
     * Pré : 1 <= difficulte <= 5
     * Pré : pointExperience > 0
     *
     * @param ing1            premier ingrédient
     * @param ing2            deuxième ingrédient
     * @param ing3            troisième ingrédient
     * @param nom             le nom de la recette
     * @param difficulte      la difficulté (entre 1 et 5 inclusivement)
     * @param pointExperience les points d'expérience accordés si la recette est réussie
     * @throws IllegalArgumentException si un ingrédient est null
     * @throws IllegalArgumentException si deux ingrédients sont identiques
     * @throws IllegalArgumentException si le nom est null ou contient moins de 10 caractères
     * @throws IllegalArgumentException si la difficulté n'est pas entre 1 et 5
     * @throws IllegalArgumentException si le nombre de points d'expérience est inférieur ou égal à 0
     */
    public Recette(Ingredient ing1, Ingredient ing2, Ingredient ing3, String nom, int difficulte, int pointExperience)
    {
        if (ing1 == null || ing2 == null || ing3 == null)
            throw new IllegalArgumentException("Les ingrédients ne peuvent pas être null");

        if (ing1.getNom().equals(ing2.getNom()) || ing1.getNom().equals(ing3.getNom()) || ing2.getNom().equals(ing3.getNom()))
            throw new IllegalArgumentException("Les ingrédients doivent tous être différents");

        this.ingredients = new ArrayList<Ingredient>();
        this.ingredients.add(ing1);
        this.ingredients.add(ing2);
        this.ingredients.add(ing3);

        this.setNom(nom);
        this.setDifficulte(difficulte);
        this.setPointExperience(pointExperience);
    }


    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de la recette.
     *
     * Pré : nom != null
     * Pré : nom.length() >= 10
     *
     * @param nom le nom de la recette
     * @throws IllegalArgumentException si nom est null ou contient moins de 10 caractères
     */
    private void setNom(String nom) {
        if (nom == null)
            throw new IllegalArgumentException("Le nom ne peut pas être null");
        if (nom.length() < 10)
            throw new IllegalArgumentException("Le nom doit contenir au moins 10 caractères");
        this.nom = nom;
    }

    public int getDifficulte() {
        return difficulte;
    }

    /**
     * Définit la difficulté de la recette.
     *
     * Pré : 1 <= difficulte <= 5
     *
     * @param difficulte la difficulté de la recette
     * @throws IllegalArgumentException si la difficulté n'est pas entre 1 et 5 inclusivement
     */
    private void setDifficulte(int difficulte) {
        if (difficulte < 1 || difficulte > 5)
            throw new IllegalArgumentException("La difficulté doit être entre 1 et 5 inclusivement");
        this.difficulte = difficulte;
    }

    public int getPointExperience() {
        return pointExperience;
    }

    /**
     * Définit le nombre de points d'expérience de la recette.
     *
     * Pré : pointExperience > 0
     *
     * @param pointExperience le nombre de points d'expérience
     * @throws IllegalArgumentException si le nombre de points d'expérience est inférieur ou égal à 0
     */
    private void setPointExperience(int pointExperience) {
        if (pointExperience <= 0)
            throw new IllegalArgumentException("Le nombre de points d'expérience doit être supérieur à 0");
        this.pointExperience = pointExperience;
    }

    public int obtenirPrix()
    {
        int prixTotal = 0;

        for (Ingredient ing : this.ingredients)
            prixTotal += ing.getPrix();

        return prixTotal;
    }

    /**
     * Vérifie si un ingrédient dont le nom est donné est présent dans la recette.
     *
     * Pré : nom != null
     *
     * @param nom le nom de l'ingrédient recherché
     * @return true si l'ingrédient est dans la recette, false sinon
     * @throws IllegalArgumentException si nom est null
     */
    public boolean contientIngredient(String nom)
    {
        if (nom == null)
            throw new IllegalArgumentException("Le nom ne peut pas être null");

        boolean estContenu = false;

        for (Ingredient ing : this.ingredients)
        {
            if (ing.getNom().equals(nom))
            {
                estContenu = true;
                break;
            }
        }

        return estContenu;
    }

    @Override
    public String toString()
    {
        return String.format("%s|%s|%s|%s|%s|%s", this.getNom(), this.ingredients.get(0).getNom(), this.ingredients.get(1).getNom(), this.ingredients.get(2).getNom(), this.getDifficulte(), this.getPointExperience());
    }


}
