package tests;

/**
 * Author : Abdelmoumen AMG
 * Ordre de conception : 5e
 */

import logique.Alchimiste;
import logique.Ingredient;
import logique.Laboratoire;
import logique.Recette;
import logique.ResultatExperience;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class LaboratoireTest
{
    private Laboratoire laboratoire;
    private Alchimiste alchimiste;
    private String contenuOriginalRecettes;

    @BeforeEach
    void setUp() throws IOException
    {
        contenuOriginalRecettes = new String(Files.readAllBytes(Paths.get("src/recettes.txt")));
        alchimiste = new Alchimiste("Etudiant", 5);
        laboratoire = new Laboratoire(alchimiste);
    }

    @AfterEach
    void tearDown() throws IOException
    {
        Files.write(Paths.get("src/recettes.txt"), contenuOriginalRecettes.getBytes());
    }

    // --- Tests trouverIngredient ---

    @Test
    void testTrouverIngredientExistant()
    {
        Ingredient ing = laboratoire.trouverIngredient("Poudre bleue");
        assertNotNull(ing);
        assertEquals("Poudre bleue", ing.getNom());
    }

    @Test
    void testTrouverIngredientInexistant()
    {
        Ingredient ing = laboratoire.trouverIngredient("Ingrédient fictif");
        assertNull(ing);
    }

    @Test
    void testTrouverIngredientNullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> laboratoire.trouverIngredient(null));
    }

    // --- Tests trouverRecette ---

    @Test
    void testTrouverRecetteExistante()
    {
        Recette recette = laboratoire.trouverRecette("Poudre bleue", "Poudre rose", "Mandagore");
        assertNotNull(recette);
        assertEquals("Philtre de paix", recette.getNom());
    }

    @Test
    void testTrouverRecetteInexistante()
    {
        Recette recette = laboratoire.trouverRecette("Poudre bleue", "Poudre rose", "Liveche");
        assertNull(recette);
    }

    @Test
    void testTrouverRecetteIng1NullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> laboratoire.trouverRecette(null, "Poudre rose", "Mandagore"));
    }

    @Test
    void testTrouverRecetteIng2NullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> laboratoire.trouverRecette("Poudre bleue", null, "Mandagore"));
    }

    @Test
    void testTrouverRecetteIng3NullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> laboratoire.trouverRecette("Poudre bleue", "Poudre rose", null));
    }

    // --- Tests fairePotion ---

    @Test
    void testFairePotionRecetteExistante()
    {
        ResultatExperience resultat = laboratoire.fairePotion("Poudre bleue", "Poudre rose", "Mandagore");
        assertTrue(resultat.getExiste());
    }

    @Test
    void testFairePotionRecetteInexistante()
    {
        ResultatExperience resultat = laboratoire.fairePotion("Poudre bleue", "Poudre rose", "Liveche");
        assertFalse(resultat.getExiste());
        assertFalse(resultat.getSuccess());
    }

    @Test
    void testFairePotionIng1NullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> laboratoire.fairePotion(null, "Poudre rose", "Mandagore"));
    }

    @Test
    void testFairePotionIng2NullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> laboratoire.fairePotion("Poudre bleue", null, "Mandagore"));
    }

    @Test
    void testFairePotionIng3NullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> laboratoire.fairePotion("Poudre bleue", "Poudre rose", null));
    }

    // --- Tests creerNouvellePotion ---

    @Test
    void testCreerNouvellePotionSucces()
    {
        ResultatExperience resultat = laboratoire.creerNouvellePotion(
                "Poudre bleue", "Eau de miel", "Liveche",
                "Potion de vitalite", 2, 150);

        assertFalse(resultat.getExiste());
        assertTrue(resultat.getSuccess());
    }

    @Test
    void testCreerNouvellePotionRecetteDejaExistante()
    {
        ResultatExperience resultat = laboratoire.creerNouvellePotion(
                "Poudre bleue", "Poudre rose", "Mandagore",
                "Philtre de paix", 3, 250);

        assertTrue(resultat.getExiste());
        assertFalse(resultat.getSuccess());
    }

    @Test
    void testCreerNouvellePotionIng1NullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () ->
                laboratoire.creerNouvellePotion(null, "Eau de miel", "Liveche", "Potion test long", 1, 100));
    }

    @Test
    void testCreerNouvellePotionNomNullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () ->
                laboratoire.creerNouvellePotion("Poudre bleue", "Eau de miel", "Liveche", null, 1, 100));
    }

    @Test
    void testCreerNouvellePotionDifficulteInvalideLanceException()
    {
        assertThrows(IllegalArgumentException.class, () ->
                laboratoire.creerNouvellePotion("Poudre bleue", "Eau de miel", "Liveche", "Potion test long", 6, 100));
    }

    @Test
    void testCreerNouvellePotionPointsExperienceInvalidesLanceException()
    {
        assertThrows(IllegalArgumentException.class, () ->
                laboratoire.creerNouvellePotion("Poudre bleue", "Eau de miel", "Liveche", "Potion test long", 1, 0));
    }

    // --- Tests getters ---

    @Test
    void testGetIngredients()
    {
        assertNotNull(laboratoire.getIngredients());
        assertFalse(laboratoire.getIngredients().isEmpty());
    }

    @Test
    void testGetRecettes()
    {
        assertNotNull(laboratoire.getRecettes());
        assertFalse(laboratoire.getRecettes().isEmpty());
    }

    @Test
    void testGetProprietaire()
    {
        assertEquals(alchimiste, laboratoire.getProprietaire());
    }
}
