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

public class ReviewsAdapter extends ArrayAdapter<ReviewList> implements Filterable {

    private ArrayList<ReviewList> filteredData;
    private LayoutInflater mInflater;


    public ReviewsAdapter(@NonNull Context context, ArrayList<ReviewList> arrayList)
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
                    (R.layout.activity_reviews, parent, false);
        }

        ReviewList currentWorkerPosition = getItem(position);

        ImageView CountryImage = currentItemView.findViewById(R.id.WorkerImg);
        assert currentWorkerPosition != null;
        CountryImage.setImageResource(currentWorkerPosition.getImageId());

        TextView Name = currentItemView.findViewById(R.id.worker);
        Name.setText(currentWorkerPosition.getName());

        TextView Review = currentItemView.findViewById(R.id.Profession);
        Review.setText(currentWorkerPosition.GetReview());

        TextView Rating = currentItemView.findViewById(R.id.rating);
        Rating.setText(currentWorkerPosition.getRating());


        return currentItemView;
    }



}
