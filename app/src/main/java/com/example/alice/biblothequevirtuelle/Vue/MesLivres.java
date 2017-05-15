package com.example.alice.biblothequevirtuelle.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alice.biblothequevirtuelle.R;
import com.example.alice.biblothequevirtuelle.Realm.Livre;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MesLivres extends AppCompatActivity {

    ArrayList<HashMap<String, String>> donnees = new ArrayList<>();
    SimpleAdapter adapter;
    ListView listLivres;
    Realm realm;
    RealmResults<Livre> realmResults;




    private class listeLivreClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(getApplicationContext(), "Clic liste", Toast.LENGTH_SHORT).show();

            TextView tvId = (TextView) view.findViewById(R.id.tvIdHidden);
            String idLivre = tvId.getText().toString();
            Intent intent = new Intent(getApplicationContext(), Modifier.class);
            intent.putExtra("livreSelectionné", idLivre);
            intent.putExtra("précédent", "MesLivres");
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meslivres_layout);


        realm = Realm.getDefaultInstance();

        //alimente la liste actvauteur et actvediteur
        AutoCompleteTextView actvauteur = (AutoCompleteTextView) findViewById(R.id.actvAuteur);
        AutoCompleteTextView actvediteur = (AutoCompleteTextView) findViewById(R.id.actvEditeur);
        RealmResults<Livre> realmLivre = realm.where(Livre.class).findAll();
        String[] listAuteur=new String[realmLivre.size()];
        String[] listEditeur=new String[realmLivre.size()];
        for(int i=0;i<realmLivre.size();i++)//créer la liste d'auteur
        {
            listAuteur[i]=realmLivre.get(i).getAuteur();
            listEditeur[i]=realmLivre.get(i).getEditeur();
        }
        ArrayAdapter<String> auteuradapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, listAuteur);
        actvauteur.setThreshold(1);//suggestion commenc a 1
        actvauteur.setAdapter(auteuradapter);
        ArrayAdapter<String> editeuradapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, listEditeur);
        actvediteur.setThreshold(1);//suggestion commenc a 1
        actvediteur.setAdapter(editeuradapter);



        adapter = new SimpleAdapter(this, donnees, R.layout.livre_liste_layout,
                new String[]{"isbn", "titre", "auteur", "id"},
                new int[]{R.id.tvCelluleIsbn, R.id.tvCelluleTitre, R.id.tvCelluleAuteur, R.id.tvIdHidden});
        listLivres = (ListView) findViewById(R.id.lvMeslivres);
        listLivres.setAdapter(adapter);
        listLivres.setOnItemClickListener(new listeLivreClickHandler());
    }

    @Override
    protected void onStart() {
        super.onStart();

        realmResults = realm.where(Livre.class).equalTo("whishlist", false).findAll();
        donnees.clear();
        if (realmResults.isEmpty()) {
            Toast.makeText(this.getBaseContext(), "Votre bibliothèque est vide ! ", Toast.LENGTH_LONG).show();
        } else {
            for (Livre lv : realmResults) {
                addItem(lv.getEan(), lv.getTitre(), lv.getAuteur(), String.valueOf(lv.getId()));
            }
        }

        adapter.notifyDataSetChanged();
    }

    public void addItem(String isbn, String titre, String auteur, String id) {
        HashMap<String,String> item = new HashMap<String,String>();
        item.put("isbn", isbn);
        item.put("titre", titre);
        item.put("auteur", auteur);
        item.put("id", id);

        donnees.add(item);
    }

    public void onClickTriTitre(View view) {
        donnees.clear();
        if (realmResults.isEmpty()) {
            Toast.makeText(this.getBaseContext(), "Votre bibliothèque est vide ! ", Toast.LENGTH_LONG).show();
        } else {
            realmResults=realmResults.sort("titre");
            for (Livre lv : realmResults) {
                addItem(lv.getEan(), lv.getTitre(), lv.getAuteur(), String.valueOf(lv.getId()));
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void onClickTriAuteur(View view) {
        donnees.clear();
        if (realmResults.isEmpty()) {
            Toast.makeText(this.getBaseContext(), "Votre bibliothèque est vide ! ", Toast.LENGTH_LONG).show();
        } else {
            realmResults=realmResults.sort("auteur");
            for (Livre lv : realmResults) {
                addItem(lv.getEan(), lv.getTitre(), lv.getAuteur(), String.valueOf(lv.getId()));
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void onClickhercherEditeur(View view) {
        AutoCompleteTextView actvediteur = (AutoCompleteTextView) findViewById(R.id.actvEditeur);
        String editeur = actvediteur.getText().toString();

        if(editeur.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Vous n'avez pas entré d'editeur.", Toast.LENGTH_LONG).show();
        }
        else {
            try {

                RealmQuery<Livre> rr = realm.where(Livre.class);
                if (!editeur.equals("")) {
                    rr.contains("editeur", editeur, Case.INSENSITIVE);
                }
                donnees.clear();

                realmResults= rr.findAll();
                if (realmResults.isEmpty()) {
                    Toast.makeText(this.getBaseContext(), "Oups aucune correspondance...", Toast.LENGTH_LONG).show();
                } else {
                    for (Livre lv : realmResults) {
                        addItem(lv.getEan(), lv.getTitre(), lv.getAuteur(),String.valueOf(lv.getId()));
                    }
                }

                adapter.notifyDataSetChanged();

            } catch (Exception e) {
                Log.e("Main : ", e.toString());
            }
        }
    }

    public void onClickTriChercherAuteur(View view) {
        AutoCompleteTextView actvauteur = (AutoCompleteTextView) findViewById(R.id.actvAuteur);
        String auteur = actvauteur.getText().toString();


            if(auteur.equals(""))
            {
                Toast.makeText(getApplicationContext(), "Vous n'avez pas entré d'auteur.", Toast.LENGTH_LONG).show();
            }
            else {
                try {

                    RealmQuery<Livre> rr = realm.where(Livre.class);
                    if (!auteur.equals("")) {
                        rr.contains("auteur", auteur, Case.INSENSITIVE);
                    }
                    donnees.clear();

                    realmResults= rr.findAll();
                    if (realmResults.isEmpty()) {
                        Toast.makeText(this.getBaseContext(), "Oups aucune correspondance...", Toast.LENGTH_LONG).show();
                    } else {
                        for (Livre lv : realmResults) {
                            addItem(lv.getEan(), lv.getTitre(), lv.getAuteur(), String.valueOf(lv.getId()));
                        }
                    }

                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    Log.e("Main : ", e.toString());
                }
            }
        }
    }


