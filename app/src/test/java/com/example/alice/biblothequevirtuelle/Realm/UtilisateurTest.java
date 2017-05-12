package com.example.alice.biblothequevirtuelle.Realm;

import org.junit.Test;

import io.realm.RealmList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Kiki on 11/05/2017.
 */
public class UtilisateurTest {
    Utilisateur util0 = new Utilisateur();

    Type typeTest2 = new Type("nom2");
    Type typeTest3 = new Type(3,"nom3");

    Livre livreTest1 = new Livre(1,"ean1","titre1","auteur1","editeur1","datePub1","resume1","langue1");
    Livre livreTest2 = new Livre(2,"ean2","titre2","auteur2","editeur2","datePub2","resume2","langue2",typeTest2,"categorie2", 0, false);
    Livre livreTest3 = new Livre("ean3","titre3","auteur3","editeur3","datePub3","resume3","langue3",typeTest3);

    RealmList<Livre> listeLivre0 = new RealmList<>();
    RealmList<Livre> listeLivre1 = new RealmList<>();
    RealmList<Livre> listeLivre2 = new RealmList<>();

    RealmList<CollectionP> listeCollection0 = new RealmList<>();
    RealmList<CollectionP> listeCollection1 = new RealmList<>();

    CollectionP collection0 = new CollectionP();
    CollectionP collection1 = new CollectionP("nom1");
    CollectionP collection2 = new CollectionP(2,"nom2");
    CollectionP collection3 = new CollectionP(3,"nom3",listeLivre0);
    CollectionP collection4 = new CollectionP("nom4",listeLivre2);

    @Test
    public void testUtil() throws Exception {
        listeLivre0.add(livreTest1);
        listeLivre0.add(livreTest2);

        collection0.setId(0);
        collection0.setNom("nom0");
        collection0.setLivres(listeLivre1);

        listeCollection0.add(collection0);
        listeCollection0.add(collection1);
        listeCollection0.add(collection2);

        util0.setFirebaseID("idFirebase0");
        util0.setMail("Mail0");
        util0.setPseudo("Pseudo0");
        util0.setBibliotheque(listeLivre0);
        util0.setWhishlist(listeLivre1);
        util0.setListeCollections(listeCollection0);
        util0.setDejaConnecte(true);
        util0.ajouterLivreBibliotheque(livreTest3);
        util0.supprimerLivreBibliotheque(livreTest1);

        listeLivre1.add(livreTest3);

        listeCollection1.add(collection3);
        listeCollection1.add(collection4);

        Utilisateur util1 = new Utilisateur("Mail1","idFirebase1","Pseudo1",listeLivre1,listeCollection1,false);
        Utilisateur util2 = new Utilisateur("Mail2","idFirebase2");
        Utilisateur util3 = new Utilisateur("idFirebase3","Mail3","Pseudo3",listeLivre0,listeLivre1,listeCollection1,true);

        util1.ajouterLivreWhishList(livreTest2);
        util1.supprimerLivreWhishList(livreTest1);
        util2.creerCollection("listeCollection2");
        util1.supprimerCollection(collection3);


        assertNotEquals(collection0.getId(),collection2.getId());
        assertNotEquals(collection0.getNom(),collection1.getNom());
        assertNotEquals(collection0.getLivres(),collection3.getLivres());

        assertNotEquals(util0.getMail(),util1.getMail());
        assertNotEquals(util0.getFirebaseID(),util2.getFirebaseID());
        assertNotEquals(util0.getPseudo(),util1.getPseudo());
        assertNotEquals(util0.getBibliotheque(),util1.getBibliotheque());
        assertEquals(util0.getWhishlist(),listeLivre1);
        assertEquals(util0.getListeCollections(),listeCollection0);
        assertEquals(util0.isDejaConnecte(),true);
    }
}