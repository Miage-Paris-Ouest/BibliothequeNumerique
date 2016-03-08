package com.example.alpottie.myapplication.Donnees;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alice on 08/03/2016.
 */

// Les objets Parcelable peuvent être passé entre les activités = sérialisation
public class Auteur implements Parcelable
{
    private long id;
    private String nom;
    private String prenom;

    public Auteur(long id, String prenom, String nom) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
    }

    private Auteur(Parcel in)
    {
        super();
        this.id = in.readLong();
        this.nom = in.readString();
        this.prenom = in.readString();
    }

    public static final Creator<Auteur> CREATOR = new Creator<Auteur>() {
        @Override
        public Auteur createFromParcel(Parcel in) {
            return new Auteur(in);
        }

        @Override
        public Auteur[] newArray(int size) {
            return new Auteur[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(getId());
        dest.writeString(getNom());
        dest.writeString(getPrenom());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auteur auteur = (Auteur) o;

        if (id != auteur.id) return false;
        if (!nom.equals(auteur.nom)) return false;
        return prenom.equals(auteur.prenom);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + nom.hashCode();
        result = 31 * result + prenom.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Auteur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
