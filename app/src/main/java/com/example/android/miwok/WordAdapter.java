package com.example.android.miwok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<word> {
    private AppCompatActivity context;
    private int colorid;
    public WordAdapter(AppCompatActivity context, int resource, ArrayList<word> words, int colorid) {
        super(context, resource, words);
        this.context=context;
        this.colorid=colorid;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        word currentword=getItem(position);
        ImageView img=(ImageView) convertView.findViewById(R.id.imageView);
        if(currentword.getimageid()==-1){
            img.setVisibility(View.GONE);
        }
        else {
            img.setImageResource(currentword.getimageid());
        }
        TextView miwoktextview = (TextView) convertView.findViewById(R.id.miwok);
        miwoktextview.setText(currentword.getMiwokTranslation());
        TextView englishtextview = (TextView) convertView.findViewById(R.id.english);
        englishtextview.setText(currentword.getDefaultTranslation());
        View line1= convertView.findViewById(R.id.slot);
        int color = ContextCompat.getColor(getContext(),colorid);
        line1.setBackgroundColor(color);
        return convertView;


    }
}
