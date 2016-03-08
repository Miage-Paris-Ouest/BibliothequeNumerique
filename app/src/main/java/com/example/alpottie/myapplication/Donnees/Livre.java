package com.example.alpottie.myapplication.Donnees;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alice on 08/03/2016.
 */
// Les objets Parcelable peuvent être passé entre les activités = sérialisation
public class Livre implements Parcelable
{
    private long id;
    private String ean;
    private String titre;
    private Auteur auteur;

   public Livre(long id, String ean, String titre, Auteur auteur) {
        this.id = id;
        this.ean = ean;
        this.titre = titre;
        this.auteur = auteur;
    }

    protected Livre(Parcel in) {
        id = in.readLong();
        ean = in.readString();
        titre = in.readString();
        auteur = in.readParcelable(Auteur.class.getClassLoader());
    }

    public Auteur getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public static final Creator<Livre> CREATOR = new Creator<Livre>() {
        @Override
        public Livre createFromParcel(Parcel in) {
            return new Livre(in);
        }

        @Override
        public Livre[] newArray(int size) {
            return new Livre[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(ean);
        dest.writeString(titre);
        dest.writeParcelable(auteur, flags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Livre livre = (Livre) o;

        if (id != livre.id) return false;
        if (!ean.equals(livre.ean)) return false;
        if (!titre.equals(livre.titre)) return false;
        return auteur.equals(livre.auteur);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + ean.hashCode();
        result = 31 * result + titre.hashCode();
        result = 31 * result + auteur.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", ean='" + ean + '\'' +
                ", titre='" + titre + '\'' +
                ", auteur=" + auteur +
                '}';
    }
}
