package com.mariamramadan.link_up;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

interface CallbackBook {

    void onCallbackBook(boolean OneBooking);
}

public class ProfileServiceClientView extends AppCompatActivity
{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView NameText, ProfessionText, EmailText, BookWith;
    String Fname, Lname, Email, Number, Category, SubCategory, CurrentName, ClientPhone;
    FirebaseUser CurrentUser;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Date date = new Date();
    Button Book;

    public void OneBookingPerDay(String PhoneService, CallbackBook myCallback)
    {
        db.collection("Offers").orderBy("ServicePhone", Query.Direction.ASCENDING).addSnapshotListener
                (new EventListener<QuerySnapshot>()
                {
                    boolean OneBooking = true;
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null)
                        {
                            Log.e(TAG, "Firebase Error");
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges())
                        {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                            {

                                String ts_string = (String) dc.getDocument().get("TimeStamp");
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                LocalDateTime offerTimeStamp= LocalDateTime.parse(ts_string, formatter);
                                if (offerTimeStamp.getDayOfMonth() == LocalDateTime.now().getDayOfMonth())
                                    OneBooking = false;
                            }
                        }
                        myCallback.onCallbackBook(OneBooking);
                    }
                });

    }

    public void MakeOffer(String PhoneService, String FNservice, String LNService)
    {
        db.collection("clients").orderBy("Phone", Query.Direction.ASCENDING).addSnapshotListener
                (new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
                    {
                        ArrayList<User> ArrayUsers= new ArrayList<>();
                        CurrentUser= FirebaseAuth.getInstance().getCurrentUser();
                        ClientPhone= CurrentUser.getPhoneNumber();
                        if(error != null)
                        {
                            Log.e(TAG, "Firebase Error");
                            return;
                        }
                        for (DocumentChange dc: value.getDocumentChanges())
                        {
                            User user= new User();
                            user.Number= (String) dc.getDocument().get("Phone");
                            user.Fname= (String) dc.getDocument().get("FirstName");
                            user.Lname= (String) dc.getDocument().get("LastName");
                            user.Email= (String) dc.getDocument().get("Email");
                            ArrayUsers.add(user);
                        }

                        for (int i=0; i < ArrayUsers.size(); i++)
                        {
                            if (ClientPhone.equals(ArrayUsers.get(i).Number))
                            {
                                CurrentName = ArrayUsers.get(i).Fname + " " + ArrayUsers.get(i).Lname;
                            }
                        }

                        OneBookingPerDay(PhoneService, new CallbackBook()
                        {
                            @Override
                            public void onCallbackBook(boolean OneBooking)
                            {
                                Log.d("One Booking", String.valueOf(OneBooking));
                                if (OneBooking)
                                {
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("ClientName", CurrentName);
                                    user.put("ClientPhone", ClientPhone);
                                    user.put("ServiceName", FNservice+ " " + LNService);
                                    user.put("ServicePhone", PhoneService);
                                    user.put("Status", "0");
                                    user.put("Rating", "0");
                                    user.put("Review", "");

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                                    {
                                        user.put("TimeStamp", String.valueOf(LocalDateTime.now()));
                                    }

                                    db.collection("Offers")
                                            .add(user)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference)
                                                {
                                                    Toast.makeText(ProfileServiceClientView.this, "Request sent to "+
                                                            Fname + " "+ Lname, Toast.LENGTH_LONG).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener()
                                            {
                                                @Override
                                                public void onFailure(@NonNull Exception e)
                                                {
                                                    Toast.makeText(ProfileServiceClientView.this,
                                                            "Failed, please try again", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                }
                                else if (OneBooking==false)
                                {
                                    Toast.makeText(ProfileServiceClientView.this, "You already booked this service for the day", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_service_client_view);

        NavigationBarView BottomBar= (NavigationBarView) findViewById(R.id.bottomNavigationView);

        Book= (Button) findViewById(R.id.Book);
        Intent fromWorkerList = getIntent();
        Fname=fromWorkerList.getStringExtra("FirstName");
        Lname=fromWorkerList.getStringExtra("LastName");
        Email=fromWorkerList.getStringExtra("Email");
        Number=fromWorkerList.getStringExtra("Number");
        Category=fromWorkerList.getStringExtra("Category");
        SubCategory=fromWorkerList.getStringExtra("SubCategory");

        NameText =findViewById(R.id.Name);
        ProfessionText= findViewById(R.id.Profession);
        EmailText= findViewById(R.id.Email);
        BookWith= findViewById(R.id.Bookwith);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));

        NameText.setText(Fname + " " + Lname);
        //String[] SplitSubCategory = SubCategory.split(" ");
        ProfessionText.setText(SubCategory);
        EmailText.setText(Email);
        BookWith.setText("Book with " + Fname);

        Book.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                MakeOffer(Number, Fname, Lname);

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
                        Intent toBookings= new Intent(getApplicationContext(), BookingsClient.class);
                        startActivity(toBookings);
                        overridePendingTransition(0,0);
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
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}