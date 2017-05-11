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

import com.example.alice.biblothequevirtuelle.R;
import com.example.alice.biblothequevirtuelle.Realm.Livre;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by alice on 11/05/2017.
 */

public class WhishList extends AppCompatActivity
{
    ArrayList<HashMap<String, String>> donnees = new ArrayList<>();
    SimpleAdapter adapter;
    ListView listLivres;
    Realm realm;

    private class listeLivreClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            TextView tvId = (TextView) view.findViewById(R.id.tvIdHidden);
            String idLivre = tvId.getText().toString();
            Intent intent = new Intent(parent.getContext(), Modifier.class);
            intent.putExtra("livreSelectionné", idLivre);
            intent.putExtra("précédent", "WhishList");
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_layout);


        realm = Realm.getDefaultInstance();

        adapter = new SimpleAdapter(this, donnees, R.layout.livre_liste_layout,
                new String[]{"isbn", "titre", "auteur", "id"},
                new int[]{R.id.tvCelluleIsbn, R.id.tvCelluleTitre, R.id.tvCelluleAuteur, R.id.tvIdHidden});
        listLivres = (ListView) findViewById(R.id.lvMaWishlist);
        listLivres.setAdapter(adapter);
        listLivres.setOnItemClickListener(new listeLivreClickHandler());

       /* RealmResults<Livre> realmResults = realm.where(Livre.class).equalTo("whishlist", true).findAll();
        if (realmResults.isEmpty()) {
            Toast.makeText(this.getBaseContext(), "Votre WhishList est vide ! ", Toast.LENGTH_LONG).show();
        } else {
            for (Livre lv : realmResults) {
                addItem(lv.getEan(), lv.getTitre(), lv.getAuteur(), String.valueOf(lv.getId()));
            }
        }

        adapter.notifyDataSetChanged();*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        RealmResults<Livre> realmResults = realm.where(Livre.class).equalTo("whishlist", true).findAll();
        donnees.clear();
        if (realmResults.isEmpty()) {
            Toast.makeText(this.getBaseContext(), "Votre WhishList est vide ! ", Toast.LENGTH_LONG).show();
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
}