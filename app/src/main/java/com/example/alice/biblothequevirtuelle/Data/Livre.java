package com.example.alice.biblothequevirtuelle.Data;

import com.orm.SugarRecord;

/**
 * Created by alice on 21/03/2017.
 */

public class Livre extends SugarRecord
{
    // pas besoin de variable id, elle est héritée directement de la classe SugarRecord
    private String titre;
    private int ean;
    private int type;

    public Livre(String titre, int ean, int type) {
        this.titre = titre;
        this.ean = ean;
        this.type = type;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getEan() {
        return ean;
    }

    public void setEan(int ean) {
        this.ean = ean;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "titre='" + titre + '\'' +
                ", ean=" + ean +
                ", type=" + type +
                '}';
    }
}
