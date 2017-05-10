package com.example.alice.biblothequevirtuelle.Vue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.alice.biblothequevirtuelle.Appli.BVAppli;
import com.example.alice.biblothequevirtuelle.R;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;

/*
TODO Ajouter la recherche et le filtrage
 */
public class MesLivres extends AppCompatActivity {

    ArrayList<HashMap<String, String>> donnees = new ArrayList<>();
    SimpleAdapter adapter;
    ListView listLivres;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meslivres_layout);

        realm = Realm.getInstance(BVAppli.getInstance());

        adapter = new SimpleAdapter(this, donnees, R.layout.livre_liste_layout,
                new String[]{"isbn", "titre", "auteur"},
                new int[]{R.id.cellule_isbn, R.id.cellule_titre, R.id.cellule_auteur});
        listLivres = (ListView) findViewById(R.id.lvMeslivres);
        listLivres.setAdapter(adapter);
    }

    public void addItem(String isbn, String titre, String auteur) {
        HashMap<String,String> item = new HashMap<String,String>();
        item.put("isbn", isbn);
        item.put("titre", titre);
        item.put("auteur", auteur);
        donnees.add(item);
    }
}
