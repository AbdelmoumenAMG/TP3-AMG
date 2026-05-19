package tests;

/**
 * Author : Abdelmoumen AMG
 * Ordre de conception : 1er
 */

import logique.ResultatExperience;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResultatExperienceTest
{
    private ResultatExperience resultat;

    @BeforeEach
    void setUp()
    {
        resultat = new ResultatExperience();
    }

    @Test
    void testConstructeurValeursParDefaut()
    {
        assertFalse(resultat.getExiste());
        assertFalse(resultat.getSuccess());
    }

    @Test
    void testSetExisteTrue()
    {
        resultat.setExiste(true);
        assertTrue(resultat.getExiste());
    }

    @Test
    void testSetExisteFalse()
    {
        resultat.setExiste(true);
        resultat.setExiste(false);
        assertFalse(resultat.getExiste());
    }

    @Test
    void testSetSuccessTrue()
    {
        resultat.setSuccess(true);
        assertTrue(resultat.getSuccess());
    }

    @Test
    void testSetSuccessFalse()
    {
        resultat.setSuccess(true);
        resultat.setSuccess(false);
        assertFalse(resultat.getSuccess());
    }
}
