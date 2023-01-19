package com.mariamramadan.link_up;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignUpService2 extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String CategoryChosen, SubCategoryChosen, Phone, Fname, Lname, Email, Password;
    Spinner CategoriesSpinner;
    Spinner SubCategoriesSpinner;
    String[] SubCategories;
    String[] Categories;

    public void writeNewUser(String PhoneNum, String email, String FirstName, String LastName, String Pass, String Category, String SubCategory) {
        Map<String, Object> user = new HashMap<>();
        user.put("Phone", PhoneNum);
        user.put("FirstName", FirstName);
        user.put("LastName", LastName);
        user.put("Password", Pass);
        user.put("Email", email);
        user.put("Category", Category);
        user.put("SubCategory", SubCategory);

        db.collection("service providers")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(SignUpService2.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_service2);

        CategoriesSpinner = findViewById(R.id.CategoriesSpinner);
        SubCategoriesSpinner = findViewById(R.id.SubCategoriesSpinner);
        Categories = new String[]{"Tutoring", "Cosmetics", "Home Services", "Volunteering", "Tech Freelancers", "Art & Design", "Therapy"};

        Phone= getIntent().getStringExtra("Phone");
        Email= getIntent().getStringExtra("Email");
        Fname= getIntent().getStringExtra("FName");
        Lname= getIntent().getStringExtra("LName");
        Password= getIntent().getStringExtra("Password");
        Log.d("NAME", Fname);
        Button Submit= (Button) findViewById(R.id.submit);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.selected_item, Categories);
        adapter.setDropDownViewResource(R.layout.drop_down_item);
        CategoriesSpinner.setAdapter(adapter);

        CategoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                CategoryChosen= Categories[i];
                switch (Categories[i]) {
                    case "Tutoring":

                        SubCategories = new String[]{"Mathematics", "English", "Biology", "Shadow Teacher"};
                        break;
                    case "Cosmetics":
                        SubCategories = new String[]{"Hair Stylist", "MakeUp Artist", "Barbers", "Esthetician"};
                        break;
                    case "Home Services":
                        SubCategories = new String[]{"Electricians", "Plumbers", "Carpenter", "Gardener", "Wall Painter"};
                        break;
                    case "Volunteering":
                        SubCategories = new String[]{"Elderly Care", "Pet Care", "Sighted Assistant"};
                        break;
                    case "Tech Freelancers":
                        SubCategories = new String[]{"Application Developer", "Website Developer", "Software Engineer", "Game Design"};
                        break;
                    case "Art & Design":
                        SubCategories = new String[]{"Architects", "Graphic Designers", "Interior Designers", "Fashion Designers", "Animators"};
                        break;
                    case "Therapy":
                        SubCategories = new String[]{"Counseling", "Physical Therapy"};
                        break;
                }

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SignUpService2.this, R.layout.selected_item, SubCategories);
                adapter2.setDropDownViewResource(R.layout.drop_down_item);
                SubCategoriesSpinner.setAdapter(adapter2);
                SubCategoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        SubCategoryChosen= SubCategories[i];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView)
                    {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        Submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                writeNewUser(Phone, Email, Fname, Lname, Password, CategoryChosen, SubCategoryChosen);
                Intent toHome= new Intent(SignUpService2.this, HomeServiceProv.class);
                startActivity(toHome);
            }
        });
    }
}
