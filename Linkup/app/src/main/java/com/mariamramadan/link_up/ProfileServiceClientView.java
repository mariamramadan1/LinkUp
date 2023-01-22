package com.mariamramadan.link_up;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfileServiceClientView extends AppCompatActivity
{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView NameText, ProfessionText, EmailText, BookWith;
    String Fname, Lname, Email, Number, Category, SubCategory, CurrentName, ClientPhone;
    FirebaseUser CurrentUser;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Date date = new Date();
    Button Book;

    public void MakeOffer(String PhoneService)
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

                        Map<String, Object> user = new HashMap<>();
                        user.put("ClientName", CurrentName);
                        user.put("ClientPhone", ClientPhone);
                        user.put("ServicePhone", PhoneService);
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
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_service_client_view);
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

        NameText.setText(Fname + " " + Lname);
        ProfessionText.setText(SubCategory);
        EmailText.setText(Email);
        BookWith.setText("Book with " + Fname);

        Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                MakeOffer(Number);

            }
        });


    }
}