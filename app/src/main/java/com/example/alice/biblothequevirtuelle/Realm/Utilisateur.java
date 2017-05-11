package com.example.alice.biblothequevirtuelle.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alice on 11/05/2017.
 */

public class Utilisateur extends RealmObject
{
    @PrimaryKey
    private String firebaseID;
    private String mail;
    private String pseudo;
    private RealmList<Livre> whishlist;
    private RealmList<CollectionP> listeCollections;
    private boolean dejaConnecte;

    public Utilisateur() {
    }

    public Utilisateur(String mail, String firebaseID) {
        this.mail = mail;
        this.firebaseID = firebaseID;
        this.dejaConnecte = false;
        this.whishlist = new RealmList<>();
        this.listeCollections = new RealmList<>();
    }

    public Utilisateur(String mail, String firebaseID, String pseudo, RealmList<Livre> wishlist, RealmList<CollectionP> listeCollections, boolean dejaConnecte) {
        this.mail = mail;
        this.firebaseID = firebaseID;
        this.pseudo = pseudo;
        this.whishlist = wishlist;
        this.listeCollections = listeCollections;
        this.dejaConnecte = dejaConnecte;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFirebaseID() {
        return firebaseID;
    }

    public void setFirebaseID(String firebaseID) {
        this.firebaseID = firebaseID;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public RealmList<Livre> getWhishlist() {
        return whishlist;
    }

    public void setWhishlist(RealmList<Livre> whishlist) {
        this.whishlist = whishlist;
    }

    public RealmList<CollectionP> getListeCollections() {
        return listeCollections;
    }

    public void setListeCollections(RealmList<CollectionP> listeCollections) {
        this.listeCollections = listeCollections;
    }

    public boolean isDejaConnecte() {
        return dejaConnecte;
    }

    public void setDejaConnecte(boolean dejaConnecte) {
        this.dejaConnecte = dejaConnecte;
    }

    public void ajouterLivreWhishList(Livre l)
    {
        this.whishlist.add(l);
    }

    public void supprimerLivreWhishList(Livre l)
    {
        this.whishlist.remove(l);
    }

    public void creerCollection(String nom)
    {
        this.listeCollections.add(new CollectionP(nom));
    }

    public void supprimerCollection(int id)
    {
        CollectionP col = this.listeCollections.where().equalTo("id", id).findFirst();
        this.listeCollections.remove(col);
    }
}
