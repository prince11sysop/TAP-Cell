package com.nitjamshedpur.tapcell.Highlights;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitjamshedpur.tapcell.R;

import java.util.ArrayList;
import java.util.List;

public class HighlightsMainActivity extends AppCompatActivity {

    DatabaseReference mMessagesDatabaseReference;
    RecyclerView recyclerView;

    GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlights_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Highlights");

        mMessagesDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Highlights");
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);

        final List<Highlights> friendlyMessages = new ArrayList<>();


        layoutManager = new GridLayoutManager(this,1);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Highlights studentDetails = dataSnapshot.getValue(Highlights.class);
                    friendlyMessages.add(studentDetails);
                }

               HighlightsAdapter adapter = new HighlightsAdapter(getApplicationContext(), friendlyMessages);
               recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}