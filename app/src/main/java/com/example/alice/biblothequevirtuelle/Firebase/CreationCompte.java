package com.example.alice.biblothequevirtuelle.Firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.alice.biblothequevirtuelle.Appli.BVAppli;
import com.example.alice.biblothequevirtuelle.R;
import com.example.alice.biblothequevirtuelle.Realm.Utilisateur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreationCompte extends AppCompatActivity {


    private Button bValiderInscription;
    private Button bSignIn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    private EditText etMdpInscription1;
    private EditText etMailInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription_layout);

        auth = FirebaseAuth.getInstance();

        bValiderInscription = (Button) findViewById(R.id.bValiderInscription);
        etMailInscription = (EditText) findViewById(R.id.etMailInscription);
        etMdpInscription1 = (EditText) findViewById(R.id.etMdpInscription1);
        bSignIn = (Button) findViewById(R.id.bSignIn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

       bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Authentification.class);
                startActivity(intent);
            }
        });

        bValiderInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etMailInscription.getText().toString().trim();
                String password = etMdpInscription1.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Entrez votre adresse mail !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Entrez votre mot de passe !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Votre mot de passe doit faire minimum 6 caratères !", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                // Création de l'utilisateur dans la partie authentification de Firebase
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CreationCompte.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(CreationCompte.this, "Problème lors de l'inscription", Toast.LENGTH_LONG).show();
                                    System.out.println("Erreur d'inscription = "+task.getException());
                                }
                                else {
                                    Toast.makeText(CreationCompte.this, "Inscription réussie, veuillez vous authentifier !", Toast.LENGTH_LONG).show();

                                    //création utilisateur dans la partie BDD de Firebase
                                    // On récupère l'utilisateur créé à l'instant par FirebaseAuth
                                    FirebaseUser user = task.getResult().getUser();
                                    //On réucpère une référence vers notre BDD Firebase
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                                    // On créé l'utilisateur Realm
                                    Utilisateur util = new Utilisateur(user.getEmail(),user.getUid());
                                    BVAppli.setUtilisateur(util);
                                    // On l'ajoute à la table "Utilisateurs" de notre BDD Firebase
                                    // Dans la référence de notre BDD, on prend la table Utilisateurs puis on ajoute l'utilisateur créer précédemment
                                    ref.child(String.valueOf(R.string.tableUtilisateur)).child(user.getUid()).setValue(util);
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}