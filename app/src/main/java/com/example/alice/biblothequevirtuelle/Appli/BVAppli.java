package com.example.alice.biblothequevirtuelle.Appli;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.alice.biblothequevirtuelle.Firebase.Authentification;
import com.example.alice.biblothequevirtuelle.Realm.CollectionP;
import com.example.alice.biblothequevirtuelle.Realm.Livre;
import com.example.alice.biblothequevirtuelle.Realm.Type;
import com.example.alice.biblothequevirtuelle.Realm.Utilisateur;
import com.example.alice.biblothequevirtuelle.Vue.Accueil;
import com.google.firebase.auth.FirebaseUser;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.annotations.RealmModule;
import io.realm.exceptions.RealmException;


/**
 * Created by alice on 03/04/2017.
 */

public class BVAppli extends Application {
    public static final String PREFS = "PREFS";
    private static final String INSTAL_OK = "INSTAL_OK";
    private static BVAppli instance;
    private static Utilisateur utilisateur;
    private static String utilisateurFirebaseID;

    private static Realm realm;

    // Create the module
    @RealmModule(classes = { Livre.class, Type.class, CollectionP.class, Utilisateur.class})
    private class MyModule {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .modules(new MyModule())
                .build();
        Realm.setDefaultConfiguration(config);


        realm = Realm.getDefaultInstance();
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        //objectif : sauvegarder une seule fois si les types
        //Si la clef n'existe pas ou si elle existe mais que la valeur est fausse
        if ((!sharedPreferences.contains(INSTAL_OK)) || (sharedPreferences.contains(INSTAL_OK) && (!sharedPreferences.getBoolean(INSTAL_OK, false))))
        {
            try {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Number maxId = realm.where(Type.class).max("id");
                        int idType;
                        if(maxId != null)
                            idType = (int) maxId;
                        else
                            idType = 0;
                        // On ajoute tous les types disponibles
                        Type indefini = realm.createObject(Type.class, idType);
                        indefini.setNom("Indéfini");
                        idType++;
                        Type grandFormat = realm.createObject(Type.class, idType);
                        grandFormat.setNom("Grand Format");
                        idType++;
                        Type poche = realm.createObject(Type.class, idType);
                        poche.setNom("Poche");
                        idType++;
                        Type bd = realm.createObject(Type.class, idType);
                        bd.setNom("Bande dessinée");
                        idType++;
                        Type comics = realm.createObject(Type.class, idType);
                        comics.setNom("Comics");
                        idType++;
                        Type manga = realm.createObject(Type.class, idType);
                        manga.setNom("Manga");
                        idType++;
                        Type presse = realm.createObject(Type.class, idType);
                        presse.setNom("Presse");
                    }
                });

                sharedPreferences.edit().putBoolean(INSTAL_OK, true).apply();
                Toast.makeText(getApplicationContext(), "Données téléchargées", Toast.LENGTH_LONG).show();

            }catch (RealmException re)
            {
                Toast.makeText(getApplicationContext(), "Erreur lors du téléchargement des données", Toast.LENGTH_LONG).show();
                System.err.println(re.toString());
            }
        }
        else
            Toast.makeText(getApplicationContext(), "Données déjà téléchargées", Toast.LENGTH_LONG).show();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                utilisateur = realm.where(Utilisateur.class).findFirst();
            }
        });

        Intent intent;
        if(utilisateur != null && utilisateur.isDejaConnecte())
        {
            utilisateurFirebaseID = utilisateur.getFirebaseID();
            intent=new Intent(getApplicationContext(), Accueil.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            intent=new Intent(getApplicationContext(), Authentification.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        realm.close();
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

    public static Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public static String getUtilisateurID() {
        return utilisateur.getFirebaseID();
    }

    public static void setUtilisateur(Utilisateur utilisateur) {
        BVAppli.utilisateur = utilisateur;
    }

    public static void setUtilisateurFromFirebase(final FirebaseUser fu, final boolean connexion)
    {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
                                     @Override
                                     public void execute(Realm realm) {

                                         RealmResults<Utilisateur> rrU = realm.where(Utilisateur.class).equalTo("firebaseID", fu.getUid()).findAll();

                                         if(rrU.isEmpty()) {
                                             BVAppli.utilisateur = realm.createObject(Utilisateur.class, fu.getUid());
                                             BVAppli.utilisateur.setMail(fu.getEmail());
                                             BVAppli.utilisateur.setDejaConnecte(connexion);
                                             BVAppli.utilisateurFirebaseID = fu.getUid();
                                         }
                                         else
                                         {
                                            BVAppli.utilisateur = rrU.first();
                                            BVAppli.utilisateur.setDejaConnecte(true);
                                         }
                                     }
                                 });
        realm.close();
    }

    public static String getUtilisateurFirebaseID() {
        return utilisateurFirebaseID;
    }

    public static void setUtilisateurFirebaseID(String utilisateurFirebaseID) {
        BVAppli.utilisateurFirebaseID = utilisateurFirebaseID;
    }

    /*public void AjoutUtilisateurFirebaseARealm(final String id, final Context context)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Utilisateurs").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Utilisateur util=dataSnapshot.getValue(Utilisateur.class);
                try {

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Utilisateur ru = realm.createObject(Utilisateur.class, id);
                            ru=util;
                        }
                    });

                    Toast.makeText(context, "Ajout réussi !", Toast.LENGTH_LONG).show();

                } catch (RealmException re) {
                    System.err.println(re.toString());
                    Toast.makeText(context, "Erreur lors de l'ajout", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }*/
}
