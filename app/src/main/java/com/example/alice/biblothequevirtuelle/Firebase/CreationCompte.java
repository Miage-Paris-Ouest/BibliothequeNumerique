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
    private Button btnResetPassword;
    private Button btnSignIn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    private EditText etMdpInscription1;
    private EditText etMailInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_inscription_layout);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        bValiderInscription = (Button) findViewById(R.id.bValiderInscription);
        etMailInscription = (EditText) findViewById(R.id.etMailInscription);
        etMdpInscription1 = (EditText) findViewById(R.id.etMdpInscription1);
       // btnResetPassword = (Button) findViewById(R.id.btnResetPassword);
        //btnSignIn = (Button) findViewById(R.id.btnSignIn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


       /* btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleLogin authClient = new SimpleLogin(myRef, getApplicationContex());
                authClient.sendPasswordResetEmail("email@example.com", new SimpleLoginCompletionHandler() {
                    public void completed(FirebaseSimpleLoginError error, boolean success) {
                        if(error != null) {
                            // There was an error processing this request
                        }
                        else if(success) {
                            // Password reset email sent successfully
                        }
                    }
                });
            }*/
        //});

       /* btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        bValiderInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etMailInscription.getText().toString().trim();
                String password = etMdpInscription1.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CreationCompte.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(CreationCompte.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(CreationCompte.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }/* else {
                                    startActivity(new Intent(SignupActivity.this, CreationCompte.class));
                                    finish();
                                }*/
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