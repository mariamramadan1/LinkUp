package com.mariamramadan.link_up;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomWorkerAdapter extends ArrayAdapter<CustomWorkerList> implements Filterable {

    private ArrayList<CustomWorkerList>filteredData;
    private LayoutInflater mInflater;


    public CustomWorkerAdapter(@NonNull Context context, ArrayList<CustomWorkerList> arrayList)
    {

        super(context, 0, arrayList);

    }

//    public int getCount() {
//        return arrayList.size();
//    }
//
//    public CustomWorkerList getItem(int position) {
//        return filteredData.get(position);
//    }

//    public long getItemId(int position) {
//        return position;
//    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate
                    (R.layout.activity_custom_worker_list, parent, false);
        }

        CustomWorkerList currentWorkerPosition = getItem(position);

        ImageView CountryImage = currentItemView.findViewById(R.id.WorkerImg);
        assert currentWorkerPosition != null;
        CountryImage.setImageResource(currentWorkerPosition.getImageId());

        TextView WorkerName = currentItemView.findViewById(R.id.worker);
        WorkerName.setText(currentWorkerPosition.getWorker());

        TextView WorkerSubCategory = currentItemView.findViewById(R.id.Profession);
        WorkerSubCategory.setText(currentWorkerPosition.getSubCategory());

        TextView WorkerRating = currentItemView.findViewById(R.id.rating);
        WorkerRating.setText(currentWorkerPosition.getRating());


        return currentItemView;
    }



}
