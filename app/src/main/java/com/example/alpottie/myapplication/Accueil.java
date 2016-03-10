package com.example.alpottie.myapplication;
import android.app.Activity;
import android.os.Bundle;

import com.example.alpottie.myapplication.BDD.GestionLivre;
import com.example.alpottie.myapplication.BDD.MySqLiteHelper;
import com.example.alpottie.myapplication.Donnees.Livre;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Accueil extends Activity implements OnClickListener
{
    private Button scanBtn, ajoutLivreBtn, ajoutAuteurBtn;

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
        MySqLiteHelper myhelper = MySqLiteHelper.getHelper(this);
        myhelper.onUpgrade(myhelper.getWritableDatabase(), MySqLiteHelper.DATABASE_VERSION, MySqLiteHelper.DATABASE_VERSION +1);
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

            GestionLivre gl = new GestionLivre(this);
            Livre trouvaille = gl.getLivre(ean);
            if(trouvaille != null)
            {
                Toast toast2 = Toast.makeText(getApplicationContext(), "Ce livre est déjà dans la base", Toast.LENGTH_SHORT);
                toast.show();
                Intent intention1 = new Intent(this, AfficherLivre.class);
                intention1.putExtra("livre", trouvaille);
                startActivity(intention1);
            }
            else {
                Intent intention = new Intent(this, Enregistrer.class);
                intention.putExtra("ean", ean);
                startActivity(intention);
            }
        }



    }
}