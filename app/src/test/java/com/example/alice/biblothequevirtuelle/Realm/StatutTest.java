package com.example.alice.biblothequevirtuelle.Realm;

import org.junit.Test;

/**
 * Created by Kiki on 07/04/2017.
 */
public class StatutTest {
    Statut statutTest = new Statut();

    @Test
    public void testStatut() throws Exception {
        statutTest.setId(1);
        statutTest.setIntitule("intitule");
        Statut statutTest2 = new Statut(statutTest.getId(),statutTest.getIntitule());
    }
}