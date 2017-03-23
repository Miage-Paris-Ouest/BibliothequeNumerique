package com.example.alice.biblothequevirtuelle.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by alice on 21/03/2017.
 */

public class Livre extends SugarRecord implements Parcelable
{
    // pas besoin de variable id, elle est héritée directement de la classe SugarRecord
    private String titre;
    private String ean;
    private int type;
    private String auteur;
    private String editeur;
    private String categorie;
    private int datePub;
    private String langue;
    private String resume;

    public Livre() {
    }

    public Livre(String titre, String ean, int type, String auteur, String editeur, String categorie, int datePub, String langue, String resume ) {
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

    protected Livre(Parcel in) {
        titre = in.readString();
        ean = in.readString();
        type = in.readInt();
        auteur = in.readString();
        editeur = in.readString();
        categorie = in.readString();
        datePub = in.readInt();
        langue = in.readString();
        resume = in.readString();
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public int getDatePub() {
        return datePub;
    }

    public void setDatePub(int datePub) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.titre);
        dest.writeString(this.ean);
        dest.writeInt(this.type);
        dest.writeString(this.auteur);
        dest.writeString(this.editeur);
        dest.writeString(this.categorie);
        dest.writeInt(this.datePub);
        dest.writeString(this.langue);
        dest.writeString(this.resume);
    }
}
