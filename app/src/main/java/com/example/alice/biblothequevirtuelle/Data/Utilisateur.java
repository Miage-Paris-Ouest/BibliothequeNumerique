package com.example.alice.biblothequevirtuelle.Data;

import com.orm.SugarRecord;

/**
 * Created by austepha on 23/03/2017.
 */

public class Utilisateur extends SugarRecord {

    private String nom;
    private String prenom;
    private String motDePasse;

    public Utilisateur(String nom, String prenom, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }


    @Override
    public String toString() {
        return "Utilisateur{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                '}';
    }
}
