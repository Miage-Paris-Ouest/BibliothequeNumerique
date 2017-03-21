package com.example.alice.biblothequevirtuelle.Vue;

import com.example.alice.biblothequevirtuelle.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Accueil extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
    }

    public void onClickScan(View V)
    {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }

    // utilisation du résultat du scan
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String ean = "";
        String type = "";
        String prefix ="";
        if (scanningResult != null) {
            ean = scanningResult.getContents().toLowerCase();
            type = scanningResult.getFormatName().toLowerCase();
            prefix = ean.substring(0, 3);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Aucunes données scannées", Toast.LENGTH_SHORT);
            toast.show();
        }

        if(!type.equals("ean_13"))
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Mauvais format", Toast.LENGTH_SHORT);
            toast.show();
        }

        if(!(prefix.equals("977") || prefix.equals("978") || prefix.equals("979")))
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "C'est n'est pas un livre !", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "code ok : ean = "+ean+" type= "+type+" prefix="+prefix, Toast.LENGTH_LONG).show();
        }
    }
}
