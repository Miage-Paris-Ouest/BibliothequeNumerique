package com.example.alpottie.myapplication;
import android.app.Activity;
import android.os.Bundle;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Accueil extends Activity implements OnClickListener
{
    private Button scanBtn, saveBtn;
    private TextView formatTxt, contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debut);
        scanBtn = (Button)findViewById(R.id.scan_button);
        saveBtn = (Button)findViewById(R.id.save_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        scanBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
    }
    public void onClick(View v){
        if(v.getId()==R.id.scan_button || v.getId()==R.id.save_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String ean = "";
        String type = "";
        String prefix ="";
        if (scanningResult != null) {
            ean = scanningResult.getContents().toLowerCase();
            type = scanningResult.getFormatName().toLowerCase();
            prefix = ean.substring(0, 3);
            formatTxt.setText("FORMAT: " + type);
            contentTxt.setText("CONTENT: " + ean);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Aucunes données scannées", Toast.LENGTH_SHORT);
            toast.show();
        }

        if(!type.equals("ean_13"))
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Mauvais format", Toast.LENGTH_SHORT);
            toast.show();
        }

        if(!(prefix.equals("977") || prefix.equals("978") || prefix.equals("979")))
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "C'est n'est pas un livre !", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Code Ok", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}