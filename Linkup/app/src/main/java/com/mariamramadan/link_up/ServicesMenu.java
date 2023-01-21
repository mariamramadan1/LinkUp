package com.mariamramadan.link_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ServicesMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_menu);
        NavigationBarView BottomBar= (NavigationBarView) findViewById(R.id.bottomNavigationView);
        GridView menu= findViewById(R.id.ServicesMenu);
        ArrayList<String> Categories = new ArrayList<>();
        Categories.add("Tutoring");
        Categories.add("Cosmetics");
        Categories.add("Home Services");
        Categories.add("Volunteering");
        Categories.add("Tech Freelancers");
        Categories.add("Art & Design");
        Categories.add("Therapy");


        ArrayList<Integer> Images = new ArrayList<>();

        Images.add(R.drawable.tutoring);
        Images.add(R.drawable.cosmetology);
        Images.add(R.drawable.homeservices);
        Images.add(R.drawable.voulnteering);
        Images.add(R.drawable.tech);
        Images.add(R.drawable.design);
        Images.add(R.drawable.design);
        CustomGrid adapter=new CustomGrid(ServicesMenu.this, Categories, Images);
        menu.setAdapter(adapter);
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
                Intent ToUniversityPage = new Intent(ServicesMenu.this, ProfessionMenu.class);                          //if an item is clicked, go the activity 3
                ToUniversityPage.putExtra("category", Categories.get(position));                                                             //name, domain, and url of the clicked university will be passed as args with the intent
                startActivity(ToUniversityPage);
            }
        });

    }

}