package com.example.alpottie.myapplication;
import android.app.Activity;
import android.os.Bundle;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Accueil extends Activity implements OnClickListener
{
    private Button scanBtn, ajoutLivreBtn, ajoutAuteurBtn;
    private TextView formatTxt, contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        scanBtn = (Button)findViewById(R.id.scan_button);
        ajoutLivreBtn = (Button)findViewById(R.id.b_ajout_livre);
        ajoutAuteurBtn = (Button)findViewById(R.id.b_ajout_auteur);
        scanBtn.setOnClickListener(this);
        ajoutAuteurBtn.setOnClickListener(this);
        ajoutLivreBtn.setOnClickListener(this);
    }
    public void onClick(View v){
        if(v.getId()==R.id.scan_button || v.getId()==R.id.b_ajout_livre){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
        else if (v.getId() == R.id.b_ajout_auteur)
        {
            Intent intent = new Intent(this, AjoutAuteur.class);
            startActivity(intent);
        }
    }

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
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Code Ok", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}