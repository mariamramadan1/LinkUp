package com.mariamramadan.link_up;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ReviewPage extends AppCompatActivity
{
    String TimeStamp, FinalRating, FinalReview;
    TextView ReviewTitle;
    EditText CommentsBox;
    Spinner RatingSpinner;
    String[] RatingOptions= new String[]{"0","1","2","3","4","5"};
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser CurrentUser;
    Button Submit;

    public void PostServiceEdit(String ts, String Rating, String Review)
    {
        db.collection("Offers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d("TAG", document.getId() + " => " + document.getData());

                                if (ts.equals(String.valueOf(document.getData().get("TimeStamp"))))
                                {
                                    db.collection("Offers").document(document.getId())
                                                .update("Rating", Rating);
                                    db.collection("Offers").document(document.getId())
                                                .update("Review", Review);
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
    public void CumaltiveRating(int CurrentRating, String PhoneService)
    {
        db.collection("service providers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d("TAG", document.getId() + " => " + document.getData());

                                if (PhoneService.equals(document.getData().get("Phone")))
                                {
                                    int NewServicesNum= Integer.parseInt((String) document.getData().get("ServicesNum"))+1;
                                    int OldRating= Integer.parseInt((String) document.getData().get("Rating"));
                                    int newRating = (OldRating+CurrentRating)/NewServicesNum;
                                    db.collection("service providers").document(document.getId())
                                            .update("Rating", Integer.toString(newRating));
                                    db.collection("service providers").document(document.getId())
                                            .update("ServicesNum", Integer.toString(NewServicesNum));
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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));
        ReviewTitle= (TextView) findViewById(R.id.Title);
        CommentsBox= (EditText) findViewById(R.id.CommentsBox);
        RatingSpinner= (Spinner) findViewById(R.id.RatingSpinner);
        Submit= (Button) findViewById(R.id.SubmitReview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.selected_item, RatingOptions);
        adapter.setDropDownViewResource(R.layout.drop_down_item);
        RatingSpinner.setAdapter(adapter);
        TimeStamp= getIntent().getStringExtra("TimeStamp");
        ReviewTitle.setText("What do you think of " + getIntent().getStringExtra("ServiceName") + "'s service?");

        RatingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                FinalRating= RatingOptions[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }

        });

        Submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FinalReview= CommentsBox.getText().toString();
                PostServiceEdit(TimeStamp, FinalRating, FinalReview);
                CumaltiveRating(Integer.parseInt(FinalRating), getIntent().getStringExtra("ServicePhone"));
                finish();


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