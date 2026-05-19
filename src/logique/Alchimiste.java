package logique;

import java.util.Random;

/**
 * Représente un alchimiste capable de fabriquer des potions et de monter en niveau.
 * Un alchimiste possède un nom, un niveau et un compteur d'expérience.
 */
public class Alchimiste
{
    public static final int EXPERIENCE_POUR_NIVEAU_SUIVANT = 500;
    private String nom;
    private int niveau;
    private int experience;

    /**
     * Construit un alchimiste de niveau 1 avec le nom donné.
     *
     * Pré : nom != null
     * Pré : nom.length() >= 6
     *
     * @param nom le nom de l'alchimiste
     * @throws IllegalArgumentException si nom est null ou contient moins de 6 caractères
     */
    public Alchimiste(String nom)
    {
        this.setNom(nom);
        this.setNiveau(1);
        this.setExperience(0);
    }

    /**
     * Construit un alchimiste avec le nom et le niveau donnés.
     *
     * Pré : nom != null
     * Pré : nom.length() >= 6
     *
     * @param nom    le nom de l'alchimiste
     * @param niveau le niveau de départ de l'alchimiste
     * @throws IllegalArgumentException si nom est null ou contient moins de 6 caractères
     */
    public Alchimiste(String nom, int niveau)
    {
        this(nom);
        setNiveau(niveau);
    }

    public String getNom()
    {
        return this.nom;
    }
    public int getNiveau()
    {
        return this.niveau;
    }
    public int getExperience()
    {
        return this.experience;
    }

    /**
     * Définit le nom de l'alchimiste.
     *
     * Pré : nom != null
     * Pré : nom.length() >= 6
     *
     * @param nom le nom de l'alchimiste
     * @throws IllegalArgumentException si nom est null ou contient moins de 6 caractères
     */
    private void setNom(String nom)
    {
        if (nom == null)
            throw new IllegalArgumentException("Le nom ne peut pas être null");
        if (nom.length() < 6)
            throw new IllegalArgumentException("Le nom doit contenir au moins 6 caractères");
        this.nom = nom;
    }

    private void setNiveau(int niveau)
    {
        this.niveau = niveau;
    }

    private void setExperience(int experience)
    {
        this.experience = experience;
    }

    /**
     * Tente de fabriquer une potion à partir de la recette fournie.
     * La réussite dépend du niveau de l'alchimiste et de la difficulté de la recette.
     * Si la tentative est réussie, l'alchimiste gagne les points d'expérience de la recette.
     *
     * Pré : recette != null
     *
     * @param recette la recette à tenter de réaliser
     * @return true si la potion est réussie, false sinon
     * @throws IllegalArgumentException si recette est null
     */
    public boolean fairePotion(Recette recette)
    {
        if (recette == null)
            throw new IllegalArgumentException("La recette ne peut pas être null");

        boolean estReussi = false;
        double tauxExperience = this.niveau * 0.05;
        double tauxEchec = (recette.getDifficulte() * 0.25) - tauxExperience;

        if (tauxEchec < 0)
            tauxEchec = 0;

        Random random = new Random();
        double jetSuccess = ((double) random.nextInt(1, 101))/100;

        if (jetSuccess >= tauxEchec)
        {
            int nbExperience = recette.getPointExperience();
            this.ajouterExperience(nbExperience);
            estReussi = true;
        }

        return estReussi;
    }

    private void ajouterExperience(int experience)
    {
        this.setExperience(this.getExperience() + experience);

        if(this.getExperience() >= EXPERIENCE_POUR_NIVEAU_SUIVANT)
        {
            this.setNiveau(this.getNiveau() + 1);
            this.setExperience(this.getExperience() - EXPERIENCE_POUR_NIVEAU_SUIVANT);
            System.out.println("Vous êtes maintenant un alchimiste de niveau " + this.getNiveau());
        }
    }

}
