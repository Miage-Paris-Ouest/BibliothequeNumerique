package com.example.alice.biblothequevirtuelle.Vue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Ajouter extends Activity {

    private String ean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent reception = getIntent();
        ean = reception.getStringExtra("ean");

    }
}
