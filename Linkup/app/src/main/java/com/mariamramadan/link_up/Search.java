package com.mariamramadan.link_up;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

interface MyCallback {
    void onCallback(ArrayList<Worker> WorkersArray);
}
public class Search extends AppCompatActivity {
    ListView WorkerQuery;
    EditText SearchBox;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Worker> WorkersArray= new ArrayList<>();
    ArrayList<CustomWorkerList> WorkerList= new ArrayList<>();
    CustomWorkerAdapter mAdapter;

    public void FetchWorkerData(MyCallback myCallback)
    {

        db.collection("service providers").orderBy("Rating", Query.Direction.ASCENDING).addSnapshotListener
                (new EventListener<QuerySnapshot>()
                {
                    Menu menu;
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
                        myCallback.onCallback(WorkersArray);
                    }

                });
    }
    private void Filter(String searched, ArrayList<Worker> WorkersArray) {

        for (Worker worker: WorkersArray)
        {
            if (worker.Fname.equalsIgnoreCase(searched.toString()) || worker.Lname.equalsIgnoreCase(searched.toString())
                    || worker.SubCategory.equalsIgnoreCase(searched.toString()))
            {
                WorkerList.add(new CustomWorkerList(R.drawable.tutoring, worker.Fname +" "+ worker.Lname, worker.Rating, worker.SubCategory));

            }
        }

        WorkerQuery.setAdapter(new CustomWorkerAdapter(Search.this, WorkerList));
        mAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchBox= (EditText) findViewById(R.id.SearchBox);
        WorkerQuery= (ListView) findViewById(R.id.SearchMenu);
        FetchWorkerData(new MyCallback()
        {
            @Override
            public void onCallback(ArrayList<Worker> WorkersArray)
            {
                for (Worker worker: WorkersArray)
                {
                    WorkerList.add(new CustomWorkerList(R.drawable.tutoring, worker.Fname +" "+ worker.Lname, worker.Rating, worker.SubCategory));
                }
                mAdapter= new CustomWorkerAdapter(getApplicationContext(), WorkerList);
                WorkerQuery.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                SearchBox.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {

                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {

                    }

                    @Override
                    public void afterTextChanged(Editable editable)
                    {
                        WorkerList.clear();

                        if (editable.toString().isEmpty())
                        {
                            WorkerQuery.setAdapter(new CustomWorkerAdapter(Search.this, WorkerList));
                            mAdapter.notifyDataSetChanged();
                        } else {
                            Filter(editable.toString(), WorkersArray);
                        }

                    }
                });

            }
        });



    }
}