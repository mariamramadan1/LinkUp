package com.mariamramadan.link_up;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

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
        db.collection("Offers").orderBy("ServicePhone", Query.Direction.ASCENDING).addSnapshotListener
                (new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
                    {
//                        Log.d("OnEvent", "");

                        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String PhoneService = CurrentUser.getPhoneNumber();
                        if (error != null) {
                            Log.d("Firebase Error", "");
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges())
                        {
//                            Log.d("FORLOOP", "");
                            Log.d("Doc ID", dc.getDocument().getId());
                            if (ts.equals(String.valueOf(dc.getDocument().get("TimeStamp"))))
                            {
                                Log.d("Doc ID", dc.getDocument().getId());
                                db.collection("Offers").document(dc.getDocument().getId())
                                            .update("Rating", Rating);
                                db.collection("Offers").document(dc.getDocument().getId())
                                            .update("Review", Review);
                            }

                        }
                    }
                });
    }
    public void CumaltiveRating(int CurrentRating)
    {
        db.collection("service providers").orderBy("Phone", Query.Direction.ASCENDING).addSnapshotListener
                (new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
                    {
//                        Log.d("OnEvent", "");

                        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String PhoneService = CurrentUser.getPhoneNumber();
                        if (error != null) {
                            Log.d("Firebase Error", "");
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges())
                        {
//                            Log.d("FORLOOP", "");
                            Log.d("Doc ID", dc.getDocument().getId());
                            if (PhoneService.equals(String.valueOf(dc.getDocument().get("Phone"))))
                            {
                                int NewServicesNum= Integer.parseInt((String) dc.getDocument().get("ServicesNum"))+1;
                                int OldRating= Integer.parseInt((String) dc.getDocument().get("Rating"));
                                int newRating = (OldRating+CurrentRating)/NewServicesNum;
                                Log.d("Doc ID", dc.getDocument().getId());
                                db.collection("Rating").document(dc.getDocument().getId())
                                        .update("Rating", String.valueOf(newRating));
                                db.collection("Offers").document(dc.getDocument().getId())
                                        .update("Review", NewServicesNum);
                            }

                        }
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);
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
                CumaltiveRating(Integer.parseInt(FinalRating));
                finish();


            }
        });



    }
}