package com.mariamramadan.link_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumber extends AppCompatActivity {

    EditText Phone,OTP ;
    Button SendCode, Verify;
    FirebaseAuth mauth;
    String VerificationID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);
         Phone= findViewById(R.id.PhoneNum);
         OTP = findViewById(R.id.CodeVerification);
         SendCode = findViewById(R.id.SendCode);
         Verify = findViewById(R.id.Verify);
         mauth= FirebaseAuth.getInstance();
         VerificationID= new String();
         //Log.i("sendcode", String.valueOf(SendCode));
        SendCode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (TextUtils.isEmpty(Phone.getText().toString()))
                {
                    Toast.makeText(VerifyPhoneNumber.this, "Enter a valid Phone Number", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String Number= Phone.getText().toString();
                    SendCode(Number);
                }

            }
        });

        Verify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (TextUtils.isEmpty(OTP.getText().toString()))
                {
                    Toast.makeText(VerifyPhoneNumber.this, "Wrong Code entered", Toast.LENGTH_LONG).show();
                }
                else
                {
                    VerifyCode(OTP.getText().toString());
                }
            }
        });

    }

    private void VerifyCode(String code)
    {
        Log.i("InVerify", "Here in verify function");
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(VerificationID, code);
        SignIn(credential);
    }

    private void SignIn(PhoneAuthCredential credential)
    {
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        Log.i("InSignFunc", "Here on sign in function");
        Log.i("credential", String.valueOf(credential));
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(VerifyPhoneNumber.this, "Sign Up Successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(VerifyPhoneNumber.this, AccountType.class));
                }
//                if (!task.isSuccessful())
//                {
//                    startActivity(new Intent(VerifyPhoneNumber.this, ServicesMenu.class));
//                }

            }
        });
    }
//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//        FirebaseUser CurrentUser= FirebaseAuth.getInstance().getCurrentUser();
//        if (CurrentUser != null)
//        {
//            startActivity(new Intent(VerifyPhoneNumber.this, AccountType.class));
//            finish();
//        }
//    }
    private void SendCode(String Number)
    {
        PhoneAuthOptions options= PhoneAuthOptions.newBuilder(mauth).setPhoneNumber(Number)
                .setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(mCallbacks).build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
        {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null)
                VerifyCode(code);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e)
        {
            Toast.makeText(VerifyPhoneNumber.this, "Verification Failed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token)
        {
            super.onCodeSent(s, token);
            VerificationID= s;
            Log.i("CodeSent", VerificationID);
        }
    };
}