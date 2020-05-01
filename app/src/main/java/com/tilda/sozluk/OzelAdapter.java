package com.tilda.sozluk;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OzelAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<Kelime> kelimeler;

    public OzelAdapter(Context context, ArrayList<Kelime> kelimeler) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.kelimeler = kelimeler;
    }

    @Override
    public int getCount() {
        return kelimeler.size();
    }

    @Override
    public Object getItem(int position) {
        return kelimeler.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.satir_layout, parent, false);
        TextView txt_id = convertView.findViewById(R.id.satir_id);
        TextView txt_turkce = convertView.findViewById(R.id.satir_turkce);
        TextView txt_ingilizce = convertView.findViewById(R.id.satir_ingilizce);

        Kelime k = kelimeler.get(position);

        txt_id.setText(String.valueOf(k.getId()));
        txt_turkce.setText(k.getTurkce());
        txt_ingilizce.setText(k.getIngilizce());

        return convertView;
    }
}
