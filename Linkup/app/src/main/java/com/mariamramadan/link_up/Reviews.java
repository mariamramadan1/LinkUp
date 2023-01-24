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

public class Reviews extends AppCompatActivity {

    FirebaseUser CurrentUser;
    String CurrentPhone;
    ReviewsAdapter ReviewsArrayAdapter;
    ListView ClientsReviews;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_list_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));
        NavigationBarView BottomBar= (NavigationBarView) findViewById(R.id.bottomNavigationView);
        ClientsReviews = (ListView) findViewById(R.id.WorkerListView);


        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        CurrentPhone = CurrentUser.getPhoneNumber();


        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        fstore.collection("Offers").orderBy("ServicePhone", Query.Direction.ASCENDING).addSnapshotListener
                (new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
                    {
                        ArrayList<ReviewList> FinalReviewsList=new ArrayList<>();
                        ArrayList<Offers> ReviewsArray= new ArrayList<>();
                        ArrayList<Offers> ReviewsInfo= new ArrayList<>();
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
                            //offer.status=(String) dc.getDocument().get("Status");
                            offer.Review=(String) dc.getDocument().get("Review");
                            offer.Rating=(String) dc.getDocument().get("Rating");
                            ReviewsArray.add(offer);
                        }

                        for (int i=0; i < ReviewsArray.size(); i++)
                        {
                            if (CurrentPhone.equals(ReviewsArray.get(i).servicePhone))
                            {

                                //ReviewsInfo.add(ReviewsArray.get(i));
                                FinalReviewsList.add(new ReviewList(R.drawable.person_24,
                                        ReviewsArray.get(i).clientName ,ReviewsArray.get(i).Review, ReviewsArray.get(i).Rating));

                            }

                        }
                        ReviewsArrayAdapter = new ReviewsAdapter(Reviews.this, FinalReviewsList);
                          ClientsReviews.setAdapter(ReviewsArrayAdapter);
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