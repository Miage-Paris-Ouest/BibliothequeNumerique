package com.example.alice.biblothequevirtuelle.Vue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.alice.biblothequevirtuelle.R;
import com.example.alice.biblothequevirtuelle.Scanner;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Accueil extends Activity
{

    Scanner scan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        scan = new Scanner(this);
    }

    public void onClickVerif(View V)
    {
        scan.scanner();
    }

    // utilisation du résultat du scan
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String ean = "";
        String type = "";
        String prefix ="";

        if (resultCode != 0) {
            ean = scanningResult.getContents().toLowerCase();
            type = scanningResult.getFormatName().toLowerCase();
            prefix = ean.substring(0, 3);
        }
        else{
            Toast.makeText(getApplicationContext(),"Aucunes données scannées", Toast.LENGTH_SHORT).show();
        }

        if(!type.equals("ean_13"))
        {
            Toast.makeText(getApplicationContext(), "Mauvais format", Toast.LENGTH_SHORT).show();

        }

        if(!(prefix.equals("977") || prefix.equals("978") || prefix.equals("979")))
        {
            Toast.makeText(getApplicationContext(),"C'est n'est pas un livre !", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "code ok : ean = "+ean+" type= "+type+" prefix="+prefix, Toast.LENGTH_LONG).show();
        }
    }
}
