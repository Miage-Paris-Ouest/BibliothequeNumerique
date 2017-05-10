package com.example.alice.biblothequevirtuelle.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alice.biblothequevirtuelle.Appli.BVAppli;
import com.example.alice.biblothequevirtuelle.R;
import com.example.alice.biblothequevirtuelle.Realm.Livre;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmResults;

/*
TODO Ajouter la recherche et le filtrage
 */
public class MesLivres extends AppCompatActivity {

    ArrayList<HashMap<String, String>> donnees = new ArrayList<>();
    SimpleAdapter adapter;
    ListView listLivres;
    Realm realm;

    public class listeLivreClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            TextView tvEan = (TextView) view.findViewById(R.id.tvCelluleIsbn);
            String ean = tvEan.getText().toString();
            Intent intent = new Intent(parent.getContext(), Modifier.class);
            intent.putExtra("livreSelectionné", ean);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meslivres_layout);


        realm = Realm.getInstance(BVAppli.getInstance());

        adapter = new SimpleAdapter(this, donnees, R.layout.livre_liste_layout,
                new String[]{"isbn", "titre", "auteur"},
                new int[]{R.id.tvCelluleIsbn, R.id.tvCelluleTitre, R.id.tvCelluleAuteur});
        listLivres = (ListView) findViewById(R.id.lvMeslivres);
        listLivres.setAdapter(adapter);
        listLivres.setOnItemClickListener(new listeLivreClickHandler());

        RealmResults<Livre> realmResults = realm.where(Livre.class).findAll();
        if (realmResults.isEmpty()) {
            Toast.makeText(this.getBaseContext(), "Votre bibliothèque est vide ! ", Toast.LENGTH_LONG).show();
        } else {
            for (Livre lv : realmResults) {
                addItem(lv.getEan(), lv.getTitre(), lv.getAuteur());
            }
        }

        adapter.notifyDataSetChanged();
    }

    public void addItem(String isbn, String titre, String auteur) {
        HashMap<String,String> item = new HashMap<String,String>();
        item.put("isbn", isbn);
        item.put("titre", titre);
        item.put("auteur", auteur);
        donnees.add(item);
    }


}
