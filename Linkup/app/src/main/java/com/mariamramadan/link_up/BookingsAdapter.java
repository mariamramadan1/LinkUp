
package com.mariamramadan.link_up;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookingsAdapter extends ArrayAdapter<BookingsList> {


    public BookingsAdapter(@NonNull Context context, ArrayList<BookingsList> arrayList)
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
                    (R.layout.activity_booking_service, parent, false);
        }


        BookingsList currentWorkerPosition = getItem(position);

        ImageView CountryImage = currentItemView.findViewById(R.id.ClientImg);
        assert currentWorkerPosition != null;
        CountryImage.setImageResource(currentWorkerPosition.getImageId());

        TextView ClientNames = currentItemView.findViewById(R.id.ClientName);
        ClientNames.setText(currentWorkerPosition.getWorker());

        TextView ClientPhone = currentItemView.findViewById(R.id.ClientPhone);
        ClientPhone.setText(currentWorkerPosition.getphoneNum());
        ClientPhone.setVisibility(View.INVISIBLE);

        TextView Status = currentItemView.findViewById(R.id.Status);
        if (currentWorkerPosition.getStatus().equals("1"))
        {
            Status.setText("Accepted");
            ClientPhone.setVisibility(View.VISIBLE);
        }
        else if (currentWorkerPosition.getStatus().equals("2"))
        {
            Status.setText("Rejected");
        }
        else
        {
            Status.setText("Pending");
        }


        return currentItemView;
    }


}