package com.nitjamshedpur.tapcell.Recruiters;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitjamshedpur.tapcell.MainActivity;
import com.nitjamshedpur.tapcell.R;

import java.util.ArrayList;

public class RecruitersMainActivity extends AppCompatActivity {

    ArrayList<DataModel> friendlyMessages;
    ListView listView;
    private static CustomAdapter adapter;
    DatabaseReference mMessagesDatabaseReference;
    ChildEventListener mChildEventListener=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiters_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Recruiters");

       //retriving data from firebase
        mMessagesDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Recruiters");
        listView = (ListView) findViewById(R.id.list);
        friendlyMessages = new ArrayList<>();

        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    DataModel studentDetails = dataSnapshot.getValue(DataModel.class);
                    friendlyMessages.add(studentDetails);
                }
                adapter = new CustomAdapter(friendlyMessages, getApplicationContext());
                listView.setAdapter(adapter);
                listView.setTextFilterEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel = friendlyMessages.get(position);

                Snackbar.make(view, dataModel.getName() + "\n" + "Total Selects:" + dataModel.getVersion_number() + "\nEligibility: Min 8.0 CGPA", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });*/

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
