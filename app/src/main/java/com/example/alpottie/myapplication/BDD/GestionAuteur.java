package com.example.alpottie.myapplication.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alpottie.myapplication.Donnees.Auteur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alice on 08/03/2016.
 */
public class GestionAuteur extends Liaison
{

    private static final String WHERE_ID_EQUALS = MySqLiteHelper.COLONNE_AUTEUR_ID+"=?";

    public GestionAuteur(Context context) {
        super(context);
    }

    public long save(Auteur auteur)
    {
        ContentValues values = new ContentValues();
        values.put(MySqLiteHelper.COLONNE_AUTEUR_NOM, auteur.getNom());
        values.put(MySqLiteHelper.COLONNE_AUTEUR_PRENOM, auteur.getPrenom());

        return database.insert(MySqLiteHelper.TABLE_AUTEUR, null, values);
    }

    public long update(Auteur auteur)
    {
        ContentValues values = new ContentValues();
        values.put(MySqLiteHelper.COLONNE_AUTEUR_NOM, auteur.getNom());
        values.put(MySqLiteHelper.COLONNE_AUTEUR_PRENOM, auteur.getPrenom());

        long result = database.update(MySqLiteHelper.TABLE_AUTEUR, values, WHERE_ID_EQUALS, new String[] {String.valueOf(auteur.getId())});
        Log.d("Résultat de la MAJ: ", " = " + result);
        return result;
    }

    public int delete(Auteur auteur) {
        return database.delete(MySqLiteHelper.TABLE_AUTEUR,
                WHERE_ID_EQUALS, new String[]{auteur.getId() + ""});
    }

    public List<Auteur> getAuteurs() {
        List<Auteur> auteurs = new ArrayList<>();
        Cursor cursor = database.query(MySqLiteHelper.TABLE_LIVRE,
                new String[]{MySqLiteHelper.COLONNE_AUTEUR_ID,
                        MySqLiteHelper.COLONNE_AUTEUR_NOM, MySqLiteHelper.COLONNE_AUTEUR_PRENOM}, null, null, null, null,
                null);

        while (cursor.moveToNext()) {
            Auteur auteur = new Auteur(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
            auteurs.add(auteur);
        }

        cursor.close();
        return auteurs;
    }

    public Auteur getAuteur(Long id)
    {
        Cursor cursor = database.query(MySqLiteHelper.TABLE_AUTEUR, new String[]{MySqLiteHelper.COLONNE_AUTEUR_ID,
                MySqLiteHelper.COLONNE_AUTEUR_NOM, MySqLiteHelper.COLONNE_AUTEUR_PRENOM}, WHERE_ID_EQUALS, new String[] {id.toString()}, null, null, null);
        return new Auteur(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
    }


}
