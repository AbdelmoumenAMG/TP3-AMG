package tests;

/**
 * Author : Abdelmoumen AMG
 * Ordre de conception : 3e
 */

import logique.Ingredient;
import logique.Recette;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecetteTest
{
    private Ingredient ing1;
    private Ingredient ing2;
    private Ingredient ing3;
    private Recette recette;

    @BeforeEach
    void setUp()
    {
        ing1 = new Ingredient("Poudre bleue", 25);
        ing2 = new Ingredient("Poudre rose", 15);
        ing3 = new Ingredient("Mandagore", 250);
        recette = new Recette(ing1, ing2, ing3, "Philtre de paix", 3, 250);
    }

    // --- Tests du constructeur valides ---

    @Test
    void testConstructeurValide()
    {
        assertEquals("Philtre de paix", recette.getNom());
        assertEquals(3, recette.getDifficulte());
        assertEquals(250, recette.getPointExperience());
    }

    @Test
    void testDifficulteMinimale()
    {
        Recette r = new Recette(ing1, ing2, ing3, "Philtre de paix", 1, 100);
        assertEquals(1, r.getDifficulte());
    }

    @Test
    void testDifficulteMaximale()
    {
        Recette r = new Recette(ing1, ing2, ing3, "Philtre de paix", 5, 100);
        assertEquals(5, r.getDifficulte());
    }

    @Test
    void testNomLongueurMinimale()
    {
        Recette r = new Recette(ing1, ing2, ing3, "Potion ABC", 1, 100);
        assertEquals("Potion ABC", r.getNom());
    }

    @Test
    void testPointExperienceMinimal()
    {
        Recette r = new Recette(ing1, ing2, ing3, "Philtre de paix", 1, 1);
        assertEquals(1, r.getPointExperience());
    }

    // --- Tests du constructeur invalides ---

    @Test
    void testIngredient1NullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Recette(null, ing2, ing3, "Philtre de paix", 3, 250));
    }

    @Test
    void testIngredient2NullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Recette(ing1, null, ing3, "Philtre de paix", 3, 250));
    }

    @Test
    void testIngredient3NullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Recette(ing1, ing2, null, "Philtre de paix", 3, 250));
    }

    @Test
    void testIngredientsDoublonsIng1Ing2LanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Recette(ing1, ing1, ing3, "Philtre de paix", 3, 250));
    }

    @Test
    void testIngredientsDoublonsIng1Ing3LanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Recette(ing1, ing2, ing1, "Philtre de paix", 3, 250));
    }

    @Test
    void testIngredientsDoublonsIng2Ing3LanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Recette(ing1, ing2, ing2, "Philtre de paix", 3, 250));
    }

    @Test
    void testNomNullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Recette(ing1, ing2, ing3, null, 3, 250));
    }

    @Test
    void testNomTropCourtLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Recette(ing1, ing2, ing3, "Potion AB", 3, 250));
    }

    @Test
    void testDifficulteZeroLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Recette(ing1, ing2, ing3, "Philtre de paix", 0, 250));
    }

    @Test
    void testDifficulte6LanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Recette(ing1, ing2, ing3, "Philtre de paix", 6, 250));
    }

    @Test
    void testDifficulteNegativeLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Recette(ing1, ing2, ing3, "Philtre de paix", -1, 250));
    }

    @Test
    void testPointExperienceZeroLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Recette(ing1, ing2, ing3, "Philtre de paix", 3, 0));
    }

    @Test
    void testPointExperienceNegatifLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Recette(ing1, ing2, ing3, "Philtre de paix", 3, -50));
    }

    // --- Tests obtenirPrix ---

    @Test
    void testObtenirPrix()
    {
        assertEquals(290, recette.obtenirPrix());
    }

    // --- Tests contientIngredient ---

    @Test
    void testContientIngredientExistant()
    {
        assertTrue(recette.contientIngredient("Poudre bleue"));
    }

    @Test
    void testContientIngredientInexistant()
    {
        assertFalse(recette.contientIngredient("Eau de miel"));
    }

    @Test
    void testContientIngredientNullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> recette.contientIngredient(null));
    }

    // --- Test toString ---

    @Test
    void testToString()
    {
        String resultat = recette.toString();
        assertEquals("Philtre de paix|Poudre bleue|Poudre rose|Mandagore|3|250", resultat);
    }
}
