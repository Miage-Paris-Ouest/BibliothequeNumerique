package com.example.alice.biblothequevirtuelle.Appli;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.alice.biblothequevirtuelle.Realm.Livre;
import com.example.alice.biblothequevirtuelle.Realm.Statut;
import com.example.alice.biblothequevirtuelle.Realm.Type;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.annotations.RealmModule;
import io.realm.exceptions.RealmException;

/**
 * Created by alice on 03/04/2017.
 */

public class BVAppli extends Application {
    private static final String PREFS = "PREFS";
    private static final String INSTAL_OK = "INSTAL_OK";
    private static BVAppli instance;

    private static Realm realm;

    // Create the module
    @RealmModule(classes = { Livre.class, Type.class, Statut.class })
    private class MyModule {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);


        realm = Realm.getDefaultInstance();
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        //objectif : sauvegarder une seule fois si les types et les statuts ont été chargé dans la BDD interne
        //Si la clef n'existe pas ou si elle existe mais que la valeur est fausse
        if ((!sharedPreferences.contains(INSTAL_OK)) || (sharedPreferences.contains(INSTAL_OK) && (!sharedPreferences.getBoolean(INSTAL_OK, false))))
        {

            try {
                realm.beginTransaction();
                Number maxId = realm.where(Type.class).max("id");
                int premierId;
                if(maxId != null)
                    premierId = (int) maxId;
                else
                    premierId = 0;
                // On ajoute tous les types disponibles
                Type indefini = realm.createObject(Type.class, premierId);
                indefini.setNom("Indéfini");
                premierId++;
                Type grandFormat = realm.createObject(Type.class, premierId);
                grandFormat.setNom("Grand Format");
                premierId++;
                Type poche = realm.createObject(Type.class, premierId);
                poche.setNom("Poche");
                premierId++;
                Type bd = realm.createObject(Type.class, premierId);
                bd.setNom("Bande dessinée");
                premierId++;
                Type comics = realm.createObject(Type.class, premierId);
                comics.setNom("Comics");
                premierId++;
                Type manga = realm.createObject(Type.class, premierId);
                manga.setNom("Manga");
                premierId++;
                Type presse = realm.createObject(Type.class, premierId);
                presse.setNom("Presse");

                //On ajoute tous les status disponibles
                realm.createObject(Statut.class, "A Lire");
                realm.createObject(Statut.class, "En Cours");
                realm.createObject(Statut.class, "Lu");
                realm.createObject(Statut.class, "Preté");
                realm.createObject(Statut.class, "Non Preté");
                realm.commitTransaction();

                sharedPreferences.edit().putBoolean(INSTAL_OK, true).apply();
                Toast.makeText(getApplicationContext(), "Données téléchargées", Toast.LENGTH_LONG).show();
                realm.close();
            }catch (RealmException re)
            {
                Toast.makeText(getApplicationContext(), "Erreur lors du téléchargement des données", Toast.LENGTH_LONG).show();
                System.err.println(re.toString());
            }
        }
        else
            Toast.makeText(getApplicationContext(), "données déjà dl", Toast.LENGTH_LONG).show();
    }

    public static BVAppli getInstance() {

        return instance;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}
