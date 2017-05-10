package com.example.alice.biblothequevirtuelle.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by alice on 21/03/2017.
 */

@RealmClass
public class Type extends RealmObject {

    @PrimaryKey
    private int id;
    private String nom;

    public Type(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Type(String nom) {
        this.nom = nom;
    }

    public Type() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
