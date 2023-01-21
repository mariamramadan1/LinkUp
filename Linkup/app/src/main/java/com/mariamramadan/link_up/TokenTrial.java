package com.mariamramadan.link_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class TokenTrial extends AppCompatActivity {
    TextView text;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_trial);
        text= (TextView) findViewById(R.id.text_trial);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            Toast.makeText(TokenTrial.this, "unsuccessful", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Get new FCM registration token
                        token = task.getResult();
                        Toast.makeText(TokenTrial.this, "your token is" + token, Toast.LENGTH_SHORT).show();
                        text.setText(token);
                        Log.d("token", token);
                    }
                });

        FirebaseMessaging firebaseMessaging= FirebaseMessaging.getInstance();
        firebaseMessaging.subscribeToTopic("new_user_forms").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                String msg= "subscribed";
                if (!task.isSuccessful())
                {
                    msg = "subscribed failed";
                    Toast.makeText(TokenTrial.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}