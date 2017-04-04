package com.example.alice.biblothequevirtuelle.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alice on 21/03/2017.
 */

public class Type extends RealmObject {

    @PrimaryKey
    private String nom;

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
}
