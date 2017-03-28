package com.example.alice.biblothequevirtuelle.Vue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
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
import com.example.alice.biblothequevirtuelle.AppelService.Scanner;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * TODO implémenter le fait que le type et la catégorie doivent être des listes déroulantes
 */
public class Ajouter extends AppCompatActivity {

    private String ean;
    private Scanner scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_layout);
        Intent reception = getIntent();
        ean = reception.getStringExtra("ean");
        scan = new Scanner(this);

        if (ean != null) {
            appelGoogleBooksApi(ean);
        }

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

                String titre = etTitre.getText().toString();
                String auteur = etAuteur.getText().toString();
                String editeur = etEditeur.getText().toString();
                String type = etType.getText().toString();
                String isbn = etEan.getText().toString();
                String categ = etCateg.getText().toString();
                String date = etDate.getText().toString();
                String langue = etLangue.getText().toString();
                String resume = etResume.getText().toString();

                if (!titre.equals("") && !auteur.equals("") && !isbn.equals("")) {
                    Livre ajout = new Livre(titre, isbn, type, auteur, editeur, categ, date, langue, resume);
                    try {
                        ajout.save();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Ajouter.this);
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
                                scan.scanner();
                            }
                        });
                        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                        builder.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Il manque un champ obligatoire !", Toast.LENGTH_LONG).show();
            }
        });
    }

    // utilisation du résultat du scan
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String type;
        String prefix;
        AlertDialog.Builder builder = new AlertDialog.Builder(Ajouter.this);

        if (resultCode == 0) {
            builder.setTitle("Aucune données scannées !");
        } else if (scanningResult != null) {
            ean = scanningResult.getContents().toLowerCase();
            type = scanningResult.getFormatName().toLowerCase();
            prefix = ean.substring(0, 3);

            if (!type.equals("ean_13")) {
                builder.setTitle("Mauvais format");

            } else if (!(prefix.equals("977") || prefix.equals("978") || prefix.equals("979"))) {
                builder.setTitle("Ce n'est pas un livre !");
            } else {
                final ArrayList<Livre> resultat = (ArrayList<Livre>) Livre.find(Livre.class, "ean = '" + ean + "'");
                if (resultat.size() == 0) {
                    builder.setTitle("Vous n'avez pas ce livre !");
                    builder.setMessage("Voulez vous l'ajouter à votre bibliothèque ?");
                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent ajout = new Intent(getApplicationContext(), Ajouter.class);
                            ajout.putExtra("ean", ean);
                            startActivity(ajout);
                        }
                    });
                } else {
                    builder.setTitle("Vous avez déjà ce livre !");
                    builder.setMessage("Que voulez vous faire ?");
                    builder.setNeutralButton("Supprimer", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            resultat.get(0).delete();
                        }
                    });
                    builder.setNeutralButton("Scanner un autre livre", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            scan.scanner();
                        }
                    });
                    builder.setNegativeButton("Rien", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                }

            }
            builder.setMessage("Voulez vous scanner un autre livre ?");
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ean = null;
                    scan.scanner();
                }
            });
            builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });

        }
        builder.show();
    }


    private Livre lectureJSON(String reponse) throws JSONException {
        Livre ajout = null;
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

            if (infoLivreJson.has("title")) {
                titre = infoLivreJson.getString("title");
            }

            EditText etEan = (EditText) findViewById(R.id.etISBN);
            etEan.setText(ean);

            if (infoLivreJson.has("authors")) {
                auteur = infoLivreJson.getJSONArray("authors").getString(0);
            }

            if (infoLivreJson.has("publisher")) {
                editeur = infoLivreJson.getString("publisher");
            }
            if (infoLivreJson.has("publisherDate")) {
                dateEdi = infoLivreJson.getString("publisherDate");
            }

            if (infoLivreJson.has("resume")) {
                resume = infoLivreJson.getString("resume");
            }
            if (infoLivreJson.has("language")) {
                langue = infoLivreJson.getString("language");
            }
            ajout = new Livre(titre, ean, auteur, editeur, dateEdi, langue, resume);
        }
        else
            Toast.makeText(getApplicationContext(), "Nous n'avons pas d'informations sur ce livre.", Toast.LENGTH_LONG).show();

        return ajout;
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
