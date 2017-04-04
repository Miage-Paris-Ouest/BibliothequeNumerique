package com.example.alice.biblothequevirtuelle.Firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alice.biblothequevirtuelle.R;

/**
 * Created by Audrey on 04/04/2017.
 */

public class CreationCompte extends AppCompatActivity implements View.OnClickListener {

    private Button bValiderInscription;
    private EditText etPseudoInscription;
    private EditText etNomInscription;
    private EditText etPrenomInscription;
    private EditText etMdpInscription1;
    private EditText etMdpInscription2;
    private EditText etMailInscription;
    private TextView tvSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_inscription_layout);

        bValiderInscription = (Button) findViewById(R.id.bValiderInscription);

        etMailInscription = (EditText) findViewById(R.id.etMailInscription);
        etPseudoInscription = (EditText) findViewById(R.id.etPseudoInscription);
        etNomInscription = (EditText) findViewById(R.id.etNomInscription);
        etPrenomInscription = (EditText) findViewById(R.id.etPrenomInscription);
        etMdpInscription1 = (EditText) findViewById(R.id.etMdpInscription1);
        etMdpInscription2 = (EditText) findViewById(R.id.etMdpInscription2);

        bValiderInscription.setOnClickListener(this);
        tvSignin.setOnClickListener(this);

    }

    private void registerUser(){
        String email = etMailInscription.getText().toString().trim();
        String password = etMdpInscription1.getText().toString().trim();

        if(TextUtils.isEmpty(email)){

        }

        if(TextUtils.isEmpty(password)){

        }
    }

    @Override
    public void onClick(View v) {

        if(v == bValiderInscription){
            registerUser();
        }
        if(v == tvSignin){
            //redirige vers la page de connection
        }
    }
}
