package com.example.alice.biblothequevirtuelle.Vue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alice.biblothequevirtuelle.Data.Livre;
import com.example.alice.biblothequevirtuelle.R;
import com.example.alice.biblothequevirtuelle.Scanner;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ajouter extends AppCompatActivity {

    private String ean;
    private Scanner scan;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_layout);
        Intent reception = getIntent();
        ean = reception.getStringExtra("ean");
        scan = new Scanner(this);

        Button bScanner = (Button) findViewById(R.id.bScan);
        bScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan.scanner();
            }
        });

        Button bAjout = (Button) findViewById(R.id.bAjout);
        bAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText etTitre = (EditText) findViewById(R.id.etTitre);
                final EditText etAuteur = (EditText) findViewById(R.id.etAuteur);
                final EditText etEditeur = (EditText) findViewById(R.id.etEditeur);
                final EditText etType = (EditText) findViewById(R.id.etType);
                final EditText etEan = (EditText) findViewById(R.id.etISBN);
                final EditText etCateg = (EditText) findViewById(R.id.etCategorie);
                final EditText etDate = (EditText) findViewById(R.id.etDatePub);
                final EditText etLangue = (EditText) findViewById(R.id.etLangue);
                final EditText etResume = (EditText) findViewById(R.id.etResume);

                Livre ajout = new Livre(etTitre.getText().toString(), etEan.getText().toString(), etType.getText().toString(), etAuteur.getText().toString(), etEditeur.getText().toString(), etCateg.getText().toString(), etDate.getText().toString(), etLangue.getText().toString(), etResume.getText().toString());
                try
                {
                    ajout.save();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("Ajout réussi !");

                    builder.setMessage("Voulez vous ajouter un autre livre ?");
                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            etTitre.setText("");
                            etAuteur.setText("");
                            etEditeur.setText("");
                            etType.setText("");
                            etEan.setText("");
                            etCateg.setText("");
                            etDate.setText("");
                            etLangue.setText("");
                            etResume.setText("");
                            ean = null;
                        }
                    });
                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
                    builder.show();
                    Toast.makeText(getApplicationContext(), "Ce livre a été ajouté à votre bibliothèque !", Toast.LENGTH_LONG).show();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        if(ean != null)
        {
            appelGoogleBooksApi();
        }
    }

    // utilisation du résultat du scan
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String type;
        String prefix;

        if (resultCode == 0) {
            Toast.makeText(getApplicationContext(),"Aucunes données scannées", Toast.LENGTH_SHORT).show();
        }
        else if(scanningResult != null){
            ean = scanningResult.getContents().toLowerCase();
            type = scanningResult.getFormatName().toLowerCase();
            prefix = ean.substring(0, 3);

            if(!type.equals("ean_13"))
            {
                Toast.makeText(getApplicationContext(), "Mauvais format", Toast.LENGTH_SHORT).show();

            }
            else if(!(prefix.equals("977") || prefix.equals("978") || prefix.equals("979")))
            {
                Toast.makeText(getApplicationContext(),"C'est n'est pas un livre !", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "code ok : ean = "+ean+" type= "+type+" prefix="+prefix, Toast.LENGTH_LONG).show();

                ArrayList<Livre> resultat = (ArrayList<Livre>) Livre.find(Livre.class, "ean = '"+ean+"'");
                if(resultat.size() != 0)
                {
                    Toast.makeText(getApplicationContext(), "Vous avez déjà ce livre !", Toast.LENGTH_LONG).show();
                }
                else
                {
                    appelGoogleBooksApi();
                }
            }
        }
    }

    private void lectureJSON(String reponse) throws JSONException {
        JSONObject reponseJson = new JSONObject(reponse);
        if(reponseJson.has("items")) {
            JSONArray tabLivreJson = reponseJson.getJSONArray("items");
            JSONObject livreJson = tabLivreJson.getJSONObject(0);
            JSONObject infoLivreJson = livreJson.getJSONObject("volumeInfo");

            String titre;
            String auteur;
            String editeur;
            String dateEdi;
            String resume;
            String langue;

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
                EditText etEditeur = (EditText) findViewById(R.id.etEditeur);
                etEditeur.setText(editeur);
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
        }
    }

    private void appelGoogleBooksApi()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+ean;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "onResponse", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "onErrorResponse", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(stringRequest);
    }


}
