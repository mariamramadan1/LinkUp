package com.mariamramadan.link_up;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
    TextView PhoneNum;
    TextView Fname;
    TextView Lname;
    TextView Email;
    String CurrentPhone;

    FirebaseUser CurrentUser;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String UserId = mAuth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_client);
        PhoneNum=(TextView) findViewById(R.id.ProfilePhone);
        Fname =(TextView) findViewById(R.id.ProfileFname);
        Lname=(TextView) findViewById(R.id.ProfileLname);
        Email =(TextView) findViewById(R.id.ProfileEmail);
        CurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        CurrentPhone= CurrentUser.getPhoneNumber();
        //Log.d("PHONENUMBER", CurrentUser.getPhoneNumber());
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
                        Fname.setText(ArrayUsers.get(i).Fname);
                        Lname.setText(ArrayUsers.get(i).Lname);
                        Email.setText(ArrayUsers.get(i).Email);
                    }
                }

            }
        });

    }
}