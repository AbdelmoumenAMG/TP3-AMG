package tests;

/**
 * Author : Abdelmoumen AMG
 * Ordre de conception : 2e
 */

import logique.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest
{
    private Ingredient ingredient;

    @BeforeEach
    void setUp()
    {
        ingredient = new Ingredient("Poudre bleue", 25);
    }

    // --- Tests du constructeur valides ---

    @Test
    void testConstructeurValide()
    {
        assertEquals("Poudre bleue", ingredient.getNom());
        assertEquals(25, ingredient.getPrix());
    }

    @Test
    void testNomLongueurMinimale()
    {
        Ingredient ing = new Ingredient("Herbes", 10);
        assertEquals("Herbes", ing.getNom());
    }

    @Test
    void testPrixMinimal()
    {
        Ingredient ing = new Ingredient("Mandagore", 1);
        assertEquals(1, ing.getPrix());
    }

    // --- Tests du constructeur invalides ---

    @Test
    void testNomNullLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(null, 25));
    }

    @Test
    void testNomTropCourtLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Ingredient("Herb", 25));
    }

    @Test
    void testNomExactement5CaracteresLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Ingredient("Herbe", 25));
    }

    @Test
    void testPrixZeroLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Ingredient("Poudre bleue", 0));
    }

    @Test
    void testPrixNegatifLanceException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Ingredient("Poudre bleue", -5));
    }

    // --- Tests des getters ---

    @Test
    void testGetNom()
    {
        assertEquals("Poudre bleue", ingredient.getNom());
    }

    @Test
    void testGetPrix()
    {
        assertEquals(25, ingredient.getPrix());
    }

    // --- Test toString ---

    @Test
    void testToString()
    {
        String resultat = ingredient.toString();
        assertTrue(resultat.contains("Poudre bleue"));
        assertTrue(resultat.contains("25"));
    }
}
