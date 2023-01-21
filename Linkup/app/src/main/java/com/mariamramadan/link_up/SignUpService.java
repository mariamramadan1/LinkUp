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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpService extends AppCompatActivity {

    EditText email, Password, ConfPassword, fname, lname;
    String inputEmail, inputFname, inputLname, inputPass, inputConfPass, PhoneNum ;
    Button Next;
    ProgressDialog progressDialog;
    String EmailRegex= "^(.+)@(gmail|yahoo|hotmail|aucegypt){1}(.com|.edu|.org|.eg){1}$";
    Pattern PatternEmail = Pattern.compile(EmailRegex);
    boolean ValidInput= true;
    Matcher EmailMatcher;
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
                inputConfPass= ConfPassword.getText().toString();
                EmailMatcher = PatternEmail.matcher(inputEmail);
                if (TextUtils.isEmpty(inputFname))
                {
                    fname.requestFocus();
                    fname.setError("Cannot leave this field empty");
                    ValidInput=false;
                }
                if (TextUtils.isEmpty(inputLname))
                {
                    lname.requestFocus();
                    lname.setError("Cannot leave this field empty");
                    ValidInput=false;
                }
                if (!EmailMatcher.matches())
                {
                    email.requestFocus();
                    email.setError("Enter a valid email address");
                    ValidInput=false;
                }
                if (!(inputConfPass.equals(inputPass)) || TextUtils.isEmpty(inputPass) || TextUtils.isEmpty(inputConfPass))
                {
                    ConfPassword.requestFocus();
                    ConfPassword.setError("Passwords do not match");
                    ValidInput= false;
                }
                else
                {
                    if (ValidInput)
                    {
                        Intent toPage2 = new Intent(SignUpService.this, SignUpService2.class );
                        toPage2.putExtra("FName",inputFname);
                        toPage2.putExtra("LName",inputLname);
                        toPage2.putExtra("Email",inputEmail);
                        toPage2.putExtra("Password",inputPass);
                        toPage2.putExtra("Phone",PhoneNum);
                        startActivity(toPage2);
                    }
                }

            }

        });

    }

}