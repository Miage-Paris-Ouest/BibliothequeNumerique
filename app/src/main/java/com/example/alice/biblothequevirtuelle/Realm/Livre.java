package com.example.alice.biblothequevirtuelle.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by alice on 03/04/2017.
 */

/*
TODO Modifier gestion statut et type
 */
@RealmClass
public class Livre extends RealmObject
{
    @PrimaryKey
    private int id;
    private String ean;
    private String titre;
    private String auteur;
    private String editeur;
    private String datePub;
    private String resume;
    private String langue;
    private Type type; // grand format, poche, BD, ....
    private String categorie; // à définir
    private int statut; // 0 : pas lu, 1 : en cours, 2 : lu
    private boolean pret; // false : non, true : oui

    public Livre(int id, String ean, String titre, String auteur, String editeur, String datePub, String resume, String langue, Type type, String categorie, int statut, boolean pret) {
        this.id = id;
        this.ean = ean;
        this.titre = titre;
        this.auteur = auteur;
        this.editeur = editeur;
        this.datePub = datePub;
        this.resume = resume;
        this.langue = langue;
        this.type = type;
        this.categorie = categorie;
        this.statut = statut;
        this.pret = pret;
    }

    public Livre(int id, String ean, String titre, String auteur, String editeur, String datePub, String resume, String langue) {
        this.id = id;
        this.ean = ean;
        this.titre = titre;
        this.auteur = auteur;
        this.editeur = editeur;
        this.datePub = datePub;
        this.resume = resume;
        this.langue = langue;
    }

    public Livre() {
    }

    public Livre(String ean, String titre, String auteur, String editeur, String datePub, String resume, String langue, Type type) {
        this.ean = ean;
        this.titre = titre;
        this.auteur = auteur;
        this.editeur = editeur;
        this.datePub = datePub;
        this.resume = resume;
        this.langue = langue;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public boolean isPret() {
        return pret;
    }

    public void setPret(boolean pret) {
        this.pret = pret;
    }
}
