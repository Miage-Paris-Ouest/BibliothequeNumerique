package com.example.alice.biblothequevirtuelle.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alice.biblothequevirtuelle.R;
import com.example.alice.biblothequevirtuelle.Realm.Livre;
import com.example.alice.biblothequevirtuelle.Realm.Type;

import java.util.ArrayList;
import java.util.Iterator;

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

        String precedent = reception.getStringExtra("précédent");
        String retourId = reception.getStringExtra("livreSelectionné");
        int idLivre = Integer.parseInt(retourId);
        livre = realm.where(Livre.class).equalTo("id", idLivre).findFirst();

        autoCompletionFiche(livre);

        Button bAjout = (Button) findViewById(R.id.bAjout);
        bAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modification(livre);
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
    }

    private void modification(Livre livre)
    {

        Toast.makeText(this, "Click modif", Toast.LENGTH_SHORT).show();
        final EditText etTitre = (EditText) findViewById(R.id.etTitre);
        final EditText etAuteur = (EditText) findViewById(R.id.etAuteur);
        final EditText etEditeur = (EditText) findViewById(R.id.etEditeur);
        final Spinner sType = (Spinner) findViewById(R.id.sType);
        final EditText etEan = (EditText) findViewById(R.id.etISBN);
        final EditText etCateg = (EditText) findViewById(R.id.etCategorie);
        final EditText etDate = (EditText) findViewById(R.id.etDatePub);
        final EditText etLangue = (EditText) findViewById(R.id.etLangue);
        final EditText etResume = (EditText) findViewById(R.id.etResume);
        /*final RadioButton rbLu =(RadioButton) findViewById(R.id.rbLu);
        final RadioButton rbNonLu =(RadioButton) findViewById(R.id.rbNonLu);
        final RadioButton rbEnCours=(RadioButton) findViewById(R.id.rbEnCours);
        final RadioButton rbNonPret=(RadioButton) findViewById(R.id.rbNonPrete);
        final RadioButton rbPret=(RadioButton) findViewById(R.id.rbPrete);*/

        final String titre = etTitre.getText().toString();
        final String auteur = etAuteur.getText().toString();
        final String editeur = etEditeur.getText().toString();
        final int type = sType.getSelectedItemPosition();
        final String isbn = etEan.getText().toString();
        final String categ = etCateg.getText().toString();
        final String date = etDate.getText().toString();
        final String langue = etLangue.getText().toString();
        final String resume = etResume.getText().toString();

        /*// récupère l'élément coché grace a l'id trouvé ci dessus
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
        final String finstatutPret=statutPret;*/

        if (!titre.equals("") && !auteur.equals("") && !isbn.equals("")) {
            try {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        modifierLivre(isbn, titre, auteur, editeur, date, langue, resume, categ, type);
                    }
                });

                Toast.makeText(getApplicationContext(), "Modification réussis !", Toast.LENGTH_LONG).show();

            } catch (RealmException re) {
                System.err.println(re.toString());
                Toast.makeText(getApplicationContext(), "Erreur lors de la modification", Toast.LENGTH_LONG).show();
            }
        } else
            Toast.makeText(getApplicationContext(), "Il manque un champ obligatoire !", Toast.LENGTH_LONG).show();
    }

    private void modifierLivre(String isbn, String titre, String auteur, String editeur, String date, String langue, String resume, String categ, int type) {
        livre.setEan(isbn);
        livre.setTitre(titre);
        livre.setAuteur(auteur);
        livre.setEditeur(editeur);
        livre.setDatePub(date);
        livre.setLangue(langue);
        livre.setResume(resume);
        livre.setCategorie(categ);
        livre.setType(realm.where(Type.class).equalTo("id", type).findFirst());
        /*//------------------ recupérer la liste dans rlivre puis faire add((realm.where... pour les deux
        livre.getStatut().add(0,(realm.where(Statut.class).equalTo("intitule", finstatutLu).findFirst()));
        livre.getStatut().add(1,(realm.where(Statut.class).equalTo("intitule", finstatutPret).findFirst()));*/
    }

    private void autoCompletionFiche(Livre l)
    {
        final EditText etTitre = (EditText) findViewById(R.id.etTitre);
        final EditText etAuteur = (EditText) findViewById(R.id.etAuteur);
        final EditText etEditeur = (EditText) findViewById(R.id.etEditeur);
        final EditText etEan = (EditText) findViewById(R.id.etISBN);
        final EditText etCateg = (EditText) findViewById(R.id.etCategorie);
        final EditText etDate = (EditText) findViewById(R.id.etDatePub);
        final EditText etLangue = (EditText) findViewById(R.id.etLangue);
        final EditText etResume = (EditText) findViewById(R.id.etResume);
        /*final RadioButton rbLu =(RadioButton) findViewById(R.id.rbLu);
        final RadioButton rbNonLu =(RadioButton) findViewById(R.id.rbNonLu);
        final RadioButton rbEnCours=(RadioButton) findViewById(R.id.rbEnCours);
        final RadioButton rbNonPret=(RadioButton) findViewById(R.id.rbNonPrete);
        final RadioButton rbPret=(RadioButton) findViewById(R.id.rbPrete);*/

        etTitre.setText(l.getTitre());
        etAuteur.setText(l.getAuteur());
        etEditeur.setText(l.getEditeur());
        etEan.setText(l.getEan());
        etCateg.setText(l.getCategorie());
        etDate.setText(l.getDatePub());
        etLangue.setText(l.getLangue());
        etResume.setText(l.getResume());

        final Spinner sType = (Spinner) findViewById(R.id.sType);
        sType.setSelection(l.getType().getId());
    }
}
