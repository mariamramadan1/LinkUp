package com.mariamramadan.link_up;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SignUpService extends AppCompatActivity {

    EditText email, Password, ConfPassword, fname, lname;
    String inputEmail, inputFname, inputLname, inputPass, PhoneNum ;
    Button Next;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_service);
        email = (EditText) findViewById(R.id.EmailService);
        fname = (EditText) findViewById(R.id.FNameService);
        lname = (EditText) findViewById(R.id.LNameService);
        Password = (EditText) findViewById(R.id.PassService);
        ConfPassword = (EditText) findViewById(R.id.ConfPassService);
        //Log.d("PHONENUMBERClient", getIntent().getStringExtra("Phone"));
        PhoneNum= getIntent().getStringExtra("Phone");
        Next = (Button) findViewById(R.id.Next);
        progressDialog = new ProgressDialog(this);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                inputEmail = email.getText().toString();
                inputFname = fname.getText().toString();
                inputLname = lname.getText().toString();
                inputPass = Password.getText().toString();
                Intent toPage2 = new Intent(SignUpService.this, SignUpService2.class );
                toPage2.putExtra("FName",inputFname);
//                Log.d("NAME1", inputFname);
                toPage2.putExtra("LName",inputLname);
                toPage2.putExtra("Email",inputEmail);
                toPage2.putExtra("Password",inputPass);
                toPage2.putExtra("Phone",PhoneNum);
                startActivity(toPage2);
            }

        });

    }

}