package com.tilda.sozluk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EkleActivity extends AppCompatActivity {
    Veritabani vt;
    EditText eTurkce, eIngilizce;
    TextView tId;
    Button bEkle, bDuzelt, bSil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekle);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        eTurkce = findViewById(R.id.edit_turkce);
        eIngilizce = findViewById(R.id.edit_ingilizce);
        tId = findViewById(R.id.txt_id);

        bEkle = findViewById(R.id.button);
        bDuzelt = findViewById(R.id.button2);
        bSil = findViewById(R.id.button3);

        String islem = getIntent().getStringExtra("islem");
        if(islem.equals("düzelt")){
            tId.setText(String.valueOf(getIntent().getIntExtra("id",0)));
            eTurkce.setText(getIntent().getStringExtra("turkce"));
            eIngilizce.setText(getIntent().getStringExtra("ingilizce"));
            bEkle.setVisibility(View.GONE);
        }
        else {
            bDuzelt.setVisibility(View.GONE);
            bSil.setVisibility(View.GONE);
        }

     }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnEkle(View v)
    {
        String turkce = eTurkce.getText().toString();
        String ingilizce = eIngilizce.getText().toString();
        if(!turkce.equals("") && !ingilizce.equals("")) {
            vt = new Veritabani(this);
            long cevap = vt.kelimeEkle(new Kelime(0, turkce, ingilizce));
            if (cevap > 0) {
                Toast.makeText(this, "Kayıt eklendi.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Hata : Kayıt eklenemedi!", Toast.LENGTH_SHORT).show();
            }
            vt.close();
        }
        else{
            Toast.makeText(this, "Eksik bilgi girdiniz!", Toast.LENGTH_SHORT).show();
        }
    }
    public void btnDuzelt(View v)
    {
        int id = Integer.parseInt(tId.getText().toString());
        String turkce = eTurkce.getText().toString();
        String ingilizce = eIngilizce.getText().toString();

        if(id>0 && !turkce.equals("") && !ingilizce.equals("")) {
            vt = new Veritabani(this);
            long cevap = vt.kelimeDuzelt(new Kelime(id, turkce, ingilizce));
            if (cevap > 0) {
                Toast.makeText(this, "Kayıt düzeltildi.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Hata : Kayıt düzeltilemedi!", Toast.LENGTH_SHORT).show();
            }
            vt.close();
        }
        else{
            Toast.makeText(this, "Eksik bilgi girdiniz!", Toast.LENGTH_SHORT).show();
        }
    }
    public void btnSil(View v)
    {
        final int id = Integer.parseInt(tId.getText().toString());
        String turkce = eTurkce.getText().toString();
        String ingilizce = eIngilizce.getText().toString();
        
        if(id>0) {
            AlertDialog.Builder mesaj = new AlertDialog.Builder(this);
            mesaj.setTitle("Kayıt Sil");
            mesaj.setMessage("Silmek istiyor musunuz?");
            mesaj.setNegativeButton("Hayır", null);
            mesaj.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    vt = new Veritabani(EkleActivity.this);
                    long cevap = vt.kelimeSil(id);
                    if (cevap > 0) {
                        Toast.makeText(EkleActivity.this, "Kayıt silindi.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EkleActivity.this, "Hata : Kayıt silinemedi!", Toast.LENGTH_SHORT).show();
                    }
                    vt.close();
                }
            });
            mesaj.create().show();
        }
        else{
            Toast.makeText(this, "Eksik bilgi girdiniz!", Toast.LENGTH_SHORT).show();
        }
    }


}
