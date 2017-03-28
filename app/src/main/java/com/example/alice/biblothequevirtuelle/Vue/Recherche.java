package com.example.alice.biblothequevirtuelle.Vue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.alice.biblothequevirtuelle.Data.Livre;
import com.example.alice.biblothequevirtuelle.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kiki on 28/03/2017.
 */

public class Recherche extends AppCompatActivity {
    ArrayList<HashMap<String, String>> donnees = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;
    ListView listLivres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recherche_layout);
        adapter = new SimpleAdapter(this, donnees, R.layout.item,
                new String[]{"isbn", "titre", "auteur"},
                new int[]{R.id.cellule_isbn, R.id.cellule_titre, R.id.cellule_auteur});
        listLivres = (ListView) findViewById(R.id.listView_Recherche);
        listLivres.setAdapter(adapter);
    }


    private void addItem(String isbn, String titre, String auteur) {
        HashMap<String,String> item = new HashMap<String,String>();
        item.put("isbn", isbn);
        item.put("titre", titre);
        item.put("auteur", auteur);
        donnees.add(item);
    }


    public void rechercher(View v){
        try {
            EditText isbn = (EditText) findViewById(R.id.etISBN);
            EditText titre = (EditText) findViewById(R.id.etTitre);
            EditText auteur = (EditText) findViewById(R.id.etAuteur);
            EditText key;
            String var;

            if (isbn.equals(null)){
                if (titre.equals(null)){
                    key = auteur;
                    var = "auteur";
                } else {
                    key = titre;
                    var = "titre";
                }
            } else {
                key = isbn;
                var = "ean";
            }

            donnees.clear();
            String search = key.getText().toString();

            List<Livre> corresp = Livre.find(Livre.class, var + " LIKE '%" + search + "%'");

            if(corresp.isEmpty()){
                Toast.makeText(this.getBaseContext(), "Oups aucune correspondance...", Toast.LENGTH_LONG).show();
            }else{
                for (Livre lv: corresp){
                    addItem(lv.getEan(), lv.getTitre(), lv.getAuteur());
                }
            }
            adapter.notifyDataSetChanged();

        }catch (Exception e){
            Log.e("Main : ", e.toString());
        }
    }

}
