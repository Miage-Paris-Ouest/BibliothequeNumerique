package com.example.alice.biblothequevirtuelle.Vue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alice.biblothequevirtuelle.AppelService.Scanner;
import com.example.alice.biblothequevirtuelle.Firebase.Authentification;
import com.example.alice.biblothequevirtuelle.Firebase.ResetPasswordActivity;
import com.example.alice.biblothequevirtuelle.R;
import com.example.alice.biblothequevirtuelle.Realm.Livre;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

public class Accueil extends AppCompatActivity
{

    private Scanner scan;
    private static String ean;
    private Realm realm;
    private Button bdeconnexion;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_layout);

        auth = FirebaseAuth.getInstance().getInstance();

        scan = new Scanner(this);
        realm = Realm.getDefaultInstance();

        Button bScan = (Button) findViewById(R.id.bScanner);
        bScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan.scanner();
            }
        });

        bdeconnexion = (Button) findViewById(R.id.bdeconnexion);
        Button bMesLivres = (Button) findViewById(R.id.bLivres);

        bMesLivres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesLivres(v);
            }
        });



        bdeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this, Authentification.class);
                startActivity(intent);
                auth.signOut();

                }
            });
    }















    public void search(View v){
        Intent intent = new Intent(this, Recherche.class);
        startActivity(intent);
    }

    public void mesLivres(View v)
    {
        Intent intent = new Intent(getApplicationContext(), MesLivres.class);
        startActivity(intent);
    }

    // utilisation du résultat du scan
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String type;
        String prefix;
        AlertDialog.Builder builder = new AlertDialog.Builder(Accueil.this);

        if (resultCode == 0) {
            builder.setTitle("Aucune données scannées !");
            builder.setMessage("Voulez vous scanner un autre livre ?");
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ean = null;
                    scan.scanner();
                }
            });
            builder.setNeutralButton("Recherche manuelle",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(getApplicationContext(), Recherche.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ean ="";
                }
            });
        }
        else if(scanningResult != null)
        {
            ean = scanningResult.getContents().toLowerCase();
            type = scanningResult.getFormatName().toLowerCase();
            prefix = ean.substring(0, 3);

            if(!type.equals("ean_13"))
            {
                builder.setTitle("Mauvais format");
                builder.setMessage("Voulez vous scanner un autre livre ?");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ean = null;
                        scan.scanner();
                    }
                });
                builder.setNeutralButton("Recherche manuelle",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), Recherche.class);
                        startActivity(intent);
                    }
                });
                builder.setNeutralButton("Ajout manuel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), Ajouter.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ean = "";
                    }
                });

            }
            else if(!(prefix.equals("977") || prefix.equals("978") || prefix.equals("979")))
            {
                builder.setTitle("Ce n'est pas un livre !");
                builder.setMessage("Voulez vous scanner un autre livre ?");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ean = null;
                        scan.scanner();
                    }
                });
                builder.setNeutralButton("Recherche manuelle",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), Recherche.class);
                        startActivity(intent);
                    }
                });
                builder.setNeutralButton("Ajout manuel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), Ajouter.class);
                        intent.putExtra("précédent", "Accueil");
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ean = "";
                    }
                });
            }
            else
            {
                RealmResults<Livre> trouvaille;
                try
                {
                    trouvaille = realm.where(Livre.class).equalTo("ean", ean).findAll();
                }
                catch(RealmException re)
                {
                    System.err.println(re.toString());
                    trouvaille = null;
                }

                if((trouvaille != null && trouvaille.size() == 0) || trouvaille == null)
                {
                    builder.setTitle("Vous n'avez pas ce livre !");
                    builder.setMessage("Voulez vous l'ajouter à votre bibliothèque ?");
                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent ajout = new Intent(getApplicationContext(), Ajouter.class);
                            ajout.putExtra("ean", ean);
                            startActivity(ajout);
                        }
                    });
                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ean = "";
                        }
                    });
                }
                else
                {
                    final Livre livre = trouvaille.first();
                    builder.setTitle("Vous avez déjà ce livre ! ("+trouvaille.get(0).getTitre()+")");
                    builder.setMessage("Que voulez vous faire ?");
                    builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try
                            {
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        livre.deleteFromRealm();
                                        }
                                });
                                ean = "";
                                Toast.makeText(getApplicationContext(), "Suppression effectuée", Toast.LENGTH_LONG).show();
                            }catch (Exception e)
                            {
                                System.err.println(e.toString());
                                Toast.makeText(getApplicationContext(), "Erreur lors de la suppression", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    builder.setNeutralButton("Scanner", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ean = "";
                            scan.scanner();
                        }
                    });
                    builder.setNegativeButton("Rien", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ean = "";
                        }
                    });
                }
            }
        }
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
