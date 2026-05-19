package logique;

/**
 * Représente un ingrédient utilisé dans les recettes de potions.
 * Un ingrédient possède un nom unique et un prix unitaire.
 */
public class Ingredient
{
    private String nom;
    private int prix;

    /**
     * Construit un ingrédient avec le nom et le prix donnés.
     *
     * Pré : nom != null
     * Pré : nom.length() >= 6
     * Pré : prix > 0
     *
     * @param nom  le nom de l'ingrédient
     * @param prix le prix unitaire de l'ingrédient
     * @throws IllegalArgumentException si nom est null ou contient moins de 6 caractères
     * @throws IllegalArgumentException si prix est inférieur ou égal à 0
     */
    public Ingredient(String nom, int prix)
    {
        this.setNom(nom);
        this.setPrix(prix);
    }

    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de l'ingrédient.
     *
     * Pré : nom != null
     * Pré : nom.length() >= 6
     *
     * @param nom le nom de l'ingrédient
     * @throws IllegalArgumentException si nom est null ou contient moins de 6 caractères
     */
    private void setNom(String nom) {
        if (nom == null)
            throw new IllegalArgumentException("Le nom ne peut pas être null");
        if (nom.length() < 6)
            throw new IllegalArgumentException("Le nom doit contenir au moins 6 caractères");
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    /**
     * Définit le prix de l'ingrédient.
     *
     * Pré : prix > 0
     *
     * @param prix le prix unitaire
     * @throws IllegalArgumentException si prix est inférieur ou égal à 0
     */
    private void setPrix(int prix) {
        if (prix <= 0)
            throw new IllegalArgumentException("Le prix doit être supérieur à 0");
        this.prix = prix;
    }

    @Override
    public String toString()
    {
        return "Ingredient{" + "nom=" + nom + ", prix=" + prix + '}';
    }
}
