package com.example.alice.biblothequevirtuelle.Firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.alice.biblothequevirtuelle.R;
import com.example.alice.biblothequevirtuelle.Vue.Accueil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Audrey on 10/05/2017.
 */

public class Authentification extends AppCompatActivity {

    private EditText etAdresseMail, etMdpConnexion;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button bSignUp, bResetPassword, bValiderInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Authentification.this, CreationCompte.class));
            finish();
        }

        setContentView(R.layout.fragment_connexion_layout);

        etAdresseMail = (EditText) findViewById(R.id.etAdresseMail);
        etMdpConnexion = (EditText) findViewById(R.id.etMdpConnexion);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        bSignUp = (Button) findViewById(R.id.bSignUp);
        bResetPassword = (Button) findViewById(R.id.bResetPassword);
        bValiderInscription = (Button) findViewById(R.id.bValiderInscription);

        auth = FirebaseAuth.getInstance();

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Authentification.this, CreationCompte.class));
            }
        });

        bResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Authentification.this, ResetPasswordActivity.class));
            }
        });

        bValiderInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etAdresseMail.getText().toString();
                String password = etMdpConnexion.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password !", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Authentification.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Authentification.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    startActivity(new Intent(Authentification.this, Accueil.class));
                                }

                            }
                        });

            }
        });
    }
}