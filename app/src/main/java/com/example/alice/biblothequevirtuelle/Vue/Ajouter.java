package com.example.alice.biblothequevirtuelle.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alice.biblothequevirtuelle.Appli.BVAppli;
import com.example.alice.biblothequevirtuelle.R;
import com.example.alice.biblothequevirtuelle.Realm.Livre;
import com.example.alice.biblothequevirtuelle.Realm.Statut;
import com.example.alice.biblothequevirtuelle.Realm.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

public class Ajouter extends AppCompatActivity {

    private String ean;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_layout);

        Intent reception = getIntent();
        ean = reception.getStringExtra("ean");

        realm = Realm.getInstance(BVAppli.getInstance());

        final Spinner sType = (Spinner) findViewById(R.id.sType);

        RealmResults listeType = realm.where(Type.class).findAll();
        ArrayList<String> types = new ArrayList<>();
        Iterator iterator = listeType.iterator();
        while(iterator.hasNext()){
            Type t = (Type) iterator.next();
            types.add(t.getNom());
        }
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sType.setAdapter(dataAdapterR);

        sType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tvType = (TextView) findViewById(R.id.tvTypeHidden);
                tvType.setText(String.valueOf(sType.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sType.setSelection(0);
                TextView tvType = (TextView) findViewById(R.id.tvTypeHidden);
                tvType.setText(String.valueOf(sType.getSelectedItem()));

            }
        });

        if (ean != null) {
            appelGoogleBooksApi(ean);
        }

        final EditText etEan = (EditText) findViewById(R.id.etISBN);
        if(etEan.getText().toString().equals(""))//si l'api google na pas trouvé d'info on empli l'edittext ean avec l'ean que le scanner a trouvé
            etEan.setText(ean);

        Button bAjout = (Button) findViewById(R.id.bAjout);
        bAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText etTitre = (EditText) findViewById(R.id.etTitre);
                final EditText etAuteur = (EditText) findViewById(R.id.etAuteur);
                final EditText etEditeur = (EditText) findViewById(R.id.etEditeur);
                final TextView tvType = (TextView) findViewById(R.id.tvTypeHidden);
                //final EditText etEan = (EditText) findViewById(R.id.etISBN);
                final EditText etCateg = (EditText) findViewById(R.id.etCategorie);
                final EditText etDate = (EditText) findViewById(R.id.etDatePub);
                final EditText etLangue = (EditText) findViewById(R.id.etLangue);
                final EditText etResume = (EditText) findViewById(R.id.etResume);
                final RadioButton rbLu =(RadioButton) findViewById(R.id.rbLu);
                final RadioButton rbNonLu =(RadioButton) findViewById(R.id.rbNonLu);
                final RadioButton rbEnCours=(RadioButton) findViewById(R.id.rbEnCours);
                final RadioButton rbNonPret=(RadioButton) findViewById(R.id.rbNonPrete);
                final RadioButton rbPret=(RadioButton) findViewById(R.id.rbPrete);

                final String titre = etTitre.getText().toString();
                final String auteur = etAuteur.getText().toString();
                final String editeur = etEditeur.getText().toString();
                final String type = tvType.getText().toString();
                final String isbn = etEan.getText().toString();
                final String categ = etCateg.getText().toString();
                final String date = etDate.getText().toString();
                final String langue = etLangue.getText().toString();
                final String resume = etResume.getText().toString();

                // récupère l'élément coché grace a l'id trouvé ci dessus
                String statutLu="";
                String statutPret="";

                if(rbLu.isChecked())
                    statutLu="Lu";
                else if(rbNonLu.isChecked())
                    statutLu="En Cours";
                else if(rbEnCours.isChecked())
                    statutLu="A Lire";

                if(rbPret.isChecked())
                    statutPret="Preté";
                else if(rbNonPret.isChecked())
                    statutPret="Non Preté";

                final String finstatutLu=statutLu;
                final String finstatutPret=statutPret;

                if (!titre.equals("") && !auteur.equals("") && !isbn.equals("")) {
                    try {

                        realm.executeTransaction(new Realm.Transaction() {
                                                     @Override
                                                     public void execute(Realm realm) {
                                                         Livre rl = realm.createObject(Livre.class);
                                                         rl.setEan(ean);
                                                         rl.setTitre(titre);
                                                         rl.setAuteur(auteur);
                                                         rl.setEditeur(editeur);
                                                         rl.setDatePub(date);
                                                         rl.setLangue(langue);
                                                         rl.setResume(resume);
                                                         rl.setCategorie(categ);
                                                         rl.setType(realm.where(Type.class).equalTo("nom", type).findFirst());
                                                         //------------------ recupérer la liste dans rlivre puis faire add((realm.where... pour les deux
                                                         rl.getStatut().add(0,(realm.where(Statut.class).equalTo("intitule", finstatutLu).findFirst()));
                                                         rl.getStatut().add(1,(realm.where(Statut.class).equalTo("intitule", finstatutPret).findFirst()));
                                                     }
                                                 });

                        Toast.makeText(getApplicationContext(), "Ajout réussi !", Toast.LENGTH_LONG).show();

                    } catch (RealmException re) {
                        System.err.println(re.toString());
                        Toast.makeText(getApplicationContext(), "Erreur lors de l'ajout", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Il manque un champ obligatoire !", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Livre lectureJSON(String reponse) throws JSONException
    {
        JSONObject reponseJson = new JSONObject(reponse);
        if(reponseJson.has("items"))
        {
            JSONArray tabLivreJson = reponseJson.getJSONArray("items");
            JSONObject livreJson = tabLivreJson.getJSONObject(0);
            JSONObject infoLivreJson = livreJson.getJSONObject("volumeInfo");

            String titre = null;
            String auteur = null;
            String editeur = null;
            String dateEdi = null;
            String resume = null;
            String langue = null;

            TextView tvCache = (TextView) findViewById(R.id.tvTypeHidden);

            if (infoLivreJson.has("title")) {
                titre = infoLivreJson.getString("title");
                EditText etTitre = (EditText) findViewById(R.id.etTitre);
                etTitre.setText(titre);
            }

            EditText etEan = (EditText) findViewById(R.id.etISBN);
            etEan.setText(ean);

            if (infoLivreJson.has("authors")) {
                auteur = infoLivreJson.getJSONArray("authors").getString(0);
                EditText etAuteur = (EditText) findViewById(R.id.etAuteur);
                etAuteur.setText(auteur);
            }

            if (infoLivreJson.has("publisher")) {
                editeur = infoLivreJson.getString("publisher");
                EditText etEdit = (EditText) findViewById(R.id.etEditeur);
                etEdit.setText(editeur);
            }
            if (infoLivreJson.has("publisherDate")) {
                dateEdi = infoLivreJson.getString("publisherDate");
                EditText etDate = (EditText) findViewById(R.id.etDatePub);
                etDate.setText(dateEdi);
            }

            if (infoLivreJson.has("resume")) {
                resume = infoLivreJson.getString("resume");
                EditText etResume = (EditText) findViewById(R.id.etResume);
                etResume.setText(resume);
            }
            if (infoLivreJson.has("language")) {
                langue = infoLivreJson.getString("language");
                EditText etLangue = (EditText) findViewById(R.id.etLangue);
                etLangue.setText(langue);
            }
            try {
                return new Livre(ean, titre, auteur, editeur, dateEdi, resume, langue, realm.where(Type.class).equalTo("nom", tvCache.getText().toString()).findFirst());
            }catch (RealmException re)
            {
                System.err.println(re.toString());
                Toast.makeText(getApplicationContext(), "Erreur lors de l'ajout", Toast.LENGTH_LONG).show();
            }
        }
        else
            Toast.makeText(getApplicationContext(), "Nous n'avons pas d'informations sur ce livre.", Toast.LENGTH_LONG).show();

        return null;
    }

    private void appelGoogleBooksApi(String ean)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+ean;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            lectureJSON(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Une erreur s'est produite.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(stringRequest);
    }
}
