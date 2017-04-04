package com.example.alice.biblothequevirtuelle.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alice on 03/04/2017.
 */

public class RLivre extends RealmObject
{
    @PrimaryKey
    private String ean;
    private String titre;
    private String auteur;
    private String editeur;
    private String datePub;
    private String resume;
    private String langue;
    private Type type; // grand format, poche, BD, ....
    private String categorie; // à définir
    //    private Statut statut; // acheté, lu, en cours, ...

    public RLivre(String ean, String titre, String auteur, String editeur, String datePub, String resume, String langue, Type type, String categorie) {
        this.ean = ean;
        this.titre = titre;
        this.auteur = auteur;
        this.editeur = editeur;
        this.datePub = datePub;
        this.resume = resume;
        this.langue = langue;
        this.type = type;
        this.categorie = categorie;
    }

    public RLivre() {
    }

    public RLivre(String ean, String titre, String auteur, String editeur, String datePub, String resume, String langue, Type type) {
        this.ean = ean;
        this.titre = titre;
        this.auteur = auteur;
        this.editeur = editeur;
        this.datePub = datePub;
        this.resume = resume;
        this.langue = langue;
        this.type = type;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getDatePub() {
        return datePub;
    }

    public void setDatePub(String datePub) {
        this.datePub = datePub;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
/*
    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
*/
    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
