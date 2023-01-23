package com.mariamramadan.link_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OfferPageService extends AppCompatActivity
{
    String ClientName, ClientPhone, TimeStamp, DayOfOffer, Status;
    Button AcceptOffer, DeclineOffer;
    TextView NameText;

    EditText NameText2, PhoneText ,StatusText;

    public void ModifyOfferStatus(int status)
    {


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
        DayOfOffer = (TimeStamp.split("T")[0]).split("-")[2];
        Status= getIntent().getStringExtra("Status");

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
//
//        AcceptOffer= (Button) findViewById(R.id.AcceptOffer);
//        DeclineOffer= (Button) findViewById(R.id.DeclineOffer);

//        AcceptOffer.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                ModifyOfferStatus(1);
//
//            }
//        });
//        DeclineOffer.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                ModifyOfferStatus(2);
//
//            }
//        });

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