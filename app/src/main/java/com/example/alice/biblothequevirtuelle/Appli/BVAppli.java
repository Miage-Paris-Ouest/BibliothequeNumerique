package com.example.alice.biblothequevirtuelle.Appli;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.alice.biblothequevirtuelle.Realm.Auteur;
import com.example.alice.biblothequevirtuelle.Realm.RLivre;
import com.example.alice.biblothequevirtuelle.Realm.Statut;
import com.example.alice.biblothequevirtuelle.Realm.Type;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.annotations.RealmModule;
import io.realm.exceptions.RealmException;

/**
 * Created by alice on 03/04/2017.
 */

public class BVAppli extends Application
{

    private SharedPreferences sharedPreferences;
    private static final String PREFS = "PREFS";
    private static final String INSTAL_OK = "INSTAL_OK";
    private static BVAppli instance;

    // Create the module
    @RealmModule(classes = { RLivre.class, Auteur.class, Type.class, Statut.class })
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
        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        //objectif : sauvegarder une seule fois si les types et les statuts ont été chargé dans la BDD interne
        //Si la clef n'existe pas ou si elle existe mais que la valeur est fausse
        if ((!sharedPreferences.contains(INSTAL_OK)) || (sharedPreferences.contains(INSTAL_OK) && sharedPreferences.getBoolean(INSTAL_OK, false)))
        {
            Realm realm = Realm.getInstance(BVAppli.getInstance());
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
                aLire.setId(realm.where(Statut.class).maximumInt("id") + 1);
                aLire.setIntitule("A Lire");
                Statut enCours = realm.createObject(Statut.class);
                enCours.setId(realm.where(Statut.class).maximumInt("id") + 1);
                enCours.setIntitule("En Cours");
                Statut lu = realm.createObject(Statut.class);
                lu.setId(realm.where(Statut.class).maximumInt("id") + 1);
                lu.setIntitule("Terminé");
                Statut prete = realm.createObject(Statut.class);
                prete.setId(realm.where(Statut.class).maximumInt("id") + 1);
                prete.setIntitule("Prêté");
                realm.commitTransaction();

                sharedPreferences.edit().putBoolean(INSTAL_OK, true).apply();
                Toast.makeText(getApplicationContext(), "Données téléchargées", Toast.LENGTH_LONG).show();
            }catch (RealmException re)
            {
                Toast.makeText(getApplicationContext(), "Erreur lors du téléchargement des données", Toast.LENGTH_LONG).show();
                System.err.println(re.toString());
            }
        }
        else if(!sharedPreferences.contains(INSTAL_OK))
        {
            Toast.makeText(getApplicationContext(), "INSTAL_OK n'existe pas", Toast.LENGTH_LONG).show();
            sharedPreferences.edit().putBoolean(INSTAL_OK, false).apply();
        }
        else
            Toast.makeText(getApplicationContext(), "données déjà dl", Toast.LENGTH_LONG).show();

    }

    public static BVAppli getInstance() {
        return instance;
    }
    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
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
