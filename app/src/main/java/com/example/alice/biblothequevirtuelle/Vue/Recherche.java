package com.example.alice.biblothequevirtuelle.Vue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alice.biblothequevirtuelle.Appli.BVAppli;
import com.example.alice.biblothequevirtuelle.R;
import com.example.alice.biblothequevirtuelle.Realm.RLivre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Kiki on 28/03/2017.
 */

public class Recherche extends AppCompatActivity {
    ArrayList<HashMap<String, String>> donnees = new ArrayList<>();
    SimpleAdapter adapter;
    ListView listLivres;
    boolean dsMaBibli;
    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recherche_layout);

        realm = Realm.getInstance(BVAppli.getInstance());

        adapter = new SimpleAdapter(this, donnees, R.layout.item,
                new String[]{"isbn", "titre", "auteur"},
                new int[]{R.id.cellule_isbn, R.id.cellule_titre, R.id.cellule_auteur});
        listLivres = (ListView) findViewById(R.id.listView_Recherche);
        listLivres.setAdapter(adapter);

        Button bMaBlibli = (Button) findViewById(R.id.bRechercheInterne);
        bMaBlibli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dsMaBibli = true;
                rechercher(v);
            }
        });

        Button bApiBlibli = (Button) findViewById(R.id.bRechercheExterne);
        bApiBlibli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dsMaBibli = false;
                rechercher(v);
            }
        });
    }


    public void addItem(String isbn, String titre, String auteur) {
        HashMap<String,String> item = new HashMap<String,String>();
        item.put("isbn", isbn);
        item.put("titre", titre);
        item.put("auteur", auteur);
        donnees.add(item);
    }


    public void rechercher(View v){

        EditText etisbn = (EditText) findViewById(R.id.etISBN);
        EditText ettitre = (EditText) findViewById(R.id.etTitre);
        EditText etauteur = (EditText) findViewById(R.id.etAuteur);

        final String isbn = etisbn.getText().toString();
        String titre = ettitre.getText().toString();
        String auteur = etauteur.getText().toString();

        // si on a choisi la recherche dans la biliothèque personnelle
        if(dsMaBibli) {
            if(isbn.equals("") && titre.equals("") && auteur.equals(""))
            {
                Toast.makeText(getApplicationContext(), "Vous n'avez entré aucun élément de recherche.", Toast.LENGTH_LONG).show();
            }
            else {
                try {

                    RealmQuery<RLivre> rr = realm.where(RLivre.class);
                    if (!isbn.equals("")) {
                        rr = rr.equalTo("ean", isbn);
                    }

                    if (!titre.equals("")) {
                        rr = rr.equalTo("titre", titre);
                    }

                    if (!auteur.equals("")) {
                        rr.equalTo("auteur", auteur);
                    }

                    donnees.clear();

                    RealmResults<RLivre> rrLivre = rr.findAll();
                    if (rrLivre.isEmpty()) {
                        Toast.makeText(this.getBaseContext(), "Oups aucune correspondance...", Toast.LENGTH_LONG).show();
                    } else {
                        for (RLivre lv : rrLivre) {
                            addItem(lv.getEan(), lv.getTitre(), lv.getAuteur());
                        }
                    }

                    adapter.notifyDataSetChanged();


                } catch (Exception e) {
                    Log.e("Main : ", e.toString());
                }
            }
        }
        // Si on a choisis la recherche dans la BDD de l'application
        else
        {
            StringBuilder sb = new StringBuilder("https://www.googleapis.com/books/v1/volumes?q=");
            RequestQueue queue = Volley.newRequestQueue(this);

            if (!isbn.equals("")) {
                if (!sb.toString().equals("https://www.googleapis.com/books/v1/volumes?q="))
                    sb.append("+");
                sb.append("isbn:").append(isbn);
            }

            if (!titre.equals("")) {
                if (!sb.toString().equals("https://www.googleapis.com/books/v1/volumes?q="))
                    sb.append("+");
                sb.append("intitle:").append(titre);
            }

            if (!auteur.equals("")) {
                if (!sb.toString().equals("https://www.googleapis.com/books/v1/volumes?q="))
                    sb.append("+");
                sb.append("inauthor").append(auteur);
            }

            String url = sb.toString();
            if(!url.equals("https://www.googleapis.com/books/v1/volumes?q=")) {
                final ProgressBar pb = (ProgressBar) findViewById(R.id.pbRecherche);
                pb.setVisibility(View.VISIBLE);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, sb.toString(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    pb.setVisibility(View.GONE);
                                    donnees.clear();
                                    lectureJSON(response, isbn);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    pb.setVisibility(View.GONE);
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pb.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Une erreur s'est produite.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                queue.add(stringRequest);
            }
            else
                Toast.makeText(getApplicationContext(), "Vous n'avez entré aucun élément de recherche.", Toast.LENGTH_LONG).show();
        }
    }

    private void lectureJSON(String reponse, String ean) throws JSONException {
        JSONObject reponseJson = new JSONObject(reponse);


        if(reponseJson.has("items")) {
            JSONArray tabLivreJson = reponseJson.getJSONArray("items");
            if(tabLivreJson.length() > 10)
            {
                Toast.makeText(getApplicationContext(), "Il y a plus d'une dizaine de correspondance, précisez votre recherche.", Toast.LENGTH_LONG).show();
            }
            else {
                for (int i = 0; i < tabLivreJson.length(); i++) {
                    JSONObject livreJson = tabLivreJson.getJSONObject(i);
                    JSONObject infoLivreJson = livreJson.getJSONObject("volumeInfo");

                    String titre = null;
                    String auteur = null;

                    if (infoLivreJson.has("title")) {
                        titre = infoLivreJson.getString("title");
                    }

                    EditText etEan = (EditText) findViewById(R.id.etISBN);
                    etEan.setText(ean);

                    if (infoLivreJson.has("authors")) {
                        auteur = infoLivreJson.getJSONArray("authors").getString(0);
                    }

                    addItem(ean, titre, auteur);
                }
            }
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Recherche.this);
            
            builder.setTitle("Aucun résultat");
            builder.setMessage("Voulez vous l'ajouter à la main ?");
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(getApplicationContext(), Ajouter.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
        }
    }

}
