package com.example.alice.biblothequevirtuelle.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alice on 11/05/2017.
 */

public class CollectionP extends RealmObject
{
    @PrimaryKey
    private int id;
    private String nom;
    private RealmList<Livre> livres;


    public CollectionP() {
    }

    public CollectionP(String nom) {
        this.nom = nom;
        this.livres = new RealmList<>();
    }

    public CollectionP(String nom, RealmList<Livre> livres) {
        this.nom = nom;
        this.livres = livres;
    }

    public CollectionP(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.livres = new RealmList<>();
    }

    public CollectionP(int id, String nom, RealmList<Livre> livres) {
        this.id = id;
        this.nom = nom;
        this.livres = livres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public RealmList<Livre> getLivres() {
        return livres;
    }

    public void setLivres(RealmList<Livre> livres) {
        this.livres = livres;
    }
}
