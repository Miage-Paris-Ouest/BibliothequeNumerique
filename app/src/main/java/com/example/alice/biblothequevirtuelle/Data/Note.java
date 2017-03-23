package com.example.alice.biblothequevirtuelle.Data;

import com.orm.SugarRecord;

/**
 * Created by austepha on 23/03/2017.
 */

public class Note extends SugarRecord {

    private int idLivre;
    private int idUtilisateur;
    private String avis;
    private int note; //note qui va de 1 à 5

    public Note(int idLivre, int idUtilisateur, String avis, int note) {
        this.idLivre = idLivre;
        this.idUtilisateur = idUtilisateur;
        this.avis = avis;
        this.note = note;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
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
                ", idUtilisateur=" + idUtilisateur +
                ", avis='" + avis + '\'' +
                ", note=" + note +
                '}';
    }
}
