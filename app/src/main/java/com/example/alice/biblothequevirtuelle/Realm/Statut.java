package com.example.alice.biblothequevirtuelle.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by alice on 03/04/2017.
 */

@RealmClass
public class Statut extends RealmObject
{
    @PrimaryKey
    private long id;
    private String intitule;

    public Statut(long id, String intitule) {
        this.id = id;
        this.intitule = intitule;
    }

    public Statut() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
}
