//package com.mariamramadan.link_up;
//
//import static android.content.ContentValues.TAG;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.DownloadManager;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.Patterns;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//import com.google.firebase.firestore.auth.User;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.regex.Pattern;
//
//public class LoginPage extends AppCompatActivity
//{
//
//    EditText Num;
//    EditText Pass;
//    FirebaseAuth fauth;
//    FirebaseFirestore fstore;
//    String UserID;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_page);
//        Num= (EditText) findViewById(R.id.username);
//        Pass= (EditText) findViewById(R.id.password);
//        fauth= FirebaseAuth.getInstance();
//        DocumentReference DocRef= fstore.collection("clients").document(UserID);
//        DocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>()
//        {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error)
//            {
//                String Phone= value.getString("Phone");
//                String Fname= value.getString("FirstName");
//            }
//        });
//        fstore= FirebaseFirestore.getInstance();
//        UserID= fauth.getCurrentUser().getUid();
//        String regexPhone = "^(010|011|012|015)[0-9]{8}$";
//        Pattern pattern = Pattern.compile(regexPhone);
//        Button login= (Button) findViewById(R.id.login);
//        SignUpLink();
//        GuestModeLink();
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));
//        login.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
////                Log.d("ONCLICK", "in on click");
////                db.collection("clients")
////                        .get()
////                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
////                        {
////                            JSONArray clients= new JSONArray();
////                            @Override
////                            public void onComplete(@NonNull Task<QuerySnapshot> task)
////                            {
////                                if (task.isSuccessful())
////                                {
////                                    Log.d("TASKSuccessful", String.valueOf(task.isSuccessful()));
////                                    for (QueryDocumentSnapshot document : task.getResult())
////                                    {
////                                        Log.d("FORLOOP", "in for loop");
////                                        JSONObject client= (JSONObject) document.getData();
////                                        Log.d("DOCUMENT", String.valueOf(document.getData()));
////                                        String PhoneDB=client.optString("Phone");
////                                        String PassDB=client.optString("Password");
////                                        String FnameDB= client.optString("FirstName");
////                                        String LnameDB= client.optString("LastName");
////                                        String EmailDB= client.optString("Email");
////
////                                        Log.d("PhoneDB", PhoneDB );
////                                        Log.d("FnameDB", FnameDB );
////                                        if (Num.getText().toString() != PhoneDB)
////                                        {
////                                            Num.setError("User does not exist");
////                                            Num.requestFocus();
////                                        }
////                                        if (Pass.getText().toString() != PassDB)
////                                        {
////                                            Pass.setError("User does not exist");
////                                            Pass.requestFocus();
////                                        }
////                                        else
////                                        {
////                                            Intent toServiceMenu= new Intent(LoginPage.this, ServicesMenu.class);
////                                            toServiceMenu.putExtra("FirstName", FnameDB);
////                                            toServiceMenu.putExtra("LastName", LnameDB);
////                                            toServiceMenu.putExtra("Email", EmailDB);
////                                            toServiceMenu.putExtra("Phone", PhoneDB);
////                                            startActivity(toServiceMenu);
////                                        }
////
////                                    }
////
////                                }
////
////                                else
////                                {
////                                    Log.d("Error getting documents", null );
////                                }
////                            }
////                        });
//            }
//        });
//    }
//
//
//
//
//    public void SignUpLink()
//    {
//        TextView SignUpLink = (TextView) findViewById(R.id.SignUp);
//        SignUpLink.setTextColor(Color.WHITE);
//        SignUpLink.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Intent ToAccountType = new Intent(LoginPage.this, VerifyPhoneNumber.class);
//                startActivity(ToAccountType);
//            }
//
//        });
//    }
//    public void GuestModeLink()
//    {
//        TextView GuestModeLink = (TextView) findViewById(R.id.Guest);
//        GuestModeLink.setTextColor(Color.WHITE);
//        GuestModeLink.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Intent ToServicesMenu = new Intent(LoginPage.this, ServicesMenu.class);
//                startActivity(ToServicesMenu);
//            }
//
//        });
//    }
//}
//
