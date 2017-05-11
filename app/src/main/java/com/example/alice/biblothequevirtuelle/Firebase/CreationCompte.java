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

import com.example.alice.biblothequevirtuelle.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
                Intent intent = new Intent(CreationCompte.this, Authentification.class);
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
                    Toast.makeText(getApplicationContext(), "Votre mot de passe toi faire minimum 6 caratères !", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                // Création de l'utilisateur
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CreationCompte.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(CreationCompte.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(CreationCompte.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    startActivity(new Intent(CreationCompte.this, Authentification.class));
                                    finish();
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