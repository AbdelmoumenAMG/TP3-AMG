package tests;

/**
 * Author : Abdelmoumen AMG
 * Ordre de conception : 4e
 */

import logique.Alchimiste;
import logique.Ingredient;
import logique.Recette;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlchimisteTest
{
    private Alchimiste alchimiste;
    private Recette recetteFacile;
    private Recette recetteDifficile;

    @BeforeEach
    void setUp()
    {
        // Niveau 1 par défaut (utilisé pour les tests d'échec garanti)
        alchimiste = new Alchimiste("Etudiant");

        Ingredient ing1 = new Ingredient("Poudre bleue", 25);
        Ingredient ing2 = new Ingredient("Poudre rose", 15);
        Ingredient ing3 = new Ingredient("Mandagore", 250);

        Ingredient ing4 = new Ingredient("Eau du fleuve", 75);
        Ingredient ing5 = new Ingredient("Baies de gui", 55);
        Ingredient ing6 = new Ingredient("Brins de Val", 200);

        // Difficulté 1 + niveau 5 → tauxEchec = 0 → réussite garantie
        recetteFacile = new Recette(ing1, ing2, ing3, "Philtre de paix", 1, 500);

        // Difficulté 5 + niveau 1 → tauxEchec = 1.20 > max jetSuccess (1.00) → échec garanti
        recetteDifficile = new Recette(ing4, ing5, ing6, "Potion puissante", 5, 400);
    }

    // --- Tests du constructeur valides ---

    @Test
    void testConstructeurValide()
    {
        assertEquals("Etudiant", alchimiste.getNom());
        assertEquals(1, alchimiste.getNiveau());
        assertEquals(0, alchimiste.getExperience());
    }

    @Test
    void testConstructeurAvecNiveau()
    {
        Alchimiste alch = new Alchimiste("Etudiant", 3);
        assertEquals(3, alch.getNiveau());
    }

    @Test
    void testNomLongueurMinimale()
    {
        Alchimiste alch = new Alchimiste("Zoltan");
        assertEquals("Zoltan", alch.getNom());
    }

    // --- Tests du constructeur invalides ---

    @Test
    void testNomNullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Alchimiste(null));
    }

    @Test
    void testNomTropCourtLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Alchimiste("Alch"));
    }

    @Test
    void testNomExactement5CaracteresLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Alchimiste("Merli"));
    }

    // --- Tests fairePotion ---

    @Test
    void testFairePotionNullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> alchimiste.fairePotion(null));
    }

    @Test
    void testFairePotionReussiteGarantie()
    {
        // niveau 5 + difficulte 1 → tauxEchec = 1*0.25 - 5*0.05 = 0 → toujours réussi
        Alchimiste alchNiveau5 = new Alchimiste("Etudiant", 5);
        boolean resultat = alchNiveau5.fairePotion(recetteFacile);
        assertTrue(resultat);
    }

    @Test
    void testFairePotionEchecGaranti()
    {
        // niveau 1 + difficulte 5 → tauxEchec = 5*0.25 - 1*0.05 = 1.20 > max jetSuccess (1.00) → toujours échoue
        boolean resultat = alchimiste.fairePotion(recetteDifficile);
        assertFalse(resultat);
    }

    @Test
    void testFairePotionAjouteExperience()
    {
        Alchimiste alchNiveau5 = new Alchimiste("Etudiant", 5);
        alchNiveau5.fairePotion(recetteFacile);
        // 500 XP = seuil → monte de niveau 5 à 6, XP remis à 0
        assertEquals(6, alchNiveau5.getNiveau());
        assertEquals(0, alchNiveau5.getExperience());
    }

    @Test
    void testMonteDeNiveauApresExperienceSuffisante()
    {
        Alchimiste alchNiveau5 = new Alchimiste("Etudiant", 5);
        alchNiveau5.fairePotion(recetteFacile);
        assertEquals(6, alchNiveau5.getNiveau());
        assertEquals(0, alchNiveau5.getExperience());
    }

    @Test
    void testFairePotionEchecNAjoutePasExperience()
    {
        alchimiste.fairePotion(recetteDifficile);
        assertEquals(0, alchimiste.getExperience());
        assertEquals(1, alchimiste.getNiveau());
    }
}
