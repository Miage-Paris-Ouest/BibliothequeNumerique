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
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Audrey on 10/05/2017.
 */

public class ReinitMotDePasse extends AppCompatActivity {

    private EditText etAdresseMail;
    private Button bReset, bBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_layout);

        etAdresseMail = (EditText) findViewById(R.id.etAdresseMail);
        bReset = (Button) findViewById(R.id.bReset);
        bBack = (Button) findViewById(R.id.bBack);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance().getInstance();

        bBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ReinitMotDePasse.this, Authentification.class);
                startActivity(intent);
            }
        });

        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etAdresseMail.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplication(), "Veuillez saisir votre mail ! ", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(ReinitMotDePasse.this, "Vous allez recevoir un mail pour réinitialiser votre mail !", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(ReinitMotDePasse.this, "Erreur, le mail n'a pas pu être envoyé", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }



}
