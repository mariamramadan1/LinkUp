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
import android.widget.Button;
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

public class HomeServiceProv extends AppCompatActivity {
    FirebaseUser CurrentUser;
    String CurrentPhone;

    TextView ClientName1;
    TextView ClientName2;
    TextView ClientName3;
    TextView Status1;
    TextView Status2;
    TextView Status3;

    TextView ViewMore1;

    TextView ViewMore2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_service_prov);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));
        NavigationBarView BottomBar= (NavigationBarView) findViewById(R.id.bottomNavigationView);

        ClientName1 = (TextView) findViewById(R.id.ClientName1);
        ClientName2 = (TextView) findViewById(R.id.ClientName2);
        ClientName3 = (TextView) findViewById(R.id.ClientName3);

        Status1 = (TextView) findViewById(R.id.Status1);
        Status2 = (TextView) findViewById(R.id.Status2);
        Status3 = (TextView) findViewById(R.id.Status3);

        ViewMore2 = (TextView) findViewById(R.id.ViewMore2);

        CurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        CurrentPhone= CurrentUser.getPhoneNumber();

        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        fstore.collection("Offers").orderBy("ServicePhone", Query.Direction.ASCENDING).addSnapshotListener
                (new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
                    {
                        ArrayList<Offers> OffersArray = new ArrayList<>();
                        if(error != null)
                        {
                            Log.e(TAG, "Firebase Error");
                            return;
                        }
                        for (DocumentChange dc: value.getDocumentChanges())
                        {
                            Offers offer= new Offers();
                            offer.clientName= (String) dc.getDocument().get("ClientName");
                            offer.servicePhone= (String) dc.getDocument().get("ServicePhone");
                            offer.status=(String) dc.getDocument().get("Status");
                            OffersArray.add(offer);
                        }

                        for (int i=0; i < OffersArray.size(); i++)
                        {
                            if (CurrentPhone.equals(OffersArray.get(i).servicePhone))
                            {
                                ClientName1.setText(OffersArray.get(0).clientName);
                                ClientName2.setText(OffersArray.get(1).clientName);
                                ClientName3.setText(OffersArray.get(2).clientName);
                                if (OffersArray.get(0).status.equals("1"))
                                {
                                    Status1.setText("Accepted");
                                }
                                else if (OffersArray.get(0).status.equals("2"))
                                {
                                    Status1.setText("Rejected");
                                }
                                else
                                {
                                    Status1.setText("Pending");
                                }

                                if (OffersArray.get(1).status.equals("1"))
                                {
                                    Status2.setText("Accepted");
                                }
                                else if (OffersArray.get(1).status.equals("2"))
                                {
                                    Status2.setText("Rejected");
                                }
                                else
                                {
                                    Status2.setText("Pending");
                                }

                                if (OffersArray.get(3).status.equals("1"))
                                {
                                    Status3.setText("Accepted");
                                }
                                else if (OffersArray.get(3).status.equals("2"))
                                {
                                    Status3.setText("Rejected");
                                }
                                else
                                {
                                    Status3.setText("Pending");
                                }

//                                Status1.setText(OffersArray.get(0).status);
//                                Status2.setText(OffersArray.get(1).status);
//                                Status3.setText(OffersArray.get(2).status);
                            }
                        }

                    }
                });

        ViewMore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toReviews= new Intent(getApplicationContext(), Reviews.class);
                startActivity(toReviews);
                overridePendingTransition(0,0);
            }
        });

        ViewMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toOffers= new Intent(HomeServiceProv.this, BookingService.class);
                startActivity(toOffers);
                //overridePendingTransition(0,0);
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
//                        Intent toHome= new Intent(getApplicationContext(), ServicesMenu.class);
//                        startActivity(toHome);
//                        overridePendingTransition(0,0);
                        return true;
                    }
                    case R.id.Bookings:
                    {
                        Intent toBookings= new Intent(getApplicationContext(), BookingService.class);
                        startActivity(toBookings);
                        overridePendingTransition(0,0);
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