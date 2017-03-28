package com.example.alice.biblothequevirtuelle.Data;

import com.orm.SugarRecord;

/**
 * Created by alice on 21/03/2017.
 */

/**
 * TODO : Gérer le fait que le type et la catégorie sont des listes déroulantes
 *
 */

public class Livre extends SugarRecord
{
    // pas besoin de variable id, elle est héritée directement de la classe SugarRecord
    private String titre; // obligatoire
    private String ean; // obligatoire
    private String auteur; // obligatoire
    private String editeur;
    private String datePub;
    private String langue;
    private String resume;
    private String type;
    private String categorie;

    public Livre() {
    }

    public Livre(String titre, String ean, String type, String auteur, String editeur, String categorie, String datePub, String langue, String resume ) {
        this.titre = titre;
        this.ean = ean;
        this.type = type;
        this.auteur = auteur;
        this.editeur = editeur;
        this.categorie = categorie;
        this.datePub = datePub;
        this.langue = langue;
        this.resume = resume;

    }

    public Livre(String titre, String ean, String auteur, String editeur, String datePub, String langue, String resume) {
        this.titre = titre;
        this.ean = ean;
        this.auteur = auteur;
        this.editeur = editeur;
        this.datePub = datePub;
        this.langue = langue;
        this.resume = resume;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDatePub() {
        return datePub;
    }

    public void setDatePub(String datePub) {
        this.datePub = datePub;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "titre='" + titre + '\'' +
                ", ean=" + ean +
                ", type=" + type +
                ", auteur='" + auteur + '\'' +
                ", editeur='" + editeur + '\'' +
                ", categorie='" + categorie + '\'' +
                ", datePub=" + datePub +
                ", langue='" + langue + '\'' +
                ", resume='" + resume + '\'' +
                '}';
    }


}
