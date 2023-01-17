package com.mariamramadan.link_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpClient extends AppCompatActivity {

    EditText email, Password, ConfPassword, PhoneNum;
    Button submit;
    String PhonePattern = "^(010|011|012|015)[0-9]{8}$";
    ProgressDialog progressDialog;

    FirebaseAuth mauth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_client);

         email = (EditText) findViewById(R.id.EmailClient);
         Password = (EditText) findViewById(R.id.PassClient);
        ConfPassword = (EditText) findViewById(R.id.ConfPassClient);
        PhoneNum = (EditText) findViewById(R.id.PhoneNumClient);


        submit = (Button) findViewById(R.id.SubmitClient);
         progressDialog = new ProgressDialog(this);
         mauth = FirebaseAuth.getInstance();
         mUser = mauth.getCurrentUser();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });



    }
//
//    private void PerformAuth() {
//        String inputEmail = email.getText().toString();
//        String inputPassword = Password.getText().toString();
//        String ConfirmPassword = ConfPassword.getText().toString();
//        String inputPhone = PhoneNum.getText().toString();
//
//        if(!inputPhone.matches(PhonePattern))
//        {
//            PhoneNum.setError("Email is wrong");
//        }
//        else if(TextUtils.isEmpty(inputPhone))
//        {
//            Password.setError("Enter a proper password with at least 8 characters");
//        }
//        else if(!inputPassword.equals(ConfirmPassword))
//        {
//            ConfPassword.setError("Passwords do not match");
//        }
//        else
//        {
//            progressDialog.setMessage("Completing sign up.....");
//            progressDialog.setTitle("Sign up");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//            mauth.createUserWithEmailAndPassword(inputPhone,inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(task.isSuccessful())
//                    {
//                        progressDialog.dismiss();
//                        ToClientHome();
//                        Toast.makeText(SignUpClient.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        progressDialog.dismiss();
//                        Toast.makeText(SignUpClient.this, " "+ task.getException(), Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            });
//        }
//
//
//    }
//
//    private void ToClientHome() {
//        Intent ClientHome = new Intent(SignUpClient.this, ServicesMenu.class);
//        ClientHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(ClientHome);
//
//    }

}