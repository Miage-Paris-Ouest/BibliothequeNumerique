package com.example.alice.biblothequevirtuelle.Vue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alice.biblothequevirtuelle.R;
import com.example.alice.biblothequevirtuelle.Realm.Livre;
import com.example.alice.biblothequevirtuelle.Realm.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

public class Modifier extends AppCompatActivity {

    private Realm realm;
    private Livre livre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifier_layout);

        Intent reception = getIntent();

        realm = Realm.getDefaultInstance();

        generationSpinnerType();

        String retourId = reception.getStringExtra("livreSelectionné");
        int idLivre = Integer.parseInt(retourId);
        livre = realm.where(Livre.class).equalTo("id", idLivre).findFirst();

        autoCompletionFiche(livre);

        Button bAjout = (Button) findViewById(R.id.bAjout);
        bAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modification();
            }
        });

        CheckBox cbWish = (CheckBox) findViewById( R.id.cbWhishList);
        cbWish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                final RadioGroup rg = (RadioGroup) findViewById(R.id.rgLu);
                final CheckBox cbPret = (CheckBox) findViewById(R.id.cbPret);
                final RadioButton rbNonLu = (RadioButton) findViewById(R.id.rbNonLu);
                if ( isChecked ) {
                    rg.setVisibility(View.GONE);
                    rbNonLu.setChecked(true);
                    cbPret.setChecked(false);
                }
                else
                    rg.setVisibility(View.VISIBLE);

            }
        });
    }

    private void generationSpinnerType() {
    /*Génération de la liste déroulante contenant les types*/
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
        sType.setSelection(0);
    }

    private void modification()
    {
        final EditText etTitre = (EditText) findViewById(R.id.etTitre);
        final EditText etAuteur = (EditText) findViewById(R.id.etAuteur);
        final EditText etEditeur = (EditText) findViewById(R.id.etEditeur);
        final Spinner sType = (Spinner) findViewById(R.id.sType);
        final EditText etEan = (EditText) findViewById(R.id.etISBN);
        final EditText etCateg = (EditText) findViewById(R.id.etCategorie);
        final EditText etDate = (EditText) findViewById(R.id.etDatePub);
        final EditText etLangue = (EditText) findViewById(R.id.etLangue);
        final EditText etResume = (EditText) findViewById(R.id.etResume);
        final RadioButton rbLu =(RadioButton) findViewById(R.id.rbLu);
        final RadioButton rbNonLu =(RadioButton) findViewById(R.id.rbNonLu);
        final RadioButton rbEnCours=(RadioButton) findViewById(R.id.rbEnCours);
        CheckBox cbPret = (CheckBox) findViewById(R.id.cbPret);
        CheckBox cbWhish = (CheckBox) findViewById(R.id.cbWhishList);

        final String titre = etTitre.getText().toString();
        final String auteur = etAuteur.getText().toString();
        final String editeur = etEditeur.getText().toString();
        final int type = sType.getSelectedItemPosition();
        final String isbn = etEan.getText().toString();
        final String categ = etCateg.getText().toString();
        final String date = etDate.getText().toString();
        final String langue = etLangue.getText().toString();
        final String resume = etResume.getText().toString();
        final boolean pret = cbPret.isChecked();
        final boolean whishlist = cbWhish.isChecked();

        int statut = 0;

        if(rbNonLu.isChecked())
            statut = 0;
        else if(rbEnCours.isChecked())
            statut = 1;
        else if(rbLu.isChecked())
            statut = 2;

        final int finalStatut = statut;

        Pattern regexp = Pattern.compile("^[0-9]*");
        Matcher verif = regexp.matcher(isbn);

        if(!verif.matches() && !isbn.equals("")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Modifier.this);
            builder.setTitle("Attention !");
            builder.setMessage(" Vous n'avez pas entré d'ISBN, ou ce dernier n'est pas correct. La recherche par scanner ne pourra être faite. Voulez-vous continuer ?");

            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    modifierLivre(isbn, titre, auteur, editeur, date, langue, resume, categ, type, finalStatut, pret, whishlist);
                }
            });
            builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.show();
        }
        else
        {
            modifierLivre(isbn, titre, auteur, editeur, date, langue, resume, categ, type, statut, pret, whishlist);
        }
    }

    private void modifierLivre(final String isbn, final String titre, final String auteur, final String editeur, final String date, final String langue, final String resume, final String categ, final int type, final int statut, final boolean pret, final boolean whishlist) {
        try {
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {
                    livre.setEan(isbn);
                    livre.setTitre(titre);
                    livre.setAuteur(auteur);
                    livre.setEditeur(editeur);
                    livre.setDatePub(date);
                    livre.setLangue(langue);
                    livre.setResume(resume);
                    livre.setCategorie(categ);
                    livre.setType(realm.where(Type.class).equalTo("id", type).findFirst());
                    livre.setStatut(statut);
                    livre.setPret(pret);
                    livre.setWhishlist(whishlist);
                }
            });

            Toast.makeText(getApplicationContext(), "Modifications réussies !", Toast.LENGTH_LONG).show();
        } catch (RealmException re) {
            System.err.println(re.toString());
            Toast.makeText(getApplicationContext(), "Erreur lors de la modification", Toast.LENGTH_LONG).show();
        }
    }

    private void autoCompletionFiche(Livre l)
    {
        EditText etTitre = (EditText) findViewById(R.id.etTitre);
        EditText etAuteur = (EditText) findViewById(R.id.etAuteur);
        EditText etEditeur = (EditText) findViewById(R.id.etEditeur);
        EditText etEan = (EditText) findViewById(R.id.etISBN);
        EditText etCateg = (EditText) findViewById(R.id.etCategorie);
        EditText etDate = (EditText) findViewById(R.id.etDatePub);
        EditText etLangue = (EditText) findViewById(R.id.etLangue);
        EditText etResume = (EditText) findViewById(R.id.etResume);

        RadioButton rbLu =(RadioButton) findViewById(R.id.rbLu);
        RadioButton rbNonLu =(RadioButton) findViewById(R.id.rbNonLu);
        RadioButton rbEnCours=(RadioButton) findViewById(R.id.rbEnCours);

        CheckBox cbPret = (CheckBox) findViewById(R.id.cbPret);
        CheckBox cbWhish = (CheckBox) findViewById(R.id.cbWhishList);

        etTitre.setText(l.getTitre());
        etAuteur.setText(l.getAuteur());
        etEditeur.setText(l.getEditeur());
        etEan.setText(l.getEan());
        etCateg.setText(l.getCategorie());
        etDate.setText(l.getDatePub());
        etLangue.setText(l.getLangue());
        etResume.setText(l.getResume());
        cbPret.setChecked(l.isPret());
        cbWhish.setChecked(l.isWhishlist());

        int statut = l.getStatut();
        switch (statut){
            case 0 :
                rbNonLu.setChecked(true);
                break;
            case 1 :
                rbEnCours.setChecked(true);
                break;
            case 2 :
                rbLu.setChecked(true);
                break;
            default :
                rbNonLu.setChecked(true);
        }

        final Spinner sType = (Spinner) findViewById(R.id.sType);
        sType.setSelection(l.getType().getId());
    }
}
