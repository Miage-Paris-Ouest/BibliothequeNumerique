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
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .setModules(new MyModule())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Realm realm = Realm.getInstance(getInstance());

        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        //objectif : sauvegarder une seule fois si les types et les statuts ont été chargé dans la BDD interne
        //Si la clef n'existe pas ou si elle existe mais que la valeur est fausse
        if ((!sharedPreferences.contains(INSTAL_OK)) || (sharedPreferences.contains(INSTAL_OK) && (sharedPreferences.getBoolean(INSTAL_OK, false)== false)))
        {

            try {
                realm.beginTransaction();
                // On ajoute tous les types disponibles
                Type indefini = realm.createObject(Type.class);
                indefini.setNom("Indéfini");
                Type grandFormat = realm.createObject(Type.class);
                grandFormat.setNom("Grand Format");
                Type poche = realm.createObject(Type.class);
                poche.setNom("Poche");
                Type bd = realm.createObject(Type.class);
                bd.setNom("Bande dessinée");
                Type comics = realm.createObject(Type.class);
                comics.setNom("Comics");
                Type manga = realm.createObject(Type.class);
                manga.setNom("Manga");
                Type presse = realm.createObject(Type.class);
                presse.setNom("Presse");

                //On ajoute tous les status disponibles
                Statut aLire = realm.createObject(Statut.class);
                aLire.setIntitule("A Lire");
                Statut enCours = realm.createObject(Statut.class);
                enCours.setIntitule("En Cours");
                Statut lu = realm.createObject(Statut.class);
                lu.setIntitule("Lu");
                Statut prete = realm.createObject(Statut.class);
                prete.setIntitule("Preté");
                Statut nonprete = realm.createObject(Statut.class);
                nonprete.setIntitule("Non Preté");
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
