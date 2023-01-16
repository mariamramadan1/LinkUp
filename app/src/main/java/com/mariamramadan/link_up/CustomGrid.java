package com.mariamramadan.link_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomGrid extends BaseAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> ServiceName = new ArrayList<>();
    private ArrayList<Integer> ServiceImage = new ArrayList<>();

    public CustomGrid(Context context, ArrayList<String> ServiceName, ArrayList<Integer> ServiceImage)
    {
        this.context=context;
        this.ServiceName=ServiceName;
        this.ServiceImage=ServiceImage;
        inflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount()
    {
        return ServiceName.size();
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        if (inflater== null)
        {
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView==null)
        {
            convertView=inflater.inflate(R.layout.activity_custom_grid, null);
        }
        ImageView imageView= convertView.findViewById(R.id.imageView);
        TextView textView= convertView.findViewById(R.id.textView);
        imageView.setImageResource(ServiceImage.get(i));
        textView.setText(ServiceName.get(i));
        return convertView;
    }
}