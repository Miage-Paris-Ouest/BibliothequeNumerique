package com.example.alpottie.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alpottie.myapplication.Donnees.Livre;

public class AfficherLivre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livre);
        Intent intent = getIntent();
        Livre livre = intent.getParcelableExtra("livre");

        TextView tvTitre = (TextView) findViewById(R.id.tvTitre);
        TextView tvAuteur = (TextView) findViewById(R.id.tvAuteur);
        TextView tvEAN = (TextView) findViewById(R.id.tvEAN);
        ImageView ivCouverture = (ImageView) findViewById(R.id.ivCouverture);

        tvTitre.setText(livre.getTitre());
        tvAuteur.setText(livre.getAuteur().toString());
        tvEAN.setText(livre.getEan());

        Bitmap couverture;
        byte[] decodedBytes = Base64.decode(livre.getCouverture(), 0);
        couverture =  BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        ivCouverture.setImageBitmap(couverture);

    }

    public void onClickAccueil(View v)
    {
        Intent intent = new Intent(this, Accueil.class);
        startActivity(intent);
    }
}
