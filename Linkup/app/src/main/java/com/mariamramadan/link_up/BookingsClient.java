package com.mariamramadan.link_up;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

}
public class BookingsClient extends AppCompatActivity {

    TextView ClientName;
    TextView ClientPhone;
    TextView ServicePhone;
    TextView TimeStamp;
    TextView ServiceName;
    TextView Status;

    FirebaseUser CurrentUser;
    String CurrentPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_client);
        NavigationBarView BottomBar= (NavigationBarView) findViewById(R.id.bottomNavigationView);
        CurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        CurrentPhone= CurrentUser.getPhoneNumber();
        ServiceName=(TextView)findViewById(R.id.ServiceName);
        ClientName=(TextView)findViewById(R.id.ClientName);
        ClientPhone=(TextView)findViewById(R.id.ClientPhone);
        ServicePhone=(TextView)findViewById(R.id.ServicePhone);
        TimeStamp=(TextView)findViewById(R.id.TimeStamp);
        Status=(TextView)findViewById(R.id.Status);

        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        fstore.collection("Offers").orderBy("ClientPhone", Query.Direction.ASCENDING).addSnapshotListener
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
                            offer.clientPhone= (String) dc.getDocument().get("ClientPhone");
                            offer.serviceName= (String) dc.getDocument().get("ServiceName");
                            offer.servicePhone= (String) dc.getDocument().get("ServicePhone");
                            offer.TimeStamp=(String) dc.getDocument().get("TimeStamp");
                            offer.status=(String) dc.getDocument().get("Status");
                            OffersArray.add(offer);
                        }

                        for (int i=0; i < OffersArray.size(); i++)
                        {
                            if (CurrentPhone.equals(OffersArray.get(i).clientPhone))
                            {
                                ClientName.setText(OffersArray.get(i).clientName);
                                ClientPhone.setText(OffersArray.get(i).clientPhone);
                                ServiceName.setText(OffersArray.get(i).serviceName);
                                ServicePhone.setText(OffersArray.get(i).servicePhone);
                                TimeStamp.setText(OffersArray.get(i).TimeStamp);
                                Status.setText(OffersArray.get(i).status);
                            }
                        }

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
}