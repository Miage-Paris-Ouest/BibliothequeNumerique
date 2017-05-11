package com.example.alice.biblothequevirtuelle.Realm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Kiki on 07/04/2017.
 */
public class LivreTest {
    Livre livreTest = new Livre();
    Livre livreTest1 = new Livre();
    Type typeTest = new Type();
    Type typeTest2 = new Type("nom2");
    Type typeTest3 = new Type(3,"nom3");

    @Test
    public void testLivre() throws Exception {
        typeTest.setId(0);
        typeTest.setNom("nom0");

        livreTest.setId(0);
        livreTest.setEan("ean");
        livreTest.setTitre("titre");
        livreTest.setAuteur("auteur");
        livreTest.setEditeur("editeur");
        livreTest.setDatePub("datePub");
        livreTest.setResume("resume");
        livreTest.setLangue("langue");
        livreTest.setType(typeTest);
        livreTest.setCategorie("categorie");
        livreTest.setStatut(0); // pas lu
        livreTest.setPret(false); // pas preté

        livreTest1 = new Livre(1,"ean1","titre1","auteur1","editeur1","datePub1","resume1","langue1");

        assertEquals(livreTest.getEan(),"ean");
        assertEquals(livreTest.getTitre(),"titre");
        assertEquals(livreTest.getAuteur(),"auteur");
        assertEquals(livreTest.getEditeur(),"editeur");
        assertEquals(livreTest.getDatePub(),"datePub");
        assertEquals(livreTest.getResume(),"resume");
        assertEquals(livreTest.getLangue(),"langue");
        assertEquals(livreTest.getType(),typeTest);
        assertEquals(livreTest.getCategorie(),"categorie");
        assertEquals(livreTest.getStatut(),0);
        assertEquals(livreTest.isPret(),false);

        assertNotEquals(livreTest.getId(),livreTest1.getId());

        Livre livreTest2 = new Livre(2,"ean2","titre2","auteur2","editeur2","datePub2","resume2","langue2",typeTest2,"categorie2", 0, false);
        typeTest3.setNom(typeTest2.getNom());
        Livre livreTest3 = new Livre("ean3","titre3","auteur3","editeur3","datePub3","resume3","langue3",typeTest3);

        assertNotEquals(typeTest.getId(),typeTest3.getId());
    }
}
