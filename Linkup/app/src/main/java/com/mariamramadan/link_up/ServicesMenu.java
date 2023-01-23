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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicesMenu extends AppCompatActivity {

    ArrayList<String> Categories = new ArrayList<>();
    ArrayList<Integer> Images = new ArrayList<>();
    ImageButton SearchButton;
    CustomWorkerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_menu);
        SearchButton= (ImageButton) findViewById(R.id.SearchButton);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));
        NavigationBarView BottomBar= (NavigationBarView) findViewById(R.id.bottomNavigationView);

        GridView menu= findViewById(R.id.ServicesMenu);

        Categories.add("Tutoring");
        Categories.add("Cosmetics");
        Categories.add("Home Services");
        Categories.add("Volunteering");
        Categories.add("Tech Freelancers");
        Categories.add("Art & Design");
        Categories.add("Therapy");


        Images.add(R.drawable.tutoring);
        Images.add(R.drawable.cosmetology);
        Images.add(R.drawable.homeservices);
        Images.add(R.drawable.voulnteering);
        Images.add(R.drawable.tech);
        Images.add(R.drawable.design);
        Images.add(R.drawable.design);
        CustomGrid adapter=new CustomGrid(ServicesMenu.this, Categories, Images);
        menu.setAdapter(adapter);

//        FirebaseMessaging firebaseMessaging= FirebaseMessaging.getInstance();
//        firebaseMessaging.subscribeToTopic("new_user_forms");

        BottomBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch(item.getItemId())
                {
                    case (R.id.Home):
                    {
                        return true;

                    }
                    case (R.id.Bookings):
                    {
                        Intent toBookings= new Intent(getApplicationContext(), BookingsClient.class);
                        startActivity(toBookings);
                        overridePendingTransition(0,0);
                        return true;
                    }
                    case (R.id.profile):
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
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent ToProfession = new Intent(ServicesMenu.this, ProfessionMenu.class);                          //if an item is clicked, go the activity 3
                ToProfession.putExtra("category", Categories.get(position));                                                             //name, domain, and url of the clicked university will be passed as args with the intent
                startActivity(ToProfession);
            }
        });

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToSearch= new Intent(ServicesMenu.this, Search.class);
                startActivity(ToSearch);
            }
        });


    }

}