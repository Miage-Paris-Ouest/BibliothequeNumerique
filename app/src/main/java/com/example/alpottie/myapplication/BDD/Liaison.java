package com.example.alpottie.myapplication.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by alice on 08/03/2016.
 * Cette classe permet de maintenir la connexion avec la BDD et prend en charge la lecture de la BDD ainsi que l'écriture
 */
public class Liaison
{
    //Champs de la base de données
    protected SQLiteDatabase database;
    private MySqLiteHelper dbHelper;
    private Context mContext;


    public Liaison(Context context)
    {
        this.mContext = context;
        dbHelper = MySqLiteHelper.getHelper(context);
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void open() throws SQLException
    {
        if(dbHelper == null)
            dbHelper = MySqLiteHelper.getHelper(mContext);
        // accès en écriture à la BDD
        database = dbHelper.getWritableDatabase();
    }

    public void close()
    {
        dbHelper.close();
    }


}
