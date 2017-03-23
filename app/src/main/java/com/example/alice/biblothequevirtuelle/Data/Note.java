package com.example.alice.biblothequevirtuelle.Data;

import com.orm.SugarRecord;

/**
 * Created by austepha on 23/03/2017.
 */

public class Note extends SugarRecord {

    private int idLivre;
    private String avis;
    private int note; //note qui va de 1 à 5

    public Note(int idLivre, String avis, int note) {
        this.idLivre = idLivre;
        this.avis = avis;
        this.note = note;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }


    @Override
    public String toString() {
        return "Note{" +
                "idLivre=" + idLivre +
                ", avis='" + avis + '\'' +
                ", note=" + note +
                '}';
    }
}
