package com.mariamramadan.link_up;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class LogoScreen extends Activity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                FirebaseUser CurrentUser= FirebaseAuth.getInstance().getCurrentUser();
//                Log.d("CurrentPhone", CurrentUser.getPhoneNumber());
//                CurrentUser = null;
                if (CurrentUser == null)
                {
                    Intent intent = new Intent(LogoScreen.this, VerifyPhoneNumber.class);
                    startActivity(intent);
                    finish();
                }
                else if (!(CurrentUser == null))
                {
                    Log.d("User not Null", "");
                    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
                    FirebaseUser finalCurrentUser = CurrentUser;
                    fstore.collection("clients").orderBy("Phone", Query.Direction.ASCENDING).addSnapshotListener
                            (new EventListener<QuerySnapshot>()
                            {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
                                {
                                    boolean Client= false;
                                    Log.d("OnEvent", "");
                                    if(error != null)
                                    {
                                        Log.d(TAG, "Firebase Error");
                                        return;
                                    }
                                    for (DocumentChange dc: value.getDocumentChanges())
                                    {
//                                        Log.d("Phone", (String) dc.getDocument().get("Phone"));
                                        if (!(((String) dc.getDocument().get("Phone")).isEmpty()))
                                        {
                                            String PhoneNumber= (String) dc.getDocument().get("Phone");
                                            if (PhoneNumber.equals(finalCurrentUser.getPhoneNumber()))
                                            {
                                                Log.d("ClientMatch", "");
                                                Client = true;
                                            }
                                        }
                                    }
//                                    Log.d("boolean Client", String.valueOf(Client));
                                    Intent intent;
                                    if(Client)
                                    {
                                        intent = new Intent(LogoScreen.this, ServicesMenu.class);
                                        Log.d("ServicesMenu", "");
                                    }
                                    else
                                    {
                                        intent = new Intent(LogoScreen.this, HomeServiceProv.class);
                                        Log.d("HomeServiceProv", "");
                                    }
                                    intent.putExtra("User", finalCurrentUser);
                                    startActivity(intent);
                                    finish();

                                }
                            });

                }
            }
        },1000);
    }
}