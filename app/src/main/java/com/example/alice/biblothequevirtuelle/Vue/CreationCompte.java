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
        setContentView(R.layout.fragment_inscription_layout);
    }

    public void ClickValider(View v) throws IOException {



    }
}

