package com.example.alpottie.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alpottie.myapplication.BDD.GestionAuteur;
import com.example.alpottie.myapplication.Donnees.Auteur;

public class AjoutAuteur extends Activity {

    private EditText etnom, etprenom;
    private GestionAuteur ga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_auteur);

        ga = new GestionAuteur(getApplicationContext());
    }

    public void onClickAjout(View v)
    {
        etnom = (EditText) findViewById(R.id.nom_auteur);
        etprenom = (EditText) findViewById(R.id.prenom_auteur);
        if((etnom.getText().toString()).isEmpty() || (etprenom.getText().toString()).isEmpty())
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Il manque des informations !", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Auteur auteur = new Auteur(etnom.getText().toString(), etprenom.getText().toString());
            Long res = ga.save(auteur);
            Log.d("Res de l'insertion: ", "=" +res);
            if(res == -1)
            {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Erreur pendant l'ajout.", Toast.LENGTH_SHORT);
                toast.show();
            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Ajout ok !", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void onClickRetour(View v)
    {
        Intent intent = new Intent(this, Accueil.class);
        startActivity(intent);
    }

    public void onClickVoir(View v)
    {
        Intent intent = new Intent(this, ListeAuteur.class);
        startActivity(intent);
    }
}
