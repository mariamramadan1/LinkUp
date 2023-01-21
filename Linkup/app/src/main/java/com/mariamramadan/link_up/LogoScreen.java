package com.mariamramadan.link_up;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


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
                //CurrentUser = null;
                if (CurrentUser == null)
                {
                    Intent intent = new Intent(LogoScreen.this, VerifyPhoneNumber.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(LogoScreen.this, ServicesMenu.class);
                    intent.putExtra("User", CurrentUser );
                    startActivity(intent);
                    finish();
                }
            }
        },1000);

    }
}