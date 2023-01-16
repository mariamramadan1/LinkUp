package com.mariamramadan.link_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Button login= (Button) findViewById(R.id.login);
        SignUpLink();
        GuestModeLink();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToServicesMenu = new Intent(LoginPage.this, ServicesMenu.class);
                startActivity(ToServicesMenu);
            }

        });

    }

    public void SignUpLink() {
        TextView SignUpLink = (TextView) findViewById(R.id.SignUp);
        SignUpLink.setTextColor(Color.WHITE);
        SignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToAccountType = new Intent(LoginPage.this, AccountType.class);
                startActivity(ToAccountType);
            }

        });
    }
    public void GuestModeLink() {
        TextView GuestModeLink = (TextView) findViewById(R.id.Guest);
        GuestModeLink.setTextColor(Color.WHITE);
        GuestModeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToServicesMenu = new Intent(LoginPage.this, ServicesMenu.class);
                startActivity(ToServicesMenu);
            }

        });
    }
}
