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
    private String intitule;

    public Statut(String intitule) {
        this.intitule = intitule;
    }

    public Statut() {
    }


    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
}
