package com.example.alice.biblothequevirtuelle.Vue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.alice.biblothequevirtuelle.AppelService.Scanner;
import com.example.alice.biblothequevirtuelle.Data.Livre;
import com.example.alice.biblothequevirtuelle.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.orm.SugarContext;

import java.util.ArrayList;

public class Accueil extends AppCompatActivity
{

    private Scanner scan;
    private static String ean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_layout);
        scan = new Scanner(this);

        SugarContext.init(getApplicationContext());

        Button bScan = (Button) findViewById(R.id.bScanner);
        bScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan.scanner();
            }
        });
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
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ean = "";
                    }
                });
            }
            else
            {
                final ArrayList<Livre> resultat = (ArrayList<Livre>) Livre.find(Livre.class, "ean = '"+ean+"'");
                if(resultat.size() == 0)
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
                    builder.setTitle("Vous avez déjà ce livre !");
                    builder.setMessage("Que voulez vous faire ?");
                    builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            resultat.get(0).delete();
                            ean = "";
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
}
