package com.example.alpottie.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.alpottie.myapplication.BDD.GestionAuteur;
import com.example.alpottie.myapplication.Donnees.Auteur;
import com.example.alpottie.myapplication.Personnalisation.AuteurAdapter;

import java.util.List;

public class ListeAuteur extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_auteur);
        GestionAuteur ga = new GestionAuteur(getApplicationContext());
        List<Auteur> lauteur = ga.getAuteurs();
        AuteurAdapter adapterAuteur = new AuteurAdapter(this, lauteur);
        setListAdapter(adapterAuteur);
    }

    public void onClickRetour(View v)
    {
        Intent annulation = new Intent(this, Accueil.class);
        startActivity(annulation);
    }


}
