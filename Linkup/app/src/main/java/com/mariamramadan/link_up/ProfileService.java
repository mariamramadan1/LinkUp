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
import android.widget.EditText;
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

class Service
{
    String Category;
    String Email;
    String Fname;
    String Lname;
    String Number;
    String SubCategory;
    String Rating;

}
public class ProfileService extends AppCompatActivity {
    FirebaseUser CurrentUser;
    EditText PhoneNum;
    EditText Fname;
    EditText Lname;
    EditText Email;

    EditText SubCategory;

    TextView Rating;

    TextView TopFname;
    TextView TopLnaame;
    String CurrentPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_service);
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        ArrayList<Service> WorkersArray = new ArrayList<>();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));
        CurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        CurrentPhone = CurrentUser.getPhoneNumber();
        PhoneNum=(EditText)findViewById(R.id.edit_phone);
        Fname=(EditText)findViewById(R.id.edit_first_name);
        Lname=(EditText)findViewById(R.id.edit_Last_name);
        Email=(EditText)findViewById(R.id.edit_email);
        SubCategory=(EditText)findViewById(R.id.subcategory);
        TopFname=(TextView)findViewById(R.id.Fname);
        TopLnaame=(TextView)findViewById(R.id.Lname);
        Rating=(TextView) findViewById(R.id.Rating);

        PhoneNum.setEnabled(false);
        Fname.setEnabled(false);
        Lname.setEnabled(false);
        Email.setEnabled(false);
        SubCategory.setEnabled(false);


        NavigationBarView BottomBar= (NavigationBarView) findViewById(R.id.bottomNavigationView);
        fstore.collection("service providers").orderBy("Phone", Query.Direction.ASCENDING).addSnapshotListener
                (new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e(TAG, "Firebase Error");
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            Service worker = new Service();
                            worker.Number = (String) dc.getDocument().get("Phone");
                            worker.Fname = (String) dc.getDocument().get("FirstName");
                            worker.Lname = (String) dc.getDocument().get("LastName");
                            worker.Email = (String) dc.getDocument().get("Email");
                            //worker.Category = (String) dc.getDocument().get("Category");
                            worker.SubCategory = (String) dc.getDocument().get("SubCategory");
                            worker.Rating = (String) dc.getDocument().get("Rating");
                            WorkersArray.add(worker);
                        }

                        for (int i=0; i < WorkersArray.size(); i++)
                        {
                            if (CurrentPhone.equals(WorkersArray.get(i).Number))
                            {
                                PhoneNum.setText(WorkersArray.get(i).Number);
                                TopFname.setText(WorkersArray.get(i).Fname);
                                TopLnaame.setText(WorkersArray.get(i).Lname);
                                Fname.setText(WorkersArray.get(i).Fname);
                                Lname.setText(WorkersArray.get(i).Lname);
                                Email.setText(WorkersArray.get(i).Email);
                                SubCategory.setText(WorkersArray.get(i).SubCategory);
                                Rating.setText(WorkersArray.get(i).Rating);
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
                        //Intent toProfile= new Intent(ProfileClient.this, ProfileClient.class);
                        //startActivity(toProfile);
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