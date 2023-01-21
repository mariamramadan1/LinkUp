package com.mariamramadan.link_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class ProfessionMenu extends AppCompatActivity {
    String Category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession_menu);
        GridView menu= findViewById(R.id.ProfessionMenu);
        Category = getIntent().getStringExtra("category");
        ArrayList<String>  Profession = new ArrayList<>();
        ArrayList<Integer> ProfessionImage = new ArrayList<>();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.linkup_background)));

        switch (Category)
        {
            case "Tutoring":
                Profession.add("Mathematics");
                Profession.add("English");
                Profession.add("Biology");
                Profession.add("Shadow\nTeacher");
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                break;
            case "Cosmetics":
                Profession.add("Hair\nStylist");
                Profession.add("MakeUp\nArtist");
                Profession.add("Barbers");
                Profession.add("Esthetician");
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                break;
            case "Home Services":
                Profession.add("Electricians");
                Profession.add("Plumbers");
                Profession.add("Carpenter");
                Profession.add("Gardener");
                Profession.add("Wall\nPainter");
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                break;
            case "Volunteering":
                Profession.add("Elderly\nCare");
                Profession.add("Pet\nCare");
                Profession.add("Sighted\nAssistant");
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);

                break;
            case "Tech Freelancers":
                Profession.add("Application Developer");
                Profession.add("Website\nDeveloper");
                Profession.add("Software\nEngineer");
                Profession.add("Game\nDesign");
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                break;
            case "Art & Design":
                Profession.add("Architects");
                Profession.add("Graphic\nDesigners");
                Profession.add("Interior\nDesigners");
                Profession.add("Fashion\nDesigners");
                Profession.add("Animators");
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                break;
            case "Therapy":
                Profession.add("Counseling");
                Profession.add("Physical\nTherapy");
                ProfessionImage.add(R.drawable.tutoring);
                ProfessionImage.add(R.drawable.tutoring);
                break;

        }


        CustomGrid adapter=new CustomGrid(ProfessionMenu.this, Profession, ProfessionImage);
        menu.setAdapter(adapter);

        menu.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent ToWorkerList = new Intent(ProfessionMenu.this, WorkerListPage.class);
                ToWorkerList.putExtra("Category", Category);
//                Log.d("cat", Category);
                ToWorkerList.putExtra("SubCategory", Profession.get(position));
//                Log.d("sub", Profession.get(position));
                startActivity(ToWorkerList);
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