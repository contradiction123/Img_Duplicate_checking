package com.example.s8534.imgduplicatechecking.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.s8534.imgduplicatechecking.R;
import com.example.s8534.imgduplicatechecking.pojo.ImageItmepojo;

import java.util.List;

public class ImageItmeAdapter extends ArrayAdapter<ImageItmepojo> {

    public ImageItmeAdapter(Context context, int textViewResourceId, List<ImageItmepojo> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageItmepojo imageItmepojo = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.image_itme, parent, false);
        TextView name_item = (TextView) view.findViewById(R.id.name_item);
        TextView style_item = (TextView) view.findViewById(R.id.style_item);
        TextView size_item = (TextView) view.findViewById(R.id.size_item);
        TextView path_item = (TextView) view.findViewById(R.id.path_item);
        TextView w_h_item = (TextView) view.findViewById(R.id.w_h_itme);
        ImageView img_item = (ImageView) view.findViewById(R.id.img_item);
        TextView text_zu = (TextView) view.findViewById(R.id.text_zu);
        LinearLayout layout_text=view.findViewById(R.id.layout_text);
        LinearLayout layour_style=view.findViewById(R.id.layour_style);
        if(!TextUtils.isEmpty(imageItmepojo.getText_zu())){
            text_zu.setText(imageItmepojo.getText_zu());
            layour_style.setVisibility(View.GONE);
            layout_text.setVisibility(View.VISIBLE);
        }else {
            name_item.setText("名字："+imageItmepojo.getName_item());
            style_item.setText("格式："+imageItmepojo.getStyle_item());
            size_item.setText(imageItmepojo.getSize_item());
            path_item.setText(imageItmepojo.getPath_item());
            Bitmap bmp= BitmapFactory.decodeFile(imageItmepojo.getPath_item());
            img_item.setImageBitmap(bmp);
            w_h_item.setText("分辩率："+bmp.getWidth()+"*"+bmp.getHeight());
        }
        return view;
    }

}
