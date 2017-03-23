package com.example.alice.biblothequevirtuelle.Vue;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alice.biblothequevirtuelle.Data.Utilisateur;
import com.example.alice.biblothequevirtuelle.R;

import java.io.IOException;

/**
 * Created by austepha on 23/03/2017.
 */

public class CreationCompte extends Activity {
    //classe qui permet la création d'un compte utilisateur et le met dans la base de données

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.ajout);
    }

    public void ClickValider(View v) throws IOException {


       try{
            EditText pseudo = (EditText) findViewById(R.id.etPseudoInscription);
            EditText nom = (EditText) findViewById(R.id.etNomInscription);
            EditText prenom = (EditText) findViewById(R.id.etPrenomInscription);
            EditText motDePasse = (EditText) findViewById(R.id.etMdpInscription1);
            EditText email = (EditText) findViewById(R.id.etMailInscription);

            Utilisateur u = new Utilisateur (pseudo.getText().toString(),nom.getText().toString(),prenom.getText().toString(), motDePasse.getText().toString(), email.getText().toString());
            u.save();
            Toast toast = Toast.makeText(getApplicationContext(), nom.getText().toString() + " " + prenom.getText().toString() + " a bien été crée et ajouté", Toast.LENGTH_LONG);
            toast.show();
        }

        catch (Exception e){
            Toast.makeText(this, "L'utilisateur n'a pas pu être crée", Toast.LENGTH_SHORT).show();
        }
    }
}

