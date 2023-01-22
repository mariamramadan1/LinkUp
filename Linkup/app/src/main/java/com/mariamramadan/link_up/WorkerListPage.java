package com.mariamramadan.link_up;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

class Worker
{
    String Category;
    String Email;
    String Fname;
    String Lname;
    String Number;
    String SubCategory;
    String Rating;

}

public class WorkerListPage extends AppCompatActivity
{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<CustomWorkerList> FinalWorkersList=new ArrayList<>();
    ArrayList<Worker> WorkersArray= new ArrayList<>();
    ArrayList<Worker> WorkerInfo= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_list_page);
        String Category= getIntent().getStringExtra("Category");
        String SubCategory= getIntent().getStringExtra("SubCategory");
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        fstore.collection("service providers").orderBy("Phone", Query.Direction.ASCENDING).addSnapshotListener
                (new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
                    {
                        if(error != null)
                        {
                            Log.e(TAG, "Firebase Error");
                            return;
                        }
                        for (DocumentChange dc: value.getDocumentChanges())
                        {
                            Worker worker= new Worker();
                            worker.Number= (String) dc.getDocument().get("Phone");
                            worker.Fname= (String) dc.getDocument().get("FirstName");
                            worker.Lname= (String) dc.getDocument().get("LastName");
                            worker.Email= (String) dc.getDocument().get("Email");
                            worker.Category= (String) dc.getDocument().get("Category");
                            worker.SubCategory= (String) dc.getDocument().get("SubCategory");
                            WorkersArray.add(worker);
                        }

                        for (int i=0; i < WorkersArray.size(); i++)
                        {
                            Log.d("cond", String.valueOf((WorkersArray.get(i).Category).equals(Category) &&
                                    (WorkersArray.get(i).SubCategory).equals(SubCategory)));
//                            Log.d("subcat", String.valueOf((WorkersArray.get(i).SubCategory).equals(SubCategory)));
                            if ((WorkersArray.get(i).Category).equals(Category) &&
                                (WorkersArray.get(i).SubCategory).equals(SubCategory))
                            {
//                                Log.d("fn", WorkersArray.get(i).Fname + " " + WorkersArray.get(i).Lname);
                                WorkerInfo.add(WorkersArray.get(i));
                                FinalWorkersList.add(new CustomWorkerList(R.drawable.tutoring,
                                        WorkersArray.get(i).Fname + " "+ WorkersArray.get(i).Lname, "0"));
                            }
                        }
//                        Log.d("workers", String.valueOf(FinalWorkersList));
                        CustomWorkerAdapter WorkerArrayAdapter = new CustomWorkerAdapter(WorkerListPage.this, FinalWorkersList);
                        ListView WorkersListView = findViewById(R.id.WorkerListView);
                        WorkersListView.setAdapter(WorkerArrayAdapter);

                        WorkersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {                                            //listener for when a list item is clicked
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                            {
                                Intent ToProfileServiceClientView= new Intent
                                        (WorkerListPage.this, ProfileServiceClientView.class);
                                ToProfileServiceClientView.putExtra("FirstName", WorkerInfo.get(i).Fname);
                                ToProfileServiceClientView.putExtra("LastName", WorkerInfo.get(i).Lname);
                                ToProfileServiceClientView.putExtra("Email", WorkerInfo.get(i).Email);
                                ToProfileServiceClientView.putExtra("Number", WorkerInfo.get(i).Number);
                                ToProfileServiceClientView.putExtra("Category", WorkerInfo.get(i).Category);
                                ToProfileServiceClientView.putExtra("SubCategory", WorkerInfo.get(i).SubCategory);
                                startActivity(ToProfileServiceClientView);
                            }

                        });

                    }
                });

    }
}