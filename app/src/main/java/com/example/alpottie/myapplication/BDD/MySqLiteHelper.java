package com.example.alpottie.myapplication.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by alice on 08/03/2016.
 */
public class MySqLiteHelper extends SQLiteOpenHelper
{
    //region def database
    // database librairie
    public static final String DATABASE_NAME = "librairie";
    public static final int DATABASE_VERSION = 1;

    // table contenant les auteurs
    public static final String TABLE_AUTEUR = "auteurs";
    public static final String COLONNE_AUTEUR_ID = "_id";
    public static final String COLONNE_AUTEUR_NOM = "nom";
    public static final String COLONNE_AUTEUR_PRENOM = "prenom";

    // table contenant les livres
    public static final String TABLE_LIVRE = "livres";
    //public static final String COLONNE_LIVRE_ID = "_id";
    public static final String COLONNE_LIVRE_EAN = "ean";
    public static final String COLONNE_LIVRE_TITRE = "titre";
    public static final String COLONNE_LIVRE_IDAUTEUR = "auteur";
    public static final String COLONNE_LIVRE_COUVERTURE = "couverture";

    // database
    private static final String CREATE_TABLE_AUTEUR = "CREATE TABLE "+TABLE_AUTEUR+" ( "+ COLONNE_AUTEUR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLONNE_AUTEUR_NOM + " TEXT NOT NULL, " + COLONNE_AUTEUR_PRENOM + " TEXT NOT NULL);";
    private static final String CREATE_TABLE_LIVRE = "CREATE TABLE "+TABLE_LIVRE+" ( "+ COLONNE_LIVRE_EAN + " TEXT PRIMARY KEY, " + COLONNE_LIVRE_TITRE+ " TEXT NOT NULL, " + COLONNE_LIVRE_IDAUTEUR+ " TEXT NOT NULL, "+ COLONNE_LIVRE_COUVERTURE+" TEXT, FOREIGN KEY("+COLONNE_LIVRE_IDAUTEUR+") REFERENCES " + TABLE_AUTEUR + "("+COLONNE_AUTEUR_ID+"));";
    //endregion

    private static MySqLiteHelper instance;

    public static synchronized MySqLiteHelper getHelper(Context context) {
        if(instance == null)
            instance = new MySqLiteHelper(context);
        return instance;
    }

    private MySqLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_AUTEUR);
        db.execSQL(CREATE_TABLE_LIVRE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int ancienneVersion, int nouvelleVersion) {
        Log.w(MySqLiteHelper.class.getName(),
                "Mise à jour de la base de la version " + ancienneVersion + " à "
                        + nouvelleVersion + ", les données vont être détruites.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVRE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTEUR);
        onCreate(db);
    }


}
