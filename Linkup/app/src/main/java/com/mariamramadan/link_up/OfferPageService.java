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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OfferPageService extends AppCompatActivity
{
    String ClientName, ClientPhone, TimeStamp, ServiceName, ServicePhone, Status;
    Button AcceptOffer, DeclineOffer;
    TextView NameText;
    FirebaseUser CurrentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText NameText2, PhoneText ,StatusText;

    public void ModifyOfferStatus(String status, String ts)
    {
//        db.collection("Offers").orderBy("ServicePhone", Query.Direction.ASCENDING).addSnapshotListener
//                (new EventListener<QuerySnapshot>()
//                {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
//                    {
//                        Log.d("OnEvent", "");
//
//                        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
//                        ClientPhone = CurrentUser.getPhoneNumber();
//                        if (error != null) {
//                            Log.d("Firebase Error", "");
//                            return;
//                        }
//                        for (DocumentChange dc : value.getDocumentChanges())
//                        {
//                            Log.d("FORLOOP", "");
//                            Log.d("Doc ID", dc.getDocument().getId());
//                            if (ts.equals(String.valueOf(dc.getDocument().get("TimeStamp"))))
//                            {
//                                Log.d("Doc ID", dc.getDocument().getId());
//                                db.collection("Offers").document(dc.getDocument().getId())
//                                        .update("Status", status);
//                                Toast.makeText(OfferPageService.this, "Request has been " + (status.equals("1") ? "accepted" : "rejected"), Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    }
//                });
        db.collection("Offers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                        ClientPhone = CurrentUser.getPhoneNumber();
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d("TAG", document.getId() + " => " + document.getData());

                                if (ts.equals(String.valueOf(document.getData().get("TimeStamp"))))
                                {
                                    Log.d("Doc ID", document.getId());
                                    db.collection("Offers").document(document.getId())
                                            .update("Status", status);
                                    Toast.makeText(OfferPageService.this, "Request has been " + (status.equals("1") ? "accepted" : "rejected"), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        else
                        {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_page_service);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));

        NavigationBarView BottomBar= (NavigationBarView) findViewById(R.id.bottomNavigationView);

        ClientName= getIntent().getStringExtra("ClientName");
        ClientPhone= getIntent().getStringExtra("ClientPhone");
        TimeStamp= getIntent().getStringExtra("TimeStamp");
        Status= getIntent().getStringExtra("Status");
        ServiceName= getIntent().getStringExtra("ServiceName");
        ServicePhone=getIntent().getStringExtra("ServicePhone");

        NameText= (TextView) findViewById(R.id.name);
        NameText2= (EditText) findViewById(R.id.ClientName);
        PhoneText= (EditText) findViewById(R.id.ClientPhone);
        StatusText= (EditText) findViewById(R.id.Status);

        NameText.setText(ClientName);
        NameText2.setText(ClientName);
        PhoneText.setText(ClientPhone);
        if (Status.equals("1"))
        {
            StatusText.setText("Accepted");
        }
        else if (Status.equals("2"))
        {
            StatusText.setText("Rejected");
        }
        else
        {
            StatusText.setText("Pending");
        }

        NameText2.setEnabled(false);
        PhoneText.setEnabled(false);
        StatusText.setEnabled(false);

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

        AcceptOffer= (Button) findViewById(R.id.AcceptOffer);
        DeclineOffer= (Button) findViewById(R.id.DeclineOffer);

        AcceptOffer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ModifyOfferStatus("1", TimeStamp);

            }
        });
        DeclineOffer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ModifyOfferStatus("2", TimeStamp);

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