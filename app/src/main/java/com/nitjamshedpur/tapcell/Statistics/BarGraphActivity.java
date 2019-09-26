package com.nitjamshedpur.tapcell.Statistics;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitjamshedpur.tapcell.R;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarGraphActivity extends AppCompatActivity {

    DatabaseReference mDatabaseReference;
    int cse,ece,ce,mme,me,eee,pie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);
        setTitle("Statistics");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       final BarChart chart = findViewById(R.id.barchart);

        //database reference
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Statistics");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    PlacementData data = dataSnapshot1.getValue(PlacementData.class);
                    cse=data.getCse();
                    ece=data.getEce();
                    ce=data.getCe();
                    me=data.getMe();
                    mme=data.getMme();
                    pie=data.getPie();
                    eee=data.getEee();

                    ArrayList percentPlaced = new ArrayList();

                    percentPlaced.add(new BarEntry(cse, 0));
                    percentPlaced.add(new BarEntry(ece, 1));
                    percentPlaced.add(new BarEntry(me, 2));
                    percentPlaced.add(new BarEntry(eee, 3));
                    percentPlaced.add(new BarEntry(ce, 4));
                    percentPlaced.add(new BarEntry(mme, 5));
                    percentPlaced.add(new BarEntry(pie, 6));

                    ArrayList year = new ArrayList();

                    year.add("CSE");
                    year.add("ECE");
                    year.add("ME");
                    year.add("EE");
                    year.add("CE");
                    year.add("MME");
                    year.add("PIE");

                    BarDataSet bardataset = new BarDataSet(percentPlaced, "% Placed");
                    chart.animateY(1500);
                    BarData barData = new BarData(year, bardataset);
                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    chart.setData(barData);

                 //   Toast.makeText(BarGraphActivity.this, data1, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
