package com.example.alice.biblothequevirtuelle.Data;

/**
 * Created by alice on 21/03/2017.
 */

public enum Type {
    GF("Grand Format", 0),
    POCHE("Poche", 1),
    BD("Bande dessinée", 2),
    COMIC("Comic Book", 3),
    MANGA("Manga", 4),
    PRESSE("Presse", 5);

    private String nom;
    private int id;

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public int getIdByName(Type t)
    {
        return t.getId();
    }

    Type(String toString, int value) {
        nom = toString;
        id = value;
    }

    @Override
    public String toString() {
        return nom;
    }


}
