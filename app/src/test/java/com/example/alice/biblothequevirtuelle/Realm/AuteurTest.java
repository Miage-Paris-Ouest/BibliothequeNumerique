package com.example.alice.biblothequevirtuelle.Realm;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kiki on 07/04/2017.
 */
public class AuteurTest {
    Auteur auteurTest = new Auteur();
    Auteur auteurTest2 = new Auteur("id2","pseudo2");

    @Test
    public void testAuteur() throws Exception {
        auteurTest.setId("id");
        auteurTest.setPseudo("pseudo");
        assertNotEquals(auteurTest.getId(),auteurTest2.getId());
        assertNotEquals(auteurTest.getPseudo(),auteurTest2.getPseudo());
    }
}