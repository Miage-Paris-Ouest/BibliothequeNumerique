package com.example.alice.biblothequevirtuelle.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alice on 03/04/2017.
 */

public class Auteur extends RealmObject
{
    @PrimaryKey
    private String id;
    private String pseudo;

    public Auteur() {
    }

    public Auteur(String id, String pseudo) {
        this.id = id;
        this.pseudo = pseudo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
