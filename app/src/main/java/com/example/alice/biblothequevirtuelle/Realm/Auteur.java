package com.example.alice.biblothequevirtuelle.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by alice on 03/04/2017.
 */

@RealmClass
public class Auteur extends RealmObject
{
    @PrimaryKey
    private String pseudo;

    public Auteur() {
    }

    public Auteur(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
