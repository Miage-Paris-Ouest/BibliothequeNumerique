package com.example.alice.biblothequevirtuelle.Realm;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kiki on 07/04/2017.
 */
public class AuteurTest {
    Auteur auteurTest = new Auteur();
    Auteur auteurTest2 = new Auteur("pseudo2");

    @Test
    public void testAuteur() throws Exception {
        auteurTest.setPseudo("pseudo");
        assertNotEquals(auteurTest.getPseudo(),auteurTest2.getPseudo());
    }
}