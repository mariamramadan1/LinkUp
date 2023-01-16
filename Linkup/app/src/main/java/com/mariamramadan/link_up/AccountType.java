package com.mariamramadan.link_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccountType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));

        Button Client = (Button) findViewById(R.id.ClientButton);
        Button ServiceProvider = (Button) findViewById(R.id.ServiceProviderButton);

        Client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ClientSignup = new Intent(AccountType.this, SignUpClient.class);
                startActivity(ClientSignup);
            }
        });
        ServiceProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ServiceProviderSignup = new Intent(AccountType.this, SignUpService.class);
                startActivity(ServiceProviderSignup);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}