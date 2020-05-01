package com.tilda.sozluk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Kelime> kelimeler;
    OzelAdapter ozelAdapter;
    Veritabani vt;
    EditText eAra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        eAra = findViewById(R.id.editText);

        vt = new Veritabani(this);
        kelimeler = vt.listele();

        listeyiYenile();

        eAra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    kelimeler = vt.listele();
                }
                else{
                    kelimeler = vt.kelimeAra(s.toString());
                }
                listeyiYenile();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EkleActivity.class);
                i.putExtra("islem", "düzelt");
                i.putExtra("id", kelimeler.get(position).getId());
                i.putExtra("turkce", kelimeler.get(position).getTurkce());
                i.putExtra("ingilizce", kelimeler.get(position).getIngilizce());
                startActivityForResult(i, 100);
            }
        });
    }

    public void listeyiYenile(){
        ozelAdapter = new OzelAdapter(MainActivity.this, kelimeler);
        listView.setAdapter(ozelAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_yeni){
            Intent i = new Intent(this, EkleActivity.class);
            i.putExtra("islem", "ekle");
            startActivityForResult(i, 100);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //EkleActivity kapatılıp bu aktivity'e geri dönüüldüğünde çalışacak kodlar
        kelimeler = vt.listele();
        listeyiYenile();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vt.close();
    }
}
