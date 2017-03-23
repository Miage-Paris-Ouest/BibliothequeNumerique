package com.example.alice.biblothequevirtuelle.Vue;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.alice.biblothequevirtuelle.Data.Livre;
import com.example.alice.biblothequevirtuelle.R;
import com.example.alice.biblothequevirtuelle.Scanner;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.orm.SugarContext;

import java.util.ArrayList;

public class Accueil extends AppCompatActivity
{

    private Scanner scan;
    private FragmentTransaction transaction;
    private FragScanAjout fragVerif;
    private String ean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_layout);
        scan = new Scanner(this);

        if (findViewById(R.id.flVerif) != null) {

            fragVerif = new FragScanAjout();
            transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.flVerif, fragVerif);
            transaction.addToBackStack(null);
            transaction.hide(fragVerif);
            transaction.commit();
        }

        SugarContext.init(getApplicationContext());
    }

    public void onClickVerif(View V) {
        scan.scanner();
    }

    public void onClickNon(View v)
    {
        transaction = getFragmentManager().beginTransaction();
        transaction.hide(fragVerif);
        transaction.commit();

    }
    public void onClickOui(View v)
    {
        Intent ajout = new Intent(this, Ajouter.class);
        ajout.putExtra("ean", ean);
        transaction = getFragmentManager().beginTransaction();
        transaction.hide(fragVerif);
        transaction.commit();
        startActivity(ajout);
    }

    public void onClickAjouter(View v)
    {
        Intent ajout = new Intent(this, Ajouter.class);
        startActivity(ajout);
    }

    // utilisation du résultat du scan
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String type;
        String prefix;

        if (resultCode == 0) {
            Toast.makeText(getApplicationContext(),"Aucunes données scannées", Toast.LENGTH_SHORT).show();
        }
        else if(scanningResult != null){
            ean = scanningResult.getContents().toLowerCase();
            type = scanningResult.getFormatName().toLowerCase();
            prefix = ean.substring(0, 3);

            if(!type.equals("ean_13"))
            {
                Toast.makeText(getApplicationContext(), "Mauvais format", Toast.LENGTH_SHORT).show();

            }
            else if(!(prefix.equals("977") || prefix.equals("978") || prefix.equals("979")))
            {
                Toast.makeText(getApplicationContext(),"C'est n'est pas un livre !", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "code ok : ean = "+ean+" type= "+type+" prefix="+prefix, Toast.LENGTH_LONG).show();

                ArrayList<Livre> resultat = (ArrayList<Livre>) Livre.find(Livre.class, "ean = '"+ean+"'");
                if(resultat.size() == 0)
                {
                    transaction = getFragmentManager().beginTransaction();
                    transaction.show(fragVerif);
                    transaction.commit();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Vous avez déjà ce livre !", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
