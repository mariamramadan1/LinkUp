package com.mariamramadan.link_up;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
class Offers
{
    String clientName;
    String serviceName;
    String servicePhone;
    String clientPhone;
    String TimeStamp;
    String status;

    String Rating;

    String Review;

}
public class BookingsClient extends AppCompatActivity {


    TextView ServicePhone;

    TextView ServiceName;
    TextView Status;

    FirebaseUser CurrentUser;
    String CurrentPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_list_page);
        NavigationBarView BottomBar= (NavigationBarView) findViewById(R.id.bottomNavigationView);
        CurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        CurrentPhone= CurrentUser.getPhoneNumber();
//        ServiceName=(TextView)findViewById(R.id.ServiceName);
//        ClientName=(TextView)findViewById(R.id.ClientName);
//        ClientPhone=(TextView)findViewById(R.id.ClientPhone);
//        ServicePhone=(TextView)findViewById(R.id.ServicePhone);
//        TimeStamp=(TextView)findViewById(R.id.TimeStamp);
//        Status=(TextView)findViewById(R.id.Status);
        final ArrayList<BookingsList> InfoArray = new ArrayList<BookingsList>();
        ListView ClientsOffers = (ListView) findViewById(R.id.WorkerListView);
        BookingsAdapter BookingsArrayAdapter = new BookingsAdapter(this, InfoArray);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        fstore.collection("Offers").orderBy("ClientPhone", Query.Direction.ASCENDING).addSnapshotListener
                (new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
                    {
                        ArrayList<Offers> OffersArray = new ArrayList<>();
                        ArrayList<Offers> OffersInfoArray = new ArrayList<>();
                        if(error != null)
                        {
                            Log.e(TAG, "Firebase Error");
                            return;
                        }
                        for (DocumentChange dc: value.getDocumentChanges())
                        {
                            Offers offer= new Offers();
                            offer.serviceName= (String) dc.getDocument().get("ServiceName");
                            offer.clientPhone= (String) dc.getDocument().get("ClientPhone");
                            offer.servicePhone= (String) dc.getDocument().get("ServicePhone");
                            offer.TimeStamp=  (String) dc.getDocument().get("TimeStamp");
                            offer.status=(String) dc.getDocument().get("Status");
                            OffersArray.add(offer);
                        }

                        for (int i=0; i < OffersArray.size(); i++)
                        {
                            if (CurrentPhone.equals(OffersArray.get(i).clientPhone))
                            {
                                OffersInfoArray.add(OffersArray.get(i));
                                InfoArray.add(new BookingsList(R.drawable.tutoring,
                                        OffersArray.get(i).serviceName ,OffersArray.get(i).servicePhone, OffersArray.get(i).status,
                                        OffersArray.get(i).TimeStamp));
                                if (OffersArray.get(i).status.equals("1"))
                                {

                                    ClientsOffers.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                    {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
                                        {
                                            if (InfoArray.get(position).getStatus().equals("1"))
                                            {
                                                Intent ToReviewPage = new Intent(BookingsClient.this, ReviewPage.class);
                                                ToReviewPage.putExtra("TimeStamp", InfoArray.get(position).getTimeStamp());
                                                ToReviewPage.putExtra("ServiceName", InfoArray.get(position).getWorker());
                                                ToReviewPage.putExtra("ServicePhone", InfoArray.get(position).getphoneNum());
                                                startActivity(ToReviewPage);
                                            }
                                        }
                                    });
                                }

                            }
                        }
//                        BookingsArrayAdapter.notifyDataSetChanged();
                        ClientsOffers.setAdapter(BookingsArrayAdapter);

                    }
                });

        BottomBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch(item.getItemId())
                {
                    case R.id.Home:
                    {
                        Intent toHome= new Intent(getApplicationContext(), ServicesMenu.class);
                        startActivity(toHome);
                        overridePendingTransition(0,0);
                        return true;
                    }
                    case R.id.Bookings:
                    {
                        //Intent toBookings= new Intent(getApplicationContext(), BookingsClient.class);
                        //startActivity(toBookings);
                        //overridePendingTransition(0,0);
                        return true;
                    }
                    case R.id.profile:
                    {
                        Intent toProfile= new Intent(getApplicationContext(), ProfileClient.class);
                        startActivity(toProfile);
                        overridePendingTransition(0,0);
                        return true;
                    }

                }
                return false;
            }
        });

    }
        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
}