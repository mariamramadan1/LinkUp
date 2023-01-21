package com.mariamramadan.link_up;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomWorkerAdapter extends ArrayAdapter<CustomWorkerList> {


    public CustomWorkerAdapter(@NonNull Context context, ArrayList<CustomWorkerList> arrayList)
    {

        super(context, 0, arrayList);
    }

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

        TextView WorkerRating = currentItemView.findViewById(R.id.rating);
        WorkerRating.setText(currentWorkerPosition.getRating());


        return currentItemView;
    }
}