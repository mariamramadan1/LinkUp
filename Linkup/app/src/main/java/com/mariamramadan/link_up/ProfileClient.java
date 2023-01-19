package com.mariamramadan.link_up;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
class User
{
    String Fname;
    String Lname;
    String Email;
    String Number;

}
public class ProfileClient extends AppCompatActivity
{
    EditText PhoneNum;
    EditText Fname;
    EditText Lname;
    EditText Email;
    String CurrentPhone;

    TextView TopFname;
    TextView TopLnaame;

    FirebaseUser CurrentUser;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String UserId = mAuth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_client);
        PhoneNum=(EditText) findViewById(R.id.edit_phone);
        Fname =(EditText) findViewById(R.id.edit_first_name);
        Lname=(EditText) findViewById(R.id.edit_Last_name);
        TopFname=(TextView) findViewById(R.id.Fname);
        TopLnaame=(TextView) findViewById(R.id.Lname);
        Email =(EditText) findViewById(R.id.edit_email);
        CurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        CurrentPhone= CurrentUser.getPhoneNumber();
        NavigationBarView BottomBar= (NavigationBarView) findViewById(R.id.bottomNavigationView);
        //Log.d("PHONENUMBER", CurrentUser.getPhoneNumber());
        Button Finish= (Button) findViewById(R.id.Finish);
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        fstore.collection("clients").orderBy("Phone", Query.Direction.ASCENDING).addSnapshotListener
                (new EventListener<QuerySnapshot>()
        {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
            {
                ArrayList<User> ArrayUsers= new ArrayList<>();
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
                    if (CurrentPhone.equals(ArrayUsers.get(i).Number))
                    {
                        PhoneNum.setText(ArrayUsers.get(i).Number);
                        TopFname.setText(ArrayUsers.get(i).Fname);
                        TopLnaame.setText(ArrayUsers.get(i).Lname);
                        Fname.setText(ArrayUsers.get(i).Fname);
                        Lname.setText(ArrayUsers.get(i).Lname);
                        Email.setText(ArrayUsers.get(i).Email);
                    }
                }

            }
        });

        Finish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
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
                        //Intent toProfile= new Intent(ProfileClient.this, ProfileClient.class);
                        //startActivity(toProfile);
                        return true;
                    }

                }
                return false;
            }
        });
    }

}