package com.example.alice.biblothequevirtuelle.Vue;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        // Ajout dans la BDD SQLite
       /** try{
            EditText number = (EditText) findViewById(R.id.number);
            EditText nom = (EditText) findViewById(R.id.nom);
            EditText prenom = (EditText) findViewById(R.id.prenom);


            Utilisateur c=new Personne(nom.getText().toString(),prenom.getText().toString(),number.getText().toString());
            c.save();
            Toast toast = Toast.makeText(getApplicationContext(), nom.getText().toString() + " " + prenom.getText().toString() + " a été ajouté", Toast.LENGTH_LONG);
            toast.show();
        }

        catch (Exception e){
            Toast.makeText(this, "Le contact n'a pas pu être inséré", Toast.LENGTH_SHORT).show();
        }
    }**/
}
}
