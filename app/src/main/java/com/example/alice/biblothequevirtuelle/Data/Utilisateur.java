package com.example.alice.biblothequevirtuelle.Data;

import com.orm.SugarRecord;

/**
 * Created by austepha on 23/03/2017.
 */

public class Utilisateur extends SugarRecord {

    private String nom;
    private String prenom;
    private String motDePasse;
    private String pseudo;
    private String email;

    public Utilisateur(String nom, String prenom, String motDePasse, String pseudo, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = motDePasse;
        this.pseudo = pseudo;
        this.email = email;
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

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
