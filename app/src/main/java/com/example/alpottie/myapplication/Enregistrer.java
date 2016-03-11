package com.example.alpottie.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alpottie.myapplication.BDD.GestionAuteur;
import com.example.alpottie.myapplication.BDD.GestionLivre;
import com.example.alpottie.myapplication.Donnees.Auteur;
import com.example.alpottie.myapplication.Donnees.Livre;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Enregistrer extends AppCompatActivity {

    List<Auteur> auteurs;
    GestionAuteur ga;
    GestionLivre gl;
    Bitmap imageBitmap;
    Spinner dropdown;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void modifDD(List<Auteur> liste)
    {
        List<String> listeAuteurs = new ArrayList<>();
        for(Auteur a : liste)
        {
            listeAuteurs.add(a.getNom()+","+a.getPrenom());
        }
        dropdown = (Spinner) findViewById(R.id.ddauteur);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listeAuteurs
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrer);

        ga = new GestionAuteur(this);
        gl = new GestionLivre(this);
        auteurs = ga.getAuteurs();
        modifDD(auteurs);
        findViewById(R.id.layoutAjoutAuteur).setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            ImageView iv = (ImageView) findViewById(R.id.ivCouverture);
            iv.setImageBitmap(imageBitmap);

        }
    }

    public void onClickAjoutAuteur(View v)
    {
        EditText etnom = (EditText) findViewById(R.id.nom_auteur);
        EditText etprenom = (EditText) findViewById(R.id.prenom_auteur);
        if((etnom.getText().toString()).isEmpty() || (etprenom.getText().toString()).isEmpty())
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Il manque des informations !", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Auteur auteur = new Auteur(etnom.getText().toString(), etprenom.getText().toString());
            Long res = ga.save(auteur);
            Log.d("Res de l'insertion: ", "=" + res);
            if(res == -1)
            {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Erreur pendant l'ajout.", Toast.LENGTH_SHORT);
                toast.show();
            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Ajout ok !", Toast.LENGTH_SHORT);
                toast.show();
                auteurs = ga.getAuteurs();
                modifDD(auteurs);
                if(res == 0)
                    dropdown.setSelection(0);
                else
                    dropdown.setSelection(res.intValue()-1);
                findViewById(R.id.layoutAjoutAuteur).setVisibility(View.INVISIBLE);
            }
        }
    }
    public void onClickAfficherAjout(View v)
    {
        View vue = findViewById(R.id.layoutAjoutAuteur);
        if(vue.getVisibility() == View.INVISIBLE)
            vue.setVisibility(View.VISIBLE);
        else
            vue.setVisibility(View.INVISIBLE);

    }
    public void onClickAjoutCouverture(View v)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void onClickAjoutLivre(View v)
    {
        EditText ettitre = (EditText) findViewById(R.id.titre);
        String titre = ettitre.getText().toString();
        String ean = getIntent().getStringExtra("ean");

        //region couverture
        // On convertie la photo en base64
        String couverture;
        if(imageBitmap == null)
            imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icone);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        couverture = Base64.encodeToString(byteArray, Base64.DEFAULT);
        //endregion

        Spinner dd = (Spinner) findViewById(R.id.ddauteur);
        long auteurid = dd.getSelectedItemId()+1;

        Auteur a = ga.getAuteur(auteurid);
        Toast toast;

        if(titre.isEmpty() || a == null)
        {
            toast = Toast.makeText(this, "Il manqe le titre ou l'auteur !", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Livre l = new Livre(ean, titre, a, couverture);
            Long res = gl.save(l);

            if (res == -1) {
                toast = Toast.makeText(getApplicationContext(),
                        "Erreur pendant l'ajout.", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                toast = Toast.makeText(getApplicationContext(), "Ajout ok !", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(this, AfficherLivre.class);
                intent.putExtra("livre", new Livre(ean, titre, ga.getAuteur(auteurid), couverture));
                startActivity(intent);
            }
        }
    }

    public void onClickRetourAccueil(View v)
    {
        Intent retour = new Intent(this, Accueil.class);
        startActivity(retour);
    }

}
