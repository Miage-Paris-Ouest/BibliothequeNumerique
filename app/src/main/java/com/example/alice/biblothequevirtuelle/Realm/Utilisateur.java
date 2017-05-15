package com.example.alice.biblothequevirtuelle.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Utilisateur extends RealmObject
{
    @PrimaryKey
    private String firebaseID;
    private String mail;
    private String pseudo;
    private RealmList<Livre> bibliotheque;
    private RealmList<Livre> whishlist;
    private RealmList<CollectionP> listeCollections;
    private boolean dejaConnecte;

    public Utilisateur() {
    }

    public Utilisateur(String mail, String firebaseID) {
        this.mail = mail;
        this.firebaseID = firebaseID;
        this.dejaConnecte = false;
        this.bibliotheque = new RealmList<>();
        this.whishlist = new RealmList<>();
        this.listeCollections = new RealmList<>();
        creerCollection("Ma première collection");
        this.pseudo="";
    }

    public Utilisateur(String mail, String firebaseID, String pseudo, RealmList<Livre> wishlist, RealmList<CollectionP> listeCollections, boolean dejaConnecte) {
        this.mail = mail;
        this.firebaseID = firebaseID;
        this.pseudo = pseudo;
        this.whishlist = wishlist;
        this.listeCollections = listeCollections;
        this.dejaConnecte = dejaConnecte;
    }

    public Utilisateur(String firebaseID, String mail, String pseudo, RealmList<Livre> bibliotheque, RealmList<Livre> whishlist, RealmList<CollectionP> listeCollections, boolean dejaConnecte) {
        this.firebaseID = firebaseID;
        this.mail = mail;
        this.pseudo = pseudo;
        this.bibliotheque = bibliotheque;
        this.whishlist = whishlist;
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

    public RealmList<Livre> getBibliotheque() {
        return bibliotheque;
    }

    public void setBibliotheque(RealmList<Livre> bibliotheque) {
        this.bibliotheque = bibliotheque;
    }

    public void ajouterLivreBibliotheque(Livre l)
    {
        this.bibliotheque.add(l);
    }

    public void supprimerLivreBibliotheque(Livre l)
    {
        this.bibliotheque.remove(l);
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

    public void supprimerCollection(CollectionP col) {
        this.listeCollections.remove(col);
    }
}
