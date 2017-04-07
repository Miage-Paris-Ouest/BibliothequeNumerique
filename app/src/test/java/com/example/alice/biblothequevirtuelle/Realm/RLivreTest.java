package com.example.alice.biblothequevirtuelle.Realm;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kiki on 07/04/2017.
 */
public class RLivreTest {
    RLivre livreTest = new RLivre();
    Type typeTest = new Type();
    Type typeTest2 = new Type("nom2");
    Type typeTest3 = new Type();

    @Test
    public void testRLivreType() throws Exception {
        livreTest.setEan("ean");
        livreTest.setTitre("titre");
        livreTest.setAuteur("auteur");
        livreTest.setEditeur("editeur");
        livreTest.setDatePub("datePub");
        livreTest.setResume("resume");
        livreTest.setLangue("langue");
        livreTest.setType(typeTest);
        livreTest.setCategorie("categorie");

        assertEquals(livreTest.getEan(),"ean");
        assertEquals(livreTest.getTitre(),"titre");
        assertEquals(livreTest.getAuteur(),"auteur");
        assertEquals(livreTest.getEditeur(),"editeur");
        assertEquals(livreTest.getDatePub(),"datePub");
        assertEquals(livreTest.getResume(),"resume");
        assertEquals(livreTest.getLangue(),"langue");
        assertEquals(livreTest.getType(),typeTest);
        assertEquals(livreTest.getCategorie(),"categorie");

        RLivre livreTest2 = new RLivre("ean2","titre2","auteur2","editeur2","datePub2","resume2","langue2",typeTest2,"categorie2");
        typeTest3.setNom(typeTest2.getNom());
        RLivre livreTest3 = new RLivre("ean3","titre3","auteur3","editeur3","datePub3","resume3","langue3",typeTest3);
    }
}