package com.example.alice.biblothequevirtuelle.AppelService;

import android.app.Activity;

import com.google.zxing.integration.android.IntentIntegrator;

public class Scanner {

    private Activity activity;

    public Scanner(Activity activity) {
        this.activity = activity;
    }

    public void scanner()
    {
        IntentIntegrator scanIntegrator = new IntentIntegrator(activity);
        scanIntegrator.initiateScan();
    }
}
