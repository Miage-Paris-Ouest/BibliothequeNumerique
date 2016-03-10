package com.example.alpottie.myapplication.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alpottie.myapplication.Donnees.Auteur;
import com.example.alpottie.myapplication.Donnees.Livre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alice on 08/03/2016.
 */
public class GestionLivre extends Liaison {

    private static final String WHERE_ID_EQUALS = MySqLiteHelper.COLONNE_LIVRE_EAN+"=?";
    private Context mContext;

    public GestionLivre(Context context)
    {
        super(context);
        mContext = context;
    }

    public long save(Livre livre)
    {
        ContentValues values = new ContentValues();
        values.put(MySqLiteHelper.COLONNE_LIVRE_EAN, livre.getEan());
        values.put(MySqLiteHelper.COLONNE_LIVRE_TITRE, livre.getTitre());
        values.put(MySqLiteHelper.COLONNE_LIVRE_IDAUTEUR, livre.getAuteur().getId());
        values.put(MySqLiteHelper.COLONNE_LIVRE_COUVERTURE, livre.getCouverture());

        return database.insert(MySqLiteHelper.TABLE_LIVRE, null, values);
    }

    public long update(Livre livre)
    {
        ContentValues values = new ContentValues();
        values.put(MySqLiteHelper.COLONNE_LIVRE_EAN, livre.getEan());
        values.put(MySqLiteHelper.COLONNE_LIVRE_TITRE, livre.getTitre());
        values.put(MySqLiteHelper.COLONNE_LIVRE_IDAUTEUR, livre.getAuteur().getId());
        values.put(MySqLiteHelper.COLONNE_LIVRE_COUVERTURE, livre.getCouverture());

        long result = database.update(MySqLiteHelper.TABLE_LIVRE, values, WHERE_ID_EQUALS, new String[] {String.valueOf(livre.getEan())});
        Log.d("Résultat de la MAJ: ", " = " + result);
        return result;
    }

    public int delete(Livre livre) {
        return database.delete(MySqLiteHelper.TABLE_LIVRE,
                WHERE_ID_EQUALS, new String[]{livre.getEan() + ""});
    }

    public List<Livre> getLivres() {
        List<Livre> livres = new ArrayList<>();
        String query = "SELECT * FROM "+MySqLiteHelper.TABLE_LIVRE+", "+MySqLiteHelper.TABLE_AUTEUR+" WHERE " + MySqLiteHelper.COLONNE_LIVRE_IDAUTEUR+ " = "+ MySqLiteHelper.COLONNE_AUTEUR_ID+";";
        Cursor cursor = database.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Livre livre = new Livre(cursor.getLong(0), cursor.getString(1), cursor.getString(2), new Auteur(cursor.getLong(3), cursor.getString(4), cursor.getString(5)), cursor.getString(6));
            livres.add(livre);
        }

        cursor.close();
        return livres;
    }

    public Livre getLivre(String ean)
    {
        String query = "SELECT * FROM "+MySqLiteHelper.TABLE_LIVRE+", "+MySqLiteHelper.TABLE_AUTEUR+
                " WHERE " + MySqLiteHelper.COLONNE_LIVRE_IDAUTEUR+ " = "+ MySqLiteHelper.COLONNE_AUTEUR_ID+
                " AND " + MySqLiteHelper.COLONNE_LIVRE_EAN +" = "+ ean +";";
        Cursor cursor = database.rawQuery(query, null);
        Livre l = new Livre(cursor.getLong(0), cursor.getString(1), cursor.getString(2), new Auteur(cursor.getLong(3), cursor.getString(4), cursor.getString(5)), cursor.getString(6));
        cursor.close();
        return l;
    }
}
