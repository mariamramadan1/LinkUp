package com.mariamramadan.link_up;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
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

public class BookingService extends AppCompatActivity {

    TextView ClientName;
    TextView ClientPhone;
    TextView Status;

    Button Acceptoffer;
    Button Declineoffer;

    FirebaseUser CurrentUser;
    String CurrentPhone;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_list_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));
        NavigationBarView BottomBar= (NavigationBarView) findViewById(R.id.bottomNavigationView);
        final ArrayList<BookingsList> InfoArray = new ArrayList<BookingsList>();
        ListView ClientsOffers = (ListView) findViewById(R.id.WorkerListView);

//        Acceptoffer=(Button)findViewById(R.id.AcceptOffer);
//        Declineoffer=(Button)findViewById(R.id)
        CurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        CurrentPhone= CurrentUser.getPhoneNumber();
//        ServiceName=(TextView)findViewById(R.id.ServiceName);
//        ClientName=(TextView)findViewById(R.id.ClientName);
//        ClientPhone=(TextView)findViewById(R.id.ClientPhone);
//        ServicePhone=(TextView)findViewById(R.id.ServicePhone);
        //TimeStamp=(TextView)findViewById(R.id.TimeStamp);
        //Status=(TextView)findViewById(R.id.Status);
        BookingsAdapter BookingsArrayAdapter = new BookingsAdapter(this, InfoArray);

        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        fstore.collection("Offers").orderBy("ServicePhone", Query.Direction.ASCENDING).addSnapshotListener
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
                            offer.clientName= (String) dc.getDocument().get("ClientName");
                            offer.clientPhone= (String) dc.getDocument().get("ClientPhone");
                            offer.servicePhone= (String) dc.getDocument().get("ServicePhone");
                            offer.status=(String) dc.getDocument().get("Status");
                            OffersArray.add(offer);
                        }

                        for (int i=0; i < OffersArray.size(); i++)
                        {
                            if (CurrentPhone.equals(OffersArray.get(i).servicePhone))
                            {
                                OffersInfoArray.add(OffersArray.get(i));
                                InfoArray.add(new BookingsList(R.drawable.tutoring,
                                        OffersArray.get(i).clientName ,OffersArray.get(i).clientPhone, OffersArray.get(i).status));
                            }
                        }

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
                        Intent toHome= new Intent(getApplicationContext(), HomeServiceProv.class);
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
                        Intent toProfile= new Intent(getApplicationContext(), ProfileService.class);
                        startActivity(toProfile);
                        overridePendingTransition(0,0);
                        return true;
                    }

                }
                return false;
            }
        });
    }
}