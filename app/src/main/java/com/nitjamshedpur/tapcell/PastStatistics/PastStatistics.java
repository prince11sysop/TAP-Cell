package com.nitjamshedpur.tapcell.PastStatistics;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
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

public class PastStatistics extends AppCompatActivity {

    Button saveChart;
    BarChart chart;
    DatabaseReference mDatabaseReference;
    int year1,year2,year3,year4;
    int year1stats,year2stats,year3stats,year4stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_statistics);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        chart = findViewById(R.id.barchart);
        saveChart=(Button)findViewById(R.id.save);


        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("PastStats");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    StatsModel model=snapshot.getValue(StatsModel.class);
                    year1=model.getYear1();
                    year1stats=model.getYear1stats();
                    year2=model.getYear2();
                    year2stats=model.getYear2stats();
                    year3=model.getYear3();
                    year3stats=model.getYear3stats();
                    year4=model.getYear4();
                    year4stats=model.getYear4stats();

                    ArrayList percentage = new ArrayList();
                    percentage.add(new BarEntry(year1stats, 0));
                    percentage.add(new BarEntry(year2stats, 1));
                    percentage.add(new BarEntry(year3stats, 2));
                    percentage.add(new BarEntry(year4stats, 3));

                    ArrayList<String> year = new ArrayList();

                    year.add(Integer.toString(year1));
                    year.add(Integer.toString(year2));
                    year.add(Integer.toString(year3));
                    year.add(Integer.toString(year4));

                    BarDataSet bardataset = new BarDataSet(percentage, "Past Stats");
                    chart.animateY(1500);
                    BarData data = new BarData(year, bardataset);
                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    chart.setData(data);
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
