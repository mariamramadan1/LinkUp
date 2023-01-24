package com.mariamramadan.link_up;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
interface CallbackOffers {

    void onCallbackOffers(ArrayList<Offers> OffersInfoArray);
}

public class BookingService extends AppCompatActivity {

    FirebaseUser CurrentUser;
    String CurrentPhone;
    ListView ClientsOffers;
    ArrayList<BookingsList> InfoArray;
    BookingsAdapter BookingsArrayAdapter;

    public void FetchOfferData(CallbackOffers myCallback)
    {
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
                            offer.TimeStamp= (String) dc.getDocument().get("TimeStamp");
                            OffersArray.add(offer);
                        }

                        for (int i=0; i < OffersArray.size(); i++)
                        {
                            if (CurrentPhone.equals(OffersArray.get(i).servicePhone))
                            {
                                OffersInfoArray.add(OffersArray.get(i));
                                InfoArray.add(new BookingsList(R.drawable.tutoring,
                                        OffersArray.get(i).clientName ,OffersArray.get(i).clientPhone, OffersArray.get(i).status,
                                        OffersArray.get(i).TimeStamp));
                            }
                        }

                        ClientsOffers.setAdapter(BookingsArrayAdapter);
                        myCallback.onCallbackOffers(OffersInfoArray);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_list_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));
        NavigationBarView BottomBar= (NavigationBarView) findViewById(R.id.bottomNavigationView);
        ClientsOffers = (ListView) findViewById(R.id.WorkerListView);
        InfoArray = new ArrayList<>();
        BookingsArrayAdapter = new BookingsAdapter(this, InfoArray);


        CurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        CurrentPhone= CurrentUser.getPhoneNumber();

        FetchOfferData(new CallbackOffers()
        {
            @Override
            public void onCallbackOffers(ArrayList<Offers> OffersInfoArray)
            {
                ClientsOffers.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        Intent toOfferPage= new Intent(BookingService.this, OfferPageService.class);
                        toOfferPage.putExtra("ClientName", OffersInfoArray.get(i).clientName);
                        toOfferPage.putExtra("ClientPhone", OffersInfoArray.get(i).clientPhone);
                        toOfferPage.putExtra("ServiceName", OffersInfoArray.get(i).serviceName);
                        toOfferPage.putExtra("ServicePhone", OffersInfoArray.get(i).servicePhone);
                        toOfferPage.putExtra("Status", OffersInfoArray.get(i).status);
                        toOfferPage.putExtra("TimeStamp", OffersInfoArray.get(i).TimeStamp);
                        startActivity(toOfferPage);
                    }
                });

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}